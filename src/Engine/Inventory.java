package Engine;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import Level.ItemStack;

public class Inventory {
    
    protected Map<NamedSlot, ItemStack> equipped_items = new EnumMap<>(NamedSlot.class);
    protected ItemStack[] items;

    public ItemStack getStack(NamedSlot slot) {
        return this.equipped_items.get(slot);
    }

    public ItemStack getStack(int slot_id) {
        try {
            if (this.items[slot_id] != null && this.items[slot_id].getCount() == 0) {
                this.items[slot_id] = null;
            }
            return this.items[slot_id];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public ItemStack setStack(int slot_id, ItemStack item) {
        var old_item = this.items[slot_id];
        this.items[slot_id] = item;
        return old_item;
    }

    public boolean isEmpty() {
        return Arrays.stream(this.items).anyMatch(v -> v != null);
    }

    public int getSize() {
        return this.items.length;
    }


    public Inventory(int size) {
        this.items = new ItemStack[size];
    }

    public enum NamedSlot {
        Armor,
        Weapon
    }
}
