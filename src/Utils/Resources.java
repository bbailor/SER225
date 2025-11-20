package Utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public abstract class Resources {

    private Resources() {}
    
    public static Font PRESS_START;
    public static File PICKUP_SFX = new File("Resources/Sounds/Effects/pickUpItem.wav");
    public static File STEP_SFX = new File("Resources/Sounds/Effects/footstep.wav");
    public static File ATTACK_SFX = new File("Resources/Sounds/Effects/weaponSlash.wav");
    
    public static File BATTLE_MUSIC = new File("Resources/Sounds/Music/danceOfKnights8Bit.wav");
    public static File END_MUSIC = new File("Resources/Sounds/Music/happyEndingSong.wav");
    public static File TUTORIAL_MAP_MUSIC = new File("Resources/Sounds/Music/tutorialMapSong.wav");
    public static File MAP_ONE_MUSIC = new File("Resources/Sounds/Music/map1DenialSong.wav");
    public static File MAP_TWO_MUSIC = new File("Resources/Sounds/Music/map2AngerSong.wav");
    public static File MAP_THREE_MUSIC = new File("Resources/Sounds/Music/map3BargainingSong.wav");
    public static File MAP_FOUR_MUSIC = new File("Resources/Sounds/Music/map4DepressionSong.wav");
    public static File MAP_FIVE_MUSIC = new File("Resources/Sounds/Music/map5AcceptanceSong.wav");
    
    static {
        try {
            PRESS_START = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/PressStart2P-Regular.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
