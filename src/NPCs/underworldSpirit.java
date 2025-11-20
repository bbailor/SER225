package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import GameObject.SpriteSheetHorizontal;
import GameObject.ImageEffect;
import Level.NPC;
import Utils.Point;

import java.util.HashMap;


public class underworldSpirit extends NPC {

    public underworldSpirit(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheetHorizontal(ImageLoader.load("underworldSpirit.png"), 32, 32),
            "STAND_RIGHT"
        );
        
        autoBattleEnabled = false;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(GameObject.SpriteSheet spriteSheet) {
        Frame standRight = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withBounds(8, 8, 16, 24)
                .build();

        Frame standLeft = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(8, 8, 16, 24)
                .build();

        HashMap<String, Frame[]> map = new HashMap<>();
        map.put("STAND_RIGHT", new Frame[] { standRight });
        map.put("STAND_LEFT",  new Frame[] { standLeft  });
        map.put("idle",        new Frame[] { standRight }); // Added idle animation
        return map;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
