package Scripts.MapTwoAnger;

import java.util.ArrayList;

import EnhancedMapTiles.CollectableItem;
import Level.Item;
import Level.Script;
import Level.ScriptState;
import ScriptActions.*;
import Utils.Point;
import Utils.Visibility;

// script for interacting with the Wizard NPC
public class AngerSideQuestScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        
        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction(){{

            addConditionalScriptActionGroup(new ConditionalScriptActionGroup(){{
                addRequirement(new FlagRequirement("hasTalkedToUnderworldSpirit", false));
                addScriptAction(new TextboxScriptAction(){{
                    addText("Grettings, traveller from another realm.");
                    addText("I know not what got you here,\n but you must leave this place.");
                    addText("Here, take this stone. \nI know not what it does, but it is of no use to me.");
                }});


                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        Point location = entity.getLocation();
                        CollectableItem reward = new CollectableItem(
                            location.x - 50, 
                            location.y - 50, 
                            Item.ItemList.powerStone
                        );
                        map.addCollectableItem(reward);
                        return ScriptState.COMPLETED;
                    }
                });
               addScriptAction(new ChangeFlagScriptAction("hasTalkedToUnderworldSpirit", true));
               addScriptAction(new NPCChangeVisibilityScriptAction(Visibility.HIDDEN));

            }});

        }});

        scriptActions.add(new UnlockPlayerScriptAction());
            
        return scriptActions;
    }
}