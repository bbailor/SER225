package Screens;

import java.util.random.RandomGenerator;

import Engine.GlobalKeyboardHandler;
import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.*;
import Maps.TestMap;
import Players.Gnome;
import Utils.Direction;
import Utils.MenuListener;

// This class is for when the RPG game is actually being played
public class PlayLevelScreen extends Screen implements GameListener, MenuListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected Inventory inventory;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected InventoryScreen inventoryScreen;
    protected LoseScreen loseScreen;
    protected FlagManager flagManager;
    protected BattleScreen battleScreen;
    protected int menuClosecD = 0;

    protected static final String LISTENER_NAME = "play_level_screen";

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        if (playLevelScreenState == PlayLevelScreenState.RUNNING) {
            return;
        }
        this.inventory = new Inventory(9);
        // setup state
        flagManager = new FlagManager();
        flagManager.addFlag("hasLostBall", false);
        flagManager.addFlag("hasEnteredDenial", false);
        flagManager.addFlag("hasTalkedToWizard", false);
        flagManager.addFlag("hasTalkedToDenialEnemy", false);
        flagManager.addFlag("hasTalkedToWalrus", false);
        flagManager.addFlag("hasTalkedToDinosaur", false);
        flagManager.addFlag("hasFoundBall", false);

        // define/setup map
        map = new TestMap();
        map.setFlagManager(flagManager);

        // setup player
        player = new Gnome(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        player.setMap(map);
        playLevelScreenState = PlayLevelScreenState.RUNNING;
        player.setFacingDirection(Direction.LEFT);

        map.setPlayer(player);

        // let pieces of map know which button to listen for as the "interact" button
        map.getTextbox().setInteractKey(player.getInteractKey());

        // add this screen as a "game listener" so other areas of the game that don't normally have direct access to it (such as scripts) can "signal" to have it do something
        // this is used in the "onWin" method -- a script signals to this class that the game has been won by calling its "onWin" method
        map.addListener(this);

        // preloads all scripts ahead of time rather than loading them dynamically
        // both are supported, however preloading is recommended
        map.preloadScripts();

        winScreen = new WinScreen(this);
        this.loseScreen = new LoseScreen(this);

        this.inventoryScreen = new InventoryScreen(this.inventory, this.player.getEntity());
        this.inventory.setStack(4, new ItemStack(Item.ItemList.test_item, 3));
        this.inventory.setStack(8, new ItemStack(Item.ItemList.test_item2, 8));
        this.inventoryScreen.addistener("play_level_screen", this);
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
            case LOST:
                this.loseScreen.update();
                break;
        }
        GlobalKeyboardHandler.runHandlers(this.screenCoordinator);
        if (Keyboard.isKeyDown(Key.E) && menuClosecD <= 0) {
            this.inventoryScreen.initialize();
            this.inventoryScreen.open();
            this.playLevelScreenState = PlayLevelScreenState.INVENTORY;
        }
        if (Keyboard.isKeyDown(Key.B) && this.battleScreen == null) {
            this.battleScreen = new BattleScreen(this.inventory, this.player, new Entity() {
                {
                    maxHealth = health = RandomGenerator.getDefault().nextDouble(1, 10);
                    baseAttack = RandomGenerator.getDefault().nextDouble(1, 2);
                }
            });
            this.battleScreen.open();
            this.battleScreen.addistener(LISTENER_NAME, this);
            this.playLevelScreenState = PlayLevelScreenState.BATTLE;
        }
        --menuClosecD;
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
        RUNNING, LEVEL_COMPLETED, INVENTORY, BATTLE, LOST
    }

    @Override
    public void onMenuClose() {
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
        this.battleScreen = null;
        menuClosecD = 12;
    }

    @Override
    public void onEvent(String eventName, Object... args) {
        if (eventName.equals("player_lose")) {
            this.playLevelScreenState = PlayLevelScreenState.LOST;
            this.battleScreen = null;
        }
    }
}
