package Screens;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Engine.Mouse;
import Level.Entity;
import Level.FlagManager;
import Level.Item;
import Level.ItemStack;
import Level.Player;
import Screens.submenus.ControlsSubMenu;
import Screens.submenus.InventorySubmenu;
import Screens.submenus.QuestSubMenu;
import Screens.submenus.SaveSubmenu;
import Screens.submenus.SelectionSubmenu;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.SaveData;
import Utils.TailwindColorScheme;

// This is the class for the main menu screen
public class MenuScreen extends Screen implements Menu, MenuListener {

    protected List<String> selections;
    protected SelectionSubmenu selector;
    @Nullable protected String selectedAction;
    protected Player player;
    protected String status = "";
    protected int openMenuCD = 0;
    protected Map<String, Menu> actions = new HashMap<>();
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected Rectangle selectorRec = new Rectangle(
        2,
        (int) (Config.GAME_WINDOW_HEIGHT * .25f)-6,
        (int) (Config.GAME_WINDOW_WIDTH * .3f),
        (int) (Config.GAME_WINDOW_HEIGHT * .7f)
    );
    protected Rectangle menuStart = new Rectangle(
        this.selectorRec.width,
        2,
        Config.GAME_WINDOW_WIDTH - (int) (Config.GAME_WINDOW_WIDTH * .3f) - 9,
        (int)(Config.GAME_WINDOW_HEIGHT * .8f)
    );
    protected Rectangle menuInfo = new Rectangle(
        this.menuStart.x,
        Config.GAME_WINDOW_HEIGHT - (Config.GAME_WINDOW_HEIGHT - this.menuStart.height) + 2,
        this.menuStart.width,
        (Config.GAME_WINDOW_HEIGHT - this.menuStart.height) - 39 // why 39???
    );
    public static final String MENU_LISTENER_NAME = "MenuScreen";

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
        this.selectedAction = null;
        this.openMenuCD = Globals.KEYBOARD_CD;
    }

    @Override
    public void initialize() {
        
    }

    public void setPlayer(Player player) {
        var inv = ((InventorySubmenu) this.actions.get("Inventory"));
        inv.setEntity(player.getEntity());
    }

    public void setFlagManager(FlagManager flagManager) {
        if (this.actions.get("Quests") != null) {
            ((QuestSubMenu) this.actions.get("Quests")).setFlagManager(flagManager);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        float nmt_size = 24f;
        
        // Background
        graphicsHandler.drawFilledRectangle(2, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.slate800);
        
        // Preview + Selector
        graphicsHandler.drawFilledRectangleWithBorder(2, 2, (int) (Config.GAME_WINDOW_WIDTH * .3f), (int) (Config.GAME_WINDOW_HEIGHT * .25f)-7, TailwindColorScheme.zinc500, TailwindColorScheme.slate700, 5);
        this.selector.draw(graphicsHandler, (int) this.selectorRec.getX(), (int) this.selectorRec.getY());

        // this.selector.draw(graphicsHandler, 0, (int) (Config.GAME_WINDOW_HEIGHT * .24f));
        
        // Menu
        graphicsHandler.drawFilledRectangleWithBorder(this.menuStart, TailwindColorScheme.slate400, TailwindColorScheme.slate700, 5);
        if (this.selectedAction == null) {
            String no_menu_text = "No Menu Selected";
            graphicsHandler.drawString(no_menu_text, (int) (this.menuStart.getX() + (this.menuStart.getWidth() - (no_menu_text.length() * nmt_size))/2), (int) (this.menuStart.getY() + (this.menuStart.getWidth() - nmt_size)/2), Resources.press_start.deriveFont(nmt_size), TailwindColorScheme.white);
        } else {
            this.actions.get(this.selectedAction).draw(graphicsHandler, (int) (this.menuStart.getX() + 3), (int) (this.menuStart.getY() + 3));
        }

        // Menu Info
        graphicsHandler.drawFilledRectangleWithBorder(this.menuInfo, TailwindColorScheme.zinc500, TailwindColorScheme.slate700, 5);
        var statusSplit = this.status.split("\n");
        for (int i = 0; i < statusSplit.length; i++) {
            var _status = statusSplit[i];
            graphicsHandler.drawString(_status, (int)this.menuInfo.getX() + 5, (int)this.menuInfo.getY() + 5 + 14 + i*14, Resources.press_start.deriveFont(12f), TailwindColorScheme.white);
        }
    }

    @Override
    public void update() {
        if (this.openMenuCD > 0) {
            --this.openMenuCD;
        }
        if (this.selectedAction == null || this.selectorRec.contains(Mouse.getCurrentPosition())) {
            this.selector.update();
            if (Keyboard.isKeyDown(Key.ESC) && this.openMenuCD == 0) {
                this.close();
            }
        } else {
            this.actions.get(this.selectedAction).update();
        }
    }

    @Override
    public void onMenuClose() {
        this.selectedAction = null;
        this.status = "";
    }

    @Override
    public void onEvent(String eventName, Object... args) {
        switch (eventName) {
            case SelectionSubmenu.SUBMENU_SELECTION_EVENT: {
                String action = (String) args[0];
                switch (action) {
                    case "Resume": {
                        this.close();
                        break;
                    }
                    case "Quit": {
                        System.exit(0);
                        break;
                    }
                    default: {
                        if (this.actions.containsKey(action)) {
                            this.selectedAction = action;
                            this.actions.get(action).open();
                            break;
                        }
                        System.out.printf("Running %s.%n", action);
                        break;
                    }
                }
                break;
            }
            case STATUS_EVENT: {
                this.status = (String) args[0];
                break;
            }
            default: {
                // Pass to playlevelscreen
                this.sendEvent(eventName, args);
            }
        }
    }

    public MenuScreen() {
        this.actions.put("Save/Load", new SaveSubmenu(this.menuStart.width - 5, this.menuStart.height - 4));
        this.actions.put("Inventory", new InventorySubmenu(new Inventory(90) {
            {
                this.addStack(new ItemStack(Item.ItemList.apple, 5));
            }
        }, new Entity())
            .setColumns(((this.menuStart.width) / InventorySubmenu.INV_SLOT_WIDTH) - 1)
        );
        this.actions.put("Quests", new QuestSubMenu());
        this.actions.put("Controls", new ControlsSubMenu());
        this.selections = new ArrayList<>(this.actions.keySet()) {
            {
                add(0, "Resume");
                // TODO: Switch control screen to the actions above
                add("Quit");
            }
        };
        this.selector = new SelectionSubmenu(this.selectorRec.width, this.selectorRec.height, this.selections);
        this.selector.addistener(MENU_LISTENER_NAME, this);
        for (Menu menu : this.actions.values()) {
            menu.addistener(MENU_LISTENER_NAME, this);
        }
    }
    
}
