package Scripts;

import java.util.ArrayList;

import Engine.Inventory;
import Level.Item;
// import Level.ItemStack;
import Level.Script;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.PickUpItemScriptAction;
public class Interactable extends Script {
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();

        actions.add(new TextboxScriptAction("You picked up an interactable item"));
   
        System.out.println("knife of life picked up");

        //implement logic

        return actions;
    }
}
