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
                    addText("Depression: At long last... The Hero.");
                    addText("Depression: At long last... The Hero.");
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
                                
                                // Get the map tile locations for the wispies
                                WispyChainBuilder builder = new WispyChainBuilder(700);
                                Wispy firstWispy = builder
                                    .addPoint(map.getMapTile(2, 5).getLocation().subtractX(32).addY(32))   // First wispy
                                    .addPoint(map.getMapTile(3, 6).getLocation())  // Second wispy
                                    .addPoint(map.getMapTile(2, 7).getLocation().subtractX(32).addY(32))  // Third wispy
                                    .addPoint(map.getMapTile(0, 6).getLocation().addX(20))  // Fourth wispy
                                    .addPoint(map.getMapTile(1, 5).getLocation().addX(32).addY(32))  // Fifth wispy
                                    .build();

                                map.addNPC(firstWispy);
                                
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
                        addScriptAction(new TextboxScriptAction("Depression: Sad... You really are a pity."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
