package Scripts.MapTwoAnger;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class MazeScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasStartedAnger", true));
                addRequirement(new FlagRequirement("hasMadeWrongTurn", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: AGHHHHH!!!");
                    addText("Gnomeo: I... HATE... MAZES!!!");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasMadeWrongTurn", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasStartedAnger", true));
                addRequirement(new FlagRequirement("hasMadeWrongTurn", true));
                addScriptAction(new TextboxScriptAction("Gnomeo: This stupid maze is driving me crazy!!"));
                addScriptAction(new TextboxScriptAction("Gnomeo: No.. I cannot get distracted.\nI must find my way to her!"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
