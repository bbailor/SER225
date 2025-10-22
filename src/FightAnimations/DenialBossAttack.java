package FightAnimations;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

/**
 * Spirit attack animation that moves from enemy to player
 */
public class DenialBossAttack extends StaticEnemyAttackAnimation {
    
    private static final int FRAMES_UNTIL_STOP = 48;
    private float stoppedX, stoppedY;
    private boolean hasStopped = false;
    private int boundX;
    private int boundY;
    
    /**
     * Creates a spirit attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (enemy position)
     * @param startY Starting Y position (enemy Y position)
     * @param targetX Target X position (player position)
     * @param targetY Target Y position (player Y position)
     * @param duration Number of game frames the animation should take to travel
     * 
     */
    public DenialBossAttack(SpriteSheet spriteSheet, float startX, float startY, 
                          float targetX, float targetY, int duration) { 
        super(spriteSheet, startX, startY, targetX, targetY, duration, "ATTACK");
        int boundX = 512;
        int Boundy = 512;
    }
    
    @Override
    protected void updatePosition(float progress) {
        // Check which animation frame we're on (not travel frame)
        int currentAnimFrame = getCurrentFrameIndex();
        
        if (currentAnimFrame < FRAMES_UNTIL_STOP) {
            // Still moving - use linear interpolation
            float newX = startX + (targetX - startX) * progress;
            float newY = startY + (targetY - startY) * progress;
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
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(1).withBounds(0,0,boundX, boundY)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 7), 8)
                        .withScale(1)
                        .build(),

                
                new FrameBuilder(spriteSheet.getSprite(1, 0), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 7), 8)
                        .withScale(1)
                        .build(),

                
                new FrameBuilder(spriteSheet.getSprite(2, 0), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 7), 8)
                        .withScale(1)
                        .build(),
               
                
                new FrameBuilder(spriteSheet.getSprite(3, 0), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 7), 8)
                        .withScale(1)
                        .build(),
                
                
                new FrameBuilder(spriteSheet.getSprite(4, 0), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(4, 7), 8)
                        .withScale(1)
                        .build(),
                

                new FrameBuilder(spriteSheet.getSprite(5, 0), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 2), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 3), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 4), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 5), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 6), 8)
                        .withScale(1)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(5, 7), 8)
                        .withScale(1)
                        .build(),
            });
        }};
    }
}