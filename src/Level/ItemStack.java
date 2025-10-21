package Level;

import com.google.gson.annotations.Expose;

public class ItemStack {
    
    @Expose protected Item item;
    @Expose protected int stackSize = 1;
    
    public void onUse() {
        this.removeItem();
    }

    public void addItem() {
        if (this.stackSize < this.item.maxStackSize) {
            this.stackSize++;
        }
    }

    public void removeItem() {
        if (this.stackSize > 0) {
            this.stackSize--;
        }
    }

    /**
     * Merges the current ItemStack with a given ItemStack
     * @param stack the stack to merge with
     * @return the amount of items remaining in the previous stack
     */
    public int merge(ItemStack stack) {
        if (!this.item.equals(stack.item)) {
            return 0;
        }
        
        int result = this.stackSize + stack.stackSize;
        int remainder = 0;
        if (result > this.item.maxStackSize) {
            remainder = result - this.item.maxStackSize;
        }
        this.stackSize = result - remainder;
        return stack.stackSize = remainder;
    }

    public int getCount() {
        return this.stackSize;
    }

    public Item getItem() {
        return this.item;
    }

    public void use(Entity entity) {
        this.item.use(this, entity);
    }

    public ItemStack(Item item) {
        this.item = item;
    }

    public ItemStack(Item item, int count) {
        this.item = item;
        this.stackSize = count;
    }

}
