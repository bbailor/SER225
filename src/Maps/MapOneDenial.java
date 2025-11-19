package Maps;

import EnhancedMapTiles.CollectableItem;
import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.DenialBoss;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import ScriptActions.CollectableScriptAction;
import Scripts.SimpleTextScript;
import Scripts.MapOneDenial.DenialBossScript;
import Scripts.MapOneDenial.DenialEnemyScript;
import Scripts.TestMap.DenialEntryScript;
import Scripts.TestMap.WizardScript;
import Tilesets.Map1Tileset;
import Scripts.MapOneDenial.AngerEntryScript;
import java.util.ArrayList;

// Represents a test map to be used in a level
public class MapOneDenial extends Map {

    public MapOneDenial() {
        super("map_one_denial.txt", new Map1Tileset());
        this.playerStartPosition = getMapTile(27, 15).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // enemies near player, all share DenialEnemyScript (spread out a bit)
        Skeleton s  = new Skeleton(100, getMapTile(11, 8).getLocation().subtractY(16).subtractX(6));
        s.setInteractScript(new DenialEnemyScript());
        // face the player at start (player is to the RIGHT of (14,18) -> face RIGHT)
        s.setCurrentAnimationName("STAND_RIGHT");
        //s.getVisionTiles();
        npcs.add(s);

        Spirit sp   = new Spirit(101, getMapTile(23, 14).getLocation().subtractY(16).subtractX(10));
        sp.setInteractScript(new DenialEnemyScript());
        // player start is to the LEFT of (20,21) -> face LEFT
        sp.setCurrentAnimationName("STAND_LEFT");
        //sp.getVisionTiles();
        npcs.add(sp);

        // moved Armored Skeleton to an open tile
        ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(1, 5).getLocation().subtractY(16).subtractX(4));
        as.setInteractScript(new DenialEnemyScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        as.setCurrentAnimationName("STAND_RIGHT");
        //as.getVisionTiles();
        npcs.add(as);

        // moved Armored Skeleton to an open tile
        ArmoredSkeleton ass = new ArmoredSkeleton(102, getMapTile(8, 5).getLocation().subtractY(16).subtractX(4));
        ass.setInteractScript(new DenialEnemyScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        ass.setCurrentAnimationName("STAND_LEFT");
        //ass.getVisionTiles();
        npcs.add(ass);

        Spirit spp = new Spirit(102, getMapTile(20, 8).getLocation().subtractY(16).subtractX(4));
        spp.setInteractScript(new DenialEnemyScript());
        spp.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(spp);

        ArmoredSkeleton asss = new ArmoredSkeleton(102, getMapTile(23, 5).getLocation().subtractY(16).subtractX(4));
        asss.setInteractScript(new DenialEnemyScript());
        asss.setCurrentAnimationName("STAND_LEFT");
        npcs.add(asss);

        // enemies near player, all share DenialEnemyScript (spread out a bit)

         // adding boss into game (fingers crossed)
        DenialBoss db = new DenialBoss(102, getMapTile(2, 8).getLocation().subtractY(16).subtractX(4));
        db.setInteractScript(new DenialBossScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        db.setCurrentAnimationName("STAND_LEFT");
        npcs.add(db);



        return npcs;
    }

    @Override
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(20, 5).getLocation(), Item.ItemList.cloakOfConcealment));
        return collectables;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        
        return triggers;
    }

    @Override
    public void loadScripts() {
       getMapTile(11, 13).setInteractScript(new AngerEntryScript());
    }
}