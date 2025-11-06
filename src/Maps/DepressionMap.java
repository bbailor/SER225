package Maps;

import Level.*;
import NPCs.*;
import Scripts.MapFourDepression.*;
import Tilesets.Map4Tileset;
import Utils.Point;
import java.util.ArrayList;

public class DepressionMap extends Map {

    public DepressionMap() {
        super("depression_map.txt", new Map4Tileset());
        this.playerStartPosition = new Point(2, 2);
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }


    //adding boss   
/*     @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();


        
        DepressionBoss db = new DepressionBoss(201, getMapTile(12, 8).getLocation().subtractY(16));
        db.setInteractScript(new DepressionBossScript());
        db.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(db);

        return npcs;
    }
*/
    @Override
    public ArrayList<Trigger> loadTriggers() {
        return new ArrayList<>();
    }

    @Override
    public void loadScripts() {
    }
}
