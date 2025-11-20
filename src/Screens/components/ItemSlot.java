package Screens.components;

import org.jetbrains.annotations.Nullable;

import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Inventory.NamedSlot;
import Level.ItemStack;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class ItemSlot extends Slot {

    protected Inventory inventory;
    protected int index;
    protected String equipName;
    protected int frameCount = 0;
    protected int lastDelay = 0;
    protected int currentFrame = 0;
    protected boolean isEquipSlot = false;

    public ItemSlot(int index, Inventory inventory, int width, int height) {
        super(width, height);
        this.index = index;
        this.inventory = inventory;
    }

    public ItemSlot(int index, Inventory inventory) {
        this(index, inventory, 25, 25);
    }

    public void setIndex(int index) {
        this.index = index;
        this.frameCount = 0;
    }

    public int getIndex() {
        return this.index;
    }

    public void setEquipSlot(boolean equipSlot) {
        this.isEquipSlot = equipSlot;
    }

    public void setEquipName(String name) {
        this.equipName = name;
    }

    String last_id;
    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        super.draw(handler, x, y);
        ItemStack stack;
        if (this.isEquipSlot) {
            handler.drawStringWithOutline(this.equipName, x + 2 + 2, x + 12 + 2, Resources.PRESS_START.deriveFont(12f), TailwindColorScheme.white, TailwindColorScheme.slate900, 3);
            handler.drawStringWithOutline("E", x + (this.width - 14)/2, y + (this.height)/2, Resources.PRESS_START.deriveFont(12f), TailwindColorScheme.white, TailwindColorScheme.slate900, 3);
            stack = this.inventory.getStack(NamedSlot.values()[this.index - this.inventory.getSize()]);
        } else {
            stack = this.inventory.getStack(this.index);
        }
        if (stack == null) {
            return;
        }
        if (!stack.getItem().id.equals(last_id)) {
            this.last_id = stack.getItem().id;
            this.currentFrame = 0;
            this.frameCount = 0;
            
        }
        this.frameCount++;
        var frames = stack.getItem().getFrames("inventory");
        if (frames != null) {
            var item_width = (int) Math.floor(this.width * .95f);
            var item_height = (int) Math.floor(this.height * .95f);
            if (this.frameCount % (Math.max(frames[currentFrame].getDelay(), 1)) == 0) {
                this.currentFrame = (this.currentFrame + 1) % frames.length;
            } 
            handler.drawImage(frames[this.currentFrame].getImage(), x + (width / 2) - (item_width / 2), y + (height / 2) - (item_height / 2), item_width, item_height);
        } else {
            var item_width = (int) Math.floor(this.width * .75f);
            var item_height = (int) Math.floor(this.height * .75f);
            handler.drawFilledRectangle(x + (this.width / 2) - (item_width / 2), y + (this.height / 2) - (item_height / 2), item_width, item_height, TailwindColorScheme.slate400);
        }
        handler.drawStringWithOutline(Integer.toString(stack.getCount()), x + this.width - 8 - 5, y + this.height - 5, Resources.PRESS_START.deriveFont(8f), TailwindColorScheme.white, TailwindColorScheme.slate900, 3);
    }
    
}
