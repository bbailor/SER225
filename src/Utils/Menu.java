package Utils;

import java.util.Map;

// Add anything else you want to have all menu's do
/**
 * Section for menus and submenus (will replace the screen class for menu items)
 * //TODO: Menu screen for Menu objects
 */
public interface Menu extends Drawable {
    
    Map<String, MenuListener> getListeners();

    default void addistener(String listenerName, MenuListener listener) {
        getListeners().put(listenerName, listener);
    }

    default void removeListener(String listenerName) {
        getListeners().remove(listenerName);
    }

    default void close() {
        getListeners().forEach((k, v) -> v.onMenuClose());
    }

    default void sendEvent(String name, Object ...args) {
        getListeners().forEach((k, v) -> v.onEvent(name, args));
    }

    default void update() {}

    void open();
}
