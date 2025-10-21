package Level;

public interface GameListener {
    void onWin();

    default void onEvent(String eventName, Object ...args) {}

    default void switchMap(Map newMap) {}
}
