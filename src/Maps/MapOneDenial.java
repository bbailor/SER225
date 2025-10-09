package Maps;

import Tilesets.CommonTileset;
import Tilesets.Map1Tileset;
import Utils.Point;
import Level.*;
import Scripts.TestMap.*;

public class MapOneDenial extends Map {
    public MapOneDenial() {
        super("map_one_denial.txt", new Map1Tileset());
        this.playerStartPosition = new Point(1, 11);
    }
}
