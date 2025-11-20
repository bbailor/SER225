package Screens;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

import javax.sound.sampled.LineUnavailableException;
import Utils.Globals;
import Engine.Config;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Inventory;
import Engine.Key;
import Engine.Keyboard;
import Engine.Screen;
import FightAnimations.ArmoredSkeletonAttack;
import FightAnimations.DenialBossAttack;
import FightAnimations.DenialsStaffAttack;
import FightAnimations.DepressionBossAttack;
import FightAnimations.EnemyProjectileAttackAnimation;
import FightAnimations.KnifeOfLifeAttack;
import FightAnimations.PlayerFistAttack;
import FightAnimations.PlayerProjectileAttackAnimation;
import FightAnimations.SkeletonAttack;
import FightAnimations.SpiritAttack;
import FightAnimations.AngerBossAttack;
import FightAnimations.AngerSpiritAttack;
import FightAnimations.TlalocsStormAttack;
import FightAnimations.SwordOfRageAttack;
import FightAnimations.StaticPlayerAttackAnimation;
import FightAnimations.StaticEnemyAttackAnimation;
import FightAnimations.powerStoneAttack;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Items.DenialsStaff;
import Items.KnifeOfLife;
import Items.SwordOfRage;
import Level.Entity;
import Level.Item;
import Level.Player;
import Level.Weapon;
import NPCs.AngerBoss;
import Screens.submenus.BattleSubmenu;
import Screens.submenus.InventoryBattleMenu;
import Screens.submenus.SelectionSubmenu;
import Utils.Globals;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import Utils.SoundThreads.Type;

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
    protected Object activeEnemyAttackAnimation = null;
    protected boolean enemyTurnStarted = false;
    protected String currentEnemyType = null; // For debugging
    protected Entity.EnemyAttack currentEnemyAttack = null;

    // New field to track if this battle involves the boss
    private boolean isBossBattle = false;
    protected Object activePlayerAttackAnimation;

    protected Rectangle STATUS_LOG_REC = new Rectangle(
        2,
        2,
        Config.GAME_WINDOW_WIDTH - 5,
        (int)(Config.GAME_WINDOW_HEIGHT * .2)
    );
    protected Rectangle BATTLE_REC = new Rectangle(
        2,
        STATUS_LOG_REC.getHeight(),
        Config.GAME_WINDOW_WIDTH - 5,
        // BATTLE_HEIGHT
        (int) ((Config.GAME_WINDOW_HEIGHT-STATUS_LOG_REC.getHeight())*.625f)
    );
    protected Rectangle SELECTOR_REC = new Rectangle(
        2,
        STATUS_LOG_REC.getHeight() + BATTLE_REC.getHeight(),
        (int)((Config.GAME_WINDOW_WIDTH - 5) * .3),
        (int) (Config.GAME_WINDOW_HEIGHT - (STATUS_LOG_REC.getHeight() + BATTLE_REC.getHeight()) - 38)
    );
    protected Rectangle BATTLE_ACTION_REC = new Rectangle(
        SELECTOR_REC.getWidth(),
        SELECTOR_REC.getY(),
        Config.GAME_WINDOW_WIDTH-SELECTOR_REC.getWidth() - 2,
        SELECTOR_REC.getHeight()
    );

    public static final String LISTENER_NAME = "battle_screen";
    public static final String LOSE_EVENT_NAME = "player_lose";
    public static final float FONT_SIZE = 12f;

    // Main constructor with boss flag and enemy source
    public BattleScreen(Inventory inventory, Player player, Entity entity, Object enemySource, boolean isBossBattle) {
        this.inventory = inventory;
        this.player = player;
        this.entity = entity;
        this.enemySource = enemySource;
        this.isBossBattle = isBossBattle;

        try{
            Globals.SOUND_SYSTEM.play(Type.Music, Globals.BATTLE_TRACK_NUMBER, new File("Resources/Sounds/Music/danceOfKnights8bit.wav"));
            Globals.SOUND_SYSTEM.getTrack(Globals.BATTLE_TRACK_NUMBER).setLoopPoint(0, -1, true);
        } catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }

        var inv = new InventoryBattleMenu(
            (int) BATTLE_ACTION_REC.getX(),
            (int) BATTLE_ACTION_REC.getY(),
            BATTLE_ACTION_REC.getWidth(),
            BATTLE_ACTION_REC.getHeight(),
            this.inventory, player, this.entity
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
            SELECTOR_REC.getWidth(),
            SELECTOR_REC.getHeight(),
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
            // Check if it's a projectile or static animation and handle appropriately
            if (activePlayerAttackAnimation instanceof PlayerProjectileAttackAnimation) {
                PlayerProjectileAttackAnimation projectile = (PlayerProjectileAttackAnimation) activePlayerAttackAnimation;
                projectile.update();
                if (projectile.isComplete()) {
                    this.entity.handleDamage(this.player.getEntity(), false);
                    activePlayerAttackAnimation = null;

                    //lifesteal effect for Knife of Life
                    if(this.player.getEntity().getCurrentWeapon() instanceof KnifeOfLife)
                    {
                        this.player.getEntity().heal(2);
                    } 
                    else if (this.player.getEntity().getCurrentWeapon() instanceof DenialsStaff)
                    {
                        //implement
                    }

                    this.currentTurn = BattleTurn.Enemy;
                }
            } else if (activePlayerAttackAnimation instanceof StaticPlayerAttackAnimation) {
                StaticPlayerAttackAnimation staticAnim = (StaticPlayerAttackAnimation) activePlayerAttackAnimation;
                staticAnim.update();
                if (staticAnim.isComplete()) {
                    this.entity.handleDamage(this.player.getEntity(), false);
                    activePlayerAttackAnimation = null;
                    this.currentTurn = BattleTurn.Enemy;
                }
            }
            return; // Don't process other updates while animating
        }
        
        // Handle active ENEMY attack animation
        if (activeEnemyAttackAnimation != null) {
            // Check if it's a projectile or static animation and handle appropriately
            if (activeEnemyAttackAnimation instanceof EnemyProjectileAttackAnimation) {
                EnemyProjectileAttackAnimation projectile = (EnemyProjectileAttackAnimation) activeEnemyAttackAnimation;
                projectile.update();
                if (projectile.isComplete()) {
                    this.player.getEntity().handleDamage(this.entity, false);
                    activeEnemyAttackAnimation = null;
                    enemyTurnStarted = false;
                    this.currentTurn = BattleTurn.Player;
                }
        } else if (activeEnemyAttackAnimation instanceof StaticEnemyAttackAnimation) {
                StaticEnemyAttackAnimation staticAnim = (StaticEnemyAttackAnimation) activeEnemyAttackAnimation;
                staticAnim.update();
                if (staticAnim.isComplete()) {
                    this.player.getEntity().handleDamage(this.entity, false);
                    activeEnemyAttackAnimation = null;
                    enemyTurnStarted = false;
                    this.currentTurn = BattleTurn.Player;
                }
            }
            return; // Don't process other updates while animating
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

        graphicsHandler.drawFilledRectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT, TailwindColorScheme.black);

        // Status Log Section
        graphicsHandler.drawFilledRectangleWithBorder(
            STATUS_LOG_REC,
            TailwindColorScheme.amber400,
            this.borderColor,
            5
        );
        // graphicsHandler.drawFilledRectangle(
        //     BORDER_LINE_WIDTH * 2,
        //     BORDER_LINE_WIDTH + MARGIN,
        //     DEFAULT_SECTION_WIDTH,
        //     BATTLE_LOG_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN)*2),
        //     TailwindColorScheme.amber400
        // );
        var playerEntity = this.player.getEntity();

        // graphicsHandler.drawFilledRectangle(
        //     10,
        //     10,
        //     60,
        //     10,
        //     Color.BLACK
        // );

        graphicsHandler.drawStringWithOutline(
            String.format("Player Health: %.2f/%.2f", playerEntity.getHealth(), playerEntity.getMaxHealth()),
            (int) (STATUS_LOG_REC.getX() + FONT_SIZE) + 2,
            (int) (STATUS_LOG_REC.getY() + FONT_SIZE) + 7,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Player Mana: %.2f/%.2f", playerEntity.getMana(), playerEntity.getMaxMana()),
            (int) (STATUS_LOG_REC.getX() + FONT_SIZE) + 2,
            (int) (STATUS_LOG_REC.getY() + FONT_SIZE * 2 + 4) + 7,
            Resources.press_start.deriveFont(FONT_SIZE),
            TailwindColorScheme.white,
            TailwindColorScheme.slate900,
            3
        );
        graphicsHandler.drawStringWithOutline(
            String.format("Enemy Health: %.2f/%.2f", this.entity.getHealth(), this.entity.getMaxHealth()),
            (int) (STATUS_LOG_REC.getX() + FONT_SIZE) + 2,
            (int) (STATUS_LOG_REC.getY() + FONT_SIZE * 3 + 8) + 7,
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
        this.selector.draw(graphicsHandler, (int) SELECTOR_REC.getX(), (int) SELECTOR_REC.getY());

        // Action Section
        graphicsHandler.drawRectangle(
            BATTLE_ACTION_REC,
            this.borderColor,
            5
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

        int entityPadding = BATTLE_REC.getWidth() / 10;
        // int battleX0 = BORDER_LINE_WIDTH*2;
        // int battleY0 = BATTLE_LOG_HEIGHT + BORDER_LINE_WIDTH + MARGIN;
        // int battleHeight = BATTLE_HEIGHT - ((BORDER_LINE_WIDTH + MARGIN) * 2);
        int battleX0 = (int) BATTLE_REC.getX();
        int battleY0 = (int) BATTLE_REC.getY();
        int battleHeight = BATTLE_REC.getHeight();

        graphicsHandler.drawFilledRectangleWithBorder(
            // battleX0,
            // battleY0,
            // DEFAULT_SECTION_WIDTH,
            // battleHeight,
            BATTLE_REC,
            TailwindColorScheme.cyan500,
            this.borderColor,
            5
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
            5
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
                BATTLE_REC.getWidth() - battleY0 - (placeholderWidth + entityPadding),
                battleY0 + (battleHeight - placeholderHeight) / 2,
                placeholderWidth,
                placeholderHeight,
                this.borderColor
            );
        } else {
            float scale = entityIdleAnimations[0].getScale();
            float enemyX = battleX0 + BATTLE_REC.getWidth() * 0.85f
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
            float enemyX = battleX0 + BATTLE_REC.getWidth() * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            float enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;

            if (isBossBattle) {
                enemyX += 300f;
            }
        
        graphicsHandler.drawFilledRectangleWithBorder(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 - 35,
            (int)enemyY - 14,
            70,
            8,
            TailwindColorScheme.black,
            this.borderColor,
            5
        );

        graphicsHandler.drawFilledRectangle(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 + 1 - 35,
            (int)enemyY - 13,
            70 - 2,
            8 - 2,
            TailwindColorScheme.red500
        );

        graphicsHandler.drawFilledRectangle(
            (int)enemyX + entityIdleAnimations[0].getWidth() / 2 + 1 - 35,
            (int)enemyY - 13,
            (int)(68 * ((this.entity.getHealth()) / this.entity.getMaxHealth())),
            8 - 2,
            TailwindColorScheme.lime500
        );
        
        // Draw active attack animation if present
        if (activeEnemyAttackAnimation != null) {
            if (activeEnemyAttackAnimation instanceof EnemyProjectileAttackAnimation) {
                ((EnemyProjectileAttackAnimation) activeEnemyAttackAnimation).draw(graphicsHandler);
            } else if (activeEnemyAttackAnimation instanceof StaticEnemyAttackAnimation) {
                ((StaticEnemyAttackAnimation) activeEnemyAttackAnimation).draw(graphicsHandler);
            }
        }
        if (activePlayerAttackAnimation != null) {
            if (activePlayerAttackAnimation instanceof PlayerProjectileAttackAnimation) {
                ((PlayerProjectileAttackAnimation) activePlayerAttackAnimation).draw(graphicsHandler);
            } else if (activePlayerAttackAnimation instanceof StaticPlayerAttackAnimation) {
                ((StaticPlayerAttackAnimation) activePlayerAttackAnimation).draw(graphicsHandler);
            }
        }
        if (activeEnemyAttackAnimation != null) {
            if (activeEnemyAttackAnimation instanceof EnemyProjectileAttackAnimation) {
                ((EnemyProjectileAttackAnimation) activeEnemyAttackAnimation).draw(graphicsHandler);
            } else if (activeEnemyAttackAnimation instanceof StaticEnemyAttackAnimation) {
                ((StaticEnemyAttackAnimation) activeEnemyAttackAnimation).draw(graphicsHandler);
            }
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
                if (args.length >= 2 && args[0] instanceof Item && args[1] instanceof Entity) {
                    Item usedItem = (Item) args[0];
                    Entity target = (Entity) args[1];

                    // Check if it's an offensive item that targets enemy
                    if (usedItem.id.equals("powerStone") && target == this.entity) {
                        // Trigger power stone attack animation
                        triggerItemAttackAnimation("powerStone");
                    } else {
                        // Healing items don't animate, just switch turn
                        this.currentTurn = BattleTurn.Enemy;
                    }
                } else {
                    this.currentTurn = BattleTurn.Enemy;
                }
                break;
            }
            case SelectionSubmenu.SUBMENU_SELECTION_EVENT: {
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
        int entityPadding = BATTLE_REC.getWidth() / 10;
        int battleX0 = (int) BATTLE_REC.getX();
        int battleY0 = (int) BATTLE_REC.getY();
        int battleHeight = BATTLE_REC.getHeight();

        // Get enemy and player sprite positions
        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        float enemyX, enemyY, playerX, playerY;

        // Calculate enemy position (where animation appears/ends)
        if (entityIdleAnimations != null) {
            float scale = entityIdleAnimations[0].getScale();
            enemyX = battleX0 + BATTLE_REC.getWidth() * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            if (isBossBattle) {
                enemyX += 300f;
            }
            enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            int placeholderWidth = placeholderHeight / 2;
            enemyX = BATTLE_REC.getWidth() - battleY0 - (placeholderWidth + entityPadding);
            enemyY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }

        // Calculate player position (where animation starts for projectiles)
        if (playerIdleAnimations != null) {
            playerX = battleX0 + entityPadding + playerIdleAnimations[0].getWidth() * playerIdleAnimations[0].getScale();
            playerY = battleY0 + (battleHeight - playerIdleAnimations[0].getHeight()) / 2;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            playerX = battleY0 + entityPadding;
            playerY = battleY0 + (battleHeight - placeholderHeight) / 2;
        }

        // Get equipped weapon and determine animation
        Weapon weapon = this.player.getEntity().getCurrentWeapon();
        String weaponAnimName = weapon.getAttackAnimationName();

        try {
            String attackFileName = "weapons//" + weaponAnimName + "Attack.png";
            SpriteSheet attackSheet = null;
            
            // Load sprite sheet with appropriate dimensions based on weapon
            switch (weaponAnimName) {
                case "fist":
                    System.out.println("using fist");
                    attackSheet = new SpriteSheet(ImageLoader.load("Enemies//ArmoredSkeletonAttack.png"), 63, 63);
                    break;
                case "TlalocsStorm":
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 60, 200);
                    break;
                case "KnifeOfLife":
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 32, 32);
                    break;
                case "DenialsStaff":
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 160, 32);
                    break;
                case "SwordOfRage":
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 63, 63);
                    break;
                default:
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 32, 32);
            }
            
            activePlayerAttackAnimation = createPlayerAttackAnimation(weaponAnimName, attackSheet, enemyX, enemyY, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Failed to load " + weaponAnimName + " attack animation: " + e.getMessage());
            e.printStackTrace();
            // Fallback: apply damage immediately
            this.entity.handleDamage(this.player.getEntity(), false);
            this.currentTurn = BattleTurn.Enemy;
        }
    }

    private void startEnemyAttackAnimation() {

        currentEnemyAttack = this.entity.selectAttack();

        // If no attacks set up, use default
        if (currentEnemyAttack == null) {
            this.player.getEntity().handleDamage(this.entity, false);
            this.currentTurn = BattleTurn.Player;
            enemyTurnStarted = false; 
            return;
        }

        // Calculate battle area positions
        int entityPadding = BATTLE_REC.getWidth() / 10;
        int battleX0 = (int) BATTLE_REC.getX();
        int battleY0 = (int) BATTLE_REC.getY();
        int battleHeight = BATTLE_REC.getHeight();

        // Get enemy and player sprite positions
        var entityIdleAnimations = this.entity.getAnimations("idle");
        var playerIdleAnimations = this.player.getEntity().getAnimations("idle");

        float enemyX, enemyY, playerX, playerY;

        // Calculate enemy position (where animation starts)
        if (entityIdleAnimations != null) {
            float scale = entityIdleAnimations[0].getScale();
            enemyX = battleX0 + BATTLE_REC.getWidth() * 0.85f
                    - (entityIdleAnimations[0].getWidth() * scale) / 2f;
            if (isBossBattle) {
                enemyX += 300f;
            }
            enemyY = battleY0 + (battleHeight - entityIdleAnimations[0].getHeight()) / 2f;
        } else {
            // Fallback to placeholder position
            int placeholderHeight = battleHeight / 2;
            int placeholderWidth = placeholderHeight / 2;
            enemyX = BATTLE_REC.getWidth() - battleY0 - (placeholderWidth + entityPadding);
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

        currentEnemyAttack = this.entity.selectAttack(); // Select attack first!
        

        try {
            String attackFileName = "Enemies/" + currentEnemyAttack.animationType + ".png";
            SpriteSheet attackSheet = null;
            switch (currentEnemyAttack.animationType) {
                case "ArmoredSkeletonAttack": {
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 63, 63);
                    break;
                }
                case "DenialBossAttack":{
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 255, 255);
                    break;
                }
                case "DepressionBossAttack":{
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 60, 200);
                    break;
                }
                case "AngerBossAttack":{
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 48, 64);
                    break;
                }
                case "BargainingBossAttack":{
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 255, 255);
                    break;
                }
                default: {
                    attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 24, 24);
                }
            }
            activeEnemyAttackAnimation = createEnemyAttackAnimation(currentEnemyAttack.attackName, attackSheet, enemyX, enemyY, playerX, playerY);

        } catch (Exception e) {
            System.err.println("Failed to load " + currentEnemyAttack.animationType + ": " + e.getMessage());
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
    protected Object createEnemyAttackAnimation(String attackName, SpriteSheet sheet, 
                                               float startX, float startY, float targetX, float targetY) {
    switch (attackName) {
        // Basic enemy attacks
        case "Slash":
            return new ArmoredSkeletonAttack(sheet, startX, startY, targetX, targetY, 45);

        case "BoneThrow":
            return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45);

        case "LaunchOrb":
            return new SpiritAttack(sheet, startX, startY, targetX, targetY, 45);
        case "FireOrb":
            return new AngerSpiritAttack(sheet, startX, startY, targetX, targetY, 45);

        // Denial Boss attacks
        case "Explosion":
            return new DenialBossAttack(sheet, startX, startY, targetX, targetY, 48);
        case "DenialBossSpecial":
            return new DenialBossAttack(sheet, startX, startY, targetX, targetY, 36); // Faster animation
        case "DenialBossHeavy":
            return new DenialBossAttack(sheet, startX, startY, targetX, targetY, 60); // Slower, heavier
        
        case "DepressionBossAttack":
            return new DepressionBossAttack(sheet, targetX, targetY);

        case "AngerBossAttack":
            return new AngerBossAttack(sheet, targetX, targetY);
            
        default:
            System.err.println("Unknown attack name: " + attackName + ", using default Skeleton attack");
            return new SkeletonAttack(sheet, startX, startY, targetX, targetY, 45);
    }
}

    protected Object createPlayerAttackAnimation(String weapon, SpriteSheet sheet, 
                                               float enemyX, float enemyY, float playerX, float playerY) {
        switch (weapon) {
            case "fist":
                return new PlayerFistAttack(sheet, playerX, playerY, enemyX, enemyY, 50);

            case "KnifeOfLife":
                // Projectile: travels from player to enemy
                return new KnifeOfLifeAttack(sheet, enemyX, enemyY, playerX, playerY, 100);
            
            case "TlalocsStorm":
                // Static: appears at enemy position
                return new TlalocsStormAttack(sheet, enemyX, enemyY);
            case "DenialsStaff":
                return new DenialsStaffAttack(sheet, enemyX, enemyY);

            case "SwordOfRage":
                return new SwordOfRageAttack(sheet, enemyX, enemyY, playerX, playerY, 60);
            // Add more weapons here as you create them
            // Projectile example:
            // case "Arrow":
            //     return new ArrowAttack(sheet, enemyX, enemyY, playerX, playerY, duration);
            // 
            // Static example:
            // case "Earthquake":
            //     return new EarthquakeAttack(sheet, enemyX, enemyY);
            
            default:
                System.err.println("Unknown weapon type: " + weapon + ", using KnifeOfLife attack");
                return new KnifeOfLifeAttack(sheet, enemyX, enemyY, playerX, playerY, 100);
        }
    }

    private void triggerItemAttackAnimation(String itemId) {
        try {
            // Position animation at enemy location
            int enemyX = (int) (Config.GAME_WINDOW_WIDTH * 0.70);
            int enemyY = (int) (BATTLE_REC.getY() + BATTLE_REC.getHeight() / 2);

            // Load sprite from Enemies folder
            String attackFileName = "Enemies/" + itemId + "Attack.png";
            SpriteSheet attackSheet = null;

            if (itemId.equals("powerStone")) {
                // powerStoneAttack is 1024x768 with 8 columns x 6 rows = 128x128 per sprite (use 127 for SpriteSheet)
                attackSheet = new SpriteSheet(ImageLoader.load(attackFileName), 127, 127);
                // Shift animation back by 127 pixels
                activePlayerAttackAnimation = new powerStoneAttack(attackSheet, enemyX - 127, enemyY);
            }
            // Add more offensive items here as needed

        } catch (Exception e) {
            System.err.println("Failed to load item attack animation for " + itemId + ": " + e.getMessage());
            // Fallback: damage immediately without animation
            this.entity.handleDamage(this.player.getEntity(), false);
            this.currentTurn = BattleTurn.Enemy;
        }
    }

    @Override
    public void close() {
        Menu.super.close();
    }

    public enum BattleTurn {
        Player,
        Enemy
    }
}
