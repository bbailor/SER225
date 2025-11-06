package Scripts.MapThreeBargaining;
import Level.Script;
import ScriptActions.*;
import java.util.ArrayList;


public class BargainingBossScript extends Script {
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("[INSERT BARGAINING BOSS DIALOGUE] ");
                    
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToBargainingBoss", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", true));
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
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Juliet, wait for me...");
                        }});
                       
                        addScriptAction(new StartBattleScriptAction(BargainingBossScript.this.entity));
                    }});

                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });
                        addScriptAction(new TextboxScriptAction("Bargaining: Cowardice... Entirely expected."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
