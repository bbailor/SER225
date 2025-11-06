package Scripts.MapTwoAnger;

import Level.GameListener;

import Level.Script;
import Level.ScriptState;
import Maps.AngerMap;
import Maps.BargainingMap;
import ScriptActions.ChangeFlagScriptAction;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.CustomRequirement;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import java.util.ArrayList;

// script for talking to tree with hole in it
// checkout the documentation website for a detailed guide on how this script works
public class BargainingEntryScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                // addRequirement(new FlagRequirement("hasDefeatedAnger", true));
                addRequirement(new FlagRequirement("hasEnteredBargaining", false));
                addRequirement(new CustomRequirement() {

                    @Override
                    public boolean isRequirementMet() {
                        // ensures player is directly underneath tile
                        // this prevents the script from working if the player tries to interact with it from the side

                        // if player is not below tree trunk tile, player location is not valid and this conditional script will not be reached
                        if (player.getBounds().getY1() <= entity.getBounds().getY2()) {
                            return false;
                        }

                        // if code gets here, it means player is below tree trunk tile and player location is valid, so this conditional script will continue
                        return true;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: I need her back, how can I continue?");
                    addText("Gnomeo: I would do anything for Juliet.");
                    addText("Gnomeo: All I ask is for her to be safe.");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasEnteredBargaining", true));

                 //alert all listeners (which includes play level screen) that the game has been won
                 addScriptAction(new ScriptAction() {
                     @Override
                     public ScriptState execute() {
                         for (GameListener listener: listeners) {
                             listener.switchMap(new BargainingMap());
                             
                         }
                         return ScriptState.COMPLETED;
                     }
                 });
            }});
        }});
       
        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}

