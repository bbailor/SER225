package FightAnimations;

import Engine.GraphicsHandler;
import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;

/**
 * Base class for player attack animations in battle
 * Handles movement from start position to target position
 */
public abstract class PlayerProjectileAttackAnimation extends AnimatedSprite {
    
    protected float startX, startY;
    protected float targetX, targetY;
    protected boolean isComplete;
    protected int totalFrames;
    protected int currentTravelFrame;
    
    /**
     * Creates an attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param startX Starting X position (typically enemy position)
     * @param startY Starting Y position
     * @param targetX Target X position (typically player position)
     * @param targetY Target Y position
     * @param duration Number of game frames the animation should take to travel
     * @param startingAnimationName The animation to play (e.g., "ATTACK")
     */
    public PlayerProjectileAttackAnimation(SpriteSheet spriteSheet, float startX, float startY, 
                          float targetX, float targetY, int duration, String startingAnimationName) {
        super(spriteSheet, startX, startY, startingAnimationName);
        this.startX = targetX;
        this.startY = targetY;
        this.targetX = startX;
        this.targetY = startY;
        this.totalFrames = duration;
        this.currentTravelFrame = 0;
        this.isComplete = false;
    }
    
    @Override
    public void update() {
        // Update the animation frames
        super.update();
        
        if (isComplete) {
            return;
        }
        
        // Safety check
        if (totalFrames <= 0) {
            System.err.println("Warning: totalFrames is " + totalFrames + ", marking animation complete");
            isComplete = true;
            onComplete();
            return;
        }
        
        // Calculate progress (0.0 to 1.0)
        currentTravelFrame++;
        float progress = Math.min(1.0f, (float)currentTravelFrame / totalFrames);
        
        // Allow subclasses to customize movement
        updatePosition(progress);
        
        // Check if animation reached target
        if (progress >= 1.0f) {
            isComplete = true;
            onComplete();
        }
    }
    
    /**
     * Update the position based on progress
     * Override this for custom movement patterns (arc, zigzag, etc.)
     * @param progress Value from 0.0 to 1.0 representing completion
     */
    protected void updatePosition(float progress) {
        // Default: linear interpolation
        float newX = startX + (targetX - startX) * progress;
        float newY = startY + (targetY - startY) * progress;
        setLocation(newX, newY);
    }
    
    /**
     * Called when animation completes
     * Override to add custom behavior (e.g., explosion effect, sound)
     */
    protected void onComplete() {
        // Default: do nothing
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public void reset(float newStartX, float newStartY, float newTargetX, float newTargetY) {
        this.startX = newStartX;
        this.startY = newStartY;
        this.targetX = newTargetX;
        this.targetY = newTargetY;
        this.currentTravelFrame = 0;
        this.isComplete = false;
        setLocation(startX, startY);
        setCurrentAnimationFrameIndex(0);
    }
    
    /**
     * Get the total duration in frames
     */
    public int getTotalFrames() {
        return totalFrames;
    }
    
    /**
     * Get current frame in travel animation
     */
    public int getCurrentTravelFrame() {
        return currentTravelFrame;
    }
    
    /**
     * Get progress as percentage (0.0 to 1.0)
     */
    public float getProgress() {
        return Math.min(1.0f, (float)currentTravelFrame / totalFrames);
    }
}