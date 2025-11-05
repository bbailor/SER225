package Screens.submenus;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import Engine.Mouse;
import Engine.Screen;
import Screens.components.SaveSlot;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class SaveSubmenu extends Screen implements Menu {
    protected int width, height;
    protected int maxSlotsX, maxSlotsY;
    protected SaveSlot[] slots;
    protected boolean isStandalone;
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected int page = 0;
    protected int pressCD = 0;
    protected int hoveredID = 0;
    protected int selectedID = -1;
    protected Rectangle saveButtonBounds = new Rectangle();
    protected Rectangle loadButtonBounds = new Rectangle();
    protected Rectangle leftButtonBounds = new Rectangle();
    protected Rectangle rightButtonBounds = new Rectangle();
    protected Rectangle[] saveBounds;
    /** left = 0, save = 1, load = 2, right = 3, none = -1 */
    protected int selectedElement = -1;
    protected Point lastMousePosition = new Point();
    
    public static final String SAVE_EVENT = "save.save";
    public static final String LOAD_EVENT = "save.load";
    protected static int BARSIZE = 75;
    protected static Color BUTTON_COLOR_BASE = TailwindColorScheme.slate700;
    protected static Color BUTTON_COLOR_HOVER = TailwindColorScheme.slate500;
    
    public SaveSubmenu(int width, int height) {
        this(width, height, false);
    }

    public SaveSubmenu(int width, int height, boolean standalone) {
        this.isStandalone = standalone;
        this.width = width;
        this.height = height;
        this.maxSlotsX = (this.getDrawWidth(standalone) - 14) / SaveSlot.SAVE_SLOT_WIDTH;
        this.maxSlotsY = (this.getDrawHeight(standalone) - 14 - BARSIZE) / SaveSlot.SAVE_SLOT_HEIGHT;
        this.slots = new SaveSlot[maxSlotsX * maxSlotsY];
        this.saveBounds = new Rectangle[maxSlotsX * maxSlotsY];
        // slot.setSaveData(save);
        for (int y = 0; y < maxSlotsY; ++y) {
            for (int x = 0; x < maxSlotsX; ++x) { 
                int i = x + y * maxSlotsX;
                this.slots[i] = new SaveSlot(i);
                this.saveBounds[i] = new Rectangle(
                    (7 + (i%maxSlotsX) * (SaveSlot.SAVE_SLOT_WIDTH + 7)) + (this.getDrawWidth(standalone) - maxSlotsX * (SaveSlot.SAVE_SLOT_WIDTH + 7)) / 2,
                     (i/maxSlotsX) * (SaveSlot.SAVE_SLOT_HEIGHT + 7) + 20,
                    SaveSlot.SAVE_SLOT_WIDTH,
                    SaveSlot.SAVE_SLOT_HEIGHT
                );
            }
        }
        this.slots[0].setBorderColor(Globals.HOVER_COLOR);
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        this.page = 0;
        this.selectedID = -1;
        this.hoveredID = 0;
        this.selectedElement = -1;
        for (var slot : this.slots) {
            slot.refreshData();
            slot.setBorderColor(null);
        }
        this.slots[0].setBorderColor(Globals.HOVER_COLOR);
    }

    @Override
    public void initialize() {
        //
    }

    @Override
    public void update() {
        
        int prevPage = this.page;
        int prevHov = this.hoveredID;
        int prevSel = this.selectedID;

        this.handleMousePress();
        this.handleKeyboardPresses();

        if (this.pressCD != 0) {
            this.pressCD--;
        }

        if (prevPage != this.page) {
            changePage(this.page);
        }
        if (prevHov != this.hoveredID) {
            if (this.selectedID != prevHov) {
                this.slots[prevHov].setBorderColor(null);
            }
            if (this.selectedID != this.hoveredID) {
                this.slots[this.hoveredID].setBorderColor(Globals.HOVER_COLOR);
            }
        }

        if (prevSel != this.selectedID) {
            if (prevSel != -1 && prevSel != this.hoveredID) {
                this.slots[prevSel].setBorderColor(null);
            }
            if (this.selectedID != -1) {
                this.slots[this.selectedID].setBorderColor(Globals.SELECT_COLOR);
            }
        }
    }

    protected boolean handleMouseMovement() {
        Point mousePos = Mouse.getCurrentPosition();

        if (!mousePos.equals(this.lastMousePosition)) {
            if (this.saveButtonBounds.contains(mousePos)) {
                this.selectedElement = 1;
            } else if (this.loadButtonBounds.contains(mousePos)) {
                this.selectedElement = 2;
            } else if (this.leftButtonBounds.contains(mousePos)) {
                this.selectedElement = 0;
            } else if (this.rightButtonBounds.contains(mousePos)) {
                this.selectedElement = 3;
            } else {
                this.selectedElement = -1;
            }
        }

        boolean isHoveringSave = false;
        for (int i = 0; i < this.saveBounds.length; ++i) {
            var rec = new Rectangle(this.saveBounds[i]);
            rec.translate(this.isStandalone ? standaloneX() : this.lastX, this.isStandalone ? standaloneY() : this.lastY);
            if (rec.contains(mousePos)) {
                if (!mousePos.equals(this.lastMousePosition)) {
                    this.hoveredID = i;
                }
                isHoveringSave = true;
                break;
            }
        }
        this.lastMousePosition = mousePos;
        return isHoveringSave;
    }

    protected void handleMousePress() {
        boolean isHoveringSave = handleMouseMovement();
        if (this.pressCD > 0) {
            return;
        }

        if (Mouse.isLeftClickDown()) {
            switch (this.selectedElement) {
                case 0: {
                    this.page = this.page > 0 ? this.page - 1 : 0;
                    break;
                }
                case 3: {
                    this.page++;
                    break;
                }
                case 1: {
                    if (this.selectedID > -1) {
                        this.sendEvent(SAVE_EVENT, this.selectedID + this.page * this.slots.length);
                        this.close();
                    }
                    break;
                }
                case 2: {
                    if (this.selectedID > -1 && this.slots[this.selectedID].getSaveData() != null) {
                        this.sendEvent(LOAD_EVENT, this.selectedID + this.page * this.slots.length);
                        this.close();
                    }
                    break;
                }
            }
            this.pressCD = Math.max(12, Globals.KEYBOARD_CD);
            if (isHoveringSave) {
                this.selectedID = this.selectedID == this.hoveredID ? -1 : this.hoveredID;
            }
        }
    }

    protected void handleKeyboardPresses() {
        if (this.pressCD > 0) {
            return;
        }

        if ((Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A))) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.selectedID != -1) {
                this.selectedElement = this.selectedElement == 1 ? 2 : 1;
                return;
            }
            if (this.hoveredID == 0) {
                this.hoveredID = this.page == 0 ? 0 : this.slots.length - 1;
                this.page = this.page > 0 ? this.page - 1 : 0;
            } else {
                this.hoveredID = this.hoveredID - 1;
            }
        }
        if (Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.selectedID != -1) {
                this.selectedElement = this.selectedElement == 1 ? 2 : 1;
                return;
            }
            if (this.hoveredID == this.slots.length - 1) {
                this.hoveredID = 0;
                this.page++;
            } else {
                this.hoveredID = this.hoveredID + 1;
            }
        }
        if ((Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) && this.selectedID == -1) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.hoveredID < this.maxSlotsX) {
                this.hoveredID = this.slots.length - (this.maxSlotsX - this.hoveredID);
                this.page = this.page > 0 ? this.page - 1 : 0;
            } else {
                this.hoveredID = this.hoveredID - this.maxSlotsX;
            }
        }
        if ((Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) && this.selectedID == -1) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.hoveredID + this.maxSlotsX >= this.slots.length) {
                this.hoveredID = (this.maxSlotsX * (this.hoveredID%this.maxSlotsX))/3;
                this.page++;
            } else {
                this.hoveredID = this.hoveredID + this.maxSlotsX;
            }
        }
        if (Keyboard.isKeyDown(Key.HOME)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.page = 1;
        }
        if (Keyboard.isKeyDown(Key.ESC)) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.selectedElement == -1) {
                this.close();
                return;
            }
            this.slots[this.selectedID].setBorderColor(Globals.HOVER_COLOR);
            this.selectedElement = -1;
            this.selectedID = -1;
        }
        if (Keyboard.isKeyDown(Key.ENTER) || Keyboard.isKeyDown(Key.SPACE)) {
            this.pressCD = Globals.KEYBOARD_CD;
            if (this.selectedElement == -1) {
                this.selectedID = this.hoveredID;
                this.selectedElement = 1;
            } else {
                if (this.selectedElement == 1) {
                    this.sendEvent(SAVE_EVENT, this.selectedID + this.page * this.slots.length);
                    this.close();
                } else if (this.slots[this.selectedID].getSaveData() != null) {
                    this.sendEvent(LOAD_EVENT, this.selectedID + this.page * this.slots.length);
                    this.close();
                }
            }
        }
    }

    public void changePage(int page) {
        for (int i = 0; i < this.slots.length; ++i) {
            var slot = this.slots[i];
            slot.setIndex(i + page * this.slots.length);
        }
    }

    protected int getDrawWidth(boolean standalone) {
        return (int) (this.width - (standalone ? (this.width * .15f) : 0));
    }

    protected int getDrawHeight(boolean standalone) {
        return (int) (this.height - (standalone ? (this.height * .30f) : 0));
    }

    protected int standaloneX() {
        return ((this.width - this.getDrawWidth(true)) / 2);
    }

    protected int standaloneY() {
        return ((this.height - this.getDrawHeight(true)) / 3);
    }
    
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int x = (this.width - this.getDrawWidth(this.isStandalone)) / 2;
        int y = (this.height - this.getDrawHeight(this.isStandalone)) / 3;
        this.draw(graphicsHandler, x, y);
    }

    int lastX = -1;
    int lastY = -1;
    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        this.lastX = x;
        this.lastY = y;
        int imageSize = 48;
        int buttonWidth = 120;
        int buttonHeight = 40;
        int buttonMargin = 7;
        handler.drawFilledRectangle(x, y, this.getDrawWidth(this.isStandalone), this.getDrawHeight(this.isStandalone), TailwindColorScheme.rose600);
        for (int i = 0; i < this.slots.length; ++i) {
            var slot = this.slots[i];
            var rec = this.saveBounds[i];
            // slot.draw(handler, (x + 7 + (i%maxSlotsX) * (SaveSlot.SAVE_SLOT_WIDTH + 7)) + (this.getDrawWidth() - maxSlotsX * (SaveSlot.SAVE_SLOT_WIDTH + 7)) / 2, y + (i/maxSlotsX) * (SaveSlot.SAVE_SLOT_HEIGHT + 7) + 20);
            slot.draw(handler, x + rec.x, y + rec.y);
        }
        // TODO: move this to a one time/reposition on width-height change
        this.saveButtonBounds.setBounds(
            x + (this.getDrawWidth(this.isStandalone) - buttonWidth*2) / 2 - buttonMargin,
            y + (this.saveBounds[this.saveBounds.length-1].y + this.saveBounds[this.saveBounds.length-1].height) + 20,
            buttonWidth,
            buttonHeight);
        this.loadButtonBounds.setBounds(
            x + (this.getDrawWidth(this.isStandalone) - buttonWidth*2) / 2 + buttonMargin + buttonWidth,
            this.saveButtonBounds.y,
            buttonWidth,
            buttonHeight);
        this.leftButtonBounds.setBounds(
            x + (this.getDrawWidth(this.isStandalone) - imageSize)/2 - (buttonWidth + buttonMargin + 35),
            this.saveButtonBounds.y - 6,
            imageSize,
            imageSize
        );
        this.rightButtonBounds.setBounds(
            x + (this.getDrawWidth(this.isStandalone) - imageSize)/2 + (buttonWidth + buttonMargin + 35),
            this.leftButtonBounds.y,
            imageSize,
            imageSize
        );
        handler.drawImage(
            ImageLoader.load("left.png", new Color(0, true)),
            this.leftButtonBounds.x,
            this.leftButtonBounds.y,
            this.leftButtonBounds.width,
            this.leftButtonBounds.height
        );

        handler.drawFilledRectangle(this.saveButtonBounds.x, this.saveButtonBounds.y, this.saveButtonBounds.width, this.saveButtonBounds.height, this.selectedElement == 1 ? BUTTON_COLOR_HOVER : BUTTON_COLOR_BASE);
        handler.drawString("Save", this.saveButtonBounds.x + (this.saveButtonBounds.width - 4 * 14)/2, this.saveButtonBounds.y + (this.saveButtonBounds.width - 14*3)/3, Resources.press_start.deriveFont(14f), TailwindColorScheme.white);
        handler.drawFilledRectangle(this.loadButtonBounds.x, this.loadButtonBounds.y, this.loadButtonBounds.width, this.loadButtonBounds.height, this.selectedElement == 2 ? BUTTON_COLOR_HOVER : BUTTON_COLOR_BASE);
        handler.drawString("Load", this.loadButtonBounds.x + (this.loadButtonBounds.width - 4 * 15)/2, this.loadButtonBounds.y + (this.loadButtonBounds.width - 14*3)/3, Resources.press_start.deriveFont(14f), TailwindColorScheme.white);

        handler.drawImage(ImageLoader.load("right.png", new Color(0, true)), this.rightButtonBounds.x, this.rightButtonBounds.y, this.rightButtonBounds.width, this.rightButtonBounds.height);
    }

}
