package Level;

public class ItemStack {
    
    protected Item item;
    protected int stackSize = 1;
    
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
