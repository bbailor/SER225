package Screens;

import com.google.gson.annotations.Expose;

import Engine.Config;
import Engine.GlobalKeyboardHandler;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;

import Game.GameState;
import Game.ScreenCoordinator;

import GameObject.Frame;
import GameObject.SpriteSheet;
import Builders.FrameBuilder;

import Level.Entity;
import Level.FlagManager;
import Level.GameListener;
import Level.Item;
import Level.ItemStack;
import Level.Map;
import Level.MapEntity;
import Level.NPC;
import Level.Player;

import Maps.TestMap;
import Players.Gnome;

import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.ArmoredSkeleton;
import NPCs.DenialBoss;

import Utils.Direction;
import Utils.Globals;
import Utils.MenuListener;
import Utils.SaveData;

public class PlayLevelScreen extends Screen implements GameListener, MenuListener {
    protected ScreenCoordinator screenCoordinator;
    @Expose protected Map map;
    @Expose protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected InventoryScreen inventoryScreen;
    protected LoseScreen loseScreen;
    protected SaveScreen saveScreen;
    @Expose protected FlagManager flagManager;
    protected BattleScreen battleScreen;
    protected int menuCloseCD = 0;
    protected int pressCD = 0;

    protected static final String LISTENER_NAME = "play_level_screen";

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void switchMap(Map map) {
        this.map = map;
        this.map.setFlagManager(this.flagManager);

        this.map.getTextbox().setInteractKey(player.getInteractKey());
        this.map.setPlayer(this.player);
        this.player.setMap(map);
        this.player.setLocation(this.map.getPlayerStartPosition().x, this.map.getPlayerStartPosition().y);
        this.player.setFacingDirection(Direction.LEFT);
        this.player.unlock();
        this.map.getCamera().setLocation(this.player.getX(), this.player.getY());
        this.map.addListener(this);
        this.map.preloadScripts();
    }

    public void loadSave(SaveData data) {
        this.player = data.player;
        this.flagManager = data.flagManager;
        this.switchMap(data.map);
        this.player.setLocation(data.playerPos.x, data.playerPos.y);
    }

    public void initialize() {
        if (playLevelScreenState == PlayLevelScreenState.RUNNING) {
            return;
        }

        // setup flags
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
        player.getEntity().getInventory().setStack(4, new ItemStack(Item.ItemList.test_item, 3));
        player.getEntity().getInventory().setStack(8, new ItemStack(Item.ItemList.test_item2, 8));
        playLevelScreenState = PlayLevelScreenState.RUNNING;

        // load map
        this.switchMap(new TestMap());

        winScreen = new WinScreen(this);
        this.loseScreen = new LoseScreen(this);

        this.inventoryScreen = new InventoryScreen(this.player.getEntity().getInventory(), this.player.getEntity());
        this.inventoryScreen.addistener(LISTENER_NAME, this);

        this.saveScreen = new SaveScreen(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT);
        this.saveScreen.addistener(LISTENER_NAME, this);
    }

    public void update() {
        switch (playLevelScreenState) {
            case RUNNING:
                player.update();
                map.update(player);
                break;
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
    }

    /**
     * Creates an Entity with a single "idle" frame that matches the interacted NPC's sprite.
     * This is used when the "start_battle" event is triggered (Yes pressed).
     */
    private Entity buildEnemyEntityFor(MapEntity me) {
        String path = null;
        int spriteW = 32;
        int spriteH = 32;
        int scale   = 3;

        int enemyHealth = 10;   // default health
        int enemyAttack = 2;    // default attack

        if (me instanceof Skeleton) {
            path = "Enemies/skeleton.png";
        } else if (me instanceof Spirit) {
            path = "Enemies/spirit.png";
        } else if (me instanceof ArmoredSkeleton) {
            path = "Enemies/armored_skeleton.png";
            enemyHealth = 15;  // Armored Skeleton HP
        } else if (me instanceof DenialBoss) {
            path = "Bosses/DenialBoss.png";
            spriteW = 120;
            spriteH = 120;
            enemyHealth = 50;  // Boss HP
        } else if (me instanceof NPC) {
            // generic NPC fallback
            return new Entity() {
                {
                    maxHealth = health = 10;
                    baseAttack = 2;
                }
            };
        } else {
            // non-NPC fallback
            return new Entity() {
                {
                    maxHealth = health = 10;
                    baseAttack = 2;
                }
            };
        }

        final String finalPath = path;
        final int w = spriteW, h = spriteH, sc = scale;
        final int fHealth = enemyHealth;
        final int fAttack = enemyAttack;

        return new Entity() {
            {
                maxHealth = health = fHealth;
                baseAttack = fAttack;

                SpriteSheet ss = new SpriteSheet(ImageLoader.load(finalPath), w, h);
                Frame idle = new FrameBuilder(ss.getSprite(0, 0), 9999)
                        .withScale(sc)
                        .build();
                this.animations.put("idle", new Frame[] { idle });
            }
        };
    }

    @Override
    public void onWin() {
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    public void draw(GraphicsHandler graphicsHandler) {
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
            case SAVE:
                this.map.draw(graphicsHandler);
                this.saveScreen.draw(graphicsHandler);
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
    public void onEvent(String eventName, Object... args) {
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
                this.loadSave(Globals.loadSave((int) args[0]));
                break;
            }
        }

        // Start battle event
        if (eventName.equals("start_battle") && args.length > 0 && args[0] instanceof MapEntity me) {
            // Debugging messsage
            // System.out.println("[PlayLevelScreen] start_battle received with: " + me.getClass().getSimpleName());
            Entity enemy = buildEnemyEntityFor(me);

            boolean isBossBattle = me instanceof DenialBoss;
            this.battleScreen = new BattleScreen(this.player.getEntity().getInventory(), this.player, enemy, isBossBattle);
            this.battleScreen.open();
            this.battleScreen.addistener(LISTENER_NAME, this);
            this.playLevelScreenState = PlayLevelScreenState.BATTLE;
        }
    }
}
