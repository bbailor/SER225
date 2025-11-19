package Level;

import com.google.gson.annotations.Expose;

import java.awt.Color;
import java.awt.image.BufferedImage;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.GameObject;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.TailwindColorScheme;

public class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    @Expose protected float walkSpeed = 0;
    @Expose protected int interactionRange = 2;
    protected Direction currentWalkingXDirection;
    protected Direction currentWalkingYDirection;
    @Expose protected Direction lastWalkingXDirection;
    @Expose protected Direction lastWalkingYDirection;
    @Expose protected Entity entity;

    // values used to handle player movement
    @Expose protected float moveAmountX, moveAmountY;
    @Expose protected float lastAmountMovedX, lastAmountMovedY;

    // values used to keep track of player's current state
    @Expose protected PlayerState playerState;
    @Expose protected PlayerState previousPlayerState;
    @Expose protected Direction facingDirection;
    @Expose protected Direction lastMovementDirection;

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    @Expose protected Key MOVE_LEFT_KEY = Key.A;
    @Expose protected Key MOVE_RIGHT_KEY = Key.D;
    @Expose protected Key MOVE_UP_KEY = Key.W;
    @Expose protected Key MOVE_DOWN_KEY = Key.S;
    @Expose protected Key INTERACT_KEY = Key.SPACE;

    protected boolean isLocked = false;
    protected boolean hidden = false;

    protected int steps;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        this.affectedByTriggers = true;
        this.entity = new Entity(25d, 50d);
        this.entity.setBaseAttack(2);
        this.entity.getAllAnimations().put("idle", new Frame[] {
            new FrameBuilder(spriteSheet.getSprite(0, 0))
            .withScale(2)
            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
            .build()
        });
    }

    /**
     * For serialization<p>
     * DO NOT USE
     */
    protected Player() {
        super(0, 0);
    }

    public void update() {
        if (!isLocked) {
            moveAmountX = 0;
            moveAmountY = 0;

            // if player is currently playing through level (has not won or lost)
            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
        }

        handlePlayerAnimation();

        updateLockedKeys();

        // update player's animation
        super.update();
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        // if a walk key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY) || Keyboard.isKeyDown(MOVE_UP_KEY) || Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            playerState = PlayerState.WALKING;
        }
    }

    // player WALKING state logic
    protected void playerWalking() {
        if (!keyLocker.isKeyLocked(INTERACT_KEY) && Keyboard.isKeyDown(INTERACT_KEY)) {
            keyLocker.lockKey(INTERACT_KEY);
            map.entityInteract(this);
        }

        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
            currentWalkingXDirection = Direction.LEFT;
            lastWalkingXDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
            currentWalkingXDirection = Direction.RIGHT;
            lastWalkingXDirection = Direction.RIGHT;
        }
        else {
            currentWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyDown(MOVE_UP_KEY)) {
            moveAmountY -= walkSpeed;
            currentWalkingYDirection = Direction.UP;
            lastWalkingYDirection = Direction.UP;
        }
        else if (Keyboard.isKeyDown(MOVE_DOWN_KEY)) {
            moveAmountY += walkSpeed;
            currentWalkingYDirection = Direction.DOWN;
            lastWalkingYDirection = Direction.DOWN;
        }
        else {
            currentWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingXDirection == Direction.RIGHT || currentWalkingXDirection == Direction.LEFT) && currentWalkingYDirection == Direction.NONE) {
            lastWalkingYDirection = Direction.NONE;
        }

        if ((currentWalkingYDirection == Direction.UP || currentWalkingYDirection == Direction.DOWN) && currentWalkingXDirection == Direction.NONE) {
            lastWalkingXDirection = Direction.NONE;
        }

        if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY) && Keyboard.isKeyUp(MOVE_UP_KEY) && Keyboard.isKeyUp(MOVE_DOWN_KEY)) {
            playerState = PlayerState.STANDING;
        }

        if(!this.getEntity().getHidden()) {
            steps = 0;
        }
        else{
            steps++;
        }

        if(steps > 400) {
            this.entity.setHidden(false);
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(INTERACT_KEY) && !isLocked) {
            keyLocker.unlockKey(INTERACT_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        if (playerState == PlayerState.STANDING) {
            // sets animation to a STAND animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
        }
        else if (playerState == PlayerState.WALKING) {
            // sets animation to a WALK animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, GameObject entityCollidedWith) { }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public Rectangle getInteractionRange() {
        return new Rectangle(
                getBounds().getX1() - interactionRange,
                getBounds().getY1() - interactionRange,
                getBounds().getWidth() + (interactionRange * 2),
                getBounds().getHeight() + (interactionRange * 2));
    }

    public Key getInteractKey() { return INTERACT_KEY; }
    public Direction getCurrentWalkingXDirection() { return currentWalkingXDirection; }
    public Direction getCurrentWalkingYDirection() { return currentWalkingYDirection; }
    public Direction getLastWalkingXDirection() { return lastWalkingXDirection; }
    public Direction getLastWalkingYDirection() { return lastWalkingYDirection; }

    public Entity getEntity() {
        return this.entity;
    }

    
    public void lock() {
        isLocked = true;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    public void unlock() {
        isLocked = false;
        playerState = PlayerState.STANDING;
        this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    }

    // used by other files or scripts to force player to stand
    public void stand(Direction direction) {
        playerState = PlayerState.STANDING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "STAND_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "STAND_LEFT";
        }
    }

    // used by other files or scripts to force player to walk
    public void walk(Direction direction, float speed) {
        playerState = PlayerState.WALKING;
        facingDirection = direction;
        if (direction == Direction.RIGHT) {
            this.currentAnimationName = "WALK_RIGHT";
        }
        else if (direction == Direction.LEFT) {
            this.currentAnimationName = "WALK_LEFT";
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

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    public boolean getHidden()
    {
        return this.hidden;
    }

    // Uncomment this to have game draw player's bounds to make it easier to visualize
    
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        int bar_length = 75;
        var health_rec = new Rectangle(this.getCalibratedXLocation() - 7, this.getCalibratedYLocation() + 5, bar_length, 10);
        // switch if we want to have a mana bar later
        // var mana_rec = new Rectangle(this.getCalibratedXLocation() - 7, this.getCalibratedYLocation() + 5, bar_length, 10);
        // var health_rec = new Rectangle(mana_rec.getX(), mana_rec.getY() - 17, bar_length, 10);
        graphicsHandler.drawFilledRectangle(health_rec, TailwindColorScheme.black);
        graphicsHandler.drawFilledRectangle(
            (int)health_rec.getX() + 2,
            (int)health_rec.getY() + 2,
            (int) (health_rec.getWidth() * (this.entity.getHealth() / this.entity.getMaxHealth())) - 4,
            health_rec.getHeight() - 4,
            TailwindColorScheme.lime500
        );

        if(this.getEntity().getHidden()) {
            var hidden_rec = new Rectangle(this.getCalibratedXLocation() - 7, this.getCalibratedYLocation() - 10, bar_length, 10);
            var cloak_rec = new Rectangle(this.getCalibratedXLocation() - 28, this.getCalibratedYLocation() - 16, 20, 20);
            graphicsHandler.drawFilledRectangle(hidden_rec, TailwindColorScheme.black);
            graphicsHandler.drawFilledRectangle(
            (int)hidden_rec.getX() + 2,
            (int)hidden_rec.getY() + 2,
            (int) ((75 - (hidden_rec.getWidth() * ((float)(steps) / 200f))) - 4),
            hidden_rec.getHeight() - 4,
            TailwindColorScheme.blue300
            );

            graphicsHandler.drawImage(ImageLoader.load("item_imgs/cloakOfConcealment.png"), cloak_rec);
        }
        //gitdrawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    
}
