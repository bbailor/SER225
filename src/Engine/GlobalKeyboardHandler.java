package Engine;

import java.util.HashMap;
import java.util.Map;

import Game.ScreenCoordinator;

// TODO
public abstract class GlobalKeyboardHandler {
    
    // Could maybe switch to <name, handler> to use these names better (will also remove the option of using multiple handlers for the same key without more changes)
    protected static Map<String, KeyHandler> listeners = new HashMap<>();

    public static void addListener(String name, KeyHandler handler) {
        listeners.put(name, handler);
    }

    public static void removeListener(String name) {
        listeners.remove(name);
    }

    public static void clearAllListeners() {
        listeners.clear();
    }

    public static void runHandlers(ScreenCoordinator screenCoordinator) {
        for (var listener : listeners.values()) {
            listener.onLoop(screenCoordinator);
        }
    }

    public static interface KeyHandler {
        /**
         * Any screen changed here should be changed back!!!!
         * @param pauseState if the game is paused
         * @param screenManager the screen manager for the game
         */
        public void onLoop(ScreenCoordinator screenCoordinator);
    }
    
    private GlobalKeyboardHandler() {}
}
