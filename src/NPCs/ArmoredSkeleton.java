package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Utils.Point;
import java.util.HashMap;

public class ArmoredSkeleton extends NPC {
    protected String enemyType;
    public ArmoredSkeleton(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheet(ImageLoader.load("Enemies/armored_skeleton.png"), 32, 32),
            "STAND_RIGHT"
        );

        // Configure stats
        this.entity.setMaxHealth(20);
        this.entity.setBaseAttack(0);
        
        // Add attacks
        this.addAttack("BoneThrow", 20, "SkeletonAttack", 3.0);
        this.addAttack("Slash", 60, "ArmoredSkeletonAttack", 2.5);

        enemyType = " An Armored Skeleton";
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

    public String getEnemyType()
    {
        return enemyType;
    }
}
