package Items;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Inventory;
import Level.Entity;
import Level.ItemStack;
import Level.Weapon;

public class SwordOfRage extends Weapon {
    public SwordOfRage(){
        super("swordofrage", "Sword of Rage", "A sword with the bloodlust of a thousand souls, contained inside.", 3.5);
        addAnimation("default", new FrameBuilder[]{
            new FrameBuilder(ImageLoader.load("weapons/swordOfRage.png"))
            .withScale(0.5f)
            .withBounds(0,0,68,190)
        });
    }
}