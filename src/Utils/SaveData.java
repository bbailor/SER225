package Utils;

import com.google.gson.annotations.Expose;

import Level.FlagManager;
import Level.Map;
import Level.Player;

public class SaveData {
    @Expose public Map map;
    @Expose public Player player;
    @Expose public FlagManager flagManager;
    @Expose public Point playerPos;

    public SaveData(Map map, Player player, FlagManager flagManager) {
        this.map = map;
        this.player = player;
        this.flagManager = flagManager;
        this.playerPos = player.getLocation();
    }

    protected SaveData() {}

}
