package Scripts.TestMap;

import java.util.ArrayList;

import Level.Script;
import ScriptActions.*;

// Script for the skeleton that traps the wizard
public class WizardQuestSkeletonScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{

            // Before wizard is saved - hostile skeleton
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("wizardSaved", false));
                addRequirement(new FlagRequirement("wizardQuestStarted", true));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Skeleton: Hehehehe... the wizard is mine!");
                    addText("Skeleton: And you'll be next, little gnome!");
                    addText("Osiris: Gnomeo! Help!");
                    addText("Osiris: This skeleton ambushed me!\nI can't fight it alone!");
                }});

                

                // Prompt with Yes/No
                addScriptAction(new TextboxScriptAction() {{
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
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Hold on, Osiris! I'm coming!");
                        }});
                        addScriptAction(new StartBattleScriptAction(WizardQuestSkeletonScript.this.entity));
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
                        addScriptAction(new TextboxScriptAction("Monster: If you can't save him,\nhow do you expect to save your precious Juliet? "));
                    }});
                }});

                
                
                
            }});

            // This shouldn't normally trigger since skeleton disappears after battle
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("wizardSaved", true));
                
                addScriptAction(new TextboxScriptAction("*The skeleton has been defeated*"));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());

        return scriptActions;
    }
}