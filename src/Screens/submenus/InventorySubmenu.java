package Screens.submenus;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Level.Entity;
import Screens.components.ItemSlot;
import Screens.components.Slot;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.TailwindColorScheme;

public class InventorySubmenu extends Screen implements Menu {

    protected int currentSlot = 0;
    protected Inventory inventory;
    protected ItemSlot[] slots;
    protected int length = 3;
    protected int height = 3;
    protected int prevHoveredID = 0;
    protected int hoveredID = 0;
    protected int selectedID = -1;
    protected int pressCD = 0;
    protected int openPressCD = 0;
    protected Entity openingEntity;
    protected Map<String, MenuListener> listeners = new HashMap<>();
    public static final int INV_SLOT_WIDTH = 50;
    public static final int INV_SLOT_HEIGHT = 50;
    public static final Color BASE_COLOR = TailwindColorScheme.slate700;

    public InventorySubmenu(int size, Entity entity) {
        this(new Inventory(size), entity);
    }

    public InventorySubmenu(Inventory inventory, Entity entity) {
        this.inventory = inventory;
        this.openingEntity = entity;
    }

    public InventorySubmenu setLength(int length) {
        this.length = length;
        return this;
    }
    public InventorySubmenu setHeight(int height) {
        this.height = height;
        return this;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void initialize() {
        this.slots = new ItemSlot[this.inventory.getSize()];
        Arrays.setAll(this.slots, i -> new ItemSlot(i, inventory, INV_SLOT_WIDTH, INV_SLOT_HEIGHT));
        this.slots[0].setBorderColor(Globals.HOVER_COLOR);
        this.hoveredID = 0;
        this.selectedID = -1;
    }

    @Override
    public void update() {
        this.slots[this.prevHoveredID].setBorderColor(BASE_COLOR);
        this.slots[this.hoveredID].setBorderColor(Globals.HOVER_COLOR);
        if (selectedID != -1) {
            this.slots[this.selectedID].setBorderColor(Globals.SELECT_COLOR);
        }
        if (this.hoveredID != this.prevHoveredID) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.prevHoveredID = this.hoveredID;
        }
        if (this.pressCD != 0) {
            --this.pressCD;
            return;
        }
        if (openPressCD > 0) {
            openPressCD--;
        }
        if ((Keyboard.isKeyDown(Key.ESC) || Keyboard.isKeyDown(Key.E)) && this.openPressCD <= 0) {
            this.close();
            return;
        }
        if (Keyboard.isKeyDown(Key.ENTER) || Keyboard.isKeyDown(Key.SPACE)) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (selectedID == -1) {
                this.selectedID = this.hoveredID;
                return;
            }
            var temp_item = this.inventory.getStack(this.hoveredID);
            this.inventory.setStack(this.hoveredID, this.inventory.getStack(this.selectedID));
            this.inventory.setStack(this.selectedID, temp_item);
            this.slots[this.selectedID].setBorderColor(BASE_COLOR);
            this.selectedID = -1;
        }
        this.handleMovement();
        var hoveredStack = this.inventory.getStack(this.hoveredID);
        if (Keyboard.isKeyDown(Key.R) && hoveredStack != null) {
            this.pressCD = Globals.KEYBOARD_CD;
            hoveredStack.removeItem();
            if (hoveredStack.getCount() == 0) {
                this.inventory.setStack(this.hoveredID, null);
            }
        }
        if (Keyboard.isKeyDown(Key.BACKSPACE) && hoveredStack != null) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.inventory.setStack(this.hoveredID, null);
        }

        if (Keyboard.isKeyDown(Key.U) && hoveredStack != null && hoveredStack.getItem().canUse(hoveredStack, this.openingEntity)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.sendEvent("inventory.use", hoveredStack.getItem());
            hoveredStack.use(this.openingEntity);
            if (hoveredStack.getCount() == 0) {
                this.inventory.setStack(this.hoveredID, null);
            }
        }
    }

    protected void handleMovement() {
        if (Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) {
            this.hoveredID = this.hoveredID == 0 ? (this.inventory.getSize() - 1) : this.hoveredID - 1;
        }
        if (Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) {
            this.hoveredID = this.hoveredID == (this.inventory.getSize() - 1) ? 0 : this.hoveredID + 1;
        }
        if (Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) {
            this.hoveredID = this.hoveredID < this.length ? this.inventory.getSize() - (this.length - this.hoveredID) : this.hoveredID - this.length;
        }
        if (Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) {
            this.hoveredID = this.hoveredID + this.length >= this.inventory.getSize() ? (this.length * (this.hoveredID%this.length))/3 : this.hoveredID + this.length;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int x = (Config.GAME_WINDOW_WIDTH / 2) - ((INV_SLOT_WIDTH * this.length) / 2);
        int y = (Config.GAME_WINDOW_HEIGHT / 2) - ((INV_SLOT_HEIGHT * this.height) / 2);
        for (int i = 0; i < inventory.getSize(); ++i) {
            Slot slot = this.slots[i];
            // slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + (x * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + (y * 5));
            slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + ((i%3) * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + ((i/3) * 5));
        }
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        //TODO: Implement when submenu (take above draw)
    }

    @Override
    public void open() {
        this.openPressCD = 12;
    }

    // static {
    //     GlobalKeyboardHandler.addListener("inventory", s -> {
    //         if (!isOpen && openPressCD <= 0) {
    //             isOpen = true;
    //             s.setGameState(GameState.INVENTORY);
    //             openPressCD = 12;
    //             return;
    //         }
    //         if (openPressCD > 0) {
    //             openPressCD--;
    //         }
    //     });
    // }
    
}
