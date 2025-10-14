package Maps;

import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import Scripts.SimpleTextScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(17, 20).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        PushableRock pushableRock = new PushableRock(getMapTile(2, 7).getLocation());
        enhancedMapTiles.add(pushableRock);

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
        
        Wizard wizard = new Wizard(4, getMapTile(10, 15).getLocation());
        wizard.setInteractScript(new WizardScript());
        npcs.add(wizard);

        // enemies near player, all share DenialEnemyScript (spread out a bit)
        Skeleton s  = new Skeleton(100, getMapTile(14, 18).getLocation().subtractY(16).subtractX(6));
        s.setInteractScript(new DenialEnemyScript());
        // face the player at start (player is to the RIGHT of (14,18) -> face RIGHT)
        s.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(s);

        Spirit sp   = new Spirit(101, getMapTile(20, 21).getLocation().subtractY(16).subtractX(10));
        sp.setInteractScript(new DenialEnemyScript());
        // player start is to the LEFT of (20,21) - face LEFT
        sp.setCurrentAnimationName("STAND_LEFT");
        npcs.add(sp);

        // moved Armored Skeleton to an open tile
        ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(22, 19).getLocation().subtractY(16).subtractX(4));
        as.setInteractScript(new DenialEnemyScript());
        // player start is to the LEFT of (22,19) -> face LEFT
        as.setCurrentAnimationName("STAND_LEFT");
        npcs.add(as);

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();
        triggers.add(new Trigger(790, 1030, 100, 10, new LostBallScript(), "hasLostBall"));
        triggers.add(new Trigger(790, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        triggers.add(new Trigger(890, 960, 10, 80, new LostBallScript(), "hasLostBall"));
        return triggers;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));
        getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));
        getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));
        getMapTile(2, 6).setInteractScript(new DenialEntryScript());
    }
}
