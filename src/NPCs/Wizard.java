package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import GameObject.SpriteSheetHorizontal;
import Level.NPC;
import Utils.Point;

import java.util.HashMap;

// Wizard NPC class
public class Wizard extends NPC {

    public Wizard(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheetHorizontal(ImageLoader.load("Wizard.png"), 32, 32),
            "STAND_RIGHT"
        );
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // --- RIGHT IDLE ---
            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 6), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 7), 12).withScale(3).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 8), 12).withScale(3).build()
            });

            // --- LEFT IDLE (mirrored) ---
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 6), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 7), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 8), 12).withScale(3).withImageEffect(GameObject.ImageEffect.FLIP_HORIZONTAL).build()
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
