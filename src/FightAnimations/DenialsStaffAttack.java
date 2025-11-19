package FightAnimations;

import java.util.HashMap;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;

public class DenialsStaffAttack extends StaticPlayerAttackAnimation {
    
    private int animationFrameCount = 1;

    public DenialsStaffAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX - 256, posY, 999, "ATTACK");
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
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
        System.out.println("Denials Staff animation complete!");
    }

}
