package FightAnimations;

import java.util.HashMap;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

public class DenialsStaffAttack extends StaticPlayerAttackAnimation {
    
    private int animationFrameCount = 22;

    public DenialsStaffAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX - 270, posY, 999, "ATTACK");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 0), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(6, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(7, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(8, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(9, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(10, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(11, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(12, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(13, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(14, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(15, 0), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(16, 0), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(17, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(18, 0), 7)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(19, 0), 8)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(20, 0), 8)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(21, 0), 8)
                        .withScale(2)
                        .build()
            });
        }};
    }

    @Override
    public void update() {
        // Use the parent AnimatedSprite's update for frame progression
        super.update();
        
        // Check if animation has completed all frames
        // AnimatedSprite will loop, so we check if it looped OR reached last frame
        if (hasAnimationLooped || getCurrentFrameIndex() >= animationFrameCount - 1) {
            if (!isComplete) {
                isComplete = true;
                onComplete();
            }
        }
    }

    @Override
    protected void updateFrame(int frame) {
        // Not used - AnimatedSprite handles frame progression
    }
    
    @Override
    protected void onComplete() {
        System.out.println("Denials Staff animation complete!");
    }

}
