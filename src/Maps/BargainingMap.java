package Maps;
import EnhancedMapTiles.PushableRock;
import Level.*;
import Tilesets.Map3Tileset;
import NPCs.BargainingBoss;
import Scripts.MapThreeBargaining.BargainingBossScript;
import java.util.ArrayList;

// Represents a test map to be used in a level
public class BargainingMap extends Map {

    public BargainingMap() {
        super("map3.txt", new Map3Tileset());
        this.playerStartPosition = getMapTile(1, 13).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        //Bargaining boss
        BargainingBoss b = new BargainingBoss(103, getMapTile(7, 7).getLocation().subtractY(16).subtractX(6));
        b.setInteractScript(new BargainingBossScript());
        b.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(b);
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