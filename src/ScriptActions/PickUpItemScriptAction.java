package ScriptActions;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Engine.Inventory;
import EnhancedMapTiles.CollectableItem;
import Level.ScriptState;
import Utils.Globals;
import Utils.SoundThreads.Type;

public class PickUpItemScriptAction extends ScriptAction{
    protected CollectableItem collectableItem;
    protected Inventory inventory;
    public PickUpItemScriptAction(CollectableItem collectableItem){
        this.collectableItem = collectableItem;
        
    }

    public void setup()
    {
        inventory = this.map.getPlayer().getEntity().getInventory();
        
    }
    public ScriptState execute()
    {
        return ScriptState.COMPLETED;
    }
}