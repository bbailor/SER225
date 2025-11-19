package FightAnimations;

import Engine.GraphicsHandler;
import GameObject.AnimatedSprite;
import GameObject.SpriteSheet;
import Level.Map;

public abstract class StaticEnemyAttackAnimation extends AnimatedSprite {
    
    protected float posX, posY;
    protected boolean isComplete;
    protected int totalFrames;
    protected int frameCounter;
    protected Map map;
    
    /**
     * Creates a static attack animation
     * @param spriteSheet The sprite sheet containing attack frames
     * @param posX X position where animation plays
     * @param posY Y position where animation plays
     * @param duration Number of game frames the animation should last (or high value if using AnimatedSprite delays)
     * @param startingAnimationName The animation to play (e.g., "ATTACK")
     */
    public StaticEnemyAttackAnimation(SpriteSheet spriteSheet, float posX, float posY, 
                          int duration, String startingAnimationName) {
        super(spriteSheet, posX, posY, startingAnimationName);
        this.posX = posX;
        this.posY = posY;
        this.totalFrames = duration;
        this.frameCounter = 0;
        this.isComplete = false;
    }
   
    public void setMap(Map map) {
        this.map = map;
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
        
        frameCounter++;
        
        // Let subclass handle frame progression (if needed)
        updateFrame(frameCounter);
        
        // Check if animation is complete
        if (frameCounter >= totalFrames) {
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
        // Default: do nothing
    }
    
    public boolean isComplete() {
        return isComplete;
    }
    
    public void reset(float newPosX, float newPosY) {
        this.posX = newPosX;
        this.posY = newPosY;
        this.frameCounter = 0;
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
        return frameCounter;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (!isComplete) {
            if (map != null) {
                // Draw with camera adjustment (for overworld animations)
                float drawX = x - map.getCamera().getX();
                float drawY = y - map.getCamera().getY();

                graphicsHandler.drawImage(
                    currentFrame.getImage(),
                    Math.round(drawX),
                    Math.round(drawY),
                    currentFrame.getWidth(),
                    currentFrame.getHeight()
                );
            } else {
                // Draw without camera adjustment (for battle animations)
                super.draw(graphicsHandler);
            }
        }
    }
    
    /**
     * Get progress as percentage (0.0 to 1.0)
     */
    public float getProgress() {
        return Math.min(1.0f, (float)frameCounter / totalFrames);
    }
}