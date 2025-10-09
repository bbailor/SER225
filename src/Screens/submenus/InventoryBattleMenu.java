package Screens.submenus;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Level.ItemStack;
import Level.Player;
import Utils.Globals;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class InventoryBattleMenu extends BattleSubMenu {
    
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected Inventory inventory;
    protected Player player;
    protected int selectedID = -1;
    protected int pressCD = 0;

    public static final int FONT_SIZE = 12;
    public static final int BORDER_WIDTH = 4;

    public InventoryBattleMenu(int x, int y, int width, int height, Inventory inventory, Player player) {
        super(x, y, width, height);
        this.inventory = inventory;
        this.player = player;
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        this.selectedID = -1;
        this.pressCD = Globals.KEYBOARD_CD;
    }

    @Override
    public void initialize() {
        // No initialize
    }

    @Override
    public void update() {
        if (this.inventory.getStack(this.selectedID) == null) {
            this.selectedID = -1;
        }
        if (this.pressCD > 0) {
            this.pressCD--;
            return;
        }
        if (Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) {
            this.selectedID = this.getNextID(this.selectedID);
            this.pressCD = Globals.KEYBOARD_CD;
        }
        if (Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) {
            this.selectedID = this.getPrevID(this.selectedID);
            this.pressCD = Globals.KEYBOARD_CD;
        }

        // TODO: Maybe implement targetting
        if (Keyboard.isKeyDown(Key.ENTER)) {
            this.inventory.getStack(this.selectedID).use(this.player.getEntity());
            this.close();
            this.sendEvent("inventory.use");
        }

        if (Keyboard.isKeyDown(Key.ESC)) {
            this.close();
        }
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public int getPrevID(int current) {
        int prev = 0;
        boolean hasSetPrev = false;
        for (var entry : this.inventory) {
            if (current == entry.getKey()) {
                break;
            }
            if (entry.getValue() != null) {
                prev = entry.getKey();
                hasSetPrev = true;
            }
        }
        return hasSetPrev ? prev : this.inventory.getSize() - 1;
    }

    public int getNextID(int current) {
        int first = -1;
        for (var entry : this.inventory) {
            if (entry.getValue() != null) {
                if (first == -1) {
                    first = entry.getKey();
                }
                if (entry.getKey() > current) {
                    return entry.getKey();
                }
            }
        }
        return first == -1 ? current : first;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int offset_x = 8;
        int offset_y = 30; // TODO
        int selected_length = 0;
        int selected_offset_x = 0;
        int selected_offset_y = 0;
        for (var slot : this.inventory) {
            ItemStack stack = slot.getValue();
            if (stack == null) {
                continue;
            }
            if (this.selectedID == -1) {
                this.selectedID = slot.getKey();
            }
            if (this.selectedID == slot.getKey()) {
                selected_offset_x = offset_x;
                selected_offset_y = offset_y;
                selected_length = stack.getItem().getName().length();
            }
            graphicsHandler.drawString(stack.getItem().getName(), this.x + offset_x, this.y + offset_y, Resources.press_start.deriveFont(Font.PLAIN, FONT_SIZE), TailwindColorScheme.white);
            offset_x += (stack.getItem().getName().length() * FONT_SIZE) + (8/2) + BORDER_WIDTH;
        }
        graphicsHandler.drawRectangle(this.x + selected_offset_x - BORDER_WIDTH, this.y + (offset_y/2) - BORDER_WIDTH, (selected_length * FONT_SIZE) + BORDER_WIDTH, FONT_SIZE + 8 + BORDER_WIDTH, Globals.HOVER_COLOR, BORDER_WIDTH);
    }
    
}
