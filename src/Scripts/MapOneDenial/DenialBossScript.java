package Scripts.MapOneDenial;

import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;

public class DenialBossScript extends Script {
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDenialBoss", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Monster: What have we here...\nGnomes are quite a rare sight ");
                    addText("Gnomeo: You are no ordinary monster\nRegardless, nothing will stop me from finding Juliet");
                    addText("Monster: You will fall by my hand\nYour journey ends here");
                    addText("Gnomeo: Juliet, wait for me");
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToDenialBoss", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDenialBoss", true));
                addScriptAction(new TextboxScriptAction() {{
                    addText("Start battle?", new String[] { "Yes", "No" });
                }});

                addScriptAction(new ConditionalScriptAction() {{
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0;
                            }
                        });
                        addScriptAction(new StartBattleScriptAction(DenialBossScript.this.entity));
                    }});

                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });
                        addScriptAction(new TextboxScriptAction("Monster: Cowardice... Entirely expected"));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
