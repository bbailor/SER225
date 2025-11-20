package Scripts.MapFiveAcceptance;

import java.io.File;
import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;
import Level.Item;
import Level.Script;
import Level.ScriptState;
import ScriptActions.*;
import Utils.Globals;
import Utils.Point;
import Utils.SoundThreads.Type;
    
public class GravestoneScript extends Script {

    // CHECK IF PLAYER HAS FLOWERS
    private boolean playerHasFlowers() {
        var inv = player.getEntity().getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            var stack = inv.getStack(i);
            if (stack != null && stack.getItem() == Item.ItemList.flowers && stack.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    // REMOVE FLOWER from INVENTORY 
    private void removeOneFlower() {
        var inv = player.getEntity().getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            var stack = inv.getStack(i);
            if (stack != null && stack.getItem() == Item.ItemList.flowers) {
                stack.removeItem();   // removes 1 item
                return;
            }
        }
    }

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();

        actions.add(new LockPlayerScriptAction());

        // ------------------------------------------------------------
        // BEFORE TALKING TO JULIET
        // ------------------------------------------------------------
        actions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetJuliet", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Here lies [][][][][][]");
                    addText("Gnomeo: Strange... I can't read the name?");
                }});
            }});
        }});

        // ------------------------------------------------------------
        // ACCEPT ROUTE — place flowers IF player has them & not placed yet
        // ------------------------------------------------------------
        actions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietAccept", true));
                addRequirement(new FlagRequirement("JulietFlowersPlacedAtGrave", false));
                addRequirement(new CustomRequirement() {
                    @Override
                    public boolean isRequirementMet() {
                        return playerHasFlowers();
                    }
                });

                

                addScriptAction(new TextboxScriptAction() {{
                    addText("You gently place the flowers by the grave...");
                }});

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {

                        removeOneFlower();

                        // Grave tile (32,5)
                        Point grave = map.getMapTile(32, 5).getLocation();

                        // Drop visible flowers next to the grave
                        CollectableItem placed = new CollectableItem(
                                grave.x + 0,
                                grave.y + 40,
                                Item.ItemList.flowers
                        );

                        map.addCollectableItem(placed);

                        map.getFlagManager().setFlag("JulietFlowersPlacedAtGrave");

                        return ScriptState.COMPLETED;
                    }
                });
            }});
        }});

        // ------------------------------------------------------------
        // ACCEPT ROUTE —placing flowers (FINAL DIALOGUE → WIN)
        // ------------------------------------------------------------
        actions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietAccept", true));
                addRequirement(new FlagRequirement("JulietFlowersPlacedAtGrave", true));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Here lies Juliet, Her spirit finally at peace.");
                    addText("Her grave adorned with flowers,a final gift from\nher lover");
                }});

                // WIN SCREEN
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        for (var listener : listeners) {
                            listener.onWin();   // FIXED
                        }
                        return ScriptState.COMPLETED;
                    }
                });
            }});
        }});

        // ------------------------------------------------------------
        // ACCEPT ROUTE — NO FLOWERS 
        // ------------------------------------------------------------
        actions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietAccept", true));
                addRequirement(new FlagRequirement("JulietFlowersPlacedAtGrave", false));

                addRequirement(new CustomRequirement() {
                    @Override
                    public boolean isRequirementMet() {
                        return !playerHasFlowers();
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("You feel something is missing...");
                }});
            }});
        }});

        // ------------------------------------------------------------
        // REJECT ROUTE (FINAL DIALOGUE → WIN)
        // ------------------------------------------------------------
        actions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietReject", true));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Here lies Juliet,");
                    addText("Captive of death. Awaiting rescue on the other side by \nher true love.");
                }});

                // → WIN SCREEN
                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        for (var listener : listeners) {
                            listener.onWin();   // FIXED
                        }
                        return ScriptState.COMPLETED;
                    }
                });
            }});
        }});

        actions.add(new UnlockPlayerScriptAction());
        return actions;
    }
}
