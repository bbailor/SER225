package ScriptActions;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jetbrains.annotations.NotNull;

import Level.ItemStack;
import Level.MapEntityStatus;
import Level.ScriptState;
import Utils.Globals;
import Utils.SoundThreads.Type;

public class CollectableScriptAction extends ScriptAction {

    @NotNull public ItemStack stack;

    public CollectableScriptAction(@NotNull ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ScriptState execute() {
        this.entity.setMapEntityStatus(MapEntityStatus.REMOVED);
        this.player.getEntity().getInventory().addStack(this.stack);
        return ScriptState.COMPLETED;
    }
    
}
