package FightAnimations;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

/**
 * Skeleton attack animation that moves from enemy to player
 */
public class KnifeOfLifeAttack extends PlayerProjectileAttackAnimation {
    
    private static final int FRAMES_UNTIL_STOP = 11;
    private float stoppedX, stoppedY;
    private boolean hasStopped = false;
    
    /**
     * Creates a skeleton attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (enemy position)
     * @param startY Starting Y position (enemy Y position)
     * @param targetX Target X position (player position)
     * @param targetY Target Y position (player Y position)
     * @param duration Number of game frames the animation should take to travel
     */
    public KnifeOfLifeAttack(SpriteSheet spriteSheet, float startX, float startY, 
                          float targetX, float targetY, int duration) {
        super(spriteSheet, startX, startY, targetX, targetY, duration, "ATTACK");
    }
    
    @Override
    protected void updatePosition(float progress) {
        // Check which animation frame we're on (not travel frame)
        int currentAnimFrame = getCurrentFrameIndex();
        
        if (currentAnimFrame < FRAMES_UNTIL_STOP) {
            // Still moving - use linear interpolation
            float newX = startX + (targetX - startX) * 1.4f * progress;
            float newY = startY;
            setLocation(newX, newY);
            
            // Store position when we reach frame 5
            if (currentAnimFrame == FRAMES_UNTIL_STOP - 1) {
                stoppedX = newX;
                stoppedY = newY;
                hasStopped = true;
            }
        } else if (hasStopped) {
            // Frame 5+ - stay at stopped position
            setLocation(stoppedX, stoppedY);
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // Adjust these based on your sprite sheet layout
            // Assuming the attack animation frames are in rows 0-1, columns 0-3
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 4), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 9)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 9)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 4), 10)
                        .withScale(2)
                        .build()
            });
        }};
    }
}