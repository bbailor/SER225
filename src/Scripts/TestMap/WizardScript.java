package Scripts.TestMap;

import Level.MapTile;
import Level.Script;
import Level.ScriptState;
import Level.TileType;
import ScriptActions.*;
import Utils.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import EnhancedMapTiles.CollectableItems;
import GameObject.Frame;
import Items.KnifeOfLife;

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
        addText("Osiris: *sighs* If you are going.. then at least\ntake your mother’s Knife of Life.");
    }});

    // Knife drops mid-dialogue
    addScriptAction(new ScriptAction() {
        @Override
        public ScriptState execute() {
            Point location = map.getMapTile(10, 18).getLocation();
            CollectableItems knifeOfLife = new KnifeOfLife(location);
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
