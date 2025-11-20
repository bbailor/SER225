package Items;

import Builders.FrameBuilder;
import Engine.ImageLoader;

import Level.Weapon;

public class DenialsStaff extends Weapon {

    public DenialsStaff() {

        super("denialsstaff", "Denial's Staff", "A powerfull staff made from spare wood and Denial's flame.", 4.0d);
        addAnimation("default", new FrameBuilder[] {
            new FrameBuilder(ImageLoader.load("weapons/DenialsStaff.png"))
                .withScale(2.0f)
                .withBounds(0, 0, 32, 32)
        });
    }
    
    @Override
    public String getAttackAnimationName() {
        return "DenialsStaff";
    }
}
