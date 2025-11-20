package FightAnimations;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

/**
 * Power Stone attack animation
 */
public class powerStoneAttack extends StaticPlayerAttackAnimation {

    private int animationFrameCount = 48;

    /**
     * Creates a power stone attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param posX X position where animation plays
     * @param posY Y position where animation plays
     */
    public powerStoneAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX, posY, 999, "ATTACK");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // Adjust these based on your sprite sheet layout
            // Assuming the attack animation frames are in rows 0-1, columns 0-3
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 7), 1)
                        .withScale(2)
                        .build(),


                new FrameBuilder(spriteSheet.getSprite(1, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 7), 1)
                        .withScale(2)
                        .build(),


                new FrameBuilder(spriteSheet.getSprite(2, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 7), 1)
                        .withScale(2)
                        .build(),


                new FrameBuilder(spriteSheet.getSprite(3, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 7), 1)
                        .withScale(2)
                        .build(),


                new FrameBuilder(spriteSheet.getSprite(4, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 7), 1)
                        .withScale(2)
                        .build(),


                new FrameBuilder(spriteSheet.getSprite(5, 0), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 3), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 4), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 5), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 6), 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 7), 1)
                        .withScale(2)
                        .build(),
            });
       }};
    }

    @Override
    public void update() {
        // Use the parent AnimatedSprite's update for frame progression
        super.update();

        // Check if animation has completed all frames
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
        // Animation complete
    }
}