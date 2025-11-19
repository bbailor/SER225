package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;
import java.util.HashMap;

public class AngerBoss extends NPC {
    public AngerBoss(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheet(ImageLoader.load("Bosses/AngerBoss.png"), 120, 120),
            "STAND_RIGHT"
        );

        this.entity.setMaxHealth(25);
        this.entity.setBaseAttack(0);
        
        this.entity.addAttack("AngerBossAttack", 50, "AngerBossAttack", 5);
        this.entity.addAttack("Explosion", 100, "BargainingBossAttack", 7.0);
        autoBattleEnabled = false;
    }
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(GameObject.SpriteSheet spriteSheet) {
        Frame standRight = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(2.6f)
                .withBounds(30, 25, 70, 80)
                .build();

        Frame standLeft = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(2.6f)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(30, 25, 70, 80)
                .build();

        HashMap<String, Frame[]> map = new HashMap<>();
        map.put("STAND_RIGHT", new Frame[] { standRight });
        map.put("STAND_LEFT",  new Frame[] { standLeft  });
        map.put("idle",        new Frame[] { standRight }); //Added idle animation(though its not animated yet)
        return map;
    }
}
