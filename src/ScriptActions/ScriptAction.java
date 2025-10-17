package ScriptActions;

import Level.GameListener;
import Level.Map;
import Level.MapEntity;
import Level.Player;
import Level.ScriptState;
import java.util.List;

public abstract class ScriptAction {
    protected Map map;
    protected Player player;
    protected MapEntity entity;
    protected ScriptActionOutputManager outputManager;
    protected List<GameListener> listeners;

    public void setup() {}

    public ScriptState execute() {
        return ScriptState.COMPLETED;
    }

    public void cleanup() {}

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEntity(MapEntity entity) {
        this.entity = entity;
    }

    public void setListeners(List<GameListener> listeners) {
        this.listeners = listeners;
    }//for when you call OnEvent in PlayLevelScreen    //////////////////////////////

    public void setOutputManager(ScriptActionOutputManager outputManager) {
        this.outputManager = outputManager;
    }
}
