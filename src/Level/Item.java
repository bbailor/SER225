package Level;

import static java.util.Map.entry;

import java.awt.image.BufferedImage;
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

    public void use(ItemStack stack) {
        // Subclasses can use this for usable items
    }

    public boolean canUse(ItemStack stack) {
        // For the item to be able to be used
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
            public boolean canUse(ItemStack stack) {
                return true;
            }

            public void use(ItemStack stack) {
                System.out.println("Hello, World!");
                stack.removeItem();
            }
        };

        private ItemList() {}
    }
}
