package Maps;
import EnhancedMapTiles.PushableRock;
import Level.*;
import Tilesets.Map3Tileset;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class BargainingMap extends Map {

    public BargainingMap() {
        super("map3.txt", new Map3Tileset());
        this.playerStartPosition = getMapTile(0, 0).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        
        return triggers;
    }

    @Override
    public void loadScripts() {
       
       
    }
}