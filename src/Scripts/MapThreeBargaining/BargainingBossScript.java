package Scripts.MapThreeBargaining;

import Level.Script;
import Level.ScriptState;
import Level.TextboxItem;
import Level.WispyChainBuilder;
import ScriptActions.*;
import java.util.ArrayList;
import NPCs.Wispy;
import Utils.Point;

/**
 * Bargaining Boss - A dialogue-based battle where your choices determine survival
 * Win: Enemy dies and you continue
 * Lose: Player dies (game over)
 */
public class BargainingBossScript extends Script {
    
    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            
            // FIRST TIME MEETING - Introduction
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", false));
                
                addScriptAction(new TextboxScriptAction() {{
                    addText("Bargaining: Ah, another soul lost in grief...");
                    addText("Bargaining: You seek your beloved Juliet, yes?");
                    addText("Bargaining: Perhaps we can make a deal...");
                }});
                
                addScriptAction(new ChangeFlagScriptAction("hasTalkedToBargainingBoss", true));
            }});

            // RETURN VISIT - Start Dialogue Battle
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", true));
                addRequirement(new FlagRequirement("hasDefeatedBargaining", false));
                
                addScriptAction(new TextboxScriptAction() {{
                    addText("Bargaining: Ready to negotiate for your fate?");
                    addText("Choose your words carefully...\nYour life depends on it.");
                    
                    ArrayList<String> options = new ArrayList<>();
                    options.add("I'm ready");
                    options.add("Not yet");
                    
                    TextboxItem responseItem = new TextboxItem(
                        "Bargain for your fate?", 
                        options, 
                        false
                    );
                    
                    addText(responseItem);
                }});

                addScriptAction(new ConditionalScriptAction() {{
                    
                    // Player chooses "I'm ready" - START DIALOGUE BATTLE
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0;
                            }
                        });
                        
                        // Round 1
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Bargaining: Juliet is lost to you forever.");
                            addText("Bargaining: What makes you think you deserve\nto find her?");
                            
                            ArrayList<String> options = new ArrayList<>();
                            options.add("Because I love her");
                            options.add("I've come this far already");
                            options.add("You can't stop me");
                            
                            TextboxItem responseItem = new TextboxItem(
                                "Gnomeo's response:", 
                                options, 
                                false
                            );
                            
                            addText(responseItem);
                        }});

                        addScriptAction(new ConditionalScriptAction() {{
                            
                            // Choice 1: "Because I love her" - WEAK RESPONSE
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 0;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Love? How naive...");
                                    addText("Bargaining: Love didn't save her before.\nIt won't save her now.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round1", false));
                            }});

                            // Choice 2: "I've come this far already" - STRONG RESPONSE
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 1;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Hmm... persistence. Interesting.");
                                    addText("Bargaining: But the hardest trials are yet to come.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round1", true));
                            }});

                            // Choice 3: "You can't stop me" - WEAK RESPONSE
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 2;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Such arrogance!");
                                    addText("Bargaining: That pride will be your downfall.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round1", false));
                            }});
                        }});

                        // ROUND 2: The bargain
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Bargaining: Let me offer you a deal...");
                            addText("Bargaining: Turn back now, and I'll spare you\nthe pain of failure.");
                            addText("Bargaining: Continue forward, and you risk\nlosing everything.");
                            
                            ArrayList<String> options = new ArrayList<>();
                            options.add("I accept defeat");
                            options.add("I'll take that risk");
                            options.add("Or.. let me pass now");
                            
                            TextboxItem responseItem = new TextboxItem(
                                "Gnomeo's response:", 
                                options, 
                                false
                            );
                            
                            addText(responseItem);
                        }});

                        addScriptAction(new ConditionalScriptAction() {{
                            
                            // Choice 1: "I accept defeat" - TERRIBLE
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 0;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Wise... but weak.");
                                    addText("Bargaining: Those who accept defeat\ndon't deserve victory.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round2", false));
                            }});

                            // Choice 2: "I'll take that risk" - STRONG
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 1;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Foolish courage...");
                                    addText("Bargaining: But perhaps that's what she needs.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round2", true));
                            }});

                            // Choice 3: "Split the difference" - NEUTRAL
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 2;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Trying to bargain with Bargaining?");
                                    addText("Bargaining: Clever... but not clever enough.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round2", false));
                            }});
                        }});

                        // ROUND 3: Final question
                        addScriptAction(new TextboxScriptAction() {{
                            addText("Bargaining: One final question...");
                            addText("Bargaining: If finding Juliet means losing yourself,\nwould you still go?");
                            
                            ArrayList<String> options = new ArrayList<>();
                            options.add("Yes, without hesitation");
                            options.add("I would find another way");
                            options.add("She wouldn't want that");
                            
                            TextboxItem responseItem = new TextboxItem(
                                "Gnomeo's response:", 
                                options, 
                                false
                            );
                            
                            addText(responseItem);
                        }});

                        addScriptAction(new ConditionalScriptAction() {{
                            
                            // Choice 1: "Yes, without hesitation" - BAD
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 0;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Reckless abandon...");
                                    addText("Bargaining: Love should strengthen you,\nnot destroy you.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round3", false));
                            }});

                            // Choice 2: "I would find another way" - GOOD
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 1;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Balance. Wisdom.");
                                    addText("Bargaining: Perhaps you understand after all.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round3", true));
                            }});

                            // Choice 3: "She wouldn't want that" - GOOD
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                        return answer == 2;
                                    }
                                });
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: You know her heart...");
                                    addText("Bargaining: That is more powerful than you realize.");
                                }});
                                addScriptAction(new ChangeFlagScriptAction("bargaining_round3", true));
                            }});
                        }});

                        // CALCULATE RESULT: Need 2/3 correct to win
                        addScriptAction(new ConditionalScriptAction() {{
                            
                            // VICTORY: Won 2 or 3 rounds
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        boolean r1 = map.getFlagManager().isFlagSet("bargaining_round1");
                                        boolean r2 = map.getFlagManager().isFlagSet("bargaining_round2");
                                        boolean r3 = map.getFlagManager().isFlagSet("bargaining_round3");
                                        int wins = (r1 ? 1 : 0) + (r2 ? 1 : 0) + (r3 ? 1 : 0);
                                        return wins >= 2;
                                    }
                                });
                                
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: Impressive...");
                                    addText("Bargaining: You've proven your resolve.");
                                    addText("Bargaining: Very well. I shall not stand\nin your way.");
                                }});
                                
                                addScriptAction(new ChangeFlagScriptAction("hasDefeatedBargaining", true));
                                
                                addScriptAction(new ScriptAction() {
                                    @Override
                                    public ScriptState execute() {
                                        // Remove the boss
                                        entity.setMapEntityStatus(Level.MapEntityStatus.REMOVED);
                                        
                                        // Get the map tile locations for the wispies
                                        WispyChainBuilder builder = new WispyChainBuilder(500);
                                        Wispy firstWispy = builder
                                            .addPoint(map.getMapTile(9, 7).getLocation())   // First wispy
                                            .addPoint(map.getMapTile(11, 7).getLocation().addY(16))  // Second wispy
                                            .addPoint(map.getMapTile(13, 7).getLocation().addY(64))  // Third wispy
                                            .addPoint(map.getMapTile(14, 7).getLocation().addX(70).subtractY(32)) // Fourth wispy
                                            .build();

                                        map.addNPC(firstWispy);
                                       
                                        return ScriptState.COMPLETED;
                                    }
                                });
                            }});

                            // DEFEAT: Won 0 or 1 rounds
                            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                                addRequirement(new CustomRequirement() {
                                    @Override
                                    public boolean isRequirementMet() {
                                        boolean r1 = map.getFlagManager().isFlagSet("bargaining_round1");
                                        boolean r2 = map.getFlagManager().isFlagSet("bargaining_round2");
                                        boolean r3 = map.getFlagManager().isFlagSet("bargaining_round3");
                                        int wins = (r1 ? 1 : 0) + (r2 ? 1 : 0) + (r3 ? 1 : 0);
                                        return wins < 2;
                                    }
                                });
                                
                                addScriptAction(new TextboxScriptAction() {{
                                    addText("Bargaining: You are not ready.");
                                    addText("Bargaining: Your words betray your weakness.");
                                    addText("Bargaining: Return when you understand\nwhat it truly means to fight for love.");
                                    addText("*The weight of your choices crushes you*");
                                }});
                                
                                addScriptAction(new ScriptAction() {
                                    @Override
                                    public ScriptState execute() {
                                        player.getEntity().kill();
                                        return ScriptState.COMPLETED;
                                    }
                                });
                            }});
                        }});
                    }}); 

                    // Player chooses "Not yet"
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1; 
                            }
                        });
                        addScriptAction(new TextboxScriptAction("Bargaining: Preparation is wise...\nBut hesitation is weakness."));
                    }});
                }}); 
            }});

            // AFTER VICTORY - Boss is gone
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasDefeatedBargaining", true));
                addScriptAction(new TextboxScriptAction("*Bargaining has been defeated*"));
            }});
        }});
    
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}