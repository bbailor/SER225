package Maps;

import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.AngerBoss;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.underworldSpirit;
import NPCs.AngerSpirit;
import Scripts.MapTwoAnger.AngerBossScript;
import Scripts.MapTwoAnger.BargainingEntryScript;
import Scripts.MapTwoAnger.MazeScript;
import Scripts.MapOneDenial.DenialEnemyScript;
import Scripts.MapTwoAnger.AngerScript;
import Scripts.MapTwoAnger.AngerSideQuestScript;
import Tilesets.Map2Tileset;
import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;

// Represents a test map to be used in a level
public class AngerMap extends Map {

    public AngerMap() {
    super("anger_map.txt", new Map2Tileset());
    this.playerStartPosition = getMapTile(2, 18).getLocation();
}

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        AngerSpirit an   = new AngerSpirit(101, getMapTile(2, 12).getLocation());
        an.setInteractScript(new DenialEnemyScript());
        an.setCurrentAnimationName("STAND_LEFT");
        npcs.add(an);


        AngerSpirit ae   = new AngerSpirit(101, getMapTile(3, 7).getLocation());
        ae.setInteractScript(new DenialEnemyScript());
        ae.setCurrentAnimationName("STAND_LEFT");
        npcs.add(ae);

        AngerSpirit ag   = new AngerSpirit(101, getMapTile(17, 2).getLocation());
        ag.setInteractScript(new DenialEnemyScript());
        ag.setCurrentAnimationName("STAND_LEFT");
        npcs.add(ag);

        // // enemies near player, all share DenialEnemyScript (spread out a bit)
        // Skeleton s  = new Skeleton(100, getMapTile(10, 8).getLocation().subtractY(16).subtractX(6));
        // s.setInteractScript(new DenialEnemyScript());
        // // face the player at start (player is to the RIGHT of (14,18) -> face RIGHT)
        // s.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(s);

        // Spirit sp   = new Spirit(101, getMapTile(21, 15).getLocation().subtractY(16).subtractX(10));
        // sp.setInteractScript(new DenialEnemyScript());
        // // player start is to the LEFT of (20,21) -> face LEFT
        // sp.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(sp);

        // // moved Armored Skeleton to an open tile
        // ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(2, 5).getLocation().subtractY(16).subtractX(4));
        // as.setInteractScript(new DenialEnemyScript());
        // // player start is to the LEFT of (22,19) -> face LEFT
        // as.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(as);

        // // moved Armored Skeleton to an open tile
        // ArmoredSkeleton ass = new ArmoredSkeleton(102, getMapTile(6, 5).getLocation().subtractY(16).subtractX(4));
        // ass.setInteractScript(new DenialEnemyScript());
        // // player start is to the LEFT of (22,19) -> face LEFT
        // ass.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(ass);
        
        // // enemies near player, all share DenialEnemyScript (spread out a bit)

        //  // adding boss into game (fingers crossed)
        AngerBoss ab = new AngerBoss(102, getMapTile(15, 15).getLocation());
        ab.setInteractScript(new AngerBossScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        ab.setCurrentAnimationName("STAND_LEFT");
        npcs.add(ab);



        underworldSpirit us = new underworldSpirit(103, getMapTile(17, 7).getLocation());
        us.setInteractScript(new AngerSideQuestScript());
        us.setCurrentAnimationName("STAND_LEFT");
        us.setAutoBattle(false);
        npcs.add(us);

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        
        triggers.add(new Trigger(0, 1000, 250, 250, new AngerScript(), "hasEnteredAnger"));
        
        return triggers;
    }

    @Override
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(2, 2).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(15, 2).getLocation(), Item.ItemList.apple));
        
        return collectables;
    }
    
    @Override
    public void loadScripts() {
       getMapTile(16, 15).setInteractScript(new BargainingEntryScript());

       getMapTile(8, 18).setInteractScript(new MazeScript());
       getMapTile(9, 18).setInteractScript(new MazeScript());

       getMapTile(2, 3).setInteractScript(new MazeScript());
       getMapTile(3, 3).setInteractScript(new MazeScript());

       getMapTile(17, 13).setInteractScript(new MazeScript());
    }
}
