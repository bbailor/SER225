package MapEditor;

import Level.Map;
import Maps.MapOneDenial;
import Maps.TestMap;
import Maps.AngerMap;
import Maps.DepressionMap;
import Maps.TitleScreenMap;
import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
//            add("MainMap");
            add("TitleScreen");
            add("Map1Denial");
            add("Anger");
            add("Depression");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            //had to comment this out for 
//          case "MainMap":
//              return new MainMap();   
            case "TitleScreen":
                return new TitleScreenMap();
            case "Map1Denial":
                return new MapOneDenial();
            case "Anger":
                return new AngerMap();
            case "Depression":
                return new DepressionMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}