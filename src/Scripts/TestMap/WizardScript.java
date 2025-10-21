package Scripts.TestMap;

import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;
import Items.KnifeOfLife;
import Level.Item;
import Level.Script;
import Level.ScriptState;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.NPCFacePlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import ScriptActions.WaitScriptAction;
import Utils.Point;

// script for interacting with the Wizard NPC
public class WizardScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        // Lock the player while the script runs
        scriptActions.add(new LockPlayerScriptAction());

        // Make the wizard face the player
        scriptActions.add(new NPCFacePlayerScriptAction());

        // Conditional dialogue based on a flag
        scriptActions.add(new ConditionalScriptAction() {{

        addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
        addRequirement(new FlagRequirement("hasTalkedToWizard", false));

    // Dialogue Part 1
    addScriptAction(new TextboxScriptAction() {{
        addText("Osiris: Where are you rushing off to?" );
        addText("Gnomeo: To save Juliet!! She has been\ncaptured by evil forces!");
        addText("Osiris: But Gnomeo, Juliet is-");
        addText("Gnomeo: I must save her, she is my world!\nI cannot lose her!");
        addText("Osiris: *sighs* If you are going.. then at least\ntake your mother’s Knife of Life. What you seek is not here. Perhaps in the forest?");
    }});

    // Knife drops mid-dialogue
    addScriptAction(new ScriptAction() {
        @Override
        public ScriptState execute() {
            Point location = map.getMapTile(10, 18).getLocation();
            CollectableItem knifeOfLife = new CollectableItem(location.x, location.y, Item.ItemList.knife_of_life);
            map.addCollectableItem(knifeOfLife);

            return ScriptState.COMPLETED;
        }
    });

    // Dialogue Part 2
    addScriptAction(new TextboxScriptAction() {{
        addScriptAction(new WaitScriptAction(30));
        addText("(Hint: Use [SPACE] to pick up items)");
        addText("(Hint: Use [E] to open your inventory)");
    }});

    // Set flag
    addScriptAction(new ChangeFlagScriptAction("hasTalkedToWizard", true));
    }});


            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWizard", true));
                addScriptAction(new TextboxScriptAction("Osiris: This is a journey you must take alone..\nFarewell, Gnomeo."));
     
            }});
        }});

        // Unlock the player at the end
        scriptActions.add(new UnlockPlayerScriptAction());
        
        return scriptActions;
    }
}
