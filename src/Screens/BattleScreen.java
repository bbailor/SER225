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
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.Player;
import FightAnimations.EnemyProjectileAttackAnimation;
import FightAnimations.SkeletonAttack;
import Screens.submenus.BattleSubMenu;
import Screens.submenus.InventoryBattleMenu;
import Screens.submenus.SelectionBattleMenu;
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
    protected SelectionBattleMenu selector;
    protected String selectedAction = null;
    protected Map<String, BattleSubMenu> actions = new HashMap<>();
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected Color borderColor = TailwindColorScheme.slate700;
    protected EnemyProjectileAttackAnimation activeAttackAnimation = null;
    protected boolean enemyTurnStarted = false;

    // New field to track if this battle involves the boss
    private boolean isBossBattle = false;

    public static final String LISTENER_NAME = "battle_screen";
    public static final String LOSE_EVENT_NAME = "player_lose";
    public static final int BORDER_LINE_WIDTH = 5;
    public static final int MARGIN = 5;
    public static final int DEFAULT_SECTION_WIDTH = Config.GAME_WINDOW_WIDTH - ((BORDER_LINE_WIDTH + MARGIN) * 2);
    public static final int BORDER_WIDTH = Config.GAME_WINDOW_WIDTH - (MARGIN);
    public static final int BATTLE_LOG_HEIGHT = (int)(Config.GAME_WINDOW_HEIGHT * .2);
    public static final int BATTLE_HEIGHT = (int)(Config.GAME_WINDOW_HEIGHT * .5);
    public static final int BATTLE_ACTIONS_HEIGHT = (int)(Config.GAME_WINDOW_HEIGHT * .3);
    public static final int BATTLE_ACTIONS_WIDTH = (int)(BORDER_WIDTH * .7);
    public static final int BATTLE_SELECTION_WIDTH = (int)(BORDER_WIDTH * .3);
    public static final float FONT_SIZE = 12f;

    // Main constructor with boss flag and enemy source
    public BattleScreen(Inventory inventory, Player player, Entity entity, Object enemySource, boolean isBossBattle) {
        this.inventory = inventory;
        this.player = player;
        this.entity = entity;
        this.enemySource = enemySource;
        this.isBossBattle = isBossBattle;

        var inv = new InventoryBattleMenu(
            BATTLE_SELECTION_WIDTH + BORDER_LINE_WIDTH,
            BATTLE_LOG_HEIGHT + BATTLE_HEIGHT + BORDER_LINE_WIDTH,
            BATTLE_ACTIONS_WIDTH - ((BORDER_LINE_WIDTH + MARGIN) * 2),
            BATTLE_ACTIONS_HEIGHT  - ((BORDER_LINE_WIDTH + MARGIN) * 2),
            this.inventory, player
        );
        this.actions.put("Inventory", inv);
        inv.addistener(LISTENER_NAME, this);

        var actionList = new ArrayList<>(actions.keySet());
        actionList.add(0, "Attack");
        actionList.add(1, "Defend");
        actionList.add(2, "Skill");
        actionList.add("Flee");

        this.selector = new SelectionBattleMenu(
            BORDER_LINE_WIDTH,
            BATTLE_LOG_HEIGHT + BATTLE_HEIGHT + MARGIN,
            BATTLE_SELECTION_WIDTH - ((BORDER_LINE_WIDTH) * 2),
            BATTLE_ACTIONS_HEIGHT - ((BORDER_LINE_WIDTH*3 + MARGIN) * 2),
            actionList
        );
        this.selector.addistener(LISTENER_NAME, this);
        this.selector.initialize();

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
            this.close();
            return;
        }
        if (this.player.getEntity().getHealth() <= 0) {
            this.sendEvent(LOSE_EVENT_NAME);
            return;
        }
        
        // Handle active attack animation
        if (activeAttackAnimation != null) {
            activeAttackAnimation.update();
            if (activeAttackAnimation.isComplete()) {
                // Apply damage when animation completes
                this.player.getEntity().handleDamage(this.entity, false);
                activeAttackAnimation = null;
                enemyTurnStarted = false;
                this.currentTurn = BattleTurn.Player;
            }
            return;
        }
        
        if (this.currentTurn == BattleTurn.Enemy && !enemyTurnStarted) {
            // Start attack animation instead of immediate damage
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
        graphicsHandler.drawFilledRectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.black);

        // Status Log Section
        graphicsHandler.drawRectangle(
            BORDER_LINE_WIDTH/2,
            0,
            BORDER_WIDTH,
            BATTLE_LOG_HEIGHT,
            this.borderColor,
            BORDER_LINE_WIDTH
        );
        graphicsHandler.drawFilledRectangle(
            BORDER_LINE_WIDTH * 2,
            BORDER_LINE_WIDTH + MARGIN,
            DEFAULT_SECTION_WIDTH,
            BATTLE_LOG_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN)*2),
            TailwindColorScheme.amber400
        );
        var playerEntity = this.player.getEntity();
        graphicsHandler.drawStringWithOutline(
            String.format("Player Health: %.2f/%.2f", playerEntity.getHealth(), playerEntity.getMaxHealth()),
            (BORDER_LINE_WIDTH + MARGIN * 2),
            BORDER_LINE_WIDTH + MARGIN*2,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Player Mana: %.2f/%.2f", playerEntity.getMana(), playerEntity.getMaxMana()),
            (BORDER_LINE_WIDTH + MARGIN * 2),
            BORDER_LINE_WIDTH + MARGIN*2 + ((int)FONT_SIZE + 4),
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Enemy Health: %.2f/%.2f", this.entity.getHealth(), this.entity.getMaxHealth()),
            (BORDER_LINE_WIDTH + MARGIN * 2),
            BORDER_LINE_WIDTH + MARGIN*2 + ((int)FONT_SIZE + 4) * 2,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );

        // Selector Section
        graphicsHandler.drawRectangle(
            0,
            BATTLE_LOG_HEIGHT + BATTLE_HEIGHT,
            BATTLE_SELECTION_WIDTH,
            BATTLE_ACTIONS_HEIGHT,
            this.borderColor,
            BORDER_LINE_WIDTH
        );
        this.selector.draw(graphicsHandler);

        // Action Section
        graphicsHandler.drawRectangle(
            BATTLE_SELECTION_WIDTH,
            BATTLE_LOG_HEIGHT + BATTLE_HEIGHT,
            BATTLE_ACTIONS_WIDTH + (BORDER_LINE_WIDTH + 1) /2,
            BATTLE_ACTIONS_HEIGHT,
            this.borderColor,
            BORDER_LINE_WIDTH
        );
        if (this.selectedAction != null) {
            this.actions.get(this.selectedAction).draw(graphicsHandler);
        }

        // Battle Section
        graphicsHandler.drawRectangle(
            BORDER_LINE_WIDTH/2,
            BATTLE_LOG_HEIGHT,
            BORDER_WIDTH,
            BATTLE_HEIGHT,
            this.borderColor,
            BORDER_LINE_WIDTH
        );

        int entityPadding = DEFAULT_SECTION_WIDTH / 10;
        int battleX0 = BORDER_LINE_WIDTH*2;
        int battleY0 = BATTLE_LOG_HEIGHT + BORDER_LINE_WIDTH + MARGIN;
        int battleHeight = BATTLE_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN) * 2);

        graphicsHandler.drawFilledRectangle(
            battleX0,
            battleY0,
            DEFAULT_SECTION_WIDTH,
            battleHeight,
            TailwindColorScheme.cyan500
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
        
        // Draw active attack animation if present
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
                this.entity.handleDamage(this.player.getEntity(), false);
                this.currentTurn = BattleTurn.Enemy;
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
            case SelectionBattleMenu.SUBMENU_SELECTION_NAME: {
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
    public void open() {}

    @Override
    public void onMenuClose() {
        this.selectedAction = null;
        this.selector.setHoverColor(Globals.HOVER_COLOR);
    }
    
    private void startEnemyAttackAnimation() {
        // Calculate battle area positions
        int entityPadding = DEFAULT_SECTION_WIDTH / 10;
        int battleX0 = BORDER_LINE_WIDTH * 2;
        int battleY0 = BATTLE_LOG_HEIGHT + BORDER_LINE_WIDTH + MARGIN;
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

        // Get enemy type name
        String enemyType = getEnemyType();
        
        try {
            String attackFileName = "Enemies/" + enemyType + "Attack.png";
            
            // Load the attack sprite sheet
            SpriteSheet attackSheet = new SpriteSheet(
                ImageLoader.load(attackFileName),
                24,  // sprite width - adjust per enemy if needed
                24   // sprite height - adjust per enemy if needed
            );
            
            // Create the appropriate attack animation based on enemy type
            activeAttackAnimation = createAttackAnimation(enemyType, attackSheet, enemyX, enemyY, playerX, playerY);
            
        } catch (Exception e) {
            System.err.println("Failed to load " + enemyType + " attack animation: " + e.getMessage());
            // Fallback: just do immediate damage if animation fails
            this.player.getEntity().handleDamage(this.entity, false);
            this.currentTurn = BattleTurn.Player;
            enemyTurnStarted = false;
        }
    }
    
    /**
     * Determines the enemy type for loading the correct attack animation
     */
    protected String getEnemyType() {
        // First try to use enemySource if it's different from entity
        Object sourceToCheck = (enemySource != null && enemySource != entity) ? enemySource : entity;
        
        // Try to get class name from the full class path
        String fullClassName = sourceToCheck.getClass().getName();

        
        
        // Check if it contains "NPCs." package (e.g., "NPCs.Skeleton")
        if (fullClassName.contains("NPCs.")) {
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        
        // Check if it contains "Enemies." package
        if (fullClassName.contains("Enemies.")) {
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        
        // Otherwise use simple name
        String simpleName = sourceToCheck.getClass().getSimpleName();
        if (simpleName != null && !simpleName.isEmpty() && !simpleName.equals("Entity")) {
            return simpleName;
        }
        
        // Final fallback
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
                return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45); // Update the '45' for a slower or faster animation
                
            // Add more enemy attack animations here as you create them:
            // case "Spirit":
            //     return new SpiritAttack(sheet, startX, startY, targetX, targetY, 45);
            // 
            // case "ArmoredSkeleton":
            //     return new ArmoredSkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
            // 
            // case "DenialBoss":
            //     return new DenialBossAttack(sheet, startX, startY, targetX, targetY, 45);
            
            default:
                System.err.println("Unknown enemy type: " + enemyType + ", using Skeleton attack");
                return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
        }
    }

    public enum BattleTurn {
        Player,
        Enemy
    }
}