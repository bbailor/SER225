package Screens.submenus;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Mouse;
import Engine.Screen;
import Engine.Inventory.NamedSlot;
import Level.Entity;
import Level.Item;
import Screens.components.ItemSlot;
import Screens.components.Slot;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.TailwindColorScheme;

public class InventorySubmenu implements Menu {

    protected Point lastMousePosition;
    protected int lastX;
    protected int lastY;
    protected Entity openingEntity;
    protected Inventory inventory;
    protected int msgDelayCount = 0;
    protected int currentSlot = 0;
    protected ItemSlot[] slots;
    protected int columns = 9;
    protected int hoveredID = 0;
    protected int selectedID = -1;
    protected int pressCD = 0;
    protected int openPressCD = 0;
    protected Map<String, MenuListener> listeners = new HashMap<>();
    public static final int INV_SLOT_WIDTH = 50;
    public static final int INV_SLOT_HEIGHT = 50;
    public static final Color BASE_COLOR = TailwindColorScheme.slate700;
    protected static int DEFAULT_DELAY_COUNT = 75;

    public InventorySubmenu setColumns(int length) {
        this.columns = length;
        return this;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setEntity(Entity entity) {
        this.openingEntity = entity;
        this.setInventory(entity.getInventory());
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        this.openPressCD = 12;
        this.slots = new ItemSlot[this.inventory.getSize() + this.inventory.getEquippedSize()];
        Arrays.setAll(this.slots, i -> new ItemSlot(i, inventory, INV_SLOT_WIDTH, INV_SLOT_HEIGHT));
        for (int i = this.inventory.getSize(); i < this.slots.length; ++i) {
            this.slots[i].setEquipSlot(true);
            this.slots[i].setEquipName(NamedSlot.values()[i - this.inventory.getSize()].toString());
        }
        this.slots[0].setBorderColor(Globals.HOVER_COLOR);
        this.hoveredID = 0;
        this.selectedID = -1;
    }

    @Override
    public void update() {
        this.handleMouseMovement();
        if (this.pressCD == 0 && this.openPressCD == 0) {
            this.handleKeyMovement();
            this.handleKeyActions();
            this.handleMouseActions();
        }
        if (this.pressCD != 0) {
            --this.pressCD;
        }
        if (openPressCD != 0) {
            openPressCD--;
        }
        this.slots[this.hoveredID].setBorderColor(Globals.HOVER_COLOR);
        if (selectedID != -1) {
            this.slots[this.selectedID].setBorderColor(Globals.SELECT_COLOR);
        }
        if (this.msgDelayCount != 0) {
            --this.msgDelayCount;
            return;
        }
        this.sendEvent(STATUS_EVENT, getStatusMessage());
    }

    public String getStatusMessage() {
        
        if (this.selectedID != -1) {
            return "Click or Press L-Click to move";
        }

        if (this.inventory.getStack(this.hoveredID) != null) {
            var stack = this.inventory.getStack(this.hoveredID);
            return String.format("%s: %s/%s\n%s", stack.getItem().getName(), stack.getCount(), stack.getItem().getMaxStackSize(), stack.getItem().getDescription());
        }

        return String.format("Enter or L-Click to move\nU or R-Click to Use\nBackspace Or M-Click to remove");
    }

    protected void handleKeyActions() {
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
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot move items into equippment slots");
                return;
            }
            var temp_item = this.inventory.getStack(this.hoveredID);
            this.inventory.setStack(this.hoveredID, this.inventory.getStack(this.selectedID));
            this.inventory.setStack(this.selectedID, temp_item);
            this.slots[this.selectedID].setBorderColor(BASE_COLOR);
            this.selectedID = -1;
        }
        var hoveredStack = this.inventory.getStack(this.hoveredID);
        if (Keyboard.isKeyDown(Key.R) && hoveredStack != null) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot delete equipped items this way");
                return;
            }
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
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot reuse equipped items");
                return;
            }
            this.msgDelayCount = DEFAULT_DELAY_COUNT;
            this.sendEvent(STATUS_EVENT, String.format("%s used", hoveredStack.getItem().getName()));
            hoveredStack.use(this.openingEntity);
            if (hoveredStack.getCount() == 0) {
                this.inventory.setStack(this.hoveredID, null);
            }
        }
    }

    protected void handleKeyMovement() {
        this.slots[this.hoveredID].setBorderColor(BASE_COLOR);
        if (Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.hoveredID = this.hoveredID == 0 ? (this.inventory.getSize() - 1) : this.hoveredID - 1;
        }
        if (Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.hoveredID = this.hoveredID == (this.inventory.getSize() - 1) ? 0 : this.hoveredID + 1;
        }
        if (Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.hoveredID = this.hoveredID < this.columns ? this.inventory.getSize() - (this.columns - this.hoveredID) : this.hoveredID - this.columns;
        }
        if (Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.hoveredID = this.hoveredID + this.columns >= this.inventory.getSize() ? (this.columns * (this.hoveredID%this.columns))/this.columns : this.hoveredID + this.columns;
        }
    }

    protected void handleMouseActions() {
        var hoveredStack = this.inventory.getStack(this.hoveredID);
        if (Mouse.isLeftClickDown()) {
            this.pressCD = Math.max(12, Globals.KEYBOARD_CD);
            if (selectedID == -1) {
                this.selectedID = this.hoveredID;
                return;
            }
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot move items into equippment slots");
                return;
            }
            var temp_item = this.inventory.getStack(this.hoveredID);
            this.inventory.setStack(this.hoveredID, this.inventory.getStack(this.selectedID));
            this.inventory.setStack(this.selectedID, temp_item);
            this.slots[this.selectedID].setBorderColor(BASE_COLOR);
            this.selectedID = -1;
        }
        if (Mouse.isRightClickDown() && hoveredStack != null && hoveredStack.getItem().canUse(hoveredStack, this.openingEntity)) {
            this.pressCD = Math.max(12, Globals.KEYBOARD_CD);
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot reuse equipped items");
                return;
            }
            // this.sendEvent("inventory.use", hoveredStack.getItem());
            this.msgDelayCount = DEFAULT_DELAY_COUNT;
            this.sendEvent(STATUS_EVENT, String.format("%s used", hoveredStack.getItem().getName()));
            hoveredStack.use(this.openingEntity);
        }
        if (Mouse.isMiddleClickDown() && hoveredStack != null) {
            this.pressCD = Math.max(12, Globals.KEYBOARD_CD);
            if (this.hoveredID >= this.inventory.getSize()) {
                this.msgDelayCount = DEFAULT_DELAY_COUNT;
                this.sendEvent(STATUS_EVENT, "You cannot delete equipped items this way");
                return;
            }
            hoveredStack.removeItem();
        }
        if (hoveredStack != null && hoveredStack.getCount() == 0) {
            this.inventory.setStack(this.hoveredID, null);
        }
    }

    protected void handleMouseMovement() {
        this.slots[this.hoveredID].setBorderColor(BASE_COLOR);
        Point mousePos = Mouse.getCurrentPosition();
        if (!mousePos.equals(lastMousePosition)) {
            for (int i = 0; i < this.slots.length; ++i) {
                int x = this.lastX + ((i%this.columns) * INV_SLOT_WIDTH) + ((i%this.columns) * 5),
                    y = this.lastY + ((i/this.columns) * INV_SLOT_HEIGHT) + ((i/this.columns) * 5);
                if (new Rectangle(x, y, INV_SLOT_WIDTH, INV_SLOT_HEIGHT).contains(mousePos)) {
                    
                    this.hoveredID = i;
                    break;
                }
            }
        }
        this.lastMousePosition = mousePos;
    }

   /*  @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int x = (Config.GAME_WINDOW_WIDTH / 2) - ((INV_SLOT_WIDTH * this.length) / 2);
        int y = (Config.GAME_WINDOW_HEIGHT / 2) - ((INV_SLOT_HEIGHT * this.height) / 2);
        for (int i = 0; i < inventory.getSize(); ++i) {
            Slot slot = this.slots[i];
            // slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + (x * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + (y * 5));
            slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + ((i%3) * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + ((i/3) * 5));
        }
    } */

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        this.lastX = x;
        this.lastY = y;
        for (int i = 0; i < this.slots.length; ++i) {
            Slot slot = this.slots[i];
            // slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + (x * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + (y * 5));
            slot.draw(handler, x + ((i%this.columns) * INV_SLOT_WIDTH) + ((i%this.columns) * 5), y + ((i/this.columns) * INV_SLOT_HEIGHT) + ((i/this.columns) * 5));
        }
    }

    public InventorySubmenu(int size, Entity entity) {
        this(new Inventory(size), entity);
    }

    public InventorySubmenu(Inventory inventory, Entity entity) {
        this.inventory = inventory;
        this.openingEntity = entity;
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
