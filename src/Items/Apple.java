package Items;

import java.util.Arrays;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Item;
import Level.ItemStack;

public class Apple extends Item {
    
    public Apple() {
        super("apple", "Apple", "A tasty red apple\nHeals 15 health", 20);
        var sheet = new SpriteSheet(ImageLoader.load("item_imgs/apple.png"), 32, 32);
        FrameBuilder[] frames = new FrameBuilder[2];
        Arrays.parallelSetAll(frames, i -> new FrameBuilder(sheet.getSprite(0, i), (int)(60 / 1.5))
            .withScale(1.25f)
            // .build()
        );
        this.addAnimation("default", frames);
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return targetedEntity.getHealth() != targetedEntity.getMaxHealth();
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        targetedEntity.heal(15);
        stack.removeItem();
    }
}
