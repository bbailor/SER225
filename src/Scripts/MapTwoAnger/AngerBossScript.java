package Scripts.MapTwoAnger;

import Level.Script;
import Level.ScriptState;
import Level.WispyChainBuilder;
import NPCs.Wispy;
import ScriptActions.*;
import Utils.Point;

import java.util.ArrayList;

public class AngerBossScript extends Script {
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToAngerBoss", false));
                addScriptAction(new TextboxScriptAction() {{
                    addText("*A villainous laugh echoes*");
                    addText("Anger: So... this is the one I have heard\nso much about.");
                    addText("Gnomeo: Cease your mockery! I have business\nbeyond the likes of you!");
                    addText("Anger: Disappointing. I expected more.");
                    addText("Gnomeo: YOU'LL REGRET IGNORING ME!");
                    addText("Anger: Still, I suppose you did make it\npast Denial...");
                    addText("Gnomeo: ...");
                    addText("Anger: Ah.. you're ready to talk now?");
                    addText("Gnomeo: I will not talk to the likes of you!");
                    addText("Anger: Then we will fight like true\nwarriors! I am so glad we agree.");
                    
                    
                }});
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToAngerBoss", true));
            }});

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToAngerBoss", true));
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
                            addText("Anger: This is going to be fun...\nMay the leveler head prevail!"); //what u say after hitting start battle 'yes'
                        }});
                       
                        addScriptAction(new StartBattleScriptAction(AngerBossScript.this.entity));

                        addScriptAction(new ChangeFlagScriptAction("hasDefeatedAnger", true));
                                
                        // Spawn the wispy guide
                        addScriptAction(new ScriptAction() {
                            @Override
                            public ScriptState execute() {
                                // Remove the boss from map
                                entity.setMapEntityStatus(Level.MapEntityStatus.REMOVED);
                                
                                // Get the map tile locations for the wispies
                                WispyChainBuilder builder = new WispyChainBuilder(600);
                                Wispy firstWispy = builder
                                    .addPoint(map.getMapTile(16, 18).getLocation())   // First wispy
                                    .addPoint(map.getMapTile(16, 16).getLocation())  // Second wispy
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
                        addScriptAction(new TextboxScriptAction("Anger: You shy away from my challenge??\nYou must learn to place your anger appropriately!"));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
