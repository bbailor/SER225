package Scripts.MapFiveAcceptance;

import java.util.ArrayList;

import Level.MapEntityStatus;
import Level.Script;
import Level.ScriptState;
import ScriptActions.ConditionalScriptAction;
import ScriptActions.ConditionalScriptActionGroup;
import ScriptActions.FlagRequirement;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.NPCFacePlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import Utils.Globals;
import Utils.SoundThreads.Type;

public class OsirisScript extends Script {

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {

        ArrayList<ScriptAction> scriptActions = new ArrayList<>();

        scriptActions.add(new LockPlayerScriptAction());
        scriptActions.add(new NPCFacePlayerScriptAction());

        scriptActions.add(new ConditionalScriptAction() {{

            //
            // 1. PLAYER HAS NOT MET JULIET
            //
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetJuliet", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: Gnomeo, you have journeyed far.\n This grave... nevermind.");
                    addText("Osiris: Someone awaits you nearby. Seek them out.");
                }});
            }});

            //
            // 2. ACCEPTANCE ROUTE — JulietAccept == true
            //
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietAccept", true));
                addRequirement(new FlagRequirement("osirisGone", false));

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        try {
                            Globals.SOUND_SYSTEM.play(Type.Music, Globals.MUSIC_TRACK, Globals.loadResource("Resources/Sounds/Music/happyEndingSong.wav"));
                            Globals.SOUND_SYSTEM.getTrack(Globals.MUSIC_TRACK).setLoopPoint(0, -1, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return ScriptState.COMPLETED;
                    }
                });

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: You’ve finally chosen to let her rest...");
                    addText("Osiris: Well done, Gnomeo.\nShe would be proud of the strength you’ve found");
                    addText("Osiris: You brought something to place at her grave?\n I shall leave you to it.");
                }});

                

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        entity.setMapEntityStatus(MapEntityStatus.REMOVED);
                        entity.setIsHidden(true);
                        map.getFlagManager().setFlag("osirisGone");
                        return ScriptState.COMPLETED;
                    }
                });
            }});

            //
            // 3. REJECTION ROUTE — JulietReject == true
            //
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("JulietReject", true));
                addRequirement(new FlagRequirement("osirisGone", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: So you refuse to let her go...\nIt is not the path I hoped for you.");
                    addText("Osiris: But even so, I can understand your heart.");
                    addText("Osiris: I shall leave now.\n This grave shall be a testament to your choice.");
                }});

                addScriptAction(new ScriptAction() {
                    @Override
                    public ScriptState execute() {
                        entity.setMapEntityStatus(MapEntityStatus.REMOVED);
                        entity.setIsHidden(true);
                        map.getFlagManager().setFlag("osirisGone");
                        return ScriptState.COMPLETED;
                    }
                });
            }});

            //
            // 4. PLAYER MET JULIET BUT HASN’T CHOSEN YET (fallback)
            //
            addConditionalScriptActionGroup(new ConditionalScriptActionGroup() {{
                addRequirement(new FlagRequirement("hasMetJuliet", true));
                addRequirement(new FlagRequirement("JulietAccept", false));
                addRequirement(new FlagRequirement("JulietReject", false));
                addRequirement(new FlagRequirement("osirisGone", false));

                addScriptAction(new TextboxScriptAction() {{
                    addText("Osiris: Speak with her, Gnomeo.");
                    addText("Osiris: The decision ahead will shape your heart.");
                }});

            }});

        }});

        scriptActions.add(new UnlockPlayerScriptAction());
        return scriptActions;
    }
}
