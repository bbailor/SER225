package Utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Resources {

    private Resources() {}
    
    public static Font PRESS_START;
    // We use supplier here to refresh the stream
    public static Supplier<InputStream> PICKUP_SFX = () -> Globals.loadResource("Resources/Sounds/Effects/pickUpItem.wav");
    public static Supplier<InputStream> STEP_SFX = () -> Globals.loadResource("Resources/Sounds/Effects/footstep.wav");
    public static Supplier<InputStream> ATTACK_SFX = () -> Globals.loadResource("Resources/Sounds/Effects/weaponSlash.wav");
    
    public static Supplier<InputStream> BATTLE_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/danceOfKnights8Bit.wav");
    public static Supplier<InputStream> END_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/happyEndingSong.wav");
    public static Supplier<InputStream> TUTORIAL_MAP_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/tutorialMapSong.wav");
    public static Supplier<InputStream> MAP_ONE_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/map1DenialSong.wav");
    public static Supplier<InputStream> MAP_TWO_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/map2AngerSong.wav");
    public static Supplier<InputStream> MAP_THREE_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/map3BargainingSong.wav");
    public static Supplier<InputStream> MAP_FOUR_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/map4DepressionSong.wav");
    public static Supplier<InputStream> MAP_FIVE_MUSIC = () -> Globals.loadResource("Resources/Sounds/Music/map5AcceptanceSong.wav");
    
    static {
        try {

            PRESS_START = Font.createFont(Font.TRUETYPE_FONT, Globals.class.getResourceAsStream("/PressStart2P-Regular.ttf"));
            if (PRESS_START == null) {
                PRESS_START = Font.createFont(Font.TRUETYPE_FONT, Globals.loadResource("PressStart2P-Regular.ttf"));
            }
            System.out.println("Hello");
            System.out.println(PRESS_START);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
