package ScriptActions;

import Level.OverworldAnimations.BargainingBossOverworldAnimation;
import Level.ScriptState;

/**
 * Script action for triggering the BargainingBoss spirit attack animation in the overworld
 *
 * Usage Example:
 * <pre>
 * // In your script
 * addScriptAction(new BargainingBossAnimationScriptAction());
 * </pre>
 */
public class BargainingBossAnimationScriptAction extends ScriptAction {

    private int duration;
    private float offsetX;
    private float offsetY;

    /**
     * Creates a BargainingBoss animation at the player's position with default duration (48 frames)
     */
    public BargainingBossAnimationScriptAction() {
        this(48, -70, -70);
    }

    /**
     * Creates a BargainingBoss animation at the player's position with custom duration
     * @param duration How long the animation lasts in frames (48 recommended)
     */
    public BargainingBossAnimationScriptAction(int duration) {
        this(duration, 0, 0);
    }

    /**
     * Creates a BargainingBoss animation at the player's position with offset
     * @param duration How long the animation lasts in frames (48 recommended)
     * @param offsetX X offset from player position
     * @param offsetY Y offset from player position
     */
    public BargainingBossAnimationScriptAction(int duration, float offsetX, float offsetY) {
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
        BargainingBossOverworldAnimation animation = new BargainingBossOverworldAnimation(x, y, duration);
        map.addOverworldAnimation(animation);

        return ScriptState.COMPLETED;
    }
}
