package FightAnimations;

import GameObject.SpriteSheet;
import Builders.FrameBuilder;
import GameObject.Frame;
import java.util.HashMap;

public class TlalocsStormAttack extends StaticPlayerAttackAnimation {
    
    private int animationFrameCount = 18; // Total number of sprite frames in the animation

    public TlalocsStormAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX + 80, posY - 100, 999, "ATTACK");
    }
    
    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            
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
                new FrameBuilder(spriteSheet.getSprite(2, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 12)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 0), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 1), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 2), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 3), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(3, 2), 4)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .build()
            });
        }};
    }
    
    @Override
    public void update() {
        // Use the parent AnimatedSprite's update for frame progression
        super.update();
        
        // Check if animation has completed all frames
        // AnimatedSprite will loop, so we check if it looped OR reached last frame
        if (hasAnimationLooped || getCurrentFrameIndex() >= animationFrameCount - 1) {
            if (!isComplete) {
                isComplete = true;
                onComplete();
            }
        }
    }
    
    @Override
    protected void updateFrame(int frame) {
        // Not used - AnimatedSprite handles frame progression
    }
    
    @Override
    protected void onComplete() {
        System.out.println("Tlaloc's Storm animation complete!");
    }
}