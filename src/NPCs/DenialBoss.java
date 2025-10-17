package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;  // <-- add this
import Level.NPC;
import Utils.Point;
import java.util.HashMap;

public class DenialBoss extends NPC {
    public DenialBoss(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheet(ImageLoader.load("Bosses/DenialBoss.png"), 120, 120),
            "STAND_RIGHT"
        );
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(GameObject.SpriteSheet spriteSheet) {
        Frame standRight = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withBounds(8, 8, 120, 120)
                .build();

        Frame standLeft = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)  // <-- mirror for left
                .withBounds(8, 8, 120, 120)
                .build();

        HashMap<String, Frame[]> map = new HashMap<>();
        map.put("STAND_LEFT", new Frame[] { standRight });
        map.put("STAND_RIGHT",  new Frame[] { standLeft  });
        return map;
    }
}
