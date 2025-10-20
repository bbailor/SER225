package Level;

public interface GameListener {
    void onWin();

    default void onEvent(String eventName, Object ...args) {}

    void switchMap(Map newMap);
}
