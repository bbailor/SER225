package Screens;

import com.google.gson.annotations.Expose;

import Engine.Config;
import Engine.GlobalKeyboardHandler;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.FlagManager;
import Level.GameListener;
import Level.Item;
import Level.ItemStack;
import Level.Map;
import Level.Player;
import Maps.TestMap;
import Players.Gnome;
import Utils.Direction;
import Utils.Globals;
import Utils.MenuListener;
import Utils.SaveData;

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener, MenuListener {
    protected ScreenCoordinator screenCoordinator;
    @Expose protected Map map;
    @Expose protected Player player;
    // @Expose protected Inventory inventory;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected InventoryScreen inventoryScreen;
    protected LoseScreen loseScreen;
    protected SaveScreen saveScreen;
    @Expose protected FlagManager flagManager;
    protected BattleScreen battleScreen;
    protected int menuCloseCD = 0;
    protected int pressCD = 0;
    // String save;

    protected static final String LISTENER_NAME = "play_level_screen";

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void switchMap(Map map) {
        this.map = map;
        this.map.setFlagManager(this.flagManager);

        // let pieces of map know which button to listen for as the "interact" button
        this.map.getTextbox().setInteractKey(player.getInteractKey());
        this.map.setPlayer(this.player);
        this.player.setMap(map);
        this.player.setLocation(this.map.getPlayerStartPosition().x, this.map.getPlayerStartPosition().y);
        this.player.setFacingDirection(Direction.LEFT);
        this.player.unlock();
        this.map.getCamera().setLocation(this.player.getX(), this.player.getY());
        
        // add this screen as a "game listener" so other areas of the game that don't normally have direct access to it (such as scripts) can "signal" to have it do something
        // this is used in the "onWin" method -- a script signals to this class that the game has been won by calling its "onWin" method
        this.map.addListener(this);

        // preloads all scripts ahead of time rather than loading them dynamically
        // both are supported, however preloading is recommended
        this.map.preloadScripts();
    }

    public void loadSave(SaveData data) {
        this.player = data.player;
        // this.inventory = data.inventory;
        this.flagManager = data.flagManager;
        this.switchMap(data.map);
        this.player.setLocation(data.playerPos.x, data.playerPos.y);
    }

    public void initialize() {
        if (playLevelScreenState == PlayLevelScreenState.RUNNING) {
            return;
        }
        
        // setup state
        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall", false);
        flagManager.addFlag("hasEnteredDenial", false);
        flagManager.addFlag("hasTalkedToWizard", false);
        flagManager.addFlag("hasTalkedToDenialEnemy", false);
        flagManager.addFlag("hasTalkedToWalrus", false);
        flagManager.addFlag("hasTalkedToDinosaur", false);
        flagManager.addFlag("hasFoundBall", false);

        // setup player
        player = new Gnome(0, 0);
        // inventory = new Inventory(9);
        player.getEntity().getInventory().setStack(4, new ItemStack(Item.ItemList.test_item, 3));
        player.getEntity().getInventory().setStack(8, new ItemStack(Item.ItemList.test_item2, 8));
        // player.setMap(map);
        playLevelScreenState = PlayLevelScreenState.RUNNING;
        
        // define/setup map
        this.switchMap(new TestMap());

        winScreen = new WinScreen(this);
        this.loseScreen = new LoseScreen(this);

        this.inventoryScreen = new InventoryScreen(this.player.getEntity().getInventory(), this.player.getEntity());
        this.inventoryScreen.addistener(LISTENER_NAME, this);
        
        this.saveScreen = new SaveScreen(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        this.saveScreen.addistener(LISTENER_NAME, this);
        // var str = gson.toJson(new SaveData(map, player, inventory, flagManager));
        // var temp = gson.fromJson(str, SaveData.class);
        // var _str = gson.toJson(this.inventory);
        // var __str = gson.toJson(new ItemStack(Item.ItemList.test_item));
        // try {
        //     Files.writeString(Path.of("Resources/test_save.json"), str);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        // System.out.println(temp);
    }

    public void update() {
        // based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // if level is "running" update player and map to keep game logic for the platformer level going
            case RUNNING:
                player.update();
                map.update(player);
                break;
            // if level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                winScreen.update();
                break;
            case INVENTORY:
                this.inventoryScreen.update();
                break;
            case BATTLE:
                this.battleScreen.update();
                break;
            case SAVE:
                this.saveScreen.update();
                break;
            case LOST:
                this.loseScreen.update();
                break;
        }
        GlobalKeyboardHandler.runHandlers(this.screenCoordinator);
        // if ()
        // if (Keyboard.isKeyDown(Key.B) && this.battleScreen == null) {
        //     this.battleScreen = new BattleScreen(this.player, new Entity() {
        //         {
        //             maxHealth = health = RandomGenerator.getDefault().nextDouble(1, 10);
        //             baseAttack = RandomGenerator.getDefault().nextDouble(1, 2);
        //         }
        //     });
        //     this.battleScreen.open();
        //     this.battleScreen.addistener(LISTENER_NAME, this);
        //     this.playLevelScreenState = PlayLevelScreenState.BATTLE;
        // }
        --menuCloseCD;
        if (this.pressCD >= 0) {
            this.pressCD--;
            return;
        }
        if (Keyboard.isKeyDown(Key.E) && menuCloseCD <= 0) {
            this.inventoryScreen.initialize();
            this.inventoryScreen.open();
            this.playLevelScreenState = PlayLevelScreenState.INVENTORY;
        }
        if (Keyboard.isKeyDown(Key.L)) {
            this.saveScreen.open();
            this.playLevelScreenState = PlayLevelScreenState.SAVE;
        }
        // if (Keyboard.isKeyDown(Key.L) && save != null) {
        //     this.loadSave(Globals.GSON.fromJson(this.save, SaveData.class));
        //     this.save = null;
        //     this.pressCD = Globals.KEYBOARD_CD;
        // }
        // if (Keyboard.isKeyDown(Key.K)) {
        //     this.save = Globals.GSON.toJson(new SaveData(this.map, this.player, this.flagManager));
        //     this.pressCD = Globals.KEYBOARD_CD;
        // }
    }

    @Override
    public void onWin() {
        // when this method is called within the game, it signals the game has been "won"
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(player, graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
            case INVENTORY:
                this.map.draw(this.player, graphicsHandler);
                this.inventoryScreen.draw(graphicsHandler);
                break;
            case SAVE: {
                this.map.draw(graphicsHandler);
                this.saveScreen.draw(graphicsHandler);
                break;
            }
            case BATTLE:
                this.battleScreen.draw(graphicsHandler);
                break;
            case LOST:
                this.loseScreen.draw(graphicsHandler);
                break;
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, INVENTORY, BATTLE, LOST, SAVE
    }

    @Override
    public void onMenuClose() {
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
        this.battleScreen = null;
        menuCloseCD = 12;
    }

    @Override
    public void onEvent(String eventName, Object... args) { //handle events sent from script action  ////////////////////////
        switch (eventName) {
            case "player_lose": {
                this.playLevelScreenState = PlayLevelScreenState.LOST;
                this.battleScreen = null;
                break;
            }
            case "save": {
                Globals.saveToFile(new SaveData(this.map, this.player, this.flagManager), (int) args[0]);
                break;
            }
            case "load": {
                this.loadSave(
                    Globals.loadSave((int) args[0])
                );
                break;
            }
        }
    }
}
