package Maps;

import Level.*;
import NPCs.*;
import Scripts.MapFiveAcceptance.AcceptanceEntryScript;
import Tilesets.Map5Tileset;   // ← We will create this next (copy of Map4Tileset + gravestone)
import Utils.Point;

import java.util.ArrayList;

public class AcceptanceMap extends Map {

    public AcceptanceMap() {
        super("acceptance_map.txt", new Map5Tileset());

        // Place player somewhere safe — change this freely later
        this.playerStartPosition = getMapTile(2, 4).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // OPTIONAL: If acceptance has a special NPC, add it here
        // Example:
        // AcceptanceBoss boss = new AcceptanceBoss(100, getMapTile(10, 5).getLocation());
        // boss.setInteractScript(new AcceptanceBossScript());
        // boss.setCurrentAnimationName("STAND_RIGHT");
        // npcs.add(boss);

        return npcs;
    }

    @Override
    public ArrayList<Trigger> loadTriggers() {
        ArrayList<Trigger> triggers = new ArrayList<>();

        // EXAMPLE: If you want interacting with the grave to leave the map:
        // triggers.add(new Trigger(gravestoneX, gravestoneY, new AcceptanceExitScript()));

        return triggers;
    }

    @Override
    public void loadScripts() {
        // Nothing needed unless special scripts run on map load
    }
}
