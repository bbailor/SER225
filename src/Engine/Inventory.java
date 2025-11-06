package Engine;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import Level.Item;
import Level.ItemStack;
import Level.Item.ItemList;

public class Inventory implements Iterable<Entry<Integer, ItemStack>> {
    
    @Expose protected Map<NamedSlot, @Nullable ItemStack> equipped_items = new EnumMap<>(NamedSlot.class);
    @Expose protected Map<Integer, @Nullable ItemStack> items;

    public ItemStack getStack(NamedSlot slot) {
        return this.equipped_items.get(slot);
    }

    @Nullable
    public ItemStack getStack(int slot_id) {
        if (slot_id >= this.getSize()) {
            return this.getStack(NamedSlot.values()[slot_id - this.getSize()]);
        }
        try {
            if (this.items.get(slot_id) != null && this.items.get(slot_id).getCount() == 0) {
                this.items.put(slot_id, null);
            }
            return this.items.get(slot_id);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Nullable
    public ItemStack setStack(int slot_id, @Nullable ItemStack item) {
        if (slot_id >= this.getSize()) {
            return this.setStack(NamedSlot.values()[slot_id - this.getSize()], item);
        }
        var old_item = this.items.get(slot_id);
        this.items.put(slot_id, item);
        return old_item;
    }

    @Nullable
    public ItemStack setStack(NamedSlot slot, @Nullable ItemStack item) {
        var old_item = this.equipped_items.get(slot);
        if (slot == NamedSlot.Weapon && item == null) {
            this.equipped_items.put(NamedSlot.Weapon, new ItemStack(Item.ItemList.fist));
            return old_item;
        }
        this.equipped_items.put(slot, item);
        return old_item != null && old_item.getItem().equals(Item.ItemList.fist) ? null : old_item;
    }

    @SuppressWarnings("null")
    public boolean addStack(@NotNull ItemStack stack) {
        int i = -1;
        int initStackCount = stack.getCount();
        while (stack.getCount() > 0 && i < this.items.size()) {
            ++i;
            if (this.items.get(i) == null) {
                this.items.put(i, stack);
                initStackCount = Integer.MAX_VALUE;
                break;
            }
            if (!this.items.get(i).getItem().equals(stack.getItem())) {
                continue;
            }
            if (this.items.get(i).merge(stack) != 0) {
                break;
            }
        }
        return initStackCount < stack.getCount();
    }

    public boolean isEmpty() {
        return this.items.containsValue(null);
    }

    public int getSize() {
        return this.items.size();
    }

    public int getEquippedSize() {
        return NamedSlot.values().length;
    }

    public Map<Integer, ItemStack> getItems() {
        Map<Integer, ItemStack> new_items = new TreeMap<>();
        for (var e : this.items.entrySet()) {
            new_items.put(e.getKey(), e.getValue());
            System.out.println(e.getValue() != null ? e.getValue().getItem().getName() : "null");
        }
        return new_items;
    }

    public Inventory(int size) {
        this.items = new LinkedHashMap<>();
        this.equipped_items.put(NamedSlot.Weapon, new ItemStack(ItemList.fist));
        for (int i = 0; i < size - NamedSlot.values().length; ++i) {
            this.items.put(i, null);
        }
    }

    @SuppressWarnings("unused")
    private Inventory() {}

    public enum NamedSlot {
        Armor,
        Weapon
    }

    @Override
    public Iterator<Entry<Integer, ItemStack>> iterator() {
        return this.items.entrySet().iterator();
    }

    public boolean isFull()
    {
        for (int i = 0; i < 9; i++)
        {
            if (getStack(i) == null)
            {
                return false;
            }
        }
        return true;
    }
}
