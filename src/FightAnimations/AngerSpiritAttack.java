package FightAnimations;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;
import java.util.HashMap;

/**
 * Anger Spirit attack animation that moves from enemy to player
 */
public class AngerSpiritAttack extends EnemyProjectileAttackAnimation {
    
    private static final int FRAMES_UNTIL_STOP = 5;
    private float stoppedX, stoppedY;
    private boolean hasStopped = false;
    
    /**
     * Creates an anger spirit attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (enemy position)
     * @param startY Starting Y position (enemy Y position)
     * @param targetX Target X position (player position)
     * @param targetY Target Y position (player Y position)
     * @param duration Number of game frames the animation should take to travel
     */
    public AngerSpiritAttack(SpriteSheet spriteSheet, float startX, float startY, 
                             float targetX, float targetY, int duration) {
        super(spriteSheet, startX, startY, targetX, targetY, duration, "ATTACK");
    }
    
    @Override
    protected void updatePosition(float progress) {
        int currentAnimFrame = getCurrentFrameIndex();
        
        if (currentAnimFrame < FRAMES_UNTIL_STOP) {
            float newX = startX + (targetX - startX) * progress;
            float newY = startY + (targetY - startY) * progress;
            setLocation(newX, newY);
            
            if (currentAnimFrame == FRAMES_UNTIL_STOP - 1) {
                stoppedX = newX;
                stoppedY = newY;
                hasStopped = true;
            }
        } else if (hasStopped) {
            setLocation(stoppedX, stoppedY);
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // Reuse the same layout as SpiritAttack, but with recolored frames
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 8).withScale(2).build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 8).withScale(2).build()
            });
        }};
    }
}
