package Maps;

import Level.*;
import NPCs.*;
import Scripts.MapFiveAcceptance.AcceptanceEntryScript;
import Scripts.MapFourDepression.*;
import Scripts.MapOneDenial.DenialEnemyScript;
import Tilesets.Map4Tileset;
import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;

public class DepressionMap extends Map {

    public DepressionMap() {
        super("depression_map.txt", new Map4Tileset());


        // Adjust X,Y below if you want a different spot
        this.playerStartPosition = getMapTile(33, 4).getLocation();
        // this.playerStartPosition = getMapTile(4, 5).getLocation();

    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(34, 1).getLocation(), Item.ItemList.health_potion));
        return collectables;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // Place Depression Boss at tile (0,3)
        DepressionBoss db = new DepressionBoss(201, getMapTile(0, 3).getLocation().subtractY(16));
        db.setInteractScript(new DepressionBossScript());
        db.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(db);

        // Place Greyscale Gnome at tile (29, 7) for side quest
        GnomeGreyscale greyscaleGnome = new GnomeGreyscale(202, getMapTile(29, 7).getLocation());
        greyscaleGnome.setInteractScript(new DepressionQuestScript());
        npcs.add(greyscaleGnome);

        // ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(31, 7).getLocation());
        // as.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(as);

        // ArmoredSkeleton ass = new ArmoredSkeleton(102, getMapTile(28, 6).getLocation());
        // ass.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(ass);

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(2500, 300, 1000, 1000, new DepressionScript(), "hasStartedDepression"));
        return triggers;
    }

    @Override
    public void loadScripts() {
        getMapTile(2, 6).setInteractScript(new AcceptanceEntryScript());
        getMapTile(1, 6).setInteractScript(new AcceptanceEntryScript());
        getMapTile(2, 6).setInteractScript(new AcceptanceEntryScript());
        getMapTile(1, 6).setInteractScript(new AcceptanceEntryScript());
    }
}
