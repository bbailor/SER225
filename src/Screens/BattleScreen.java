package Screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import FightAnimations.ArmoredSkeletonAttack;
import FightAnimations.DenialBossAttack;
import FightAnimations.EnemyProjectileAttackAnimation;
import FightAnimations.KnifeOfLifeAttack;
import FightAnimations.PlayerProjectileAttackAnimation;
import FightAnimations.SkeletonAttack;
import FightAnimations.SpiritAttack;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Player;
import Screens.submenus.BattleSubmenu;
import Screens.submenus.InventoryBattleMenu;
import Screens.submenus.SelectionSubmenu;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

public class BattleScreen extends Screen implements Menu, MenuListener {

    protected Inventory inventory;
    protected Player player;
    protected Entity entity;
    protected Object enemySource; // Store the actual NPC/enemy object
    protected BattleTurn currentTurn = BattleTurn.Player;
    protected List<String> history;
    protected SelectionSubmenu selector;
    protected String selectedAction = null;
    protected Map<String, BattleSubmenu> actions = new HashMap<>();
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected Color borderColor = TailwindColorScheme.slate700;
    protected EnemyProjectileAttackAnimation activeAttackAnimation = null;
    protected boolean enemyTurnStarted = false;
    protected String currentEnemyType = null; // For debugging

    // New field to track if this battle involves the boss
    private boolean isBossBattle = false;
    private PlayerProjectileAttackAnimation activePlayerAttackAnimation;

    public static final String LISTENER_NAME = "battle_screen";
    public static final String LOSE_EVENT_NAME = "player_lose";
    public static final int BORDER_LINE_WIDTH = 5;
    public static final int MARGIN = 5;
    public static final int DEFAULT_SECTION_WIDTH = Config.GAME_WINDOW_WIDTH - ((BORDER_LINE_WIDTH + MARGIN) * 2);
    public static final int BORDER_WIDTH = Config.GAME_WINDOW_WIDTH - (MARGIN);
    public static final int BATTLE_HEIGHT = (int)(Config.GAME_WINDOW_HEIGHT * .5);
    public static final int BATTLE_ACTIONS_HEIGHT = (int)(Config.GAME_WINDOW_HEIGHT * .3);
    public static final int BATTLE_SELECTION_WIDTH = (int)(BORDER_WIDTH * .3);
    protected static Rectangle statusLogRec = new Rectangle(
        2,
        2,
        BORDER_WIDTH,
        (int)(Config.GAME_WINDOW_HEIGHT * .2)
    );
    protected static Rectangle battleRec = new Rectangle(
        2,
        statusLogRec.getHeight(),
        BORDER_WIDTH,
        BATTLE_HEIGHT
    );
    protected static Rectangle selectorRec = new Rectangle(
        2,
        statusLogRec.getHeight() + battleRec.getHeight(),
        BATTLE_SELECTION_WIDTH,
        BATTLE_ACTIONS_HEIGHT
    );
    protected static Rectangle battleActionRec = new Rectangle(
        BATTLE_SELECTION_WIDTH + BORDER_LINE_WIDTH,
        statusLogRec.getHeight() + BATTLE_HEIGHT + BORDER_LINE_WIDTH,
        selectorRec.getWidth() - ((BORDER_LINE_WIDTH + MARGIN) * 2),
        BATTLE_ACTIONS_HEIGHT  - ((BORDER_LINE_WIDTH + MARGIN) * 2)
    );
    public static final float FONT_SIZE = 12f;

