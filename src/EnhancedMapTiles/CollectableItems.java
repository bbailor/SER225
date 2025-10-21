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
import Level.Item;
import Level.ItemStack;
import Level.MapEntity;
import Level.Player;
import Level.Script;
import Level.TileType;
import Scripts.Interactable;
/**
 * Class handles collectable items while they are on the map
 * 
 * CollectableItem class handles collectable items after they are picked up.
 */
public class CollectableItems extends MapEntity{
    protected String name;
    protected Item item;
    protected int maxStack;

    public CollectableItems(float x, float y, Frame frame, Item item) {
        super(x, y, frame);
        this.setInteractScript(new Interactable(this));
        this.item = item;
    }
    
    public CollectableItems(float x, float y, Frame frame) {
        super(x, y, frame);
        this.setInteractScript(new Interactable(this));
    }

    public CollectableItems(Point pos, Frame frame) {
        super(pos.x, pos.y, frame);
        this.setInteractScript(new Interactable(this));
    }
 
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public void setItem(Item i)
    {
        this.item = i;
    }
    public Item getItem()
    {
        return item;
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

    public String getName()
    {
        return this.name;
    }
    
}

