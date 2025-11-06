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
import Level.MapEntityStatus;
import Level.NPC;
import Level.Player;
import Level.Weapon;
import Maps.TestMap;
import Players.Gnome;
import Screens.submenus.InventorySubmenu;
import Screens.submenus.SaveSubmenu;
import ScriptActions.FlagRequirement;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.AngerBoss;
import NPCs.ArmoredSkeleton;
import NPCs.BargainingBoss;
import NPCs.DenialBoss;
import NPCs.DepressionBoss;
import Utils.Direction;
import Utils.Globals;
import Utils.MenuListener;
import Utils.Resources;
import Utils.SaveData;
import Utils.TailwindColorScheme;

public class PlayLevelScreen extends Screen implements GameListener, MenuListener {
    protected ScreenCoordinator screenCoordinator;
    @Expose protected Map map;
    @Expose protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected WinScreen winScreen;
    protected InventorySubmenu inventoryScreen; //FOR CHECK ONLY NOW
    protected LoseScreen loseScreen;
    // protected SaveSubmenu saveScreen;
    protected MenuScreen menuScreen;
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
        this.menuScreen.setInventory(this.player.getEntity().getInventory());
        this.menuScreen.setFlagManager(this.flagManager);
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
        flagManager.addFlag("hasTalkedToDenialBoss", false);
        flagManager.addFlag("hasTalkedToDepressionBoss", false);
        flagManager.addFlag("hasTalkedToAngerBoss", false);
        flagManager.addFlag("wizardQuestStarted", false);
        flagManager.addFlag("wizardSaved", false);
        flagManager.addFlag("wizardRewardGiven", false);
        flagManager.addFlag("hasEnteredAnger", false);
        flagManager.addFlag("hasDefeatedDenial", false);
        flagManager.addFlag("hasEnteredDepression",false);
        flagManager.addFlag("hasTalkedToBargainingBoss", false);
        
        
        // setup player
        player = new Gnome(0, 0);
        // player.getEntity().getInventory().setStack(4, new ItemStack(Item.ItemList.test_item, 3));
        // player.getEntity().getInventory().setStack(8, new ItemStack(Item.ItemList.test_item2, 8));
        playLevelScreenState = PlayLevelScreenState.RUNNING;

        // load map
        this.switchMap(new TestMap());

        winScreen = new WinScreen(this);
        this.loseScreen = new LoseScreen(this);

        // this.inventoryScreen = new InventorySubmenu(this.player.getEntity().getInventory(), this.player.getEntity());
        // this.inventoryScreen.addistener(LISTENER_NAME, this);

        // this.saveScreen = new SaveSubmenu(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, true);
        // this.saveScreen.addistener(LISTENER_NAME, this);

        this.menuScreen = new MenuScreen();
        this.menuScreen.setInventory(this.player.getEntity().getInventory());
        this.menuScreen.setFlagManager(this.flagManager);
        this.menuScreen.addistener(LISTENER_NAME, this);
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
                this.map.getTextbox().update();
                break;
            case BATTLE:
                this.battleScreen.update();
                break;
            case MENU:
                this.menuScreen.update();
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

        // if (Keyboard.isKeyDown(Key.E) && menuCloseCD <= 0) {
        //     // this.inventoryScreen.initialize();
        //     this.inventoryScreen.open();
        //     this.playLevelScreenState = PlayLevelScreenState.INVENTORY;
        // }

        if (Keyboard.isKeyDown(Key.ESC) && this.battleScreen == null) {
            this.menuScreen.open();
            this.playLevelScreenState = PlayLevelScreenState.MENU;
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
        } else if (me instanceof DepressionBoss) {
            path = "Bosses/DepressionBoss.png";
            spriteW = 120;
            spriteH = 120;
            enemyHealth = 65;  // Depression Boss HP
        } else if (me instanceof AngerBoss) {
            path = "Bosses/AngerBoss.png";
            spriteW = 120;
            spriteH = 120;
            enemyHealth = 55;  // Depression Boss HP
        } else if (me instanceof BargainingBoss) {
            path = "Bosses/BargainingBoss.png";
            spriteW = 120;
            spriteH = 120;
            enemyHealth = 70;  // Depression Boss HP
            
        
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
                graphicsHandler.drawFilledRectangle(0, 0, 290 * 2, 30, TailwindColorScheme.slate800);
                graphicsHandler.drawString(String.format("Health: %s/%s | Mana: %s/%s", this.player.getEntity().getHealth(), this.player.getEntity().getMaxHealth(), this.player.getEntity().getMana(), this.player.getEntity().getMaxMana()), 8, 16 + 3, Resources.press_start.deriveFont(16f), TailwindColorScheme.white);
                break;
            case LEVEL_COMPLETED:
                winScreen.draw(graphicsHandler);
                break;
            case INVENTORY:
                this.map.draw(this.player, graphicsHandler);
                this.inventoryScreen.draw(graphicsHandler, (Config.GAME_WINDOW_WIDTH / 2) - ((InventorySubmenu.INV_SLOT_WIDTH * 9) / 2), (Config.GAME_WINDOW_HEIGHT - (InventorySubmenu.INV_SLOT_HEIGHT * 9)) / 2);
                break;
            case MENU:
                this.map.draw(graphicsHandler);
                this.menuScreen.draw(graphicsHandler);
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
        RUNNING, LEVEL_COMPLETED, INVENTORY, BATTLE, LOST, MENU
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
            case SaveSubmenu.SAVE_EVENT: {
                Globals.saveToFile(new SaveData(this.map, this.player, this.flagManager), (int) args[0]);
                break;
            }
            case SaveSubmenu.LOAD_EVENT: {
                this.loadSave(Globals.loadSave((int) args[0]));
                break;
            }
            case "inventory.use": {
                Item item = (Item) args[0];
                this.map.getTextbox().addText(String.format("You have %s the %s", item instanceof Weapon ? "equipped" : "used", item.getName()));
                this.map.getTextbox().setIsActive(true);
                break;
            }
        }

        // Start battle event
        if (eventName.equals("start_battle") && args.length > 0 && args[0] instanceof MapEntity me) {
            // Debugging messsage
            // System.out.println("[PlayLevelScreen] start_battle received with: " + me.getClass().getSimpleName());
            Entity enemy = buildEnemyEntityFor(me);

            boolean isBossBattle = me.getClass().getSimpleName().contains("Boss");
            this.battleScreen = new BattleScreen(this.player.getEntity().getInventory(), this.player, enemy, me, isBossBattle);
            this.battleScreen.open();
            this.battleScreen.addistener(LISTENER_NAME, this);
            this.playLevelScreenState = PlayLevelScreenState.BATTLE;
        }

        // 👇 Enemy defeated event (added)
        if (eventName.equals("enemy_defeated") && args.length > 0 && args[0] instanceof MapEntity defeatedEnemy) {
            // Check if this is the wizard quest skeleton
            if (defeatedEnemy instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) defeatedEnemy;
            if (skeleton.getId() == 103) { // The quest skeleton ID
                // Set the flag when ACTUALLY defeated
                map.getFlagManager().setFlag("wizardSaved");
            }
        }
        
        // Check if this is the Denial Boss
        if (defeatedEnemy instanceof DenialBoss || 
            (defeatedEnemy.getClass().getSimpleName().equals("DenialBoss"))) {
            map.getFlagManager().setFlag("hasDefeatedDenial");
        }
            defeatedEnemy.setMapEntityStatus(MapEntityStatus.REMOVED);
            defeatedEnemy.setIsHidden(true);
        }
    }
}
