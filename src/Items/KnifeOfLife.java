package Items;

import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import EnhancedMapTiles.CollectableItems;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.Map;
import Scripts.Interactable;
import Utils.Point;

public class KnifeOfLife extends CollectableItems {

    protected Rectangle bounds;
    public KnifeOfLife(Point location) {
        super(location.x, location.y, 
            new FrameBuilder(ImageLoader.load("weapons//knifeOfLife.png"))
                .withScale(2.0f)
                .withBounds(8, 0, 16, 32)  // 2x the sprite size
                .build()
        );

        this.setInteractScript(new Interactable());
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        // Draw bounds in red for debugging
        //drawBounds(graphicsHandler, Color.RED);
    }

}
