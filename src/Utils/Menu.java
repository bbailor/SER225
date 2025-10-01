package Utils;

import java.util.Map;

// Add anything else you want to have all menu's do
public interface Menu {
    
    Map<String, MenuListener> getListeners();

    default void addistener(String listenerName, MenuListener listener) {
        getListeners().put(listenerName, listener);
    }

    default void removeListener(String listenerName) {
        getListeners().remove(listenerName);
    }

    default void close() {
        getListeners().forEach((k, v) -> v.onClose());
    }

    void open();
}
