package Items;

import java.util.Arrays;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Item;
import Level.ItemStack;

public class Cherry extends Item {
    
    public Cherry() {
        super("cherry", "cherry", "A tasty and refreshing cherry\nRestores 20 mana", 20);
        var world_sheet = new SpriteSheet(ImageLoader.load("item_imgs/cherry_world.png"), 32, 32);
        var sheet = new SpriteSheet(ImageLoader.load("item_imgs/cherry_default.png"), 32, 32);
        FrameBuilder[] world = new FrameBuilder[4];
        Arrays.parallelSetAll(world, i -> new FrameBuilder(world_sheet.getSprite(0, i), 60 / 3));
        this.addAnimation("world", world);
        this.addAnimation("inventory", new FrameBuilder[] {
            new FrameBuilder(sheet.getSprite(0, 0))
        });
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return targetedEntity.getMana() < targetedEntity.getMaxMana();
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        targetedEntity.setMana(targetedEntity.getMana() + 20);
        stack.removeItem();
    }
}
