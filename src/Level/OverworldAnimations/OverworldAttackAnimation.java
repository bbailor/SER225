package Level.OverworldAnimations;

import Engine.GraphicsHandler;
import GameObject.AnimatedSprite;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Builders.FrameBuilder;
import Level.Map;

import java.util.HashMap;

/**
 * Base class for displaying attack animations over the player's position in the overworld.
 * Subclass this to create custom animations with specific sprite sheets.
 *
 * Usage Example:
 * <pre>
 * // Use a specific animation subclass (like BargainingBossOverworldAnimation)
 * BargainingBossOverworldAnimation animation = new BargainingBossOverworldAnimation(
 *     player.getX(),
 *     player.getY(),
 *     48  // Duration in frames
 * );
 * map.addOverworldAnimation(animation);
 * </pre>
 */
public class OverworldAttackAnimation extends AnimatedSprite {

    private boolean isComplete;
    private int frameCounter;
    private int duration;
    private Map map;

    /**
     * Creates an attack animation at the specified position
     * @param x X position to display animation (typically player.getX())
     * @param y Y position to display animation (typically player.getY())
     * @param duration How long the animation lasts in frames (60 = 1 second)
     */
    public OverworldAttackAnimation(float x, float y, int duration) {
        super(createSpriteSheet(), x, y, "ATTACK");
        this.duration = duration;
        this.frameCounter = 0;
        this.isComplete = false;
    }

    /**
     * Creates a sprite sheet for animations
     * You can replace this with actual sprite sheets from your resources
     */
    private static SpriteSheet createSpriteSheet() {
        // For now, create a simple colored square as placeholder
        // Replace with: ImageLoader.load("AttackEffects.png")
        return new SpriteSheet(
            Utils.ImageUtils.createSolidImage(new java.awt.Color(255, 100, 100, 200), 32, 32),
            32, 32
        );
    }

    @Override
    public void update() {
        if (isComplete) {
            return;
        }

        // Update animation frames
        super.update();

        // Increment frame counter and check completion
        frameCounter++;
        if (frameCounter >= duration) {
            isComplete = true;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (!isComplete && map != null) {
            // Adjust for camera position (similar to GameObject)
            float drawX = x - map.getCamera().getX();
            float drawY = y - map.getCamera().getY();

            // Draw the current frame at camera-adjusted position
            graphicsHandler.drawImage(
                currentFrame.getImage(),
                Math.round(drawX),
                Math.round(drawY),
                currentFrame.getWidth(),
                currentFrame.getHeight()
            );
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        HashMap<String, Frame[]> animations = new HashMap<>();
        animations.put("ATTACK", createAnimation(spriteSheet));
        return animations;
    }

    private Frame[] createAnimation(SpriteSheet spriteSheet) {
        // Default placeholder animation - override loadAnimations() for custom animations
        return new Frame[] {
            new FrameBuilder(spriteSheet.getSprite(0, 0), 10)
                .withScale(2.0f)
                .build()
        };
    }
}
