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
        // Debugging info to make sure the event is actually firing
        // System.out.println("[DEBUG] StartBattleScriptAction triggered for: " + enemy);
        // System.out.println("[DEBUG] Listeners count: " + listeners.size());

        if (enemy == null) {
            System.out.println("[ERROR] StartBattleScriptAction enemy is NULL!");
            System.out.println("[HINT] This means the script that called this action didn't pass an enemy NPC.");
        }

        // Notify all listeners (e.g. PlayLevelScreen)
        for (GameListener listener : listeners) {
            // System.out.println("[DEBUG] Notifying listener: " + listener.getClass().getSimpleName());
            listener.onEvent("start_battle", enemy);
        }

        return ScriptState.COMPLETED;
    }
}
