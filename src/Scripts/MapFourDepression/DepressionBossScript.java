package Scripts.MapFourDepression;

import Level.Script;
import Level.ScriptState;
import Level.WispyChainBuilder;
import NPCs.Wispy;
import ScriptActions.*;
import Utils.Point;

import java.util.ArrayList;

public class DepressionBossScript extends Script {
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDepressionBoss", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("[INSERT DEPRESSION DIALOGUE HERE]");
                    
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToDepressionBoss", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToDepressionBoss", true));
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
                            addText("[INSERT TEXT HERE]"); //what u say after hitting start battle 'yes'
                        }});
                       
                        addScriptAction(new StartBattleScriptAction(DepressionBossScript.this.entity));

                        addScriptAction(new ChangeFlagScriptAction("hasDefeatedDepression", true));
                                
                        // Spawn the wispy guide
                        addScriptAction(new ScriptAction() {
                            @Override
                            public ScriptState execute() {
                                
                                Point wispyLocation = map.getMapTile(2, 5).getLocation().subtractX(32).addY(32);
                                Wispy wispy = new Wispy(600, wispyLocation.x, wispyLocation.y);
                                
                                map.addNPC(wispy);
                                
                                return ScriptState.COMPLETED;
                            }
                        });
                    }});

                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });
                        addScriptAction(new TextboxScriptAction("Depression: Cowardice... Entirely expected."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
