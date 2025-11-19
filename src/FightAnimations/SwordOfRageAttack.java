package FightAnimations;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

/**
 * Spirit attack animation that moves from enemy to player
 */
public class SwordOfRageAttack extends EnemyProjectileAttackAnimation {
    
    private static final int FRAMES_UNTIL_STOP = 5;
    private float stoppedX, stoppedY;
    private boolean hasStopped = false;
    
    /**
     * Creates a spirit attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (enemy position)
     * @param startY Starting Y position (enemy Y position)
     * @param targetX Target X position (player position)
     * @param targetY Target Y position (player Y position)
     * @param duration Number of game frames the animation should take to travel
     */
    public SwordOfRageAttack(SpriteSheet spriteSheet, float startX, float startY, 
                          float targetX, float targetY, int duration) {
        super(spriteSheet, startX, startY, targetX, targetY, duration, "ATTACK");
        startX = targetX;
        startY = targetY;
        spriteSheet.setSpriteHeight(63);
        spriteSheet.setSpriteWidth(63);
    }
    
    @Override
    protected void updatePosition(float progress) {
        
        setLocation(startX + (targetX - startX) * .9f, startY + (targetY - startY) * .9f);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            // Adjust these based on your sprite sheet layout
            // Assuming the attack animation frames are in rows 0-1, columns 0-3
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 5)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 4), 20)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 5), 5)
                        .withScale(2)
                        .build(),
                 new FrameBuilder(spriteSheet.getSprite(0, 6), 5)
                        .withScale(2)
                        .build(),
                 new FrameBuilder(spriteSheet.getSprite(0, 7), 5)
                        .withScale(2)
                        .build(),
                 new FrameBuilder(spriteSheet.getSprite(0, 8), 5)
                        .withScale(2)
                        .build(),
              
            });
        }};
    }
}
