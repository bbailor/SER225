package Items;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import EnhancedMapTiles.CollectableItem;
import GameObject.Rectangle;
import Level.Item;
import Utils.Point;

public class KnifeOfLife extends CollectableItem {

    protected Rectangle bounds;
    public KnifeOfLife(Point location) {
        super(location.x, location.y, Item.ItemList.knife_of_life);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        // Draw bounds in red for debugging
        //drawBounds(graphicsHandler, Color.RED);
    }

}
