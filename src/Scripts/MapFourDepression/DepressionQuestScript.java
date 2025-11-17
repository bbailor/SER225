package Scripts.MapFourDepression;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import GameObject.Frame;
import Level.*;
import ScriptActions.*;
import Utils.Direction;
import Utils.Point;
import Utils.Visibility;
import java.util.ArrayList;
import EnhancedMapTiles.CollectableItem;

// Script for the Greyscale Gnome side quest in Depression Map
public class DepressionQuestScript extends Script {
    
    // Store the exit tile X coordinate as a class variable
    private int exitTileX = -1;

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{
            // FIRST INTERACTION - Initial meeting
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetGreyscaleGnome", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Grey Gnome: ...");
                    addText("Grey Gnome: Who... who are you?");
                    addText("Gnomeo: I'm Gnomeo. Are you okay?\nYou look lost.");
                    addText("Grey Gnome: Lost... yes. Everything is so grey.\nI can't remember... anything.");
                    addText("Grey Gnome: But I feel... I need to reach the trees.\nThe treeline to the north.");
                    addText("Grey Gnome: Could you... help me find my way?");
                    addText("Help the Grey Gnome?", new String[] { "Yes", "No" });
                }});

                // Handle Yes/No selection
                addScriptAction(new ConditionalScriptAction() {{
                    // YES - Accept the quest
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: Of course. Follow me to the north.");
                            addText("Grey Gnome: Thank you... I'll stay close behind you.");
                        }});

                        addScriptAction(new ChangeFlagScriptAction("hasMetGreyscaleGnome", true));
                        addScriptAction(new ChangeFlagScriptAction("greyscaleGnomeFollowing", true));
                    }});

                    // NO - Decline the quest
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });

                        addScriptAction(new TextboxScriptAction() {{
                            addText("Gnomeo: I'm sorry, I have my own journey to make.");
                            addText("Grey Gnome: I understand... I'll keep searching alone.");
                        }});

                        addScriptAction(new ChangeFlagScriptAction("hasMetGreyscaleGnome", true));
                        addScriptAction(new ChangeFlagScriptAction("greyscaleQuestDeclined", true));
                    }});
                }});
            }});

            // SECOND INTERACTION - While following (before reaching treeline)
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("greyscaleGnomeFollowing", true));
                addRequirement(new FlagRequirement("greyscaleReachedTreeline", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Grey Gnome: The trees... I can almost feel them.\nPlease, keep going north.");
                }});
            }});

            // THIRD INTERACTION - After reaching treeline
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("greyscaleReachedTreeline", true));
                addRequirement(new FlagRequirement("greyscaleGnomeLeft", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Grey Gnome: We're here... the trees.");
                    addText("Grey Gnome: I can feel it now... something calling me.");
                    addText("Grey Gnome: Thank you, Gnomeo. You've helped me\nfind what I was searching for.");
                    addText("Gnomeo: What is it? What's in the trees?");
                    addText("Grey Gnome: Peace... I think. Or maybe... release.");
                    addText("Grey Gnome: Farewell, friend. May you find peace\nas well.");
                }});

                addScriptAction(new NPCStandScriptAction(Direction.UP));
                addScriptAction(new WaitScriptAction(30));

                // Find the nearest treeline tile at same x or ±1
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        // Get gnome's current position
                        Point gnomePos = entity.getLocation();
                        int gnomeTileX = (int)(gnomePos.x / 100); // Calculate tile X coordinate (50 pixels per tile, scaled by 2)
                        
                        // Store the target tile X coordinate for later restoration
                        exitTileX = gnomeTileX;
                        
                        Point targetLocation = map.getMapTile(exitTileX, 0).getLocation();
                        
                        // Change the treeline tile to grass temporarily
                        Frame[] grassFrames = new Frame[] {
                            new FrameBuilder(map.getTileset().getSubImage(0, 0), 25).withScale(map.getTileset().getTileScale()).build(),
                            new FrameBuilder(map.getTileset().getSubImage(1, 0), 25).withScale(map.getTileset().getTileScale()).build()
                        };

                        MapTile mapTile = new MapTileBuilder(grassFrames)
                            .withTileType(TileType.PASSABLE)
                            .build(targetLocation.x, targetLocation.y);

                        map.setMapTile(exitTileX, 0, mapTile);
                        
                        return ScriptState.COMPLETED;
                    }
                });

                // Walk into the trees
                addScriptAction(new NPCWalkScriptAction(Direction.UP, 150, 1.5f));

                // Restore the treeline tile
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        // Use the stored tile X coordinate
                        Point location = map.getMapTile(exitTileX, 0).getLocation();
                        
                        // Restore original treeline tile 
                        Frame treeFrame = new FrameBuilder(map.getTileset().getSubImage(1, 4))
                            .withScale(map.getTileset().getTileScale())
                            .build();

                        MapTile mapTile = new MapTileBuilder(treeFrame)
                            .withTileType(TileType.NOT_PASSABLE)
                            .build(location.x, location.y);

                        map.setMapTile(exitTileX, 0, mapTile);
                        
                        return ScriptState.COMPLETED;
                    }
                });
                
                addScriptAction(new NPCChangeVisibilityScriptAction(Visibility.HIDDEN));

                addScriptAction(new ChangeFlagScriptAction("greyscaleGnomeLeft", true));
                addScriptAction(new ChangeFlagScriptAction("greyscaleGnomeFollowing", false));

                // Drop weapon reward
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        Point location = entity.getLocation();
                        // Adjust the weapon type as needed (using knife_of_life as example)
                        CollectableItem reward = new CollectableItem(
                            location.x, 
                            location.y + 100,  // Slightly below gnome's position
                            Item.ItemList.tlalocs_storm  // Replace with your actual weapon
                        );
                        map.addCollectableItem(reward);
                        return ScriptState.COMPLETED;
                    }
                });
            }});

            // If quest was declined
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("greyscaleQuestDeclined", true));
                addRequirement(new FlagRequirement("greyscaleGnomeFollowing", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Grey Gnome: Still searching... still lost...");
                    addText("Grey Gnome: Perhaps you've changed your mind?\nWill you help me now?");
                    addText("Help the Grey Gnome?", new String[] { "Yes", "No" });
                }});

                addScriptAction(new ConditionalScriptAction() {{
                    // YES - Second chance
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 0;
                            }
                        });

                        addScriptAction(new TextboxScriptAction("Grey Gnome: Thank you... Let's go north."));
                        addScriptAction(new ChangeFlagScriptAction("greyscaleGnomeFollowing", true));
                        addScriptAction(new ChangeFlagScriptAction("greyscaleQuestDeclined", false));
                    }});

                    // NO - Still declined
                    addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                        addRequirement(new CustomRequirement() {
                            @Override
                            public boolean isRequirementMet() {
                                int answer = outputManager.getFlagData("TEXTBOX_OPTION_SELECTION");
                                return answer == 1;
                            }
                        });

                        addScriptAction(new TextboxScriptAction("Grey Gnome: I see... I'll continue waiting..."));
                    }});
                }});
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}