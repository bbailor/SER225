package Level;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.Nullable;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Items.KnifeOfLife;

/**
 * Definition for an item type
 */
public class Item {
    
    @Expose public final String id;
    protected String name;
    protected String description;
    protected int maxStackSize = 25;
    protected boolean canUse;
    protected java.util.Map<String, FrameBuilder[]> animations = new HashMap<>();

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
        System.out.println("Use from item class");
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
        var map = new HashMap<String, Frame[]>();
        for (var entry : this.animations.entrySet()) {
            Frame[] values = new Frame[entry.getValue().length];
            Arrays.parallelSetAll(values, i -> entry.getValue()[i].build());
            map.put(entry.getKey(), values);
        }
        return map;
    }

    @Nullable
    public Frame[] getFrames(String name) {

        var array = !this.animations.containsKey(name) ? this.animations.get("default") : this.animations.get(name);
        if (array == null) {
            return null;
        }
        Frame[] frames = new Frame[array.length];
        Arrays.parallelSetAll(frames, i -> array[i].build());
        return frames;
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

    public void addAnimation(String name, FrameBuilder[] frames) {
        this.animations.put(name, frames);
    }

    public void setCanUse(boolean canUse)
    {
        this.canUse = canUse;
    }

    public static class ItemList {
        protected static final java.util.Map<String, Item> IDMap = new HashMap<>();

        public static Item apple = new Item("apple", "Apple", "A tasty red apple", 20) {
            @Override
            public boolean canUse(ItemStack stack, Entity targetedEntity) {
                return targetedEntity.getHealth() != targetedEntity.getMaxHealth();
            }

            @Override
            public void use(ItemStack stack, Entity targetedEntity) {
                targetedEntity.heal(15);
            }

            {
                var sheet = new SpriteSheet(ImageLoader.load("item_imgs/apple.png"), 32, 32);
                FrameBuilder[] frames = new FrameBuilder[2];
                Arrays.parallelSetAll(frames, i -> new FrameBuilder(sheet.getSprite(0, i), (int)(60 / 1.5))
                    .withScale(1.25f)
                    // .build()
                );
                addAnimation("default", frames);
            }
        };

        public static Item cherry = new Item("cherry", "Cherry", "A tasty cherry", 20) {
            @Override
            public boolean canUse(ItemStack stack, Entity targetedEntity) {
                return targetedEntity.getHealth() != targetedEntity.getMaxHealth();
            }

            @Override
            public void use(ItemStack stack, Entity targetedEntity) {
                targetedEntity.setMana(targetedEntity.getMana() + 20);
            }

            {
                var world_sheet = new SpriteSheet(ImageLoader.load("item_imgs/cherry_world.png"), 32, 32);
                var sheet = new SpriteSheet(ImageLoader.load("item_imgs/cherry_default.png"), 32, 32);
                FrameBuilder[] world = new FrameBuilder[4];
                Arrays.parallelSetAll(world, i -> new FrameBuilder(world_sheet.getSprite(0, i), 60 / 3));
                addAnimation("world", world);
                addAnimation("inventory", new FrameBuilder[] {
                    new FrameBuilder(sheet.getSprite(0, 0))
                });
            }
        };

        /* public static Item test_item = new Item("test_item", "Test Item", "A test item", 5) {
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
        }; */

        public static Weapon cat = new Weapon("cat", "Cat", "A Cat", 0d) {
            {
                this.weaponSkillDamage = 100d;
                this.weaponSkillCost = 40d;
                var sheet = new SpriteSheet(ImageLoader.load("Cat.png"), 24, 24);
                FrameBuilder[] frames = new FrameBuilder[4];
                Arrays.parallelSetAll(frames, i -> new FrameBuilder(sheet.getSprite(1, i)).withScale(3).withBounds(6, 12, 12, 7));
                this.animations.put("default", frames);
            }
        };

        public static Weapon fist = new Weapon("fist", "Fist", "Your bare fist", 1d) {

        };

        public static Weapon knife_of_life = new KnifeOfLife();

        public static Item getFromID(String id) {
            return IDMap.get(id);
        }

        private ItemList() {}
    }
}

