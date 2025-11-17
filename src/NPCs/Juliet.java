package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheetHorizontal;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;

import java.util.HashMap;

public class Juliet extends NPC {

    public Juliet(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheetHorizontal(ImageLoader.load("Juliet.png"), 32, 32),
            "STAND_RIGHT"
        );

        autoBattleEnabled = false;  // Juliet never triggers combat
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{

            // --- RIGHT IDLE ---
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 18)
                    .withScale(3)
                    .withBounds(8, 8, 16, 24)
                    .build()
            });

            // --- LEFT IDLE ---
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 18)
                    .withScale(3)
                    .withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(8, 8, 16, 24)
                    .build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
