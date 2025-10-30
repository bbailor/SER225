package Screens;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Screen;
import GameObject.Rectangle;
import Screens.submenus.MenuSelector;
import Utils.Menu;
import Utils.MenuListener;
import Utils.TailwindColorScheme;

// This is the class for the main menu screen
public class MenuScreen extends Screen implements Menu {

    public Map<String, MenuListener> listeners = new HashMap<>();
    public MenuSelector selector = new MenuSelector((int) (Config.GAME_WINDOW_WIDTH * .3f), (int) (Config.GAME_WINDOW_HEIGHT * .7f));
    public Rectangle menuStart = new Rectangle((int) (Config.GAME_WINDOW_WIDTH * .3f), 0, 0, 0);
    public Rectangle menuInfo = new Rectangle((int) (Config.GAME_WINDOW_WIDTH * .3f), 0, 0, 0);

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        draw(handler);
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        this.menuStart = new Rectangle(
            (Config.GAME_WINDOW_WIDTH * .3f) + 2,
            2,
            Config.GAME_WINDOW_WIDTH - (int) (Config.GAME_WINDOW_WIDTH * .3f) - 9,
            (int)(Config.GAME_WINDOW_HEIGHT * .8f)
        );
        this.menuInfo = new Rectangle(
            this.menuStart.getX(),
            Config.GAME_WINDOW_HEIGHT - (Config.GAME_WINDOW_HEIGHT - this.menuStart.getHeight()) + 2,
            this.menuStart.getWidth(),
            (Config.GAME_WINDOW_HEIGHT - this.menuStart.getHeight()) - 39 // why 39???
        );
        
        
        // Background
        graphicsHandler.drawFilledRectangle(2, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.slate800);
        
        // Preview + Selector
        graphicsHandler.drawFilledRectangleWithBorder(2, 2, (int) (Config.GAME_WINDOW_WIDTH * .3f), (int) (Config.GAME_WINDOW_HEIGHT * .25f)-7, TailwindColorScheme.zinc500, TailwindColorScheme.slate400, 5);
        this.selector.draw(graphicsHandler, 2, (int) (Config.GAME_WINDOW_HEIGHT * .25f)-6);

        graphicsHandler.drawFilledRectangleWithBorder(this.menuStart, TailwindColorScheme.slate600, TailwindColorScheme.slate400, 5);
        graphicsHandler.drawFilledRectangleWithBorder(this.menuInfo, TailwindColorScheme.zinc500, TailwindColorScheme.slate400, 5);
        // this.selector.draw(graphicsHandler, 0, (int) (Config.GAME_WINDOW_HEIGHT * .24f));
    }

    @Override
    public void update() {
        
    }
    
}
