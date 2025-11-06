package ScriptActions;

import Level.OverworldAnimations.OverworldAttackAnimation;
import Level.ScriptState;

/**
 * Simple script action for triggering overworld attack animations
 * Note: This uses the default placeholder animation. For custom animations,
 * create a subclass of OverworldAttackAnimation (like BargainingBossOverworldAnimation).
 *
 * Usage Example:
 * <pre>
 * // In your script
 * addScriptAction(new OverworldAnimationScriptAction(
 *     60  // Duration in frames (60 = 1 second)
 * ));
 * </pre>
 */
public class OverworldAnimationScriptAction extends ScriptAction {

    private int duration;
    private float offsetX;
    private float offsetY;

    /**
     * Creates an animation at the player's position
     * @param duration How long the animation lasts in frames (60 = 1 second)
     */
    public OverworldAnimationScriptAction(int duration) {
        this(duration, 0, 0);
    }

    /**
     * Creates an animation at the player's position with offset
     * @param duration How long the animation lasts in frames (60 = 1 second)
     * @param offsetX X offset from player position
     * @param offsetY Y offset from player position
     */
    public OverworldAnimationScriptAction(int duration, float offsetX, float offsetY) {
        this.duration = duration;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public ScriptState execute() {
        // Get player position
        float x = player.getX() + offsetX;
        float y = player.getY() + offsetY;

        // Create and add animation to map
        OverworldAttackAnimation animation = new OverworldAttackAnimation(x, y, duration);
        map.addOverworldAnimation(animation);

        return ScriptState.COMPLETED;
    }
}
