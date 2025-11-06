package ScriptActions;

import Level.GameListener;
import Level.MapEntity;
import Level.ScriptState;

/**
 * This action starts a battle when triggered from a script
 * (e.g. when the player presses "Yes" in an enemy dialogue).
 */
public class StartBattleScriptAction extends ScriptAction {

    private MapEntity enemy;

    /**
     * Pass the enemy NPC/Entity you want to battle with.
     */
    public StartBattleScriptAction(MapEntity enemy) {
        this.enemy = enemy;
    }

    @Override
    public ScriptState execute() {



        if (enemy == null) {
            System.out.println("[ERROR] StartBattleScriptAction enemy is NULL!");
            return ScriptState.COMPLETED;
        }

        // Notify all listeners (e.g. PlayLevelScreen)
        for (GameListener listener : listeners) {
            listener.onEvent("start_battle", enemy);
        }

        return ScriptState.COMPLETED;
    }
}