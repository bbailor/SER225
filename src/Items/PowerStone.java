package Items;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Item;
import Level.ItemStack;

public class PowerStone extends Item {
    
    public PowerStone() {
        super("powerStone", "Mysterious stone", "A strange stone.\n It appears to hold some sort of power.", 1);
        var worldSheet = new SpriteSheet(ImageLoader.load("powerStone.png"), 252, 251);
        FrameBuilder[] worldFrames = new FrameBuilder[] {
            new FrameBuilder(worldSheet.getSprite(0, 0))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 1))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 2))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 3))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 4))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 5))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 6))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 7))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 8))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 9))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 10))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 11))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 12))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 13))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 14))
                .withScale(0.3f),
            new FrameBuilder(worldSheet.getSprite(0, 15))
                .withScale(0.3f)
        };
        addAnimation("world", worldFrames);
        FrameBuilder[] inventoryFrames = new FrameBuilder[] {
            new FrameBuilder(worldSheet.getSprite(0, 0))
        };
        addAnimation("inventory", inventoryFrames);
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return targetedEntity.getHealth() > 0;
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        targetedEntity.heal(-20);
        stack.removeItem();
    }
    
}
