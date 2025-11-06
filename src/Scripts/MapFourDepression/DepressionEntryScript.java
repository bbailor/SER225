package Scripts.MapFourDepression;

import Level.GameListener;
import Level.Script;
import Level.ScriptState;
import Maps.DepressionMap;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.CustomRequirement;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import java.util.ArrayList;

public class DepressionEntryScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasEnteredDepression", false));
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", true));

                addRequirement(new CustomRequirement() {
                    @Override
                    public boolean isRequirementMet() {
                        // ensures player is directly below the tile, not beside it
                        if (player.getBounds().getY1() <= entity.getBounds().getY2()) {
                            return false;
                        }
                        return true;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: The world... feels heavier here.");
                    addText("Gnomeo: But I can’t stop now.");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasEnteredDepression", true));

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        for (GameListener listener : listeners) {
                            listener.switchMap(new DepressionMap());
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
