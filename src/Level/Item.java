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
        if (ItemList.IDMap.containsKey(this.id)) {
            throw new RuntimeException("Duplicate ID found for items");
        }
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

    public Frame[] getFrame(String name) {
        return !this.animations.containsKey(name) ? this.animations.get("default") : this.animations.get(name);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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

        // Needs to be changed to the actual knife stats and description
        public static Weapon knife_of_life = new Weapon("knife_of_life", "Knife of Life", "-desc-", 5d) {
            @Override
            public boolean canUse(ItemStack stack, Entity targetedEntity) {
                return true;
            }

            @Override
            public void use(ItemStack stack, Entity targetedEntity) {
                targetedEntity.currentWeapon = this;
                stack.removeItem();
            }

            @Override
            public double getWeaponSkillCost() {
                return 5d;
            }

            @Override
            public double getWeaponSkillDamage() {
                return 10d;
            }

            {
                this.animations.put("default", new Frame[] {
                    new FrameBuilder(ImageLoader.load("weapons//knifeOfLife.png"))
                    .withScale(2.0f)
                    .withBounds(8, 0, 16, 32)  // 2x the sprite size
                    .build()
                });
            }
        };

        public static Item getFromID(String id) {
            return IDMap.get(id);
        }

        private ItemList() {}
    }
}
