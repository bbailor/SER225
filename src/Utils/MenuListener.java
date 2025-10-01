package Utils;

public interface MenuListener {
    void onClose();
    default void onEvent(String eventName, Object[] args) {}
}