    // Main constructor with boss flag and enemy source
    public BattleScreen(Inventory inventory, Player player, Entity entity, Object enemySource, boolean isBossBattle) {
        this.inventory = inventory;
        this.player = player;
        this.entity = entity;
        this.enemySource = enemySource;
        this.isBossBattle = isBossBattle;

        var inv = new InventoryBattleMenu(
            (int) battleActionRec.getX(),
            (int) battleActionRec.getY(),
            battleActionRec.getWidth(),
            battleActionRec.getHeight(),
            this.inventory, player
        );
        this.actions.put("Inventory", inv);
        inv.addistener(LISTENER_NAME, this);

        var actionList = new ArrayList<>(actions.keySet());
        actionList.add(0, "Attack");
        actionList.add(1, "Defend");
        actionList.add(2, "Skill");
        actionList.add("Flee");

        this.selector = new SelectionSubmenu(
            // BATTLE_SELECTION_WIDTH - ((BORDER_LINE_WIDTH) * 2),
            // BATTLE_ACTIONS_HEIGHT - ((BORDER_LINE_WIDTH*3 + MARGIN) * 2) - 1,
            selectorRec.getWidth(),
            selectorRec.getHeight(),
            actionList
        );
        this.selector.addistener(LISTENER_NAME, this);
        this.selector.open();

        this.history = new ArrayList<>();
    }

    // Old constructor for backward compatibility
    public BattleScreen(Player player, Entity entity) {
        this(player.getEntity().getInventory(), player, entity, entity, false);
    }

    // Standard constructor without boss flag
    public BattleScreen(Inventory inventory, Player player, Entity entity) {
        this(inventory, player, entity, entity, false);
    }
    
    // Constructor with boss flag but no enemy source
    public BattleScreen(Inventory inventory, Player player, Entity entity, boolean isBossBattle) {
        this(inventory, player, entity, entity, isBossBattle);
    }

    @Override
    public void initialize() {}

    @Override
    public void update() {
        if (this.entity.getHealth() <= 0) {
            // Notify PlayLevelScreen that this enemy was defeated
            this.sendEvent("enemy_defeated", this.enemySource);
            this.close();
            return;
        }

        if (this.player.getEntity().getHealth() <= 0) {
            this.sendEvent(LOSE_EVENT_NAME);
            return;
        }
        
        // Handle active PLAYER attack animation
        if (activePlayerAttackAnimation != null) {
            activePlayerAttackAnimation.update();
            if (activePlayerAttackAnimation.isComplete()) {
                // Apply damage when player animation completes
                this.entity.handleDamage(this.player.getEntity(), false);
                activePlayerAttackAnimation = null;
                // Switch to enemy turn after damage is dealt
                this.currentTurn = BattleTurn.Enemy;
            }
            return; // Don't process other updates while animating
        }
        
        // Handle active ENEMY attack animation
        if (activeAttackAnimation != null) {
            activeAttackAnimation.update();
            if (activeAttackAnimation.isComplete()) {
                this.player.getEntity().handleDamage(this.entity, false);
                activeAttackAnimation = null;
                enemyTurnStarted = false;
                this.currentTurn = BattleTurn.Player;
            }
            return;
        }
        
        if (this.currentTurn == BattleTurn.Enemy && !enemyTurnStarted) {
            enemyTurnStarted = true;
            startEnemyAttackAnimation();
            return;
        }

        if (this.selectedAction == null) {
            this.selector.update();
        } else {
            this.actions.get(this.selectedAction).update();
        }
        if (Keyboard.isKeyDown(Key.K)) {
            this.player.getEntity().kill();
        }
    }
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        Rectangle statusLogRec = new Rectangle(
            2,
            2,
            BORDER_WIDTH,
            (int)(Config.GAME_WINDOW_HEIGHT * .2)
        );
        Rectangle battleRec = new Rectangle(
            2,
            statusLogRec.getHeight(),
            BORDER_WIDTH,
            BATTLE_HEIGHT
        );
        Rectangle selectorRec = new Rectangle(
            2,
            statusLogRec.getHeight() + battleRec.getHeight(),
            BATTLE_SELECTION_WIDTH,
            BATTLE_ACTIONS_HEIGHT
        );
        Rectangle battleActionRec = new Rectangle(
            selectorRec.getWidth(),
            statusLogRec.getHeight() + battleRec.getHeight(),
            Config.GAME_WINDOW_WIDTH-selectorRec.getWidth(),
            BATTLE_ACTIONS_HEIGHT
        );

