package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

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
                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: Where are you rushing off to?" );
                    addText("Gnomeo: To save Juliet!! She has been\ncaptured by evil forces!");
                    addText("Osiris: But Gnomeo, Juliet is-");
                    addText("Gnomeo: I must save her, she is my world!\nI cannot lose her!");
                    addText("Osiris: *sighs* If you are going.. then at least\ntake your mother’s Knife of Life.");
                    addText("(Hint: Use [E] to open your inventory)");
                }});
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
