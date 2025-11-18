package Maps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import EnhancedMapTiles.CollectableItem;
import GameObject.Sprite;
import Level.Item;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Colors;
import Utils.Globals;
import Utils.Point;
import Utils.SoundThreads.Type;

// Represents the map that is used as a background for the main menu and credits menu screen
public class TitleScreenMap extends Map {

    private Sprite cat;

    public TitleScreenMap() {
        super("title_screen_map.txt", new CommonTileset());
        Point catLocation = getMapTile(8, 5).getLocation().subtractX(6).subtractY(7);
        cat = new Sprite(ImageLoader.loadSubImage("gnome.png", Colors.MAGENTA, 0, 0, 31, 63));
        cat.setScale(3);
        cat.setLocation(catLocation.x, catLocation.y);
        }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        cat.draw(graphicsHandler);
    }
}
