package Scripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.NotNull;

import Level.Item;
import Level.ItemStack;
import Level.Script;
import Level.ScriptState;
import ScriptActions.CollectableScriptAction;
import ScriptActions.LockPlayerScriptAction;
import ScriptActions.ScriptAction;
import ScriptActions.TextboxScriptAction;
import ScriptActions.UnlockPlayerScriptAction;
import Utils.Globals;
import Utils.Resources;
import Utils.SoundThreads.Type;

public class CollectableScript extends Script {

    @Expose
    @NotNull
    public ItemStack stack;

    public CollectableScript(@NotNull ItemStack stack) {
        this.stack = stack;
    }

    public CollectableScript(@NotNull Item item) {
        this.stack = new ItemStack(item);
    }

    @Override
    public ArrayList<ScriptAction> loadScriptActions() {
        ArrayList<ScriptAction> actions = new ArrayList<>();

        actions.add(new LockPlayerScriptAction());
        actions.add(new TextboxScriptAction("You picked up the " + stack.getItem().getName() + "!"));
        actions.add(new CollectableScriptAction(this.stack));
        actions.add(new ScriptAction() {
            @Override
            public ScriptState execute() {
                // Resources.
                Globals.SOUND_SYSTEM.play(Type.SFX, Globals.EFFECTS_SOUNDS, Resources.PICKUP_SFX);
                return ScriptState.COMPLETED;
            }
        });
        actions.add(new UnlockPlayerScriptAction());
        
        return actions;
    }

    /**
     * For Serialization
     * DO NOT USE
     */
    private CollectableScript() {}
}
