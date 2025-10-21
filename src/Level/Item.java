package Level;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.annotations.Expose;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;

/**
 * Definition for an item type
 */
public class Item {
    
    @Expose public final String id;
    protected String name;
    protected String description;
    protected int maxStackSize = 25;
    protected java.util.Map<String, Frame[]> animations = new HashMap<>();

    public Item(String name) {
        this(name.replace(' ', '_').toLowerCase(), name);
    }
    
    public Item(String name, int maxStackSize) {
        this(name.replace(' ', '_'), name, maxStackSize);
    }

    public Item(String id, String name) {
        this.id = id;
        this.name = name;
        ItemList.IDMap.put(this.id, this);
    }
    
    public Item(String id, String name, int maxStackSize) {
        this(id, name);
        this.maxStackSize = maxStackSize;
    }

    public Item(String id, String name, String description, int maxStackSize) {
        this(id, name, maxStackSize);
        this.description = description;
    }

    public void use(ItemStack stack, Entity targetedEntity) {
        // Subclasses can use this for usable items
    }

    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        // If the item to be able to be used
        return false;
    }

    
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    public java.util.Map<String, Frame[]> getFrames() {
        return java.util.Map.copyOf(this.animations);
    }

    public static class ItemList {
        protected static final java.util.Map<String, Item> IDMap = new HashMap<>();

        public static Item test_item = new Item("test_item", "Test Item", "A test item", 5) {
            @Override
            public boolean canUse(ItemStack stack, Entity targetedEntity) {
                return true;
            }

            @Override
            public void use(ItemStack stack, Entity targetedEntity) {
                System.out.println("Hello, World!");
                targetedEntity.setMana(Math.min(targetedEntity.getMana() + 25, targetedEntity.getMaxMana()));
                stack.removeItem();
            }
        };

        public static Item test_item2 = new Item("test_item_2", "Test Item 2", "A second test item", 40) {
            @Override
            public boolean canUse(ItemStack stack, Entity targetedEntity) {
                return true;
            }

            @Override
            public void use(ItemStack stack, Entity targetedEntity) {
                System.out.println("Hello, World! (from 2)");
                targetedEntity.heal(10d);
                stack.removeItem();
            }
        };

        public static Item cat = new Weapon("cat", "Cat", "A Cat", 0d) {
            {
                this.weaponSkillDamage = 100d;
                this.weaponSkillCost = 40d;
                var sheet = new SpriteSheet(ImageLoader.load("Cat.png"), 24, 24);
                Frame[] frames = new Frame[4];
                Arrays.parallelSetAll(frames, i -> new FrameBuilder(sheet.getSprite(1, i)).build());
                this.animations.put("inventory", frames);
            }
        };

        public static Weapon fist = new Weapon("fist", "Fist", "Your bare fist", 1d) {

        };

        public static Item getFromID(String id) {
            return IDMap.get(id);
        }

        private ItemList() {}
    }
}
