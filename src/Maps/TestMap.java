package Maps;

import EnhancedMapTiles.CollectableItem;
import EnhancedMapTiles.PushableRock;
import Level.*;
import NPCs.ArmoredSkeleton;
import NPCs.DenialBoss;
import NPCs.Skeleton;
import NPCs.Spirit;
import NPCs.Wizard;
import Scripts.SimpleTextScript;
import Scripts.MapOneDenial.DenialBossScript;
import Scripts.MapOneDenial.DenialEnemyScript;
import Scripts.TestMap.*;
import Tilesets.CommonTileset;
import java.util.ArrayList;
import NPCs.DenialBoss;

// Represents a test map to be used in a level
public class TestMap extends Map {

    //Tileset of all the items that can be placed on the map.
    protected Tileset itemSet;

    public TestMap() {
        super("test_map.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(17, 20).getLocation();
        //itemSet = 
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
        
        Wizard wizard = new Wizard(4, getMapTile(10, 15).getLocation());
        wizard.setInteractScript(new WizardScript());
        npcs.add(wizard);

        // Quest skeleton - appears when quest starts, disappears when defeated
        Skeleton questSkeleton = new Skeleton(103, getMapTile(20, 1).getLocation().subtractY(16).subtractX(6));
        questSkeleton.setInteractScript(new WizardQuestSkeletonScript());
        // Will appear when wizardQuestStarted is true AND wizardSaved is false
        questSkeleton.setExistenceFlag("wizardSaved"); 
        questSkeleton.setCurrentAnimationName("STAND_LEFT");
        npcs.add(questSkeleton);
       
        Skeleton s  = new Skeleton(100, getMapTile(14, 18).getLocation().subtractY(16).subtractX(6));
        s.setInteractScript(new DenialEnemyScript());
        s.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(s);

        Spirit sp   = new Spirit(101, getMapTile(20, 21).getLocation().subtractY(16).subtractX(10));
        sp.setInteractScript(new DenialEnemyScript());
        sp.setCurrentAnimationName("STAND_LEFT");
        npcs.add(sp);

        ArmoredSkeleton as = new ArmoredSkeleton(102, getMapTile(22, 19).getLocation().subtractY(16).subtractX(4));
        as.setInteractScript(new DenialEnemyScript());
        as.setCurrentAnimationName("STAND_LEFT");
        npcs.add(as);

        DenialBoss db = new DenialBoss(102, getMapTile(17, 10).getLocation().subtractY(16).subtractX(4));
        db.setInteractScript(new DenialBossScript());
        db.setCurrentAnimationName("STAND_LEFT");
        npcs.add(db);


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
    protected ArrayList<CollectableItem> loadCollectableItems() {
        ArrayList<CollectableItem> collectables = new ArrayList<>();
        collectables.add(new CollectableItem(getMapTile(10, 24).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(12, 26).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(9, 10).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(9, 18).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(16, 14).getLocation(), Item.ItemList.apple));
        collectables.add(new CollectableItem(getMapTile(1, 20).getLocation(), Item.ItemList.apple));
        
        collectables.add(new CollectableItem(getMapTile(22, 2).getLocation(), Item.ItemList.cherry));
        collectables.add(new CollectableItem(getMapTile(22, 5).getLocation(), Item.ItemList.cherry));
        collectables.add(new CollectableItem(getMapTile(12, 6).getLocation(), Item.ItemList.cherry));

        // collectables.add(new CollectableItem(getMapTile(10, 24).getLocation(), Item.ItemList.cat));
        // collectables.add(new CollectableItem(getMapTile(16, 20).getLocation(), Item.ItemList.cat));
        return collectables;
    }

    @Override
    public void loadScripts() {
        getMapTile(21, 19).setInteractScript(new SimpleTextScript("Cat's house"));
        getMapTile(7, 26).setInteractScript(new SimpleTextScript("Walrus's house"));
        getMapTile(20, 4).setInteractScript(new SimpleTextScript("Dino's house"));
        getMapTile(2, 6).setInteractScript(new DenialEntryScript());
    }
}
