package EnhancedMapTiles;

import java.awt.Color;

import Engine.GraphicsHandler;
import Level.Item;
import Level.ItemStack;
import Level.MapEntity;
import Level.Player;
import Scripts.CollectableScript;
import Utils.Point;

public class CollectableItem extends MapEntity {

    protected ItemStack stack;

    public CollectableItem(float x, float y, ItemStack stack) {
        super(x, y, stack.getItem().getFrames("world"));
        this.stack = stack;
        this.setInteractScript(new CollectableScript(stack));
    }

    public CollectableItem(Point pos, ItemStack stack) {
        this(pos.x, pos.y, stack);
    }

    public CollectableItem(float x, float y, Item item) {
        this(x, y, new ItemStack(item));
    }

    public CollectableItem(Point pos, Item item) {
        this(pos, new ItemStack(item));
    }
 
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    @Override
    public Point getLocation() {
        return new Point(this.x, this.y);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        var frames = stack.getItem().getFrames("world");
        if (frames == null) {
            this.animations.put("DEFAULT", frames);
        }
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

