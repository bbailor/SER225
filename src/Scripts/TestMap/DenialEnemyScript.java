package Scripts.TestMap;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

// script for talking to denial enemy npc
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

                // Prompt with Yes/No
                addScriptAction(new TextboxScriptAction() {{
                    addText("A foe blocks your path.");
                    addText("Start battle?", new String[] { "Yes", "No" });
                }});

                // Handle selection
                addScriptAction(new ConditionalScriptAction() {{

                    // Yes
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0;
                            }
                        });
                        // IMPORTANT: pass the actual NPC tied to this script
                        addScriptAction(new StartBattleScriptAction(DenialEnemyScript.this.entity));
                    }});

                    // No
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });
                        addScriptAction(new TextboxScriptAction("Monster: Cowardice... Typical of Gnomes"));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
