package Screens.submenus;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Utils.Globals;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class SelectionBattleMenu extends BattleSubMenu {

    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected int selectedID = 0;
    protected int pressCD = 0;
    protected List<String> selections;
    protected Color hoverColor = Globals.HOVER_COLOR;

    public static final int FONT_SIZE = 12;
    public static final int BORDER_WIDTH = 4;
    public static final String SUBMENU_SELECTION_NAME = "selector.submenu";

    public SelectionBattleMenu(int x, int y, int width, int height, List<String> selectors) {
        super(x, y, width, height);
        this.selections = selectors;
        
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
        // No Open (perm open)
    }

    @Override
    public void initialize() {
        this.selectedID = 0;
    }

    @Override
    public void update() {
        if (this.pressCD > 0) {
            this.pressCD--;
            return;
        }
        if (Keyboard.isKeyDown(Key.DOWN)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.selectedID = this.selectedID + 1 >= selections.size() ? 0 : this.selectedID + 1;
        }
        if (Keyboard.isKeyDown(Key.UP)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.selectedID = this.selectedID - 1 < 0 ? this.selections.size() - 1 : this.selectedID - 1;
        }
        if (Keyboard.isKeyDown(Key.ENTER)) {
            this.pressCD = Globals.KEYBOARD_CD;
            this.sendEvent(SUBMENU_SELECTION_NAME, this.selections.get(this.selectedID));
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(
            this.x,
            this.y,
            this.width,
            this.height,
            TailwindColorScheme.rose400
        );
        int offset_y = 30;
        for (int i = 0; i < this.selections.size(); ++i) {
            String name = this.selections.get(i);
            
            graphicsHandler.drawStringWithOutline(
                name,
                this.x + (this.width - (name.length() * FONT_SIZE)) / 2,
                this.y + offset_y,
                Resources.press_start.deriveFont(Font.PLAIN, FONT_SIZE),
                TailwindColorScheme.white,
                TailwindColorScheme.slate700,
                3
            );
            offset_y += FONT_SIZE + 8 + BORDER_WIDTH;
        }
        graphicsHandler.drawRectangle(this.x, this.y + 10 + this.selectedID * (FONT_SIZE + 8 + BORDER_WIDTH), this.width, BORDER_WIDTH+8+FONT_SIZE, this.hoverColor);
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        //TODO: Implement when submenu (take above draw)
    }

    
}
