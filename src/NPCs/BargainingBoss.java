package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;
import java.util.HashMap;

public class BargainingBoss extends NPC {
    public BargainingBoss(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheet(ImageLoader.load("Bosses/BargainingBoss.png"), 128, 128),
            "STAND_RIGHT"
        );
    }
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(GameObject.SpriteSheet spriteSheet) {
        Frame standRight = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withBounds(0, 0, 90, 123)
                .build();

        Frame standLeft = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(0, 0, 90, 123)
                .build();

        HashMap<String, Frame[]> map = new HashMap<>();
        map.put("STAND_RIGHT", new Frame[] { standRight });
        map.put("STAND_LEFT",  new Frame[] { standLeft  });
        map.put("idle",        new Frame[] { standRight }); //Added idle animation(though its not animated yet)
        return map;
    }
}
