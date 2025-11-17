package Scripts.MapFiveAcceptance;

import Level.Script;
import Level.TextboxItem;
import ScriptActions.*;
import java.util.ArrayList;
import Level.ScriptState;

public class JulietScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {

        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{

    
            // 1 — FIRST TIME MEETING JULIET
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetJuliet", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Juliet: ...Gnomeo?\nMy love... I've been waiting.");
                    addText("Gnomeo: What... Juliet?");
                    addText("Juliet: Hehe, you don't\nrecognize your own wife? You've gained weight, dear.");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasMetJuliet", true));
            }});

            // 2 — MAIN CONVERSATION (ONLY ONCE)
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetJuliet", true));
                addRequirement(new FlagRequirement("JulietResolved", false));

                
                // CHOICE 1 — unimportant
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: What is this? How can this be?");

                    ArrayList<String> options = new ArrayList<>();
                    options.add("Juliet?");
                    options.add("A trick?");

                    addText(new TextboxItem("Gnomeo:", options, false));
                }});

                addScriptAction(new ConditionalScriptAction() {{

                    // Juliet? option
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 0;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Juliet? Is it really you?");
                            addText("Juliet: It's really me, Gnomeo.");
                            addText("Juliet: I'm now but a whisper caught between your \nheart and the other side.");
                        }});
                    }});

                    // A trick? option
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 1;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: What trickery is this?");
                            addText("Juliet: No trick. No illusion. It's really me.");
                            addText("Juliet: I wish it were a lie. It would hurt you less.");
                            addText("Juliet: I can't cross yet, Gnomeo.\nNot while you hurt so deeply.");
                        }});
                    }});
                }});

            
                // CHOICE 2 - also unimportant
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: What are you saying?");
                    addText("Juliet: The pain in your heart ties me to this place.\nYou must let me go.");

                    ArrayList<String> options = new ArrayList<>();
                    options.add("You're stuck here?");
                    options.add("I can't lose you");

                    addText(new TextboxItem("Gnomeo:", options, false));
                }});

                addScriptAction(new ConditionalScriptAction() {{

                    // Acceptance-leaning
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 0;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: You mean I'm imprisoning you?\nMy weakness causes you pain yet again.");
                            addText("Juliet: I died, Gnomeo.\nNot because you were weak or unloving.");
                            addText("Juliet: The world is a fragile thing.\nEven our strongest hopes can be shattered.");
                            addText("Juliet: None of it was your fault.");
                        }});
                    }});

                    // Rejection-leaning
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 1;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: How can you ask that?\nI've lost you once... I can't lose you again.");
                            addText("Juliet: You've carried so much pain, Gnomeo.");
                            addText("Juliet: Please... or I will be lost to you forever.\nLet me go.");
                            addText("Juliet: I wish I could stay, but there's only one \npath now.");
                        }});
                    }});
                }});

                
                // CHOICE 3 — THE FINAL DECISION - important
                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: This isn't fair...");
                    addText("Juliet: I know, my love.\nBut we are at the end of our story.");

                    ArrayList<String> options = new ArrayList<>();
                    options.add("Goodbye...");
                    options.add("This isn't over");

                    addText(new TextboxItem("Gnomeo:", options, false));
                }});

                addScriptAction(new ConditionalScriptAction() {{

                    
                    // ACCEPT ENDING — DESPAWN AT THE END
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 0;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Goodbye... Juliet.");
                            addText("Juliet: Farewell, Gnomeo.");
                            addText("Juliet: I will carry your love always.\nAnd you, my memory.");
                            addText("Juliet: Somewhere beyond all this...\nI am at peace.");
                            addText("Juliet: I'm glad we got to talk one last time");
                            addText("Juliet: Thank you.");
                        }});

                        addScriptAction(new ChangeFlagScriptAction("JulietAccept", true));
                        addScriptAction(new ChangeFlagScriptAction("JulietResolved", true));

                        // INSTANT DESPAWN
                        addScriptAction(new ScriptAction() {
                            @Override
                            public ScriptState execute() {
                                entity.setIsHidden(true);
                                entity.setMapEntityStatus(Level.MapEntityStatus.REMOVED);
                                return ScriptState.COMPLETED;
                            }
                        });
                    }});

                    
                    // REJECT ENDING — DESPAWN AT THE END
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int ans = (int) outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return ans == 1;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: I'll bring you back. Our love will find a way.");
                            addText("Juliet: Your love is strong enough to burn through\nanything.");
                            addText("Juliet: But if you chase me into the light,\nyou will only find darkness.");
                            addText("Gnomeo: The only darkness is this world without you.");
                            addText("Juliet: Please... do not lose yourself.\nThat is all I ask.");
                            addText("Juliet: I'm glad we got to talk one last time");
                        }});

                        addScriptAction(new ChangeFlagScriptAction("JulietReject", true));
                        addScriptAction(new ChangeFlagScriptAction("JulietResolved", true));

                        // INSTANT DESPAWN
                        addScriptAction(new ScriptAction() {
                            @Override
                            public ScriptState execute() {
                                entity.setIsHidden(true);
                                entity.setMapEntityStatus(Level.MapEntityStatus.REMOVED);
                                return ScriptState.COMPLETED;
                            }
                        });
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
