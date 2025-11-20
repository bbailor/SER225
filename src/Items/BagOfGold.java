package Items;

import java.util.Arrays;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Inventory.NamedSlot;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.ItemStack;
import Level.Weapon;

public class BagOfGold extends Weapon {
    public final SpriteSheet sheet = new SpriteSheet(ImageLoader.load("weapons/BagOfGold.png"), 32, 32);
    public BagOfGold() {
        super("bag_of_gold", "Bag of Gold", "A bag of gold coins", 6.5d);
        var coin = new FrameBuilder(this.sheet.getSprite(1, 0), 1);
        FrameBuilder[] attack = new FrameBuilder[20];
        FrameBuilder[] all = new FrameBuilder[22];
        Arrays.parallelSetAll(attack, i -> coin);
        Arrays.parallelSetAll(all, i -> new FrameBuilder(this.sheet.getSprite(0, i), (int) (60/8.5)));
        addAnimation("ATTACK", attack);
        addAnimation("default", all);
    }

    // @Override
    // public void use(ItemStack stack, Entity targetedEntity) {
    //     super.use(stack, targetedEntity);
    //     targetedEntity.setResistance(100);
    //     targetedEntity.setBaseAttack(0);
    // }

    @Override
    public String getAttackAnimationName() {
        return "BagOfGold";
    }
}
