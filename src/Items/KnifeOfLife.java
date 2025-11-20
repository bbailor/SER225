package Items;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Inventory;
import Level.Entity;
import Level.ItemStack;
import Level.Weapon;

public class KnifeOfLife extends Weapon {

    public KnifeOfLife() {

        super("knifeoflife", "Knife of Life", "Trusty knife, gifted to you by Osirus.", 2.5);

        addAnimation("default", new FrameBuilder[] {
            new FrameBuilder(ImageLoader.load("weapons/knifeOfLife.png"))
                .withScale(2.0f)
                .withBounds(8, 0, 16, 32)
        });
    }

    @Override
    public String getAttackAnimationName() {
        return "KnifeOfLife";
    }
}
