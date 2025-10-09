package Screens;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import Level.Entity;
import Level.Player;
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
    protected BattleTurn currentTurn = BattleTurn.Player;
    protected List<String> history;
    protected SelectionBattleMenu selector;
    protected String selectedAction = null;
    protected Map<String, BattleSubMenu> actions = new HashMap<>();
    protected Map<String, MenuListener> listeners = new HashMap<>();
    protected Color borderColor = TailwindColorScheme.slate700;

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

    /**
     * @param inventory The Player's Inventory
     * @param player The Player Itself
     * @param entity The entity the player is fighting
     */
    public BattleScreen(Inventory inventory, Player player, Entity entity) {
        this.inventory = inventory;
        this.player = player;
        this.entity = entity;
        
        var inv = new InventoryBattleMenu(
            BATTLE_SELECTION_WIDTH + BORDER_LINE_WIDTH,
            BATTLE_LOG_HEIGHT + BATTLE_HEIGHT + BORDER_LINE_WIDTH,
            BATTLE_ACTIONS_WIDTH - ((BORDER_LINE_WIDTH + MARGIN) * 2),
            BATTLE_ACTIONS_HEIGHT  - ((BORDER_LINE_WIDTH + MARGIN) * 2),
            inventory, player
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

    @Override
    public void initialize() {
        // This type of screen will be initalized in the constructor
    }

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
        if (this.currentTurn == BattleTurn.Enemy) {
            this.player.getEntity().handleDamage(this.entity, false);
            this.currentTurn = BattleTurn.Player;
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
        // if (Globals.DEBUG) {
        //     this.selector.setX(BORDER_LINE_WIDTH);
        //     this.selector.setY(BATTLE_LOG_HEIGHT + BATTLE_HEIGHT + MARGIN);
        //     this.selector.setWidth(BATTLE_SELECTION_WIDTH - ((BORDER_LINE_WIDTH) * 2));
        //     this.selector.setHeight(BATTLE_ACTIONS_HEIGHT - ((BORDER_LINE_WIDTH*3 + MARGIN) * 2));
        // }
    }

    // protected void handleTurnAction() {

    // }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.black);
        // Status Log Section
        graphicsHandler.drawRectangle( // Border
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
        graphicsHandler.drawRectangle( // Border
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

        // TODO: Actual Animations
        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        int placeholderHeight = battleHeight / 2;
        int placeholderWidth = placeholderHeight / 2;

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
            // graphicsHandler.drawFilledRectangle(
            //     battleY0 + entityPadding,
            //     battleY0 + (battleHeight - placeholderHeight) / 2,
            //     placeholderWidth,
            //     placeholderHeight,
            //     this.borderColor
            // );
        }
        if (entityIdleAnimations == null) {
            graphicsHandler.drawFilledRectangle(
                DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding),
                battleY0 + (battleHeight - placeholderHeight) / 2,
                placeholderWidth,
                placeholderHeight,
                this.borderColor
            );
        }  else {
            entityIdleAnimations[0].setLocation(
                (DEFAULT_SECTION_WIDTH - battleY0 - (placeholderWidth + entityPadding)) - entityIdleAnimations[0].getWidth() * entityIdleAnimations[0].getScale(),
                battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2
            );
            entityIdleAnimations[0].draw(graphicsHandler);
        }

    }

    @Override
    public void onEvent(String eventName, Object ...args) {
        switch (eventName) {
            case "attack.basic_attack": {
                this.entity.handleDamage(this.player.getEntity(), false);
                this.currentTurn = BattleTurn.Enemy;
                break;
            }
            // Other skills through args later
            case "attack.skill": {
                // For now just use weapon skill
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
                        this.player.getEntity().setTempResistance(RandomGenerator.getDefault().nextDouble(this.player.getEntity().getMaxHealth() * .05f));
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
        // Unused (currently)
    }

    @Override
    public void onMenuClose() {
        this.selectedAction = null;
        this.selector.setHoverColor(Globals.HOVER_COLOR);
    }

    public enum BattleTurn {
        Player,
        Enemy
    }

    
}
