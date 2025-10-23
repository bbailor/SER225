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
                    addText("Denial: What have we here... Gnomes are quite\na rare sight! ");
                    addText("Gnomeo: You are no ordinary monster. Regardless,\nnothing will stop me from finding Juliet.");
                    addText("Denial: You will fall by my hand, just as Juliet\nfell to my master.");
                    addText("Gnomeo: Y- your master.? Death couldn't get her!");
                    addText("Gnomeo: No... this is trickery! I made sure she\nwas safe!");
                    addText("Gnomeo: You monsters speak only lies!");
                    addText("Denial: Listen to you try to convince yourself!\nIt's unbecoming of a hero.");
                    addText("Denial: Pathetic... It's a shame your journey must\nend here!");
                    addText("Denial: You've been quite the entertainement.");
                    
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
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Juliet, wait for me...");
                        }});
                       
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
                        addScriptAction(new TextboxScriptAction("Denial: Cowardice... Entirely expected."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
