package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

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
                    addText("Greetings, traveler!");
                    addText("I sense great potential in you.");
                    addText("Use it wisely on your journey.");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToWizard", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWizard", true));
                addScriptAction(new TextboxScriptAction("We meet again. Keep honing your skills!"));
            }});
        }});

        // Unlock the player at the end
        scriptActions.add(new UnlockPlayerScriptAction());
        

        return scriptActions;
    }
}
