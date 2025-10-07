package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

// script for talking to walrus npc
// checkout the documentation website for a detailed guide on how this script works
public class DenialEnemyScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());

        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDenialEnemy", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: What are you?? Some kind of monster?");
                    addText("Monster: You can't save her, she's too far gone!");
                    addText("Gnomeo: Liar!!! You'll pay for your treachery!");
                    addText("Monster: Even in anger, your words betray your insecurities.");
                    addText("Monster: You know why I'm here.\nTurn back now, or fight me if you dare continue your journey.");
                    addText("Gnomeo: You try to deceive me! Juliet lies in wait for me.\nYou will regret standing in my way.");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToDenialBoss", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDenialBoss", true));
                addScriptAction(new TextboxScriptAction("Gnomeo: An enemy... I can't let them stop me from reaching Juliet!"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}
