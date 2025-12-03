package FightAnimations;

import java.util.HashMap;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

/**
 * Overworld animation that uses the BargainingBoss attack sprite sheet
 * Displays a spirit-like effect at the player's position
 *
 * Usage Example:
 * <pre>
 * // In your script
 * BargainingBossOverworldAnimation animation = new BargainingBossOverworldAnimation(
 *     player.getX(),
 *     player.getY(),
 *     48  // Duration in frames (48 = 0.8 seconds)
 * );
 * map.addOverworldAnimation(animation);
 * </pre>
 */
public class BargainingBossOverworldAnimation extends StaticEnemyAttackAnimation {

    private int animationFrameCount = 17; // Total number of sprite frames in the animation

    /**
     * Creates a BargainingBoss attack animation at the specified position
     * @param x X position to display animation
     * @param y Y position to display animation
     * @param duration How long the animation lasts in frames (48 recommended)
     */
    public BargainingBossOverworldAnimation(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX - 43, posY - 270, 999, "ATTACK");
    }

    // /**
    //  * Override to use the BargainingBoss attack sprite sheet
    //  */
    // private static SpriteSheet createBargainingBossSpriteSheet() {
    //     try {
    //         return new SpriteSheet(ImageLoader.load("Enemies/BargainingBossSmite.png"), 60, 200);
    //     } catch (Exception e) {
    //         System.err.println("Failed to load BargainingBossSmite.png: " + e.getMessage());
    //         // Fallback to solid color
    //         return new SpriteSheet(
    //             Utils.ImageUtils.createSolidImage(new java.awt.Color(200, 100, 255, 200), 60, 200),
    //             60, 200
    //         );
    //     }
    // }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 3)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 0), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 2)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 2)
                        .withScale(2)
                        .build(),
            });
        }};
    }
    // @Override
    // public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    //     // Load the BargainingBoss sprite sheet
    //     SpriteSheet bargainingBossSheet = createBargainingBossSpriteSheet();

    @Override
    public void update() {
        super.update();
        
        if (hasAnimationLooped || getCurrentFrameIndex() >= animationFrameCount - 1) {
            if (!isComplete) {
                isComplete = true;
                onComplete();
            }
        }
    }

    @Override
    protected void updateFrame(int frame) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'updateFrame'");
    }

    //     HashMap<String, Frame[]> animations = new HashMap<>();
    //     animations.put("ATTACK", new Frame[] {
    //         // Row 0 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(0, 7), 1).withScale(1).build(),

    //         // Row 1 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(1, 7), 1).withScale(1).build(),

    //         // Row 2 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(2, 7), 1).withScale(1).build(),

    //         // Row 3 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(3, 7), 1).withScale(1).build(),

    //         // Row 4 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(4, 7), 1).withScale(1).build(),

    //         // Row 5 (8 frames)
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 0), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 1), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 2), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 3), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 4), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 5), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 6), 1).withScale(1).build(),
    //         new FrameBuilder(bargainingBossSheet.getSprite(5, 7), 1).withScale(1).build()
    //     });
    // 
    //     return animations;
    // }
}
