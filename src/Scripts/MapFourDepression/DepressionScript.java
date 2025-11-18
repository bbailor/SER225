package Scripts.MapFourDepression;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class DepressionScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasStartedDepression", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("*a tear falls down Gnomeo's cheek*");
                    addText("Gnomeo: I... I don't know how I can go on...");
                    addText("Gnomeo: What if they're right about Juliet?");
                    addText("Gnomeo: What's the point of all this suffering?");
                }});

            scriptActions.add(new ChangeFlagScriptAction("hasStartedDepression", true));
            }});
            
        scriptActions.add(new UnlockPlayerScriptAction());
        }});
        
        return scriptActions;
    }
}

