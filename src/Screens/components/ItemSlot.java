package Screens.components;

import org.jetbrains.annotations.Nullable;

import Engine.GraphicsHandler;
import Engine.Inventory;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class ItemSlot extends Slot {

    protected Inventory inventory;
    protected int index;
    protected int frameCount = 0;
    protected int lastDelay = 0;
    protected int currentFrame = 0;

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

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        super.draw(handler, x, y);
        var stack = this.inventory.getStack(this.index);
        if (stack == null) {
            return;
        }
        this.frameCount++;
        var frames = stack.getItem().getFrames().get("inventory");
        if (frames != null) {
            var item_width = (int) Math.floor(this.width * .95f);
            var item_height = (int) Math.floor(this.height * .95f);
            if (frames[currentFrame].getDelay() == this.frameCount) {
                this.currentFrame = (this.currentFrame + 1) % frames.length;
            } 
            handler.drawImage(frames[this.currentFrame].getImage(), x + (width / 2) - (item_width / 2), y + (height / 2) - (item_height / 2), item_width, item_height);
        } else {
            var item_width = (int) Math.floor(this.width * .75f);
            var item_height = (int) Math.floor(this.height * .75f);
            handler.drawFilledRectangle(x + (this.width / 2) - (item_width / 2), y + (this.height / 2) - (item_height / 2), item_width, item_height, TailwindColorScheme.slate400);
        }
        handler.drawStringWithOutline(Integer.toString(stack.getCount()), x + this.width - 8 - 5, y + this.height - 5, Resources.press_start.deriveFont(8f), TailwindColorScheme.white, TailwindColorScheme.slate900, 3);
    }
    
}
