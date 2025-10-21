package ScriptActions;

import org.jetbrains.annotations.NotNull;

import Level.ItemStack;
import Level.MapEntityStatus;
import Level.ScriptState;

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
