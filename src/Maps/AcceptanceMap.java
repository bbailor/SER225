package Maps;

import Level.*;
import NPCs.*;
import Scripts.MapFiveAcceptance.GravestoneScript;
import Scripts.MapFiveAcceptance.JulietScript;
import Scripts.MapFiveAcceptance.OsirisScript;
import Tilesets.Map5Tileset;
import Utils.Point;
import EnhancedMapTiles.CollectableItem;

import java.util.ArrayList;

public class AcceptanceMap extends Map {

    public AcceptanceMap() {
        super("acceptance_map.txt", new Map5Tileset());

        //spawn point
        this.playerStartPosition = getMapTile(2, 5).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Juliet juliet = new Juliet(1, getMapTile(24, 1).getLocation());
        juliet.setInteractScript(new JulietScript());
        juliet.setCurrentAnimationName("STAND_RIGHT");
        npcs.add(juliet);

        Wizard osiris = new Wizard(4, getMapTile(30, 5).getLocation());
        osiris.setInteractScript(new OsirisScript());
        npcs.add(osiris);

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        return new ArrayList<>();
    }

    @Override
    public void loadScripts() {
        super.loadScripts();

        // -----------------------------------------------------------
        // GRAVESTONE INTERACTION SCRIPT
        // Tile is at (32,5)
        // -----------------------------------------------------------
        MapTile gravestone = getMapTile(32, 5);

        if (gravestone != null) {
            GravestoneScript script = new GravestoneScript();

            gravestone.setInteractScript(script);

            //manually initialize script so it works
            script.setMap(this);
            script.setPlayer(this.player);
            script.setListeners(this.listeners);
            script.initialize();
        }
    }

    // -----------------------------------------------------------
    //  Juliet Acceptance → Spawn flowers ONCE
    // -----------------------------------------------------------
    @Override
    public void update(Player player) {
        super.update(player);

        FlagManager flags = this.getFlagManager();
        if (flags != null
                && flags.isFlagSet("JulietAccept")
                && !flags.isFlagSet("JulietFlowersSpawned")) {

            // Juliet originally stood at tile (11,1)
            Point pos = getMapTile(24, 1).getLocation();

            CollectableItem flowers = new CollectableItem(
                    pos.x + 25,
                    pos.y + 40,
                    Item.ItemList.flowers
            );

            this.addCollectableItem(flowers);

            flags.setFlag("JulietFlowersSpawned");
        }
    }
}
