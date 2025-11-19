package FightAnimations;

import GameObject.SpriteSheet;
import Builders.FrameBuilder;
import GameObject.Frame;
import java.util.HashMap;

public class AngerBossAttack extends StaticEnemyAttackAnimation {
    
    private int animationFrameCount = 13; // Total number of sprite frames in the animation

    public AngerBossAttack(SpriteSheet spriteSheet, float posX, float posY) {
        super(spriteSheet, posX, posY, 999, "ATTACK");
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
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 1), 6)
                        .withScale(2)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 2), 6)
                        .withScale(2)
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
    }
}