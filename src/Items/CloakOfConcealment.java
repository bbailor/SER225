package Items;

import Level.Entity;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Level.Item;
import Level.ItemStack;
import Level.Player;

public class CloakOfConcealment extends Item {
    public CloakOfConcealment(){
        super("Cloak of Concealment", "Cloak of Concealment", "A magical cloak that allows you to hide from enemies for 20 steps.", 1);
   
            addAnimation("default", new FrameBuilder[] {
            new FrameBuilder(ImageLoader.load("item_imgs/cloakOfConcealment.png"))
            .withScale(1.5f)        
            .withBounds(8, 0, 32, 48)
            });
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        
        return true;
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        System.out.println("using cloak");

        targetedEntity.setHidden(true);
        System.out.println(targetedEntity.getHidden());

        stack.removeItem();
    }
        
}
