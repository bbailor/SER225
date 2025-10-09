package Engine;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import Level.ItemStack;

public class Inventory implements Iterable<Entry<Integer, ItemStack>> {
    
    protected Map<NamedSlot, ItemStack> equipped_items = new EnumMap<>(NamedSlot.class);
    protected Map<Integer, ItemStack> items;

    public ItemStack getStack(NamedSlot slot) {
        return this.equipped_items.get(slot);
    }

    public ItemStack getStack(int slot_id) {
        try {
            if (this.items.get(slot_id) != null && this.items.get(slot_id).getCount() == 0) {
                this.items.put(slot_id, null);
            }
            return this.items.get(slot_id);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public ItemStack setStack(int slot_id, ItemStack item) {
        var old_item = this.items.get(slot_id);
        this.items.put(slot_id, item);
        return old_item;
    }

    public boolean isEmpty() {
        return this.items.containsValue(null);
    }

    public int getSize() {
        return this.items.size();
    }

    public Map<Integer, ItemStack> getItems() {
        return Map.copyOf(this.items);
    }

    public Inventory(int size) {
        this.items = new LinkedHashMap<>();
        for (int i = 0; i < size; ++i) {
            this.items.put(i, null);
        }
    }

    public enum NamedSlot {
        Armor,
        Weapon
    }

    @Override
    public Iterator<Entry<Integer, ItemStack>> iterator() {
        return this.items.entrySet().iterator();
    }
}
