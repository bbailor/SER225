package Screens;

import java.awt.Color;
import java.util.Arrays;

import Engine.Config;
import Engine.GlobalKeyboardHandler;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Item;
import Level.ItemStack;
import Screens.components.ItemSlot;
import Screens.components.Slot;
import Utils.TailwindColorScheme;

public class InventoryScreen extends Screen {

    protected ScreenCoordinator screenCoordinator;
    protected int inventorySize;
    protected int currentSlot = 0;
    protected ItemSlot[] slots;
    protected int length = 3;
    protected int height = 3;
    protected int prevHoveredID = 0;
    protected int hoveredID = 0;
    protected int selectedID = -1;
    protected int pressCD = 0;
    private static int INV_SLOT_WIDTH = 50;
    private static int INV_SLOT_HEIGHT = 50;
    private static Color SELECT_COLOR = TailwindColorScheme.sky400;
    private static Color HOVER_COLOR = TailwindColorScheme.amber600;
    private static Color BASE_COLOR = TailwindColorScheme.slate700;
    protected static int PRESS_CD = 12;

    public InventoryScreen(ScreenCoordinator coordinator, int size) {
        this.screenCoordinator = coordinator;
        this.inventorySize = size;
    }

    public InventoryScreen setLength(int length) {
        this.length = length;
        return this;
    }
    public InventoryScreen setHeight(int height) {
        this.height = height;
        return this;
    } 

    @Override
    public void initialize() {
        this.slots = new ItemSlot[this.inventorySize];
        Arrays.setAll(this.slots, i -> new ItemSlot(null, INV_SLOT_WIDTH, INV_SLOT_HEIGHT));
        this.slots[0].setBorderColor(HOVER_COLOR);
        
        this.slots[4].setStack(new ItemStack(Item.ItemList.test_item, 3));
    }

    @Override
    public void update() {
        this.slots[this.prevHoveredID].setBorderColor(BASE_COLOR);
        this.slots[this.hoveredID].setBorderColor(HOVER_COLOR);
        if (selectedID != -1) {
            this.slots[this.selectedID].setBorderColor(SELECT_COLOR);
        }
        if (this.hoveredID != this.prevHoveredID) {
            this.pressCD = PRESS_CD;
            this.prevHoveredID = this.hoveredID;
        }
        if (this.pressCD != 0) {
            --this.pressCD;
            return;
        }
        if (openPressCD > 0) {
            openPressCD--;
        }
        if ((Keyboard.isKeyDown(Key.ESC) || Keyboard.isKeyDown(Key.E)) && openPressCD <= 0) {
            this.screenCoordinator.setGameState(GameState.LEVEL);
            isOpen = false;
            openPressCD = 12;
        }
        if (Keyboard.isKeyDown(Key.ENTER) || Keyboard.isKeyDown(Key.SPACE)) {
            this.pressCD = PRESS_CD;
            if (selectedID == -1) {
                this.selectedID = this.hoveredID;
                return;
            }
            var temp_item = this.slots[this.hoveredID].getStack();
            this.slots[this.hoveredID].setStack(this.slots[this.selectedID].getStack());
            this.slots[this.selectedID].setStack(temp_item);
            this.slots[this.selectedID].setBorderColor(BASE_COLOR);
            this.selectedID = -1;
        }
        if (Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) {
            this.hoveredID = this.hoveredID == 0 ? (this.inventorySize - 1) : this.hoveredID - 1;
        }
        if (Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) {
            this.hoveredID = this.hoveredID == (this.inventorySize - 1) ? 0 : this.hoveredID + 1;
        }
        if (Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) {
            this.hoveredID = this.hoveredID < this.length ? this.inventorySize - (this.length - this.hoveredID) : this.hoveredID - this.length;
        }
        if (Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) {
            this.hoveredID = this.hoveredID + this.length >= this.inventorySize ? (this.length * (this.hoveredID%this.length))/3 : this.hoveredID + this.length;
        }
        if (Keyboard.isKeyDown(Key.R) && this.slots[this.hoveredID].hasItem()) {
            this.pressCD = PRESS_CD;
            this.slots[this.hoveredID].getStack().removeItem();
            if (this.slots[this.hoveredID].getStack().getCount() == 0) {
                this.slots[this.hoveredID].setStack(null);
            }
        }
        if (Keyboard.isKeyDown(Key.BACKSPACE) && this.slots[this.hoveredID].hasItem()) {
            this.pressCD = PRESS_CD;
            this.slots[this.hoveredID].setStack(null);
        }

        if (Keyboard.isKeyDown(Key.U) && this.slots[this.hoveredID].hasItem()) {
            this.pressCD = PRESS_CD;
            this.slots[this.hoveredID].getStack().use();
            if (this.slots[this.hoveredID].getStack().getCount() == 0) {
                this.slots[this.hoveredID].setStack(null);
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int x = (Config.GAME_WINDOW_WIDTH / 2) - ((INV_SLOT_WIDTH * this.length) / 2);
        int y = (Config.GAME_WINDOW_HEIGHT / 2) - ((INV_SLOT_HEIGHT * this.height) / 2);
        for (int i = 0; i < inventorySize; ++i) {
            Slot slot = this.slots[i];
            // slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + (x * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + (y * 5));
            slot.draw(graphicsHandler, x + ((i%this.length) * INV_SLOT_WIDTH) + ((i%3) * 5), y + ((i/this.height) * INV_SLOT_HEIGHT) + ((i/3) * 5));
        }
    }

    static boolean isOpen = false;
    static int openPressCD = 0;
    static {
        GlobalKeyboardHandler.addListener("inventory", s -> {
            if (Keyboard.isKeyDown(Key.E) && !isOpen) {
                if (openPressCD <= 0) {
                    isOpen = true;
                    s.setGameState(GameState.INVENTORY);
                    openPressCD = 12;
                    return;
                }
            }
            if (openPressCD > 0) {
                openPressCD--;
            }
        });
    }
    
}
