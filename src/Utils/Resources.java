package Utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public abstract class Resources {

    private Resources() {}
    
    public static Font press_start;
    
    static {
        try {
            press_start = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/PressStart2P-Regular.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
