package Maps;

import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.AngerBoss;
import NPCs.DenialBoss;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import NPCs.AngerSpirit;
import Scripts.SimpleTextScript;
import Scripts.MapTwoAnger.AngerBossScript;
import Scripts.MapTwoAnger.BargainingEntryScript;
import Scripts.MapOneDenial.AngerEntryScript;
import Scripts.MapOneDenial.DenialBossScript;
import Scripts.MapOneDenial.DenialEnemyScript;
import Scripts.TestMap.WizardScript;
import Tilesets.Map2Tileset;
import Utils.Point;
import java.util.ArrayList;

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

        // AngerSpirit an   = new AngerSpirit(101, getMapTile(17, 15).getLocation());
        // an.setInteractScript(new DenialEnemyScript());
        // an.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(an);

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
        AngerBoss ab = new AngerBoss(102, getMapTile(16, 16).getLocation());
        ab.setInteractScript(new AngerBossScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        ab.setCurrentAnimationName("STAND_LEFT");
        npcs.add(ab);


        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        
        return triggers;
    }

    @Override
    public void loadScripts() {
       getMapTile(16, 16).setInteractScript(new BargainingEntryScript());
    }
}
