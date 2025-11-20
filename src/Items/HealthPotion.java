package Items;

import java.util.Arrays;
import java.util.Map;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Item;
import Level.ItemStack;

public class HealthPotion extends Item {
    
    public HealthPotion() {
        super("health_potion", "Health Potion", "A rasberry flavored flask of a mysterious liquid\nHeals 10% of max health per turn (8 turns)", 1);
        var sheet = new SpriteSheet(ImageLoader.load("item_imgs/healthPotion.png"), 32, 32);
        FrameBuilder[] frames = new FrameBuilder[10];
        Arrays.parallelSetAll(frames, i -> new FrameBuilder(sheet.getSprite(0, i), (60/14)).withScale(1.5f));
        addAnimation("default", frames);
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return ((int)stack.getProperties().get("uses")) > 0;
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        var props = stack.getProperties();
        int turns = 8;
        props.put("uses", (int)props.get("uses") - 1);
        if (!targetedEntity.getInBattle()) {
            targetedEntity.heal(targetedEntity.getMaxHealth() * .1 * turns);
            return;
        }
        targetedEntity.addTurnEffect(new Entity.TurnEffect() {
            int turn_count = 0;
            @Override
            public void run(Entity entity, int turn_count) {
                if (this.turn_count >= 8) {
                    entity.removeTurnEffect(this);
                    return;
                }
                this.turn_count++;
                entity.heal(entity.getMaxHealth() * .1);
            }
        });
        if (((int)props.get("uses")) == 0) {
            stack.removeItem();
        }
    }

    @Override
    public void addDefaultProperites(Map<String, Object> properties) {
        properties.put("uses", 3);
    }
}
