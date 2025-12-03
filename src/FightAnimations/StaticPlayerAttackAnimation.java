package FightAnimations;
import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;
import Utils.Globals;
import Utils.Resources;
import Utils.SoundThreads.Type;

public abstract class StaticPlayerAttackAnimation extends AnimatedSprite {
    
    protected float posX, posY;
    protected boolean isComplete;
    protected int totalFrames;
    protected int currentFrame;
    
    /**
     * Creates a static attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param posX X position where animation plays
     * @param posY Y position where animation plays
     * @param duration Number of game frames the animation should last (or high value if using AnimatedSprite delays)
     * @param startingAnimationName The animation to play (e.g., "ATTACK")
     */
    protected StaticPlayerAttackAnimation(SpriteSheet spriteSheet, float posX, float posY, 
                          int duration, String startingAnimationName) {
        super(spriteSheet, posX, posY, startingAnimationName);
        this.posX = posX;
        this.posY = posY;
        this.totalFrames = duration;
        this.currentFrame = 0;
        this.isComplete = false;
    }
    
    @Override
    public void update() {
        // Call parent to handle AnimatedSprite frame animation
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
        
        currentFrame++;
        
        // Let subclass handle frame progression (if needed)
        updateFrame(currentFrame);
        
        // Check if animation is complete
        if (currentFrame >= totalFrames) {
            isComplete = true;
            onComplete();
        }
    }
    
    /**
     * Update the animation frame based on current frame count
     * Override this to implement custom frame sequences
     * @param frame Current frame number
     */
    protected abstract void updateFrame(int frame);
    
    /**
     * Called when animation completes
     * Override to add custom behavior (e.g., sound effects)
     */
    protected void onComplete() {
        Globals.SOUND_SYSTEM.play(Type.SFX, Globals.EFFECTS_SOUNDS, Resources.ATTACK_SFX.get());
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public void reset(float newPosX, float newPosY) {
        this.posX = newPosX;
        this.posY = newPosY;
        this.currentFrame = 0;
        this.isComplete = false;
        setLocation(posX, posY);
        setCurrentAnimationFrameIndex(0);
    }
    
    /**
     * Get the total duration in frames
     */
    public int getTotalFrames() {
        return totalFrames;
    }
    
    /**
     * Get current frame count in animation
     */
    public int getCurrentFrameCount() {
        return currentFrame;
    }
    
    /**
     * Get progress as percentage (0.0 to 1.0)
     */
    public float getProgress() {
        return Math.min(1.0f, (float)currentFrame / totalFrames);
    }
}
