package Items;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Inventory;
import Level.Entity;
import Level.ItemStack;
import Level.Weapon;

public class TlalocsStorm extends Weapon {

    public TlalocsStorm() {

        super("tlalocsstorm", "Tlaloc's Storm", "Tame the storm with the power of Tlaloc.", 8.5);

        addAnimation("default", new FrameBuilder[] {
            new FrameBuilder(ImageLoader.load("weapons/TlalocsStorm.png"))
                .withScale(0.5f)
                .withBounds(0, 4, 60, 56)
        });
    }
    
    @Override
    public String getAttackAnimationName() {
        return "TlalocsStorm";
    }
}
