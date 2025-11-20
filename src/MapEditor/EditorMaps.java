package MapEditor;

import Level.Map;
import Maps.MapOneDenial;
import Maps.TutorialMap;
import Maps.AcceptanceMap;
import Maps.AngerMap;
import Maps.DepressionMap;
import Maps.BargainingMap;
import Maps.TitleScreenMap;
import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TutorialMap");
            add("MainMap");
            add("TitleScreen");
            add("Map1Denial");
            add("Anger");
            add("Depression");
            add("Bargaining");
            add("Acceptance");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TutorialMap":
                return new TutorialMap();
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
            case "Bargaining":
                return new BargainingMap();
            case "Acceptance":
                return new AcceptanceMap();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
