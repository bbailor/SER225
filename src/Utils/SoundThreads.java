package Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundThreads {
    public static final Settings settings = new Settings();
    protected static int _id = 0;
    protected int id;
    Map<Integer, Track> tracks;
    protected Mixer sound_device;
    
    public SoundThreads() {
        this(AudioSystem.getMixer(null));
    }
        
    public SoundThreads(Mixer mixer) {
        this.tracks = new HashMap<>();
        this.sound_device = mixer;
        this.id = ++_id;
        try {
            this.sound_device.open();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play(Type type, int track_number, File file) {
        try {
            this.play(type, track_number, Files.newInputStream(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(Type type, int track_number, InputStream stream) {
        try {
            if (tracks.containsKey(track_number)) {
                tracks.get(track_number).setSound(stream, type);
            } else {
                tracks.put(track_number, new Track(stream, type, track_number));
            }

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(int track_number, File file) {
        this.play(Type.Music, track_number, file);
    }

    public Track getTrack(int track_number) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        if (!this.tracks.containsKey(track_number)) {
            var track = new Track(this.id);
            this.tracks.put(track_number, track);
            return track;
        }
        return this.tracks.get(track_number);
    }

    // Non-blocking version that returns null if track doesn't exist
    public Track getTrackIfExists(int track_number) {
        return this.tracks.get(track_number);
    }

    public class Track implements AutoCloseable {
        protected final State state;
        protected ReentrantLock state_lock = new ReentrantLock();
        protected AudioFormat format;
        protected final int thread_id;
        protected final SourceDataLine line;
        protected final Thread thread;
        // would need to be a byte array or list, rather not
        // protected static final Map<File, AudioInputStream> cache = new HashMap<>();

        // sets or calculates loop point based off of byte position
        public void setLoopPoint(float start, float end, boolean bySecond) {
            // new Thread(() -> {
                if (start > end && end != -1) {
                    throw new RuntimeException("start > end");
                }
                this.state_lock.lock();
                this.state.loop_point_start = (int) start;
                this.state.loop_point_end = (int) end;
                // gemma gave me this calculation (second to frame time)
                if (bySecond) {
                    this.state.loop_point_start = (int) (this.state.loop_point_start * this.format.getFrameRate() * this.format.getFrameSize());
                    this.state.loop_point_end = end == - 1 ? -1 : (int) (this.state.loop_point_end * this.format.getFrameRate() * this.format.getFrameSize());
                }
                state.loop_data = new ArrayList<>();
                this.state_lock.unlock();

            // }).start();
        }

        // clear the loop data
        public void clearLoop() {
            this.state_lock.lock();
            this.state.loop_point_start = 0;
            this.state.loop_point_end = -1;
            this.state.loop_data = null;
            this.state_lock.unlock();
        }
        
        //sets the sound file and volume type
        protected void setSound(InputStream stream, Type type) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            this.state_lock.lock();
            if (type != null) {
                state.type = type;
            }
            if (this.line != null && this.line.isOpen()) {
                this.line.close();
            }
            // close system resources
            if (this.state.stream != null) {
                this.state.stream.close();
            }
            this.clearLoop();
            this.state.position = 0;
            // open the new file
            if (stream != null) {
                this.state.stream = AudioSystem.getAudioInputStream(new BufferedInputStream(stream));
            } else {
                this.state.stream = new AudioInputStream(new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                    @Override
                    public int available() throws IOException {
                        return 0;
                    }
                }, new AudioFormat(48000, 16, 1, true, false), 0);
            }
            // this.state.stream.mark(Integer.MAX_VALUE);
            this.format = state.stream.getFormat();
            if (this.line != null) {
                // restart with any new format
                this.line.open(format);
                this.line.start();
            }
            this.state_lock.unlock();
        }
        
        // set sound while keeping the same type
        public void setSound(File file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            this.setSound(Files.newInputStream(file.toPath()));
        }

        public void setSound(InputStream stream) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            this.setSound(stream, null);
        }

        protected FloatControl getGainControl() { // Gain float controller for the line
            // Try MASTER_GAIN first, fall back to VOLUME if not supported
            if (this.line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                return (FloatControl) this.line.getControl(FloatControl.Type.MASTER_GAIN);
            } else if (this.line.isControlSupported(FloatControl.Type.VOLUME)) {
                return (FloatControl) this.line.getControl(FloatControl.Type.VOLUME);
            }
            return null;
        }

        protected void updateGain(Type type) {
            var gain = this.getGainControl();
            if (gain == null) {
                // Control not supported on this line, skip volume adjustment
                return;
            }
            // un-normalization -> min - normalized_value * (max - min) according to gemmma3
            var value = (gain.getMinimum()) + (settings.volumes.getOrDefault(type, 1f) * settings.master_volume) * -(gain.getMinimum());
            gain.setValue(value);
        }

        private class runner implements Runnable {

            @Override
            public void run() {
                var bytes = new byte[Track.this.state.play_buf_size]; // buffer
                int loop_index = 0;
                boolean in_loop = false;
                try {
                    while (Track.this.state.running) {
                        try {
                            Track.this.state_lock.lock();
                            Track.this.updateGain(Track.this.state.type); // set any vol changes

                            // Check pause FIRST before any audio processing
                            if (Track.this.state.paused) {
                                Track.this.state_lock.unlock();
                                // Sleep while paused to reduce CPU usage
                                Thread.sleep(10);
                                continue;
                            }

                            if (Track.this.state.stream.available() <= 0 && !in_loop) {
                                Track.this.state_lock.unlock();
                                // Sleep to avoid busy-waiting when no audio available
                                Thread.sleep(10);
                                continue;
                            }
                            if (in_loop) {
                                if (Track.this.state.loop_data == null) {
                                    in_loop = false;
                                    Track.this.state_lock.unlock();
                                    continue;
                                }
                                // end buf pos
                                var len = Math.min(loop_index + Track.this.state.play_buf_size, Track.this.state.loop_data.size());

                                // Validate that we have data to write
                                var writeLen = len - loop_index;
                                if (writeLen <= 0) {
                                    // Race condition detected - reset loop
                                    loop_index = 0;
                                    Track.this.state_lock.unlock();
                                    continue;
                                }

                                for (int i = loop_index; i < len; ++i) {
                                    bytes[i - loop_index] = Track.this.state.loop_data.get(i);
                                }
                                Track.this.line.write(bytes, 0, writeLen);
                                loop_index = len;
                                Track.this.state.position = Track.this.state.loop_point_start + loop_index;
                                if (len >= Track.this.state.loop_data.size()) {
                                    loop_index = 0;
                                }
                                Track.this.state_lock.unlock();
                                continue;
                            }

                            var actual_read = Track.this.state.stream.read(bytes, 0, Track.this.state.play_buf_size);
                            Track.this.state.position += actual_read;
                            Track.this.line.write(bytes, 0, actual_read);
                            if (Track.this.state.loop_data != null) {
                                int possible_start = (Track.this.state.position - Track.this.state.loop_point_start);
                                if (Track.this.state.position >= Track.this.state.loop_point_start) {
                                    int added = 0;
                                    for (; added < actual_read - (possible_start < Track.this.state.play_buf_size ? possible_start : 0); ++added) {
                                        // this.state.loop_data[added+loop_index] = bytes[added + (possible_start < this.state.play_buf_size ? possible_start : 0)];
                                        Track.this.state.loop_data.add(bytes[added + (possible_start < Track.this.state.play_buf_size ? possible_start : 0)]);
                                        if (Track.this.state.loop_data.size() == Track.this.state.loop_point_end - Track.this.state.loop_point_start && Track.this.state.loop_point_end != -1) {
                                            break;
                                        }
                                    }
                                    // loop_index += added;
                                }
                                if (Track.this.state.stream.available() <= 0 || (Track.this.state.loop_data.size() >= (Track.this.state.loop_point_end - Track.this.state.loop_point_start) && Track.this.state.loop_point_end != -1)) {
                                    in_loop = true;
                                    loop_index = 0;
                                }
                            }
                            Track.this.state_lock.unlock();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        public Track(File file, Type type, int thread_id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this(Files.newInputStream(file.toPath()), type, thread_id);
        }
        public Track(InputStream stream, Type type, int thread_id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this.thread_id = thread_id;
            this.state_lock.lock();
            this.state = new State();
            this.setSound(stream, type);
            this.line = (SourceDataLine) SoundThreads.this.sound_device.getLine(new DataLine.Info(SourceDataLine.class, format));
            this.line.open(format);
            this.updateGain(type);
            this.line.start();
            this.thread = new Thread(new runner(), "Sound Threads " + id + " Track - " + thread_id);
            this.thread.start();
            this.state_lock.unlock();
        }

        public Track(int thread_id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this((InputStream) null, Type.Music, thread_id);
        }

        public Track(File file) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this(file, Type.Music, 0);
        }

        public Track(File file, int thread_id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this(file, Type.Music, thread_id);
        }

        public Track(Type type) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this((InputStream) null, type, 0);
        }

        public Track(Type type, int thread_id) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
            this((InputStream) null, type, thread_id);
        }

        public void pause() {
            this.state_lock.lock();
            // Direct assignment - no lock needed for boolean
            this.state.paused = true;
            this.state_lock.unlock();
        }

        public void resume() {
            // Direct assignment - no lock needed for boolean
            this.state_lock.lock();
            this.state.paused = false;
            this.state_lock.unlock();
        }

        @Override
        public void close() throws Exception {
            this.state_lock.lock();
            this.state.running = false;
            this.state.stream.close();
            this.clearLoop();
            this.line.close();
            this.state.stream.close();
            this.state_lock.unlock();
        }

        public static class State {
            public volatile boolean running = true;
            public volatile boolean paused = false;
            public int position = 0;
            protected int loop_point_start = 0;
            protected int loop_point_end = -1;
            protected List<Byte> loop_data = null;
            protected AudioInputStream stream;
            protected Type type;
            protected int play_buf_size = 512;
        }
    }

    public enum Type {
        SFX,
        Ambience,
        Music
    }

    public static class Settings {
        public Map<Type, Float> volumes = new EnumMap<>(Type.class);
        public float master_volume = 1;
        private Settings() {}

    }
}
