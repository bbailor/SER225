package Level;

import NPCs.Wispy;
import Utils.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class to build chains of wispy spirit guides
 * Makes it easy to create paths that guide players to objectives
 */
public class WispyChainBuilder {
    private List<Point> pathPoints;
    private int startingId;
    
    // Static map to store chain data that wispies can access
    private static Map<Integer, ChainData> chainRegistry = new HashMap<>();
    
    public static class ChainData {
        public int nextId;
        public Point nextLocation;
        
        ChainData(int nextId, Point nextLocation) {
            this.nextId = nextId;
            this.nextLocation = nextLocation;
        }
    }
    
    public WispyChainBuilder(int startingId) {
        this.pathPoints = new ArrayList<>();
        this.startingId = startingId;
    }
    
    // Add a point to the path
    public WispyChainBuilder addPoint(Point point) {
        pathPoints.add(point);
        return this;
    }
    
    // Add a point to the path using x, y coordinates
    public WispyChainBuilder addPoint(float x, float y) {
        pathPoints.add(new Point(x, y));
        return this;
    }
    
    // Add multiple points at once
    public WispyChainBuilder addPoints(Point... points) {
        for (Point p : points) {
            pathPoints.add(p);
        }
        return this;
    }
    
    // Build and return the first wispy in the chain
    // Only the first wispy needs to be added to the map - rest spawn automatically
    public Wispy build() {
        if (pathPoints.isEmpty()) {
            return null;
        }
        
        // Register the entire chain in the static registry
        for (int i = 0; i < pathPoints.size() - 1; i++) {
            int currentId = startingId + i;
            int nextId = startingId + i + 1;
            Point nextLocation = pathPoints.get(i + 1);
            
            chainRegistry.put(currentId, new ChainData(nextId, nextLocation));
        }
        
        // Last wispy has no next (marks end of chain)
        chainRegistry.put(startingId + pathPoints.size() - 1, new ChainData(-1, null));
        
        // Create first wispy
        Point firstPoint = pathPoints.get(0);
        Wispy firstWispy = new Wispy(startingId, firstPoint.x, firstPoint.y);
        
        // Set up its next link
        if (pathPoints.size() > 1) {
            firstWispy.setNextWispy(startingId + 1, pathPoints.get(1));
        }
        
        return firstWispy;
    }
    
    // Get the next wispy info for a given ID
    // Used by Wispy class when spawning the next in chain
    public static ChainData getChainData(int wispyId) {
        return chainRegistry.get(wispyId);
    }
    
    // Clear all points and reset the builder
    public WispyChainBuilder reset() {
        pathPoints.clear();
        return this;
    }
}