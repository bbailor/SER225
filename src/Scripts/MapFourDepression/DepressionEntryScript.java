package Scripts.MapFourDepression;

import Level.GameListener;

import Level.Script;
import Level.ScriptState;
import Maps.BargainingMap;
import Maps.MapOneDenial;
import Maps.DepressionMap;
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
public class DepressionEntryScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> scriptActions = new ArrayList<>();
        scriptActions.add(new LockPlayerScriptAction());
        
        scriptActions.add(new ConditionalScriptAction() {{
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasTalkedToBargainingBoss", true));
                addRequirement(new FlagRequirement("hasEnteredDepression", false));
                addRequirement(new CustomRequirement() {

                    @Override
                    public boolean isRequirementMet() {
                        // ensures player is directly underneath tree trunk tile
                        // this prevents the script from working if the player tries to interact with it from the side

                        // if player is not below tree trunk tile, player location is not valid and this conditional script will not be reached
// edit these lines                       if (player.getBounds().getY1() <= entity.getBounds().getY2()) {
// for entry placement                           return false;
// once u make it to bargaining                       }

                        // if code gets here, it means player is below tree trunk tile and player location is valid, so this conditional script will continue
                        return true;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("Gnomeo: Just a little longer Juliet.");
                    addText("Gnomeo: I will save you...\nNo matter what.");
                }});

                addScriptAction(new ChangeFlagScriptAction("hasEnteredDepression", true));

                 //alert all listeners (which includes play level screen) that the game has been won
                 addScriptAction(new ScriptAction() {
                     @Override
                     public ScriptState execute() {
                         for (GameListener listener: listeners) {
                             listener.switchMap(new MapOneDenial());
                             
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

