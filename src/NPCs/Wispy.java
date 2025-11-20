package NPCs;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;
import Level.WispyChainBuilder;

import java.util.HashMap;

// Wispy spirit guide that leads the player to the next area
public class Wispy extends NPC {
    
    private int nextWispyId; // ID of the next wispy in the chain
    private Point nextWispyLocation; // Where the next wispy should spawn
    
    public Wispy(int id, float x, float y) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("wispy.png"), 96, 96), "IDLE");
        this.nextWispyId = -1; // -1 means this is the last wispy
        this.nextWispyLocation = null;
        this.autoBattleEnabled = false;
        this.isUncollidable = true;
    }
    
    public Wispy(int id, float x, float y, int nextWispyId, Point nextWispyLocation) {
        super(id, x, y, new SpriteSheet(ImageLoader.load("Wispy.png"), 96, 96), "IDLE");
        this.nextWispyId = nextWispyId;
        this.nextWispyLocation = nextWispyLocation;
        this.autoBattleEnabled = false;
        this.isUncollidable = true;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("IDLE", new Frame[] {
                new FrameBuilder(spriteSheet.getSubImage(0, 0), 20)
                        .withScale(.5f)
                        .withBounds(6, 6, 16, 16)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(0, 1), 16)
                        .withScale(.5f)
                        .withBounds(6, 6, 16, 16)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(0, 2), 20)
                        .withScale(.5f)
                        .withBounds(6, 6, 16, 16)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(0, 1), 16)
                        .withScale(.5f)
                        .withBounds(6, 6, 16, 16)
                        .build()
            });
            
            put("POOF", new Frame[] {
                new FrameBuilder(spriteSheet.getSubImage(1, 0), 6)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 1), 6)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(1, 2), 6)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(2, 0), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(2, 1), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(2, 2), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(3, 0), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(3, 1), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(3, 2), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
                new FrameBuilder(spriteSheet.getSubImage(0, 3), 4)
                        .withScale(.5f)
                        .withBounds(6, 6, 12, 12)
                        .build(),
            });
        }};
    }
    
    private boolean hasPoofed = false;
    private int poofCounter = 0;
    
    private void triggerPoof() {
        if (hasPoofed) return; // Only poof once
        
        hasPoofed = true;
        
        // Play poof animation
        this.currentAnimationName = "POOF";
        
        // Spawn next wispy if this isn't the last one
        if (nextWispyId != -1 && nextWispyLocation != null && map != null) {
            Wispy nextWispy = new Wispy(nextWispyId, nextWispyLocation.x, nextWispyLocation.y);
            
            // Check if there's a wispy after this one in the chain
            WispyChainBuilder.ChainData chainData = WispyChainBuilder.getChainData(nextWispyId);
            if (chainData != null && chainData.nextId != -1) {
                nextWispy.setNextWispy(chainData.nextId, chainData.nextLocation);
            }
            
            map.addNPC(nextWispy);
        }
    }
    
    @Override
    public void update(Player player) {
        // If we're in poof animation, count frames until we can remove
        if (hasPoofed && this.currentAnimationName.equals("POOF")) {
            poofCounter++;
            if (poofCounter > 42) { // Give animation time to complete
                setMapEntityStatus(Level.MapEntityStatus.REMOVED);
            }
        }
        
        // Check if player is touching this wispy
        if (!hasPoofed && intersects(player)) {
            triggerPoof();
        }
        
        super.update(player);
    }
    
    public int getNextWispyId() {
        return nextWispyId;
    }
    
    public Point getNextWispyLocation() {
        return nextWispyLocation;
    }
    
    public void setNextWispy(int id, Point location) {
        this.nextWispyId = id;
        this.nextWispyLocation = location;
    }
}
