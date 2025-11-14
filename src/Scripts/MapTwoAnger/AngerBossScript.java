package Scripts.MapTwoAnger;

import Level.Script;
import Level.ScriptState;
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
                    addText("[INSERT ANGER DIALOGUE HERE]");
                    
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
                            addText("[INSERT TEXT HERE]"); //what u say after hitting start battle 'yes'
                        }});
                       
                        addScriptAction(new StartBattleScriptAction(AngerBossScript.this.entity));

                        addScriptAction(new ChangeFlagScriptAction("hasDefeatedAnger", true));
                                
                        // Spawn the wispy guide
                        addScriptAction(new ScriptAction() {
                            @Override
                            public ScriptState execute() {
                                // Remove the boss from map
                                entity.setMapEntityStatus(Level.MapEntityStatus.REMOVED);
                                
                                // Spawn single wispy at tile (17, 15) with Y offset
                                Point wispyLocation = map.getMapTile(17, 16).getLocation();
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
                        addScriptAction(new TextboxScriptAction("Anger: Cowardice... Entirely expected."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
