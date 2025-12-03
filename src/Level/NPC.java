package Level;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.SpriteSheet;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.StartBattleScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import Utils.Direction;
import Utils.Point;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
    @Expose protected int id = 0;
    @Expose protected boolean isLocked = false;
    @Expose protected Entity entity;

    //for enemy battle vision logic
    protected boolean autoBattleEnabled = true;
    protected Direction facingDirection = Direction.RIGHT;
    protected int visionRange = 8;
    protected boolean battleTriggered;

    protected List<Point> tiles;

    public NPC(int id, float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.id = id;
        getStartingDirection(this.getCurrentAnimationName());

        // FIX: Initialize entity with default values
        this.entity = new Entity(10, 30, true); 
           
        // Copy animations from NPC to Entity so they show in battle
        this.entity.getAllAnimations().putAll(this.animations);

        getVisionTiles();
    }

    public NPC(int id, float x, float y, java.util.Map<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.id = id;
    }

    public NPC(int id, float x, float y, Frame[] frames) {
        super(x, y, frames);
        this.id = id;
    }

    public NPC(int id, float x, float y, Frame frame) {
        super(x, y, frame);
        this.id = id;
    }

    public NPC(int id, float x, float y) {
        super(x, y);
        this.id = id;
    }

    public Entity getEntity() {
        return this.entity;
    }
    
    protected void addAttack(String attackName, int weight, String animationType, double damage) {
        this.entity.addAttack(attackName, weight, animationType, damage);
    }
    
    protected void initializeEntity() {
        // Subclasses override this to customize HP, mana, attacks, etc.
    }

    public int getId() { return id; }

    public void facePlayer(Player player) {
        // if npc's center point is to the right of the player's center point, npc needs to face left
        // else if npc's center point is to the left of the player's center point, npc needs to face right
        float centerPoint = getBounds().getX() + (getBounds().getWidth() / 2);
        float playerCenterPoint = player.getBounds().getX() + (player.getBounds().getWidth() / 2);

        if (centerPoint < playerCenterPoint) {
            this.currentAnimationName = "STAND_RIGHT";
            this.facingDirection = Direction.RIGHT;
        }
        else if (centerPoint >= playerCenterPoint) {
            this.currentAnimationName = "STAND_LEFT";
            this.facingDirection = Direction.LEFT;
        }
    }

    public void getStartingDirection(String animationName){
        if (animationName != null) {
            if (animationName.contains("RIGHT")) {
                this.facingDirection = Direction.RIGHT;
            } else if (animationName.contains("LEFT")) {
                this.facingDirection = Direction.LEFT;
            } else if (animationName.contains("UP")) {
                this.facingDirection = Direction.UP;
            } else if (animationName.contains("DOWN")) {
                this.facingDirection = Direction.DOWN;
            }
        }
    }

    /**
     * Get the tiles that this NPC can currently see based on facing direction
     */
    public List<Point> getVisionTiles() {
        tiles = new ArrayList<>();
        
        Point currentPos = toTileCoords(getLocation());

        for (int i = 1; i <= visionRange; i++) {
            switch (facingDirection) {
                case UP:
                    tiles.add(new Point(currentPos.x, currentPos.y - i * 32));
                    break;
                case DOWN:
                    tiles.add(new Point(currentPos.x, currentPos.y + i * 32));
                    break;
                case LEFT:
                    tiles.add(new Point(((currentPos.x - 8 + this.getBounds().getWidth() / 2) - i * 32) + this.getBounds().getWidth(), currentPos.y + 32));
                    break;
                case RIGHT:
                    tiles.add(new Point(((currentPos.x + 8 + this.getBounds().getWidth() / 2) + i * 32), currentPos.y + 32));
                    break;
                default:
                    break;
            }
        }
        return tiles;
    }

    public void stand(Direction direction) {
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    public void walk(Direction direction, float speed) {
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "WALK_LEFT";
        }
        else {
            if (this.currentAnimationName.contains("RIGHT")) {
                this.currentAnimationName = "WALK_RIGHT";
            }
            else {
                this.currentAnimationName = "WALK_LEFT";
            }
        }
        if (direction == Direction.UP) {
            moveY(-speed);
        }
        else if (direction == Direction.DOWN) {
            moveY(speed);
        }
        else if (direction == Direction.LEFT) {
            moveX(-speed);
        }
        else if (direction == Direction.RIGHT) {
            moveX(speed);
        }
    }

    public void update(Player player) {
        if (!isLocked) {
            this.performAction(player);
        }
        getStartingDirection(this.currentAnimationName);
        getVisionTiles();
        super.update();
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }


    /**
     * Check if player is in vision range and trigger battle if enabled
     */
    protected void performAction(Player player) {
        // Only check if auto-battle is enabled and battle hasn't been triggered yet
        if (autoBattleEnabled && !battleTriggered) {
            Point playerTile1 = toTileCoords(player.getLocation().add(new Point(32, 32)));
            Point playerTile2 = toTileCoords(player.getLocation().add(new Point(32, 64)));
            Point playerTile3 = toTileCoords(player.getLocation().add(new Point(32, 96)));

            Point[] playerVisionTiles = {playerTile1, playerTile2, playerTile3};

            // Check each tile in the NPC's vision and player vision
            for (Point tile : getVisionTiles()) {
                for (Point pTile : playerVisionTiles) {
                    if (toTileCoords(tile).equals(pTile) && (!player.getEntity().getHidden())) {
                        battleTriggered = true;
                        initiateBattle();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Trigger the battle by executing the StartBattleScriptAction
     * This uses the map's listener system to notify PlayLevelScreen
     */
    public void initiateBattle() {
        if (map != null) {
            // Create a custom script that shows text then starts battle
            Script battleScript = new Script() {
                @Override
                public ArrayList<ScriptAction> loadScriptActions() {
                    ArrayList<ScriptAction> actions = new ArrayList<>();
                    actions.add(new LockPlayerScriptAction());
                    actions.add(new TextboxScriptAction(NPC.this.getEnemyType() + " has spotted you!"));
                    actions.add(new StartBattleScriptAction(NPC.this));
                    actions.add(new UnlockPlayerScriptAction());
                    return actions;
                }
            };
            
            battleScript.setMap(map);
            battleScript.setPlayer(map.getPlayer());
            map.setActiveScript(battleScript);
        }
    }

    private Point toTileCoords(Point p) {
        return new Point((int)(p.x / 32) * 32, (int)(p.y / 32) * 32);
    }

    public void setAutoBattle(boolean autobattle)
    {
        this.autoBattleEnabled = autobattle;
    }

    public String getEnemyType()
    {
        return "";
    }

 @Override
 public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);

        //debug
        // if (true) {
        //     for (Point tile : tiles) {
        //         // Convert map coordinates to screen coordinates using the camera
        //         int screenX = Math.round(tile.x - map.getCamera().getX());
        //         int screenY = Math.round(tile.y - map.getCamera().getY());
        //         Rectangle visionBox = new Rectangle(screenX, screenY, 32, 32);
        //         graphicsHandler.drawRectangle(visionBox, Color.RED);
        //     }
        

        //     if (map != null && map.getPlayer() != null) {
        //         Point playerTile1 = toTileCoords(map.getPlayer().getLocation()).add(new Point(32, 32));
        //         Point playerTile2 = toTileCoords(map.getPlayer().getLocation()).add(new Point(32, 64));
        //         Point[] playerTiles = {playerTile1, playerTile2};

        //         for(Point tile: playerTiles)
        //         {
        //             int playerScreenX = Math.round(tile.x - map.getCamera().getX());
        //             int playerScreenY = Math.round(tile.y - map.getCamera().getY());
        //             Rectangle playerBox = new Rectangle(playerScreenX, playerScreenY, 32, 32);
        //             graphicsHandler.drawRectangle(playerBox, Color.BLUE);
        //         }


        //     }
        // }
    }
} 
