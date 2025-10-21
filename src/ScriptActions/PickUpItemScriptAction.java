package ScriptActions;

import Engine.Inventory;
import EnhancedMapTiles.CollectableItem;
import Level.ScriptState;

public class PickUpItemScriptAction extends ScriptAction{
    protected CollectableItem collectableItem;
    protected Inventory inventory;
    public PickUpItemScriptAction(CollectableItem collectableItem){
        this.collectableItem = collectableItem;
    }

    public void setup()
    {
        inventory = this.map.getPlayer().getEntity().getInventory();
    }
    public ScriptState execute()
    {
        return ScriptState.COMPLETED;
    }
}