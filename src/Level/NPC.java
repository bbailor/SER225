package Level;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;
import ScriptActions.StartBattleScriptAction;

import java.awt.Color;
import java.awt.color.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.Expose;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
    @Expose protected int id = 0;
    @Expose protected boolean isLocked = false;

    //for enemy battle vision logic
    protected boolean autoBattleEnabled = true;
    protected Direction facingDirection = Direction.RIGHT;
    protected int visionRange = 4;
    protected boolean battleTriggered;

    public NPC(int id, float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.id = id;
        getStartingDirection(this.getCurrentAnimationName());
        //getVisionTiles();
    }

    public NPC(int id, float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
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
    // public List<Point> getVisionTiles() {
    //     List<Point> tiles = new ArrayList<>();
        
    //     Point currentPos = toTileCoords(getLocation());

    //     for (int i = 1; i <= visionRange; i++) {
    //         switch (facingDirection) {
    //             case UP:
    //                 tiles.add(new Point(currentPos.x, currentPos.y - i * 16));
    //                 break;
    //             case DOWN:
    //                 tiles.add(new Point(currentPos.x, currentPos.y + i * 16));
    //                 break;
    //             case LEFT:
    //                 tiles.add(new Point((currentPos.x - i * 16), currentPos.y + 0));
    //                 break;
    //             case RIGHT:
    //                 tiles.add(new Point((currentPos.x + i * 16), currentPos.y + 0));
    //                 break;
    //             default:
    //                 break;
    //         }
    //     }
    //     return tiles;
    // }

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
            //this.performAction(player);
        }
        // getStartingDirection(this.currentAnimationName);
        // getVisionTiles();
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
            Point playerTile = toTileCoords(player.getLocation());

            // Check each tile in the NPC's vision
            // for (Point tile : getVisionTiles()) {
            //     if (toTileCoords(tile).equals(playerTile)) {
            //         battleTriggered = true;
            //         System.out.println("Player spotted at: " + playerTile + " by " + this.getClass().getSimpleName());
            //         initiateBattle();
            //         break;
            //     }
            // }
        }
    }

    /**
     * Trigger the battle by executing the StartBattleScriptAction
     * This uses the map's listener system to notify PlayLevelScreen
     */
    public void initiateBattle() {
        // Create the battle script action
        StartBattleScriptAction battleAction = new StartBattleScriptAction(this);
        
        // Set up the action with map's listeners if map is available
        if (map != null) {
            battleAction.setListeners(map.getListeners());
        }
        
        battleAction.execute();
    }

    private Point toTileCoords(Point p) {
        return new Point((int)(p.x / 16) * 16, (int)(p.y / 16) * 16);
    }

 @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);

        // Draw vision boxes if debug mode is enabled
        // if (true) {
        //     for (Point tile : getVisionTiles()) {
        //         // Convert map coordinates to screen coordinates using the camera
        //         int screenX = Math.round(tile.x - map.getCamera().getX());
        //         int screenY = Math.round(tile.y - map.getCamera().getY());
        //         Rectangle visionBox = new Rectangle(screenX, screenY, 16, 16);
        //         graphicsHandler.drawRectangle(visionBox, Color.RED);
        //     }
        // }
    }
}
