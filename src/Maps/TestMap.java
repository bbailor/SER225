package Maps;

import EnhancedMapTiles.CollectableItem;
import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.BargainingBoss;
import NPCs.DenialBoss;
import NPCs.DepressionBoss;
import NPCs.AngerBoss;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import Scripts.SimpleTextScript;
import Scripts.MapOneDenial.DenialBossScript;
import Scripts.MapOneDenial.DenialEnemyScript;
import Scripts.MapThreeBargaining.BargainingBossScript;
import Scripts.MapFourDepression.DepressionBossScript;
import Scripts.MapTwoAnger.AngerBossScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import java.util.ArrayList;
import NPCs.DenialBoss;
import Scripts.MapOneDenial.AngerEntryScript;
import Scripts.MapTwoAnger.BargainingEntryScript;

// Represents a test map to be used in a level
public class TestMap extends Map {

    //Tileset of all the items that can be placed on the map.
    protected Tileset itemSet;

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 22).getLocation();
        //itemSet = 
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        PushableRock pushableRock = new PushableRock(getMapTile(21, 7).getLocation());
        enhancedMapTiles.add(pushableRock);

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        /*Walrus walrus = new Walrus(1, getMapTile(4, 28).getLocation().subtractY(40));
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
        
        Wizard wizard = new Wizard(4, getMapTile(2, 22).getLocation());
        wizard.setInteractScript(new WizardScript());
        npcs.add(wizard);

        // Quest skeleton - appears when quest starts, disappears when defeated
        Skeleton questSkeleton = new Skeleton(103, getMapTile(2, 3).getLocation().subtractY(16).subtractX(6));
        questSkeleton.setInteractScript(new WizardQuestSkeletonScript());
        // Will appear when wizardQuestStarted is true AND wizardSaved is false
        questSkeleton.setExistenceFlag("wizardSaved"); 
        questSkeleton.setCurrentAnimationName("STAND_RIGHT");
        questSkeleton.setAutoBattle(false);
        npcs.add(questSkeleton);
        // BargainingBoss b = new BargainingBoss(103, getMapTile(10, 10).getLocation().subtractY(16).subtractX(6));
        // b.setInteractScript(new BargainingBossScript());
        // b.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(b);
        
    

        // DepressionBoss dp = new DepressionBoss(102, getMapTile(17, 20).getLocation().subtractY(16).subtractX(4));
        // dp.setInteractScript(new DepressionBossScript());
        // dp.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(dp);

        // AngerBoss ab = new AngerBoss(102, getMapTile(15, 10).getLocation().subtractY(16).subtractX(4));
        // ab.setInteractScript(new AngerBossScript());
        // ab.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(ab);
        
        // Skeleton s  = new Skeleton(100, getMapTile(9, 22).getLocation().subtractY(16).subtractX(6));
        // s.setInteractScript(new DenialEnemyScript());
        // s.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(s); 

        // Spirit sp   = new Spirit(101, getMapTile(9, 14).getLocation().subtractY(16).subtractX(10));
        // sp.setInteractScript(new DenialEnemyScript());
        // sp.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(sp);

        // ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(8, 15).getLocation().subtractY(16).subtractX(4));
        // as.setInteractScript(new DenialEnemyScript());
        // as.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(as);

        // DenialBoss db = new DenialBoss(102, getMapTile(7, 13).getLocation().subtractY(16).subtractX(4));
        // db.setInteractScript(new DenialBossScript());
        // db.setCurrentAnimationName("STAND_LEFT");
        // npcs.add(db);

        return npcs;
    }

    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();

        triggers.add(new Trigger(0, 0, 5000, 5000, new LostBallScript(), "hasLostBall"));
        
        return triggers;
    }

    @Override
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(1, 27).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(1, 22).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(1, 17).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(15, 20).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(18, 14).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(12, 9).getLocation(), Item.ItemList.apple));
        
        collectables.add(new CollectableItem(getMapTile(14, 3).getLocation(), Item.ItemList.cherry));
        collectables.add(new CollectableItem(getMapTile(4, 3).getLocation(), Item.ItemList.cherry));
        collectables.add(new CollectableItem(getMapTile(2, 9).getLocation(), Item.ItemList.cherry));

        //collectables.add(new CollectableItem(getMapTile(16, 20).getLocation(), Item.ItemList.denials_staff));
        return collectables;
    }

    @Override
    public void loadScripts() {
        getMapTile(3, 3).setInteractScript(new SimpleTextScript("Storage Shack"));
        getMapTile(7, 22).setInteractScript(new SimpleTextScript("Isis x Osiris <3 <3 <3"));
        getMapTile(21, 6).setInteractScript(new DenialEntryScript());
        //getMapTile(21, 6).setInteractScript(new AngerEntryScript());
        //getMapTile(21, 6).setInteractScript(new BargainingEntryScript());
    }
}
