package FightAnimations;

import GameObject.SpriteSheet;
import Builders.FrameBuilder;
import GameObject.Frame;
import java.util.HashMap;

public class DepressionBossAttack extends StaticEnemyAttackAnimation {
    
    private int animationFrameCount = 13; // Total number of sprite frames in the animation

    public DepressionBossAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX, posY - 200, 999, "ATTACK");
    }
    
    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            
            put("ATTACK", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 3), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 0), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 1), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 2), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(2, 3), 6)
                        .withScale(1.5f)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 12)
                        .withScale(1.5f)
                        .build(),
            });
        }};
    }
    
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
        // Not used - AnimatedSprite handles frame progression
    }
    
    @Override
    protected void onComplete() {
        System.out.println("Tlaloc's Storm animation complete!");
    }
}