package Scripts;

import java.util.ArrayList;

import Engine.Inventory;
import EnhancedMapTiles.CollectableItems;
import Level.Item;
import Level.Script;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.PickUpItemScriptAction;
public class Interactable extends Script {
    protected CollectableItems collectableItem;
    public Interactable(CollectableItems collectableItem)
    {
        this.collectableItem = collectableItem;
    }
 
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();

        actions.add(new PickUpItemScriptAction(collectableItem));
        actions.add(new TextboxScriptAction("You picked up the " + collectableItem.getName() + "!"));
        return actions;
    }
}
