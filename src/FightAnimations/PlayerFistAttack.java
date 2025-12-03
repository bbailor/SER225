package FightAnimations;

import java.util.HashMap;
import java.util.random.RandomGenerator;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
public class PlayerFistAttack extends PlayerProjectileAttackAnimation {
    
    // private static final int FRAMES_UNTIL_STOP = 5;
    // private float stoppedX, stoppedY;
    // private boolean hasStopped = false;
    
    /**
     * Creates a player fist attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (enemy position)
     * @param startY Starting Y position (enemy Y position)
     * @param targetX Target X position (player position)
     * @param targetY Target Y position (player Y position)
     * @param duration Number of game frames the animation should take to travel
     */
    public PlayerFistAttack(SpriteSheet spriteSheet, float startX, float startY,
                          float targetX, float targetY, int duration) {
        super(spriteSheet, startX, startY, targetX, targetY, duration, "ATTACK");
    }
    
    @Override
    protected void updatePosition(float progress) {
        setLocation(targetX + (startX - targetX) * .9f, startY + (targetY - startY) * .9f);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // Adjust these based on your sprite sheet layout
            // Assuming the attack animation frames are in rows 0-1, columns 0-3
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, RandomGenerator.getDefault().nextInt(8)), RandomGenerator.getDefault().nextInt(5) + 1)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, RandomGenerator.getDefault().nextInt(8)), RandomGenerator.getDefault().nextInt(5) + 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, RandomGenerator.getDefault().nextInt(8)), RandomGenerator.getDefault().nextInt(5) + 1)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, RandomGenerator.getDefault().nextInt(8)), RandomGenerator.getDefault().nextInt(5) + 1)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, RandomGenerator.getDefault().nextInt(8)), RandomGenerator.getDefault().nextInt(5) + 1)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .build(),

            });
        }};
    }
}
