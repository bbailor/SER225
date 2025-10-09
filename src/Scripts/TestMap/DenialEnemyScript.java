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
                    addText("Monster: Forfeit your journey!");
                    addText("Monster: You can't save her, she's too far gone!");
                    addText("Gnomeo: Liar!!! You'll pay for your treachery!");

                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToDenialEnemy", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDenialEnemy", true));

                // Prompt with Yes/No (same pattern as BugScript)
                addScriptAction(new TextboxScriptAction() {{
                    addText("A foe blocks your path.");
                    addText("Start battle?", new String[] { "Yes", "No" });
                }});

                // Handle the selection (0 = Yes, 1 = No)
                addScriptAction(new ConditionalScriptAction() {{
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0; // Yes
                            }
                        });
                        addScriptAction(new TextboxScriptAction("*growl*..."));
                    }});

                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1; // No
                            }
                        });
                        addScriptAction(new TextboxScriptAction("You cower in retreat."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
