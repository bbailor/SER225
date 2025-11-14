package Maps;

import Level.*;
import NPCs.*;
import Scripts.MapFiveAcceptance.AcceptanceEntryScript;
import Scripts.MapFourDepression.*;
import Tilesets.Map4Tileset;
import Utils.Point;
import java.util.ArrayList;

public class DepressionMap extends Map {

    public DepressionMap() {
        super("depression_map.txt", new Map4Tileset());


        // Adjust X,Y below if you want a different spot
        this.playerStartPosition = getMapTile(33, 4).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }


    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // Place Depression Boss at tile (0,3)
        DepressionBoss db = new DepressionBoss(201, getMapTile(0, 3).getLocation().subtractY(16));
        db.setInteractScript(new DepressionBossScript());
        db.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(db);

        //add enemy npcs?

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        return new ArrayList<>();
    }

    @Override
    public void loadScripts() {
        getMapTile(2, 6).setInteractScript(new AcceptanceEntryScript());
        getMapTile(1, 6).setInteractScript(new AcceptanceEntryScript());
    }
}
