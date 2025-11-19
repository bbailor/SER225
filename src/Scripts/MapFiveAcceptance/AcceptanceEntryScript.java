package Scripts.MapFiveAcceptance;

import Level.GameListener;
import Level.Script;
import Level.ScriptState;
import Maps.AcceptanceMap;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;

import java.util.ArrayList;

public class AcceptanceEntryScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{

                addRequirement(new FlagRequirement("hasEnteredAcceptance", false));
                addRequirement(new FlagRequirement("hasTalkedToDepressionBoss", true));

                // Dialogue
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: This is it... The final step.");
                    addText("Gnomeo: I wish things were different.");
                    addText("Gnomeo: I can't go home now..\nNot when I'm so close to saving Juliet.");
                }});

                
                addScriptAction(new ChangeFlagScriptAction("hasEnteredAcceptance", true));

                // Switch maps
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        for (GameListener listener : listeners) {
                            listener.switchMap(new AcceptanceMap());
                        }
                        return ScriptState.COMPLETED;
                    }
                });
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
