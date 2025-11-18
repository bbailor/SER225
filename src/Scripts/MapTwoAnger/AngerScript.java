package Scripts.MapTwoAnger;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class AngerScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasStartedAnger", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: Those rotten demons! They dare to threaten my\nlove.. They will rue the day they crossed me!");
                }});

            scriptActions.add(new ChangeFlagScriptAction("hasStartedAnger", true));
            }});
            
        scriptActions.add(new UnlockPlayerScriptAction());
        }});
        
        return scriptActions;
    }
}

