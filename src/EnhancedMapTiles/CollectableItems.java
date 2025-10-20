package EnhancedMapTiles;

import java.awt.Color;
import java.awt.Point;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntity;
import Level.Player;
import Level.Script;
import Level.TileType;
import Scripts.Interactable;
public class CollectableItems extends MapEntity{

    public CollectableItems(float x, float y, Frame frame) 
    {
        super(x, y, frame);
        this.setInteractScript(new Interactable());
    }
 
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    // public void collect()
    // {
    //     System.out.println("collected item");
    // }

    // public boolean isCollected()
    // {
    //     return true;
    // }
    public void update(Player player)
    {
        super.update();
    }

    
}