        graphicsHandler.drawFilledRectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.black);

        // Status Log Section
        graphicsHandler.drawFilledRectangleWithBorder(
            statusLogRec,
            TailwindColorScheme.amber400,
            this.borderColor,
            BORDER_LINE_WIDTH
        );
        // graphicsHandler.drawFilledRectangle(
        //     BORDER_LINE_WIDTH * 2,
        //     BORDER_LINE_WIDTH + MARGIN,
        //     DEFAULT_SECTION_WIDTH,
        //     BATTLE_LOG_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN)*2),
        //     TailwindColorScheme.amber400
        // );
        var playerEntity = this.player.getEntity();

        graphicsHandler.drawFilledRectangle(
            10,
            10,
            60,
            10,
            Color.BLACK
        );

        graphicsHandler.drawStringWithOutline(
            String.format("Player Health: %.2f/%.2f", playerEntity.getHealth(), playerEntity.getMaxHealth()),
            (int) (statusLogRec.getX() + FONT_SIZE) + 2,
            (int) (statusLogRec.getY() + FONT_SIZE) + 7,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Player Mana: %.2f/%.2f", playerEntity.getMana(), playerEntity.getMaxMana()),
            (int) (statusLogRec.getX() + FONT_SIZE) + 2,
            (int) (statusLogRec.getY() + FONT_SIZE * 2 + 4) + 7,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Enemy Health: %.2f/%.2f", this.entity.getHealth(), this.entity.getMaxHealth()),
            (int) (statusLogRec.getX() + FONT_SIZE) + 2,
            (int) (statusLogRec.getY() + FONT_SIZE * 3 + 8) + 7,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        


        // Selector Section
        // graphicsHandler.drawRectangle(
        //     0,
        //     BATTLE_LOG_HEIGHT + BATTLE_HEIGHT,
        //     BATTLE_SELECTION_WIDTH,
        //     BATTLE_ACTIONS_HEIGHT,
        //     this.borderColor,
        //     BORDER_LINE_WIDTH
        // );
        this.selector.draw(graphicsHandler, (int) selectorRec.getX(), (int) selectorRec.getY());

        // Action Section
        graphicsHandler.drawRectangle(
            battleActionRec,
            this.borderColor,
            BORDER_LINE_WIDTH
        );
        if (this.selectedAction != null) {
            this.actions.get(this.selectedAction).draw(graphicsHandler);
        }

        // Battle Section
        // graphicsHandler.drawRectangle(
        //     battleRec,
        //     this.borderColor,
        //     BORDER_LINE_WIDTH
        // );

        int entityPadding = DEFAULT_SECTION_WIDTH / 10;
        // int battleX0 = BORDER_LINE_WIDTH*2;
        // int battleY0 = BATTLE_LOG_HEIGHT + BORDER_LINE_WIDTH + MARGIN;
        int battleX0 = (int) battleRec.getX();
        int battleY0 = (int) battleRec.getY();
        int battleHeight = BATTLE_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN) * 2);

        graphicsHandler.drawFilledRectangleWithBorder(
            // battleX0,
            // battleY0,
            // DEFAULT_SECTION_WIDTH,
            // battleHeight,
            battleRec,
            TailwindColorScheme.cyan500,
            this.borderColor,
            BORDER_LINE_WIDTH
        );

        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        int placeholderHeight = battleHeight / 2;
        int placeholderWidth = placeholderHeight / 2;

        // PLAYER SPRITE
        if (playerIdleAnimations == null) {
            graphicsHandler.drawFilledRectangle(
                battleY0 + entityPadding,
                battleY0 + (battleHeight - placeholderHeight) / 2,
                placeholderWidth,
                placeholderHeight,
                this.borderColor
            );
        } else {
            playerIdleAnimations[0].setLocation(
                battleX0 + entityPadding + playerIdleAnimations[0].getWidth() * playerIdleAnimations[0].getScale(),
                battleY0 + (battleHeight - playerIdleAnimations[0].getHeight()) / 2
            );
            playerIdleAnimations[0].draw(graphicsHandler);
        }


        int playerLocX = (int) (battleX0 + entityPadding + playerIdleAnimations[0].getWidth() * playerIdleAnimations[0].getScale());
        int playerLocY = (int) (battleY0 + (battleHeight - playerIdleAnimations[0].getHeight()) / 2);
      
        graphicsHandler.drawFilledRectangleWithBorder(
            playerLocX,
            playerLocY,
            70,
            8,
            TailwindColorScheme.black,
            this.borderColor,
            BORDER_LINE_WIDTH
        );

        graphicsHandler.drawFilledRectangle(
            playerLocX + 1,
            playerLocY + 1,
            70 - 2,
            8 - 2,
            TailwindColorScheme.red500
        );

        graphicsHandler.drawFilledRectangle(
            playerLocX + 1,
            playerLocY + 1,
            (int)(68 * ((playerEntity.getHealth()) / playerEntity.getMaxHealth())),
            8 - 2,
            TailwindColorScheme.lime500
        );


        //System.out.println(            (int)((playerEntity.getHealth() / (battleY0 + (battleHeight - placeholderHeight) / 2 + 12 + 1)) * (battleY0 + (battleHeight - placeholderHeight)) / 2 + 12 + 1));

        
        // ENEMY SPRITE
  if (entityIdleAnimations == null) {
            graphicsHandler.drawFilledRectangle(
                DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding),
                battleY0 + (battleHeight - placeholderHeight) / 2,
                placeholderWidth,
                placeholderHeight,
                this.borderColor
            );
        } else {
            float scale = entityIdleAnimations[0].getScale();
            float enemyX = battleX0 + DEFAULT_SECTION_WIDTH * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            float enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;

            if (isBossBattle) {
                enemyX += 300f;
            }

            entityIdleAnimations[0].setLocation(enemyX, enemyY);
            entityIdleAnimations[0].setImageEffect(ImageEffect.FLIP_HORIZONTAL);
            entityIdleAnimations[0].draw(graphicsHandler);
        }
        
        // graphicsHandler.drawFilledRectangleWithBorder(
        //     DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding),
        //     battleY0 + (battleHeight - placeholderHeight) / 2,
        //     70,
        //     8,
        //     TailwindColorScheme.black,
        //     this.borderColor,
        //     BORDER_LINE_WIDTH
        // );

            float scale = entityIdleAnimations[0].getScale();
            float enemyX = battleX0 + DEFAULT_SECTION_WIDTH * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            float enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;

            if (isBossBattle) {
                enemyX += 300f;
            }
        
        graphicsHandler.drawFilledRectangleWithBorder(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 - 35,
            (int)enemyY - 10,
            70,
            8,
            TailwindColorScheme.black,
            this.borderColor,
            BORDER_LINE_WIDTH
        );

        graphicsHandler.drawFilledRectangle(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 + 1 - 35,
            (int)enemyY - 9,
            70 - 2,
            8 - 2,
            TailwindColorScheme.red500
        );

        graphicsHandler.drawFilledRectangle(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 + 1 - 35,
            (int)enemyY - 9,
            (int)(68 * ((this.entity.getHealth()) / this.entity.getMaxHealth())),
            8 - 2,
            TailwindColorScheme.lime500
        );
        
        // Draw active attack animation if present
        if (activeAttackAnimation != null) {
            activeAttackAnimation.draw(graphicsHandler);
        }
        if (activePlayerAttackAnimation != null) {
            activePlayerAttackAnimation.draw(graphicsHandler);
        }
                
        if (activeAttackAnimation != null) {
            activeAttackAnimation.draw(graphicsHandler);
        }
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        // TODO: Implement when submenu (take above draw)
    }

    @Override
    public void onEvent(String eventName, Object ...args) {
        switch (eventName) {
            case "attack.basic_attack": {
                startPlayerAttackAnimation();                
                //this.entity.handleDamage(this.player.getEntity(), false);
                //this.currentTurn = BattleTurn.Enemy;
                break;
            }
            case "attack.skill": {
                this.entity.handleDamage(this.player.getEntity(), true);
                this.currentTurn = BattleTurn.Enemy;
                break;
            }
            case "inventory.use": {
                this.currentTurn = BattleTurn.Enemy;
                break;
            }
            case SelectionSubmenu.SUBMENU_SELECTION_NAME: {
                switch ((String) args[0]) {
                    case "Attack": {
                        onEvent("attack.basic_attack");
                        break;
                    }
                    case "Defend": {
                        this.player.getEntity().setTempResistance(
                            RandomGenerator.getDefault().nextDouble(this.player.getEntity().getMaxHealth() * .05f)
                        );
                        this.currentTurn = BattleTurn.Enemy;
                        break;
                    }
                    case "Skill": {
                        onEvent("attack.skill");
                        break;
                    }
                    case "Inventory": {
                        this.selectedAction = "Inventory";
                        this.actions.get(this.selectedAction).open();
                        this.selector.setHoverColor(Globals.UNFOCUS_HOVER_COLOR);
                        break;
                    }
                    case "Flee": {
                        if (RandomGenerator.getDefault().nextInt(3) == 1) { 
                            this.close();
                            return;
                        }
                        this.currentTurn = BattleTurn.Enemy;
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return this.listeners;
    }

    @Override
    public void open() {
        Keyboard.clear();
    }

    @Override
    public void onMenuClose() {
        this.selectedAction = null;
        this.selector.setHoverColor(Globals.HOVER_COLOR);
    }
    
    private void startPlayerAttackAnimation() {
        // Calculate battle area positions
        int entityPadding = DEFAULT_SECTION_WIDTH / 10;
        // int battleX0 = BORDER_LINE_WIDTH * 2;
        // int battleY0 = BATTLE_LOG_HEIGHT + BORDER_LINE_WIDTH + MARGIN;
        int battleX0 = (int) battleRec.getX();
        int battleY0 = (int) battleRec.getY();
        int battleHeight = BATTLE_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN) * 2);

        // Get enemy and player sprite positions
        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        float enemyX, enemyY, playerX, playerY;

        // Calculate enemy position (where animation starts)
        if (entityIdleAnimations != null) {
            float scale = entityIdleAnimations[0].getScale();
            enemyX = battleX0 + DEFAULT_SECTION_WIDTH * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            if (isBossBattle) {
                enemyX += 300f;
            }
            enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            int placeholderWidth = placeholderHeight / 2;
            enemyX = DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding);
            enemyY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }

        // Calculate player position (where animation ends)
        if (playerIdleAnimations != null) {
            playerX = battleX0 + entityPadding + playerIdleAnimations[0].getWidth() * playerIdleAnimations[0].getScale();
            playerY = battleY0 + (battleHeight - playerIdleAnimations[0].getHeight()) / 2;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            playerX = battleY0 + entityPadding;
            playerY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }


        //modular in the future
        String weapon = "KnifeOfLife";

        try {
            String attackFileName = "weapons//" + weapon + "Attack.png";
            SpriteSheet attackSheet = null;
            attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 32, 32);
            
            activePlayerAttackAnimation = createPlayerAttackAnimation(weapon, attackSheet, enemyX, enemyY, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Failed to load " + weapon + " attack animation: " + e.getMessage());
            this.player.getEntity().handleDamage(this.entity, false);
            this.currentTurn = BattleTurn.Player;
            enemyTurnStarted = false;
        }
    }

    private void startEnemyAttackAnimation() {
        // Calculate battle area positions
        int entityPadding = DEFAULT_SECTION_WIDTH / 10;
        int battleX0 = (int) battleRec.getX();
        int battleY0 = (int) battleRec.getY();
        int battleHeight = BATTLE_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN) * 2);

        // Get enemy and player sprite positions
        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        float enemyX, enemyY, playerX, playerY;

        // Calculate enemy position (where animation starts)
        if (entityIdleAnimations != null) {
            float scale = entityIdleAnimations[0].getScale();
            enemyX = battleX0 + DEFAULT_SECTION_WIDTH * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            if (isBossBattle) {
                enemyX += 300f;
            }
            enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            int placeholderWidth = placeholderHeight / 2;
            enemyX = DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding);
            enemyY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }

        // Calculate player position (where animation ends)
        if (playerIdleAnimations != null) {
            playerX = battleX0 + entityPadding + playerIdleAnimations[0].getWidth() * playerIdleAnimations[0].getScale();
            playerY = battleY0 + (battleHeight - playerIdleAnimations[0].getHeight()) / 2;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            playerX = battleY0 + entityPadding;
            playerY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }

        String enemyType = getEnemyType();
        currentEnemyType = enemyType;

        try {
            String attackFileName = "Enemies/" + enemyType + "Attack.png";
            SpriteSheet attackSheet = null;
            switch (enemyType) {
                case "ArmoredSkeleton": {
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 63, 63);
                    break;
                }
                case "DenialBoss":{
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 255, 255);
                    break;
                }
                default: {
                    attackSheet= new SpriteSheet(ImageLoader.load(attackFileName), 24, 24);
                }
            }
            activeAttackAnimation = createAttackAnimation(enemyType, attackSheet, enemyX, enemyY, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Failed to load " + enemyType + " attack animation: " + e.getMessage());
            this.player.getEntity().handleDamage(this.entity, false);
            this.currentTurn = BattleTurn.Player;
            enemyTurnStarted = false;
        }
    }
    
    /**
     * Determines the enemy type for loading the correct attack animation
     */
    protected String getEnemyType() {
        Object sourceToCheck = (enemySource != null && enemySource != entity) ? enemySource : entity;
        String fullClassName = sourceToCheck.getClass().getName();
        if (fullClassName.contains("NPCs.")) {
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        if (fullClassName.contains("Enemies.")) {
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        String simpleName = sourceToCheck.getClass().getSimpleName();
        if (simpleName != null && !simpleName.isEmpty() && !simpleName.equals("Entity")) {
            return simpleName;
        }
        return null;
    }
    
    /**
     * Creates the appropriate attack animation instance based on enemy type
     * Add new cases here when you create new enemy attack animations
     */
    protected EnemyProjectileAttackAnimation createAttackAnimation(String enemyType, SpriteSheet sheet, 
                                                   float startX, float startY, float targetX, float targetY) {
        switch (enemyType) {
            case "Skeleton":
                return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
            case "Spirit":
                return new SpiritAttack(sheet, startX, startY, targetX, targetY, 45);
            case "ArmoredSkeleton":
                return new ArmoredSkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
            case "DenialBoss":
                return new DenialBossAttack(sheet, startX, startY, targetX, targetY, 48);
            default:
                System.err.println("Unknown enemy type: " + enemyType + ", using Skeleton attack");
                return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
        }
    }

    protected PlayerProjectileAttackAnimation createPlayerAttackAnimation(String weapon, SpriteSheet sheet, 
                                                   float startX, float startY, float targetX, float targetY) {
        switch (weapon) {
            case "KnifeOfLife":
                return new KnifeOfLifeAttack(sheet, startX, startY, targetX, targetY, 100);
            default:
                System.err.println("Unknown enemy type: " + weapon + ", using Skeleton attack");
                return new KnifeOfLifeAttack(sheet, startX, startY, targetX, targetY, 120);
        }
    }

    public enum BattleTurn {
        Player,
        Enemy
    }
}
