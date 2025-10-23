package Scripts.TestMap;

import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;
import Level.Item;
import Level.Script;
import Level.ScriptState;
import ScriptActions.*;
import Utils.Point;

// script for interacting with the Wizard NPC
public class WizardScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{

            // FIRST INTERACTION - Main quest dialogue + side quest hint
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWizard", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: Where are you rushing off to?");
                    addText("Gnomeo: To save Juliet!! She has been\ncaptured by evil forces!");
                    addText("Osiris: But Gnomeo, Juliet is-");
                    addText("Gnomeo: I must save her, she is my world!\nI cannot lose her!");
                    addText("Osiris: *sighs* If you are going.. then at least\ntake your mother's Knife of Life."); 
                }});

                // Knife drops mid-dialogue
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        Point location = map.getMapTile(10, 18).getLocation();
                        CollectableItem knifeOfLife = new CollectableItem(location.x, location.y, Item.ItemList.knife_of_life);
                        map.addCollectableItem(knifeOfLife);
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addScriptAction(new WaitScriptAction(30));
                    addText("(Hint: Use [SPACE] to pick up items)");
                    addText("(Hint: Use [E] to open your inventory)");
                    addText("Osiris: What you seek is not here.\nPerhaps in the forest?");
                    addText("Osiris: Oh, and Gnomeo... I've been having\ntrouble with skeletons lately.");
                    addText("Osiris: If you see any, be careful.\nThey've been more aggressive than usual.");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasTalkedToWizard", true));
            }});

            // SECOND INTERACTION - Wizard mentions he's going to investigate
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToWizard", true));
                addRequirement(new FlagRequirement("wizardQuestStarted", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: This is a journey you must take alone..\nFarewell, Gnomeo.");
                    addText("Osiris: Actually... You've got me in an adventurous\nmood now.");
                    addText("Osiris: I'm going to investigate those\nskeleton sightings.");
                    addText("Osiris: I'll be by the cabin up north if you\nneed me.");
                }});

                // Move wizard to new location (northern area)
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        // Move wizard to top right
                        Point newLocation = map.getMapTile(19, 0).getLocation();
                        entity.setLocation(newLocation.x, newLocation.y);
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new ChangeFlagScriptAction("wizardQuestStarted", true));
            }});

            // THIRD INTERACTION - Wizard is trapped, spawn skeleton battle
            // addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
            //     addRequirement(new FlagRequirement("wizardQuestStarted", true));
            //     addRequirement(new FlagRequirement("wizardSaved", false));

                // addScriptAction(new TextboxScriptAction() {{
                //     addText("Osiris: Gnomeo! Help!");
                //     addText("Osiris: This skeleton ambushed me!\nI can't fight it alone!");
                //     addText("Gnomeo: Hold on, Osiris! I'm coming!");
                // }});

                // Trigger battle with skeleton
                // addScriptAction(new StartBattleScriptAction());

                // addScriptAction(new ChangeFlagScriptAction("wizardSaved", true));
            // }});

            // FOURTH INTERACTION - After saving wizard, give reward
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("wizardSaved", true));
                addRequirement(new FlagRequirement("wizardRewardGiven", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: Thank you, Gnomeo! You saved my life!");
                    addText("Osiris: I was foolish to investigate alone.");
                    addText("Osiris: Please, take this weapon as thanks.\nIt's enchanted with ancient magic.");
                }});

                // Drop weapon reward
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        Point location = entity.getLocation();
                        // Adjust the weapon type as needed (using knife_of_life as example)
                        CollectableItem reward = new CollectableItem(
                            location.x + 100, 
                            location.y + 100, 
                            Item.ItemList.knife_of_life  // Replace with your actual weapon
                        );
                        map.addCollectableItem(reward);
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new TextboxScriptAction("Osiris: Good luck on your quest, brave Gnomeo!"));

                addScriptAction(new ChangeFlagScriptAction("wizardRewardGiven", true));
            }});

            // FINAL INTERACTION - Quest completed
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("wizardRewardGiven", true));

                addScriptAction(new TextboxScriptAction("Osiris: Thank you again for saving me!\nMay the magic guide you."));
            }});
        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        
        return scriptActions;
    }
}