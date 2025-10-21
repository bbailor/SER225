package Maps;

import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import Scripts.SimpleTextScript;
import Tilesets.Map1Tileset;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class MapOneDenial extends Map {

    public MapOneDenial() {
        super("map_one_denial.txt", new Map1Tileset());
        this.playerStartPosition = getMapTile(17, 16).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

/*      Walrus walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));
        walrus.setInteractScript(new WalrusScript());
        npcs.add(walrus);

        Dinosaur dinosaur = new Dinosaur(2, getMapTile(13, 4).getLocation());
        dinosaur.setExistenceFlag("hasTalkedToDinosaur");
        dinosaur.setInteractScript(new DinoScript());
        npcs.add(dinosaur);
        
        Bug bug = new Bug(3, getMapTile(7, 12).getLocation().subtractX(20));
        bug.setInteractScript(new BugScript());
        npcs.add(bug);
*/  
        

        // enemies near player, all share DenialEnemyScript (spread out a bit)
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
