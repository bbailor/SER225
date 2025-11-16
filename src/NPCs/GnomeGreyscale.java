package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import GameObject.ImageEffect;
import Level.NPC;
import Utils.Point;
import Level.Player;

import java.util.HashMap;

// Greyscale Gnome NPC for Depression Map side quest
public class GnomeGreyscale extends NPC {

    private static int boundY = 40;
    private static int boundX = 4;
    private static int boundWidth = 23;
    private static int boundHeight = 10;

    public GnomeGreyscale(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheet(ImageLoader.load("gnomeGreyscale.png"), 31, 63),
            "STAND_RIGHT"
        );
        
        autoBattleEnabled = false;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(2)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build()
            });

            put("STAND_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build()
            });

            put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                        .withScale(2)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                        .withScale(2)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                        .withScale(2)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                        .withScale(2)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build()
            });

            put("WALK_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(boundX, boundY, boundWidth, boundHeight)
                        .build()
            });
        }};
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        // Check if should follow player
        if (map != null && map.getFlagManager().isFlagSet("greyscaleGnomeFollowing")) {
            followPlayer(player);
            
            // Check if reached treeline (y <= 50, which is tile y=1)
            if (this.getY() <= 100 && !map.getFlagManager().isFlagSet("greyscaleReachedTreeline")) {
                map.getFlagManager().setFlag("greyscaleReachedTreeline");
            }
        }
    }
    
    private void followPlayer(Player player) {
        float followDistance = 80f; // Stay about 80 pixels behind
        float speed = 2.0f;
        
        float dx = player.getX() - this.getX();
        float dy = player.getY() - this.getY();
        float distance = (float)Math.sqrt(dx * dx + dy * dy);
        
        // Only move if player is far enough away
        if (distance > followDistance) {
            // Calculate direction to player
            float moveX = (dx / distance) * speed;
            float moveY = (dy / distance) * speed;
            
            // Determine facing direction based on movement
            if (Math.abs(moveX) > Math.abs(moveY)) {
                if (moveX > 0) {
                    this.currentAnimationName = "WALK_RIGHT";
                } else {
                    this.currentAnimationName = "WALK_LEFT";
                }
            } else {
                // Keep current facing when moving vertically
                if (this.currentAnimationName.contains("RIGHT")) {
                    this.currentAnimationName = "WALK_RIGHT";
                } else {
                    this.currentAnimationName = "WALK_LEFT";
                }
            }
            
            this.moveX(moveX);
            this.moveY(moveY);
        } else {
            // Stand still when close enough
            if (this.currentAnimationName.contains("RIGHT")) {
                this.currentAnimationName = "STAND_RIGHT";
            } else {
                this.currentAnimationName = "STAND_LEFT";
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}