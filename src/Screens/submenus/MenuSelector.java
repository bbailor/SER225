package Screens.submenus;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import Engine.GraphicsHandler;
import Utils.Menu;
import Utils.MenuListener;
import Utils.TailwindColorScheme;

public class MenuSelector implements Menu {

    protected int width;
    protected int height;
    protected Map<String, MenuListener> listeners = new HashMap<>();

    protected static Color BACKGROUND_COLOR = TailwindColorScheme.slate600;

    public MenuSelector(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        handler.drawFilledRectangleWithBorder(x, y, this.width, this.height, BACKGROUND_COLOR, TailwindColorScheme.slate400, 5);
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        
    }
    
}
