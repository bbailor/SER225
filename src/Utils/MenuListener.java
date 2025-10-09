package Utils;

public interface MenuListener {
    void onMenuClose();
    default void onEvent(String eventName, Object ...args) {}
}
