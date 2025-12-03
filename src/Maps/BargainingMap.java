package Maps;
import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;
import Level.EnhancedMapTile;
import Level.Item;
import Level.Map;
import Level.NPC;
import Level.Trigger;
import NPCs.BargainingBoss;
import Scripts.MapFourDepression.DepressionEntryScript;
import Scripts.MapThreeBargaining.BargainingBossScript;
import Scripts.MapThreeBargaining.BargainingScript;
import Tilesets.Map3Tileset;

// Represents a test map to be used in a level
public class BargainingMap extends Map {

    public BargainingMap() {
        super("map3.txt", new Map3Tileset());
        this.playerStartPosition = getMapTile(1, 13).getLocation();
        // this.playerStartPosition = getMapTile(9, 8).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(8, 10).getLocation().subtractX(40), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(10, 12).getLocation().addX(30).addY(72), Item.ItemList.apple));
        
        return collectables;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();
        
        // Bargaining boss
        BargainingBoss b = new BargainingBoss(103, getMapTile(6, 5).getLocation().subtractY(16).subtractX(6));
        b.setInteractScript(new BargainingBossScript());
        b.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(b);

        return npcs;
    }


    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(0, 1000, 1000, 1000, new BargainingScript(), "hasStartedBargaining"));
        return triggers;
    }

    @Override
    public void loadScripts() {
       getMapTile(14, 6).setInteractScript(new DepressionEntryScript());
    }
}
