package Screens.submenus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Engine.Mouse;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class SelectionSubmenu implements Menu {

    protected int selectedID = 0;
    protected int pressCD = 0;
    protected int width;
    protected int height;
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected List<String> selections;
    protected Color hoverColor = Globals.HOVER_COLOR;
    protected Rectangle[] selectionBounds;

    public static final int FONT_SIZE = 12;
    public static final int BORDER_WIDTH = 4;
    public static final String SUBMENU_SELECTION_EVENT = "selector.submenu";

    public SelectionSubmenu(int width, int height, List<String> selectors) {
        // super(x, y, width, height);
        this.selections = selectors;
        this.width = width;
        this.height = height;
        this.selectionBounds = new Rectangle[selectors.size()];
        for (int i = 0; i < this.selectionBounds.length; ++i) {
            this.selectionBounds[i] = new Rectangle(
                5,
                10 + i * (FONT_SIZE + 8 + BORDER_WIDTH),
                this.width - 12,
                BORDER_WIDTH+8+FONT_SIZE
            );
        }
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        this.selectedID = 0;
    }

    @Override
    public void update() {
        this.handleMouseMovement();
        if (this.pressCD > 0) {
            this.pressCD--;
            return;
        }
        this.handleMouseActions();
        if (Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.selectedID = this.selectedID + 1 >= selections.size() ? 0 : this.selectedID + 1;
        }
        if (Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.selectedID = this.selectedID - 1 < 0 ? this.selections.size() - 1 : this.selectedID - 1;
        }
        if (Keyboard.isKeyDown(Key.ENTER) || Keyboard.isKeyDown(Key.SPACE)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.sendEvent(SUBMENU_SELECTION_EVENT, this.selections.get(this.selectedID));
        }
    }

    public void handleMouseActions() {
        if (Mouse.isLeftClickDown()) {
            this.pressCD = Math.max(12, Globals.KEYBOARD_CD);
            this.sendEvent(SUBMENU_SELECTION_EVENT, this.selections.get(this.selectedID));
        }
    }

    protected Point lastMousePos;
    public void handleMouseMovement() {
        var mousePos = Mouse.getCurrentPosition();
        if (mousePos.equals(lastMousePos)) {
            return;
        }
        for (int i = 0; i < this.selectionBounds.length; ++i) {
            var rec = new Rectangle(this.selectionBounds[i]);
            rec.translate(this.lastX, this.lastY);
            if (rec.contains(mousePos)) {
                this.selectedID = i;
                break;
            }
        }
    }

    protected int lastX;
    protected int lastY;
    @Override
    public void draw(GraphicsHandler graphicsHandler, int x, int y) {
        this.lastX = x;
        this.lastY = y;
        graphicsHandler.drawFilledRectangleWithBorder(
            x,
            y,
            this.width,
            this.height,
            TailwindColorScheme.slate400,
            TailwindColorScheme.slate800,
            5
        );
        int offset_y = 30;
        for (int i = 0; i < this.selections.size(); ++i) {
            String name = this.selections.get(i);
            
            graphicsHandler.drawStringWithOutline(
                name,
                x + (this.width - (name.length() * FONT_SIZE)) / 2,
                y + offset_y,
                Resources.PRESS_START.deriveFont(Font.PLAIN, FONT_SIZE),
                TailwindColorScheme.white,
                TailwindColorScheme.slate700,
                3
            );
            offset_y += FONT_SIZE + 8 + BORDER_WIDTH;
        }
        graphicsHandler.drawRectangle(x + this.selectionBounds[this.selectedID].x, y + this.selectionBounds[this.selectedID].y,  + this.selectionBounds[this.selectedID].width,  this.selectionBounds[this.selectedID].height, this.hoverColor);
    }
    
}
