package ScriptActions;

import Engine.Inventory;
import EnhancedMapTiles.CollectableItems;
import Level.ItemStack;
import Level.ScriptState;

public class PickUpItemScriptAction extends ScriptAction{
    protected CollectableItems collectableItem;
    protected Inventory inventory;
    public PickUpItemScriptAction(CollectableItems collectableItem){
        this.collectableItem = collectableItem;
    }

    public void setup()
    {
        inventory = this.map.getPlayer().getEntity().getInventory();

        if (inventory.isFull()){
            System.out.println("inventory full");
        }
        else {
            map.getCollectableItems().remove(collectableItem);
            inventory.setStack(1, new ItemStack(collectableItem.getItem()));
        }
    }
    public ScriptState execute()
    {
        return ScriptState.COMPLETED;
    }
}