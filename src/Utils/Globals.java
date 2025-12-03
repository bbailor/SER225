package Utils;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import javax.swing.filechooser.FileSystemView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import Engine.Config;
import Level.Item;
import Level.ItemStack;
import Level.Map;
import Level.Player;
import Level.Script;
import Level.Weapon;
import Utils.Serializers.ItemSerializer;
import Utils.Serializers.ItemStackSerializer;
import Utils.Serializers.MapSerializer;
import Utils.Serializers.PlayerSerializer;
import Utils.Serializers.ScriptSerializer;

public abstract class Globals {
    
    public static final SoundThreads SOUND_SYSTEM = new SoundThreads();
    /** Base hover color for ui's */
    public static final Color HOVER_COLOR = TailwindColorScheme.amber600;
    /** Base unfocused hover color for ui's */
    public static final Color UNFOCUS_HOVER_COLOR = TailwindColorScheme.sky800;
    /** Base select color for ui's */
    public static final Color SELECT_COLOR = TailwindColorScheme.sky400;
    /** UI interact cooldowns */
    public static final int KEYBOARD_CD = 10;
    /** The Gson serializer */
    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Item.class, new ItemSerializer())
        .registerTypeAdapter(Weapon.class, new ItemSerializer())
        .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
        .registerTypeAdapter(Script.class, new ScriptSerializer())
        .registerTypeAdapter(Map.class, new MapSerializer())
        .registerTypeAdapter(Player.class, new PlayerSerializer())
        .excludeFieldsWithoutExposeAnnotation()
        .serializeNulls()
        .disableJdkUnsafe()
        // .enableComplexMapKeySerialization()
        // .serializeNulls()
        .setPrettyPrinting()
        .create();
    /** Path for any game data that is creating at runtime */
    public static final Path GAMEDATAPATH = FileSystemView.getFileSystemView().getDefaultDirectory().toPath().resolve(".gnomeo");
    /** Save path */
    public static final Path SAVEPATH = GAMEDATAPATH.resolve("saves");
    public static final boolean DEBUG = true;
    public static final int BATTLE_TRACK_NUMBER = 1;
    public static final int MUSIC_TRACK = 2;
    // public static final int STORY_TRACK = 3;
    public static final int EFFECTS_SOUNDS = 3;

    public static boolean saveToFile(SaveData data, int index) {
        String json = GSON.toJson(data);
        Path file = SAVEPATH.resolve("save" + index + ".json");
        // if (index >= Globals.saves.size()) {
        //     var _saves = new ArrayList<Path>(index+1);
        //     _saves.addAll(Globals.saves);
        //     Globals.saves = _saves;
        // }
        try {
            Files.writeString(file, json);
            Globals.saves.put(index, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static SaveData loadSave(int index) {
        if (!saves.containsKey(index) || saves.get(index) == null) {
            return null;
        }
        try {
            return GSON.fromJson(Files.readString(saves.get(index)), SaveData.class);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream loadResource(String name) {
        var first = Globals.class.getResourceAsStream(name);
        if (first == null) {
            return Globals.class.getResourceAsStream(name.replace(Config.RESOURCES_PATH, "/").replace(Config.MAP_FILES_PATH, "/"));
        }
        return first;
    }

    private static java.util.Map<Integer, Path> saves = new HashMap<>();
    
    private Globals() {}

    static {
        if (!Files.exists(GAMEDATAPATH)) {
            try {
                Files.createDirectories(GAMEDATAPATH);
                Files.createDirectories(SAVEPATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (var file : SAVEPATH.toFile().listFiles((dir, filename) -> filename.endsWith(".json"))) {
            saves.put(Integer.parseInt(file.getName().replace(".json", "").replace("save", "")), file.toPath());
        }
    }
}
