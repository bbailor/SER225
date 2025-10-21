package Scripts;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

import Level.Script;
import ScriptActions.*;


// simple reusable script that just shows text in textbox
// useful for generic dialogue, signs, etc.
// checkout the documentation website for a detailed guide on how this script works
public class SimpleTextScript extends Script {
    @Expose private String[] textItems;

    public SimpleTextScript(String text) {
        this.textItems = new String[] { text };
    }

    public SimpleTextScript(String[] text) {
        this.textItems = text;
    }

    private SimpleTextScript() {}
    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new TextboxScriptAction(textItems));
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
