package Level;

import java.util.HashMap;

import GameObject.Frame;

/**
 * Definition for an item type
 */
public class Item {
    
    protected String name;
    protected String description;
    protected int maxStackSize = 25;
    protected java.util.Map<String, Frame[]> animations = new HashMap<>();

    public Item(String name) {
        this.name = name;
    }
    
    public Item(String name, int maxStackSize) {
        this.name = name;
        this.maxStackSize = maxStackSize;
    }

    public Item(String name, String description, int maxStackSize) {
        this.name = name;
        this.description = description;
        this.maxStackSize = maxStackSize;
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
        public static Item test_item = new Item("Test Item", "A test item", 5) {
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

        public static Item test_item2 = new Item("Test Item 2", "A second test item", 40) {
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

        public static Weapon fist = new Weapon("Fist", "Your bare fist", 1d) {

        };

        private ItemList() {}
    }
}
