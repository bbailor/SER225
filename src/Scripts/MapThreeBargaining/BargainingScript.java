package Scripts.MapThreeBargaining;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class BargainingScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasStartedBargaining", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: That was tough... she was stronger\nthan the last one.");
                    addText("Gnomeo: I'm going to have to be smarter...\nNo more rushing in head first.");
                    addText("Gnomeo: This looks like a church. Maybe\n if I can't do it all myself, I can get Juliet help from Him.");
                }});

            scriptActions.add(new ChangeFlagScriptAction("hasStartedBargaining", true));
            }});
            
        scriptActions.add(new UnlockPlayerScriptAction());
        }});
        
        return scriptActions;
    }
}

