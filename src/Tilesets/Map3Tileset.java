package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.MapTile;
import Level.TileType;
import Level.Tileset;
import Utils.MathUtils;

import java.util.ArrayList;

import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;

import org.w3c.dom.events.MutationEvent;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class Map3Tileset extends Tileset {

    public Map3Tileset() {

        super(ImageLoader.load("map_three_tileset.png"),40, 37, 3);
    }
        @Override
        public ArrayList<MapTileBuilder> defineTiles() {
            ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

            //row 0

                Frame tile00Frame = new FrameBuilder(getSubImage(0,0)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile00Tile = new MapTileBuilder(tile00Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile00Tile);

                Frame tile01Frame = new FrameBuilder(getSubImage(0,1)).withScale(tileScale).build();
                MapTileBuilder tile01Tile = new MapTileBuilder(tile01Frame);
                mapTiles.add(tile01Tile);

                 Frame tile02Frame = new FrameBuilder(getSubImage(0,2)).withScale(tileScale).build();
                MapTileBuilder tile02Tile = new MapTileBuilder(tile02Frame);
                mapTiles.add(tile02Tile);

                 Frame tile03Frame = new FrameBuilder(getSubImage(0,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile03Tile = new MapTileBuilder(tile03Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile03Tile);

                 Frame tile04Frame = new FrameBuilder(getSubImage(0,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile04Tile = new MapTileBuilder(tile04Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile04Tile);

                 Frame tile05Frame = new FrameBuilder(getSubImage(0,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile05Tile = new MapTileBuilder(tile05Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile05Tile);

                 Frame tile06Frame = new FrameBuilder(getSubImage(0,6)).withScale(tileScale).build();
                MapTileBuilder tile06Tile = new MapTileBuilder(tile06Frame);
                mapTiles.add(tile06Tile);

                 Frame tile07Frame = new FrameBuilder(getSubImage(0,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile07Tile = new MapTileBuilder(tile07Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile07Tile);

                 Frame tile08Frame = new FrameBuilder(getSubImage(0,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile08Tile = new MapTileBuilder(tile08Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile08Tile);

                 Frame tile09Frame = new FrameBuilder(getSubImage(0,9)).withScale(tileScale).build();
                MapTileBuilder tile09Tile = new MapTileBuilder(tile09Frame);
                mapTiles.add(tile09Tile);

                 Frame tile010Frame = new FrameBuilder(getSubImage(0,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile010Tile = new MapTileBuilder(tile010Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile010Tile);

                 Frame tile011Frame = new FrameBuilder(getSubImage(0,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile011Tile = new MapTileBuilder(tile011Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile011Tile);

                 Frame tile012Frame = new FrameBuilder(getSubImage(0,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile012Tile = new MapTileBuilder(tile012Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile012Tile);

                 Frame tile013Frame = new FrameBuilder(getSubImage(0,13)).withScale(tileScale).build();
                MapTileBuilder tile013Tile = new MapTileBuilder(tile013Frame);
                mapTiles.add(tile013Tile);

                 Frame tile014Frame = new FrameBuilder(getSubImage(0,14)).withScale(tileScale).build();
                MapTileBuilder tile014Tile = new MapTileBuilder(tile014Frame);
                mapTiles.add(tile014Tile);

                 Frame tile015Frame = new FrameBuilder(getSubImage(0,15)).withScale(tileScale).build();
                MapTileBuilder tile015Tile = new MapTileBuilder(tile015Frame);
                mapTiles.add(tile015Tile);


            //row 1

                Frame tile10Frame = new FrameBuilder(getSubImage(1,0)).withScale(tileScale).build();
                MapTileBuilder tile0Tile = new MapTileBuilder(tile10Frame);
                mapTiles.add(tile0Tile);

                Frame tile11Frame = new FrameBuilder(getSubImage(1,1)).withScale(tileScale).build();
                MapTileBuilder tile1Tile = new MapTileBuilder(tile11Frame);
                mapTiles.add(tile1Tile);

                 Frame tile12Frame = new FrameBuilder(getSubImage(1,2)).withScale(tileScale).build();
                MapTileBuilder tile12Tile = new MapTileBuilder(tile12Frame);
                mapTiles.add(tile12Tile);

                 Frame tile13Frame = new FrameBuilder(getSubImage(1,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile13Tile = new MapTileBuilder(tile13Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile13Tile);

                 Frame tile14Frame = new FrameBuilder(getSubImage(1,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile14Tile = new MapTileBuilder(tile14Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile14Tile);

                 Frame tile15Frame = new FrameBuilder(getSubImage(1,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile15Tile = new MapTileBuilder(tile15Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile15Tile);

                 Frame tile16Frame = new FrameBuilder(getSubImage(1,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile16Tile = new MapTileBuilder(tile16Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile16Tile);

                 Frame tile17Frame = new FrameBuilder(getSubImage(1,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile17Tile = new MapTileBuilder(tile17Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile17Tile);

                 Frame tile18Frame = new FrameBuilder(getSubImage(1,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile18Tile = new MapTileBuilder(tile18Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile18Tile);

                 Frame tile19Frame = new FrameBuilder(getSubImage(1,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile19Tile = new MapTileBuilder(tile19Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile19Tile);

                 Frame tile110Frame = new FrameBuilder(getSubImage(1,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile110Tile = new MapTileBuilder(tile110Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile110Tile);

                 Frame tile111Frame = new FrameBuilder(getSubImage(1,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile111Tile = new MapTileBuilder(tile111Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile111Tile);

                 Frame tile112Frame = new FrameBuilder(getSubImage(1,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile112Tile = new MapTileBuilder(tile112Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile112Tile);

                 Frame tile113Frame = new FrameBuilder(getSubImage(1,13)).withScale(tileScale).build();
                MapTileBuilder tile113Tile = new MapTileBuilder(tile113Frame);
                mapTiles.add(tile113Tile);

                 Frame tile114Frame = new FrameBuilder(getSubImage(1,14)).withScale(tileScale).build();
                MapTileBuilder tile114Tile = new MapTileBuilder(tile114Frame);
                mapTiles.add(tile114Tile);

                 Frame tile115Frame = new FrameBuilder(getSubImage(1,15)).withScale(tileScale).build();
                MapTileBuilder tile115Tile = new MapTileBuilder(tile115Frame);
                mapTiles.add(tile115Tile);

            //row 2

                Frame tile20Frame = new FrameBuilder(getSubImage(2,0)).withScale(tileScale).build();
                MapTileBuilder tile20Tile = new MapTileBuilder(tile20Frame);
                mapTiles.add(tile20Tile);

                Frame tile21Frame = new FrameBuilder(getSubImage(2,1)).withScale(tileScale).build();
                MapTileBuilder tile21Tile = new MapTileBuilder(tile21Frame);
                mapTiles.add(tile21Tile);

                 Frame tile22Frame = new FrameBuilder(getSubImage(2,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile22Tile = new MapTileBuilder(tile22Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile22Tile);

                 Frame tile23Frame = new FrameBuilder(getSubImage(2,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile23Tile = new MapTileBuilder(tile23Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile23Tile);

                 Frame tile24Frame = new FrameBuilder(getSubImage(2,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile24Tile = new MapTileBuilder(tile24Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile24Tile);

                 Frame tile25Frame = new FrameBuilder(getSubImage(2,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile25Tile = new MapTileBuilder(tile25Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile25Tile);

                 Frame tile26Frame = new FrameBuilder(getSubImage(2,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile26Tile = new MapTileBuilder(tile26Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile26Tile);

                 Frame tile27Frame = new FrameBuilder(getSubImage(2,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile27Tile = new MapTileBuilder(tile27Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile27Tile);

                 Frame tile28Frame = new FrameBuilder(getSubImage(2,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile28Tile = new MapTileBuilder(tile28Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile28Tile);

                 Frame tile29Frame = new FrameBuilder(getSubImage(2,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile29Tile = new MapTileBuilder(tile29Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile29Tile);

                 Frame tile210Frame = new FrameBuilder(getSubImage(2,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile210Tile = new MapTileBuilder(tile210Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile210Tile);

                 Frame tile211Frame = new FrameBuilder(getSubImage(2,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile211Tile = new MapTileBuilder(tile211Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile211Tile);

                 Frame tile212Frame = new FrameBuilder(getSubImage(2,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile212Tile = new MapTileBuilder(tile212Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile212Tile);

                 Frame tile213Frame = new FrameBuilder(getSubImage(2,13)).withScale(tileScale).build();
                MapTileBuilder tile213Tile = new MapTileBuilder(tile213Frame);
                mapTiles.add(tile213Tile);

                 Frame tile214Frame = new FrameBuilder(getSubImage(2,14)).withScale(tileScale).build();
                MapTileBuilder tile214Tile = new MapTileBuilder(tile214Frame);
                mapTiles.add(tile214Tile);

                 Frame tile215Frame = new FrameBuilder(getSubImage(2,15)).withScale(tileScale).build();
                MapTileBuilder tile215Tile = new MapTileBuilder(tile215Frame);
                mapTiles.add(tile215Tile);

            //row 3

                Frame tile30Frame = new FrameBuilder(getSubImage(3,0)).withScale(tileScale).build();
                MapTileBuilder tile30Tile = new MapTileBuilder(tile30Frame);
                mapTiles.add(tile30Tile);

                Frame tile31Frame = new FrameBuilder(getSubImage(3,1)).withScale(tileScale).build();
                MapTileBuilder tile31Tile = new MapTileBuilder(tile31Frame);
                mapTiles.add(tile31Tile);

                 Frame tile32Frame = new FrameBuilder(getSubImage(3,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile32Tile = new MapTileBuilder(tile32Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile32Tile);

                 Frame tile33Frame = new FrameBuilder(getSubImage(3,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile33Tile = new MapTileBuilder(tile33Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile33Tile);

                 Frame tile34Frame = new FrameBuilder(getSubImage(3,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile34Tile = new MapTileBuilder(tile34Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile34Tile);

                 Frame tile35Frame = new FrameBuilder(getSubImage(3,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile35Tile = new MapTileBuilder(tile35Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile35Tile);

                 Frame tile36Frame = new FrameBuilder(getSubImage(3,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile36Tile = new MapTileBuilder(tile36Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile36Tile);

                 Frame tile37Frame = new FrameBuilder(getSubImage(3,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile37Tile = new MapTileBuilder(tile37Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile37Tile);

                 Frame tile38Frame = new FrameBuilder(getSubImage(3,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile38Tile = new MapTileBuilder(tile38Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile38Tile);

                 Frame tile39Frame = new FrameBuilder(getSubImage(3,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile39Tile = new MapTileBuilder(tile39Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile39Tile);

                 Frame tile310Frame = new FrameBuilder(getSubImage(3,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile310Tile = new MapTileBuilder(tile310Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile310Tile);

                 Frame tile311Frame = new FrameBuilder(getSubImage(3,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile311Tile = new MapTileBuilder(tile311Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile311Tile);

                 Frame tile312Frame = new FrameBuilder(getSubImage(3,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile312Tile = new MapTileBuilder(tile312Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile312Tile);

                 Frame tile313Frame = new FrameBuilder(getSubImage(3,13)).withScale(tileScale).build();
                MapTileBuilder tile313Tile = new MapTileBuilder(tile313Frame);
                mapTiles.add(tile313Tile);

                 Frame tile314Frame = new FrameBuilder(getSubImage(3,14)).withScale(tileScale).build();
                MapTileBuilder tile314Tile = new MapTileBuilder(tile314Frame);
                mapTiles.add(tile314Tile);

                 Frame tile315Frame = new FrameBuilder(getSubImage(3,15)).withScale(tileScale).build();
                MapTileBuilder tile315Tile = new MapTileBuilder(tile315Frame);
                mapTiles.add(tile315Tile);

            //row 4

                Frame tile40Frame = new FrameBuilder(getSubImage(4,0)).withScale(tileScale).build();
                MapTileBuilder tile40Tile = new MapTileBuilder(tile40Frame);
                mapTiles.add(tile40Tile);

                Frame tile41Frame = new FrameBuilder(getSubImage(4,1)).withScale(tileScale).build();
                MapTileBuilder tile41Tile = new MapTileBuilder(tile41Frame);
                mapTiles.add(tile41Tile);

                 Frame tile42Frame = new FrameBuilder(getSubImage(4,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile42Tile = new MapTileBuilder(tile42Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile42Tile);

                 Frame tile43Frame = new FrameBuilder(getSubImage(4,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile43Tile = new MapTileBuilder(tile43Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile43Tile);

                 Frame tile44Frame = new FrameBuilder(getSubImage(4,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile44Tile = new MapTileBuilder(tile44Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile44Tile);

                 Frame tile45Frame = new FrameBuilder(getSubImage(4,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile45Tile = new MapTileBuilder(tile45Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile45Tile);

                 Frame tile46Frame = new FrameBuilder(getSubImage(4,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile46Tile = new MapTileBuilder(tile46Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile46Tile);

                 Frame tile47Frame = new FrameBuilder(getSubImage(4,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile47Tile = new MapTileBuilder(tile47Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile47Tile);

                 Frame tile48Frame = new FrameBuilder(getSubImage(4,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile48Tile = new MapTileBuilder(tile48Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile48Tile);

                 Frame tile49Frame = new FrameBuilder(getSubImage(4,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile49Tile = new MapTileBuilder(tile49Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile49Tile);

                 Frame tile410Frame = new FrameBuilder(getSubImage(4,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile410Tile = new MapTileBuilder(tile410Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile410Tile);

                 Frame tile411Frame = new FrameBuilder(getSubImage(4,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile411Tile = new MapTileBuilder(tile411Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile411Tile);

                 Frame tile412Frame = new FrameBuilder(getSubImage(4,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile412Tile = new MapTileBuilder(tile412Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile412Tile);

                 Frame tile413Frame = new FrameBuilder(getSubImage(4,13)).withScale(tileScale).build();
                MapTileBuilder tile413Tile = new MapTileBuilder(tile413Frame);
                mapTiles.add(tile413Tile);

                 Frame tile414Frame = new FrameBuilder(getSubImage(4,14)).withScale(tileScale).build();
                MapTileBuilder tile414Tile = new MapTileBuilder(tile414Frame);
                mapTiles.add(tile414Tile);

                 Frame tile415Frame = new FrameBuilder(getSubImage(4,15)).withScale(tileScale).build();
                MapTileBuilder tile415Tile = new MapTileBuilder(tile415Frame);
                mapTiles.add(tile415Tile);

            //row 5

                Frame tile50Frame = new FrameBuilder(getSubImage(5,0)).withScale(tileScale).build();
                MapTileBuilder tile50Tile = new MapTileBuilder(tile50Frame);
                mapTiles.add(tile50Tile);

                Frame tile51Frame = new FrameBuilder(getSubImage(5,1)).withScale(tileScale).build();
                MapTileBuilder tile51Tile = new MapTileBuilder(tile51Frame);
                mapTiles.add(tile51Tile);

                 Frame tile52Frame = new FrameBuilder(getSubImage(5,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile52Tile = new MapTileBuilder(tile52Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile52Tile);

                 Frame tile53Frame = new FrameBuilder(getSubImage(5,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile53Tile = new MapTileBuilder(tile53Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile53Tile);

                 Frame tile54Frame = new FrameBuilder(getSubImage(5,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile54Tile = new MapTileBuilder(tile54Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile54Tile);

                 Frame tile55Frame = new FrameBuilder(getSubImage(5,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile55Tile = new MapTileBuilder(tile55Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile55Tile);

                 Frame tile56Frame = new FrameBuilder(getSubImage(5,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile56Tile = new MapTileBuilder(tile56Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile56Tile);

                 Frame tile57Frame = new FrameBuilder(getSubImage(5,7)).withBounds(0,0,40,7).withScale(tileScale).build();
                MapTileBuilder tile57Tile = new MapTileBuilder(tile57Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile57Tile);

                 Frame tile58Frame = new FrameBuilder(getSubImage(5,8)).withBounds(0,0,40,7).withScale(tileScale).build();
                MapTileBuilder tile58Tile = new MapTileBuilder(tile58Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile58Tile);

                 Frame tile59Frame = new FrameBuilder(getSubImage(5,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile59Tile = new MapTileBuilder(tile59Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile59Tile);

                 Frame tile510Frame = new FrameBuilder(getSubImage(5,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile510Tile = new MapTileBuilder(tile510Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile510Tile);

                 Frame tile511Frame = new FrameBuilder(getSubImage(5,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile511Tile = new MapTileBuilder(tile511Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile511Tile);

                 Frame tile512Frame = new FrameBuilder(getSubImage(5,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile512Tile = new MapTileBuilder(tile512Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile512Tile);

                 Frame tile513Frame = new FrameBuilder(getSubImage(5,13)).withScale(tileScale).build();
                MapTileBuilder tile513Tile = new MapTileBuilder(tile513Frame);
                mapTiles.add(tile513Tile);

                 Frame tile514Frame = new FrameBuilder(getSubImage(5,14)).withScale(tileScale).build();
                MapTileBuilder tile514Tile = new MapTileBuilder(tile514Frame);
                mapTiles.add(tile514Tile);

                 Frame tile515Frame = new FrameBuilder(getSubImage(5,15)).withScale(tileScale).build();
                MapTileBuilder tile515Tile = new MapTileBuilder(tile515Frame);
                mapTiles.add(tile515Tile);

            //row 6

                Frame tile60Frame = new FrameBuilder(getSubImage(6,0)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile60Tile = new MapTileBuilder(tile60Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile60Tile);

                Frame tile61Frame = new FrameBuilder(getSubImage(6,1)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile61Tile = new MapTileBuilder(tile61Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile61Tile);

                 Frame tile62Frame = new FrameBuilder(getSubImage(6,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile62Tile = new MapTileBuilder(tile62Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile62Tile);

                 Frame tile63Frame = new FrameBuilder(getSubImage(6,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile63Tile = new MapTileBuilder(tile63Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile63Tile);

                 Frame tile64Frame = new FrameBuilder(getSubImage(6,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile64Tile = new MapTileBuilder(tile64Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile64Tile);

                 Frame tile65Frame = new FrameBuilder(getSubImage(6,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile65Tile = new MapTileBuilder(tile65Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile65Tile);

                 Frame tile66Frame = new FrameBuilder(getSubImage(6,6)).withBounds(0,0,30,30).withScale(tileScale).build();
                MapTileBuilder tile66Tile = new MapTileBuilder(tile66Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile66Tile);

                 Frame tile67Frame = new FrameBuilder(getSubImage(6,7)).withScale(tileScale).build();
                MapTileBuilder tile67Tile = new MapTileBuilder(tile67Frame);
                mapTiles.add(tile67Tile);

                 Frame tile68Frame = new FrameBuilder(getSubImage(6,8)).withScale(tileScale).build();
                MapTileBuilder tile68Tile = new MapTileBuilder(tile68Frame);
                mapTiles.add(tile68Tile);

                 Frame tile69Frame = new FrameBuilder(getSubImage(6,9)).withBounds(0,0,40,30).withScale(tileScale).build();
                MapTileBuilder tile69Tile = new MapTileBuilder(tile69Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile69Tile);

                 Frame tile610Frame = new FrameBuilder(getSubImage(6,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile610Tile = new MapTileBuilder(tile610Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile610Tile);

                 Frame tile611Frame = new FrameBuilder(getSubImage(6,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile611Tile = new MapTileBuilder(tile611Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile611Tile);

                 Frame tile612Frame = new FrameBuilder(getSubImage(6,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile612Tile = new MapTileBuilder(tile612Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile612Tile);

                 Frame tile613Frame = new FrameBuilder(getSubImage(6,13)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile613Tile = new MapTileBuilder(tile613Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile613Tile);

                 Frame tile614Frame = new FrameBuilder(getSubImage(6,14)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile614Tile = new MapTileBuilder(tile614Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile614Tile);

                 Frame tile615Frame = new FrameBuilder(getSubImage(6,15)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile615Tile = new MapTileBuilder(tile615Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile615Tile);

            //row 7

                Frame tile70Frame = new FrameBuilder(getSubImage(7,0)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile70Tile = new MapTileBuilder(tile70Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile70Tile);

                Frame tile71Frame = new FrameBuilder(getSubImage(7,1)).withBounds(0,0,25,37).withScale(tileScale).build();
                MapTileBuilder tile71Tile = new MapTileBuilder(tile71Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile71Tile);

                 Frame tile72Frame = new FrameBuilder(getSubImage(7,2)).withBounds(30,0,10,10).withScale(tileScale).build();
                MapTileBuilder tile72Tile = new MapTileBuilder(tile72Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile72Tile);

                 Frame tile73Frame = new FrameBuilder(getSubImage(7,3)).withScale(tileScale).build();
                MapTileBuilder tile73Tile = new MapTileBuilder(tile73Frame);
                mapTiles.add(tile73Tile);

                 Frame tile74Frame = new FrameBuilder(getSubImage(7,4)).withScale(tileScale).build();
                MapTileBuilder tile74Tile = new MapTileBuilder(tile74Frame);
                mapTiles.add(tile74Tile);

                 Frame tile75Frame = new FrameBuilder(getSubImage(7,5)).withScale(tileScale).build();
                MapTileBuilder tile75Tile = new MapTileBuilder(tile75Frame);
                mapTiles.add(tile75Tile);

                 Frame tile76Frame = new FrameBuilder(getSubImage(7,6)).withScale(tileScale).build();
                MapTileBuilder tile76Tile = new MapTileBuilder(tile76Frame);
                mapTiles.add(tile76Tile);

                 Frame tile77Frame = new FrameBuilder(getSubImage(7,7)).withScale(tileScale).build();
                MapTileBuilder tile77Tile = new MapTileBuilder(tile77Frame);
                mapTiles.add(tile77Tile);

                 Frame tile78Frame = new FrameBuilder(getSubImage(7,8)).withScale(tileScale).build();
                MapTileBuilder tile78Tile = new MapTileBuilder(tile78Frame);
                mapTiles.add(tile78Tile);

                 Frame tile79Frame = new FrameBuilder(getSubImage(7,9)).withScale(tileScale).build();
                MapTileBuilder tile79Tile = new MapTileBuilder(tile79Frame);
                mapTiles.add(tile79Tile);

                 Frame tile710Frame = new FrameBuilder(getSubImage(7,10)).withScale(tileScale).build();
                MapTileBuilder tile710Tile = new MapTileBuilder(tile710Frame);
                mapTiles.add(tile710Tile);

                 Frame tile711Frame = new FrameBuilder(getSubImage(7,11)).withScale(tileScale).build();
                MapTileBuilder tile711Tile = new MapTileBuilder(tile711Frame);
                mapTiles.add(tile711Tile);

                 Frame tile712Frame = new FrameBuilder(getSubImage(7,12)).withBounds(35,0,5,10).withScale(tileScale).build();
                MapTileBuilder tile712Tile = new MapTileBuilder(tile712Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile712Tile);

                 Frame tile713Frame = new FrameBuilder(getSubImage(7,13)).withBounds(0,0,3,10).withScale(tileScale).build();
                MapTileBuilder tile713Tile = new MapTileBuilder(tile713Frame);
                mapTiles.add(tile713Tile);

                 Frame tile714Frame = new FrameBuilder(getSubImage(7,14)).withScale(tileScale).build();
                MapTileBuilder tile714Tile = new MapTileBuilder(tile714Frame);
                mapTiles.add(tile714Tile);

                 Frame tile715Frame = new FrameBuilder(getSubImage(7,15)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile715Tile = new MapTileBuilder(tile715Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile715Tile);

            //row 8

                Frame tile80Frame = new FrameBuilder(getSubImage(8,0)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile80Tile = new MapTileBuilder(tile80Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile80Tile);

                Frame tile81Frame = new FrameBuilder(getSubImage(8,1)).withBounds(0,10,40,27).withScale(tileScale).build();
                MapTileBuilder tile81Tile = new MapTileBuilder(tile81Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile81Tile);

                 Frame tile82Frame = new FrameBuilder(getSubImage(8,2)).withBounds(0,0,25,37).withScale(tileScale).build();
                MapTileBuilder tile82Tile = new MapTileBuilder(tile82Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile82Tile);

                 Frame tile83Frame = new FrameBuilder(getSubImage(8,3)).withBounds(15,0,25,37).withScale(tileScale).build();
                MapTileBuilder tile83Tile = new MapTileBuilder(tile83Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile83Tile);

                 Frame tile84Frame = new FrameBuilder(getSubImage(8,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile84Tile = new MapTileBuilder(tile84Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile84Tile);

                 Frame tile85Frame = new FrameBuilder(getSubImage(8,5)).withBounds(35,0,5,37).withScale(tileScale).build();
                MapTileBuilder tile85Tile = new MapTileBuilder(tile85Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile85Tile);

                 Frame tile86Frame = new FrameBuilder(getSubImage(8,6)).withBounds(0,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile86Tile = new MapTileBuilder(tile86Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile86Tile);

                 Frame tile87Frame = new FrameBuilder(getSubImage(8,7)).withScale(tileScale).build();
                MapTileBuilder tile87Tile = new MapTileBuilder(tile87Frame);
                mapTiles.add(tile87Tile);

                 Frame tile88Frame = new FrameBuilder(getSubImage(8,8)).withScale(tileScale).build();
                MapTileBuilder tile88Tile = new MapTileBuilder(tile88Frame);
                mapTiles.add(tile88Tile);

                 Frame tile89Frame = new FrameBuilder(getSubImage(8,9)).withBounds(5,0,35,37).withScale(tileScale).build();
                MapTileBuilder tile89Tile = new MapTileBuilder(tile89Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile89Tile);

                 Frame tile810Frame = new FrameBuilder(getSubImage(8,10)).withBounds(35,0,5,37).withScale(tileScale).build();
                MapTileBuilder tile810Tile = new MapTileBuilder(tile810Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile810Tile);

                 Frame tile811Frame = new FrameBuilder(getSubImage(8,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile811Tile = new MapTileBuilder(tile811Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile811Tile);

                 Frame tile812Frame = new FrameBuilder(getSubImage(8,12)).withBounds(0,0,10,37).withScale(tileScale).build();
                MapTileBuilder tile812Tile = new MapTileBuilder(tile812Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile812Tile);

                 Frame tile813Frame = new FrameBuilder(getSubImage(8,13)).withBounds(5,10,35,27).withScale(tileScale).build();
                MapTileBuilder tile813Tile = new MapTileBuilder(tile813Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile813Tile);

                 Frame tile814Frame = new FrameBuilder(getSubImage(8,14)).withBounds(0,10,40,27).withScale(tileScale).build();
                MapTileBuilder tile814Tile = new MapTileBuilder(tile814Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile814Tile);

                 Frame tile815Frame = new FrameBuilder(getSubImage(8,15)).withScale(tileScale).build();
                MapTileBuilder tile815Tile = new MapTileBuilder(tile815Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile815Tile);

            //row 9

                Frame tile90Frame = new FrameBuilder(getSubImage(9,0)).withScale(tileScale).build();
                MapTileBuilder tile90Tile = new MapTileBuilder(tile90Frame);
                mapTiles.add(tile90Tile);

                Frame tile91Frame = new FrameBuilder(getSubImage(9,1)).withScale(tileScale).build();
                MapTileBuilder tile91Tile = new MapTileBuilder(tile91Frame);
                mapTiles.add(tile91Tile);

                 Frame tile92Frame = new FrameBuilder(getSubImage(9,2)).withBounds(0,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile92Tile = new MapTileBuilder(tile92Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile92Tile);

                 Frame tile93Frame = new FrameBuilder(getSubImage(9,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile93Tile = new MapTileBuilder(tile93Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile93Tile);

                 Frame tile94Frame = new FrameBuilder(getSubImage(9,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile94Tile = new MapTileBuilder(tile94Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile94Tile);

                 Frame tile95Frame = new FrameBuilder(getSubImage(9,5)).withBounds(0,0,10,37).withScale(tileScale).build();
                MapTileBuilder tile95Tile = new MapTileBuilder(tile95Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile95Tile);

                 Frame tile96Frame = new FrameBuilder(getSubImage(9,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile96Tile = new MapTileBuilder(tile96Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile96Tile);

                 Frame tile97Frame = new FrameBuilder(getSubImage(9,7)).withScale(tileScale).build();
                MapTileBuilder tile97Tile = new MapTileBuilder(tile97Frame);
                mapTiles.add(tile97Tile);

                 Frame tile98Frame = new FrameBuilder(getSubImage(9,8)).withScale(tileScale).build();
                MapTileBuilder tile98Tile = new MapTileBuilder(tile98Frame);
                mapTiles.add(tile98Tile);

                 Frame tile99Frame = new FrameBuilder(getSubImage(9,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile99Tile = new MapTileBuilder(tile99Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile99Tile);

                 Frame tile910Frame = new FrameBuilder(getSubImage(9,10)).withBounds(20,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile910Tile = new MapTileBuilder(tile910Frame);
                mapTiles.add(tile910Tile);

                 Frame tile911Frame = new FrameBuilder(getSubImage(9,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile911Tile = new MapTileBuilder(tile911Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile911Tile);

                 Frame tile912Frame = new FrameBuilder(getSubImage(9,12)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile912Tile = new MapTileBuilder(tile912Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile912Tile);

                 Frame tile913Frame = new FrameBuilder(getSubImage(9,13)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile913Tile = new MapTileBuilder(tile913Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile913Tile);

                 Frame tile914Frame = new FrameBuilder(getSubImage(9,14)).withScale(tileScale).build();
                MapTileBuilder tile914Tile = new MapTileBuilder(tile914Frame);
                mapTiles.add(tile914Tile);

                 Frame tile915Frame = new FrameBuilder(getSubImage(9,15)).withScale(tileScale).build();
                MapTileBuilder tile915Tile = new MapTileBuilder(tile915Frame);
                mapTiles.add(tile915Tile);

            //row 10

                Frame tile100Frame = new FrameBuilder(getSubImage(10,0)).withScale(tileScale).build();
                MapTileBuilder tile100Tile = new MapTileBuilder(tile100Frame);
                mapTiles.add(tile100Tile);

                Frame tile101Frame = new FrameBuilder(getSubImage(10,1)).withScale(tileScale).build();
                MapTileBuilder tile101Tile = new MapTileBuilder(tile101Frame);
                mapTiles.add(tile101Tile);

                 Frame tile102Frame = new FrameBuilder(getSubImage(10,2)).withBounds(0,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile102Tile = new MapTileBuilder(tile102Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile102Tile);

                 Frame tile103Frame = new FrameBuilder(getSubImage(10,3)).withBounds(15,18,25,19).withScale(tileScale).build();
                MapTileBuilder tile103Tile = new MapTileBuilder(tile103Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile103Tile);

                 Frame tile104Frame = new FrameBuilder(getSubImage(10,4)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile104Tile = new MapTileBuilder(tile104Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile104Tile);

                 Frame tile105Frame = new FrameBuilder(getSubImage(10,5)).withBounds(35,19,5,18).withScale(tileScale).build();
                MapTileBuilder tile105Tile = new MapTileBuilder(tile105Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile105Tile);

                 Frame tile106Frame = new FrameBuilder(getSubImage(10,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile106Tile = new MapTileBuilder(tile106Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile106Tile);

                 Frame tile107Frame = new FrameBuilder(getSubImage(10,7)).withBounds(0,0,28,15).withScale(tileScale).build();
                MapTileBuilder tile107Tile = new MapTileBuilder(tile107Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile107Tile);

                 Frame tile108Frame = new FrameBuilder(getSubImage(10,8)).withBounds(5,0,35,15).withScale(tileScale).build();
                MapTileBuilder tile108Tile = new MapTileBuilder(tile108Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile108Tile);

                 Frame tile109Frame = new FrameBuilder(getSubImage(10,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile109Tile = new MapTileBuilder(tile109Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile109Tile);

                 Frame tile1010Frame = new FrameBuilder(getSubImage(10,10)).withScale(tileScale).build();
                MapTileBuilder tile1010Tile = new MapTileBuilder(tile1010Frame);
                mapTiles.add(tile1010Tile);

                 Frame tile1011Frame = new FrameBuilder(getSubImage(10,11)).withScale(tileScale).build();
                MapTileBuilder tile1011Tile = new MapTileBuilder(tile1011Frame);
                mapTiles.add(tile1011Tile);

                 Frame tile1012Frame = new FrameBuilder(getSubImage(10,12)).withScale(tileScale).build();
                MapTileBuilder tile1012Tile = new MapTileBuilder(tile1012Frame);
                mapTiles.add(tile1012Tile);

                 Frame tile1013Frame = new FrameBuilder(getSubImage(10,13)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile1013Tile = new MapTileBuilder(tile1013Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1013Tile);

                 Frame tile1014Frame = new FrameBuilder(getSubImage(10,14)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile1014Tile = new MapTileBuilder(tile1014Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1014Tile);

                 Frame tile1015Frame = new FrameBuilder(getSubImage(10,15)).withScale(tileScale).build();
                MapTileBuilder tile1015Tile = new MapTileBuilder(tile1015Frame);
                mapTiles.add(tile1015Tile);

            //row 11

                Frame tile11_0Frame = new FrameBuilder(getSubImage(11,0)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_0Tile = new MapTileBuilder(tile11_0Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_0Tile);

                Frame tile11_1Frame = new FrameBuilder(getSubImage(11,1)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_1Tile = new MapTileBuilder(tile11_1Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_1Tile);

                 Frame tile11_2Frame = new FrameBuilder(getSubImage(11,2)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_2Tile = new MapTileBuilder(tile11_2Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_2Tile);

                 Frame tile11_3Frame = new FrameBuilder(getSubImage(11,3)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_3Tile = new MapTileBuilder(tile11_3Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_3Tile);

                 Frame tile11_4Frame = new FrameBuilder(getSubImage(11,4)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_4Tile = new MapTileBuilder(tile11_4Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_4Tile);

                 Frame tile11_5Frame = new FrameBuilder(getSubImage(11,5)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_5Tile = new MapTileBuilder(tile11_5Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_5Tile);

                 Frame tile11_6Frame = new FrameBuilder(getSubImage(11,6)).withBounds(0,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile11_6Tile = new MapTileBuilder(tile11_6Frame);
                mapTiles.add(tile11_6Tile);

                 Frame tile11_7Frame = new FrameBuilder(getSubImage(11,7)).withBounds(0,0,25,37).withScale(tileScale).build();
                MapTileBuilder tile11_7Tile = new MapTileBuilder(tile11_7Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_7Tile);

                 Frame tile11_8Frame = new FrameBuilder(getSubImage(11,8)).withBounds(10,33,30,4).withScale(tileScale).build();
                MapTileBuilder tile11_8Tile = new MapTileBuilder(tile11_8Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_8Tile);

                 Frame tile11_9Frame = new FrameBuilder(getSubImage(11,9)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_9Tile = new MapTileBuilder(tile11_9Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_9Tile);

                 Frame tile11_10Frame = new FrameBuilder(getSubImage(11,10)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_10Tile = new MapTileBuilder(tile11_10Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_10Tile);

                 Frame tile11_11Frame = new FrameBuilder(getSubImage(11,11)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_11Tile = new MapTileBuilder(tile11_11Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_11Tile);

                 Frame tile11_12Frame = new FrameBuilder(getSubImage(11,12)).withBounds(0,0,10,37).withScale(tileScale).build();
                MapTileBuilder tile11_12Tile = new MapTileBuilder(tile11_12Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_12Tile);

                 Frame tile11_13Frame = new FrameBuilder(getSubImage(11,13)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_13Tile = new MapTileBuilder(tile11_13Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_13Tile);

                 Frame tile11_14Frame = new FrameBuilder(getSubImage(11,14)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_14Tile = new MapTileBuilder(tile11_14Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_14Tile);

                 Frame tile11_15Frame = new FrameBuilder(getSubImage(11,15)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile11_15Tile = new MapTileBuilder(tile11_15Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile11_15Tile);

            //row 12

                Frame tile120Frame = new FrameBuilder(getSubImage(12,0)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile120Tile = new MapTileBuilder(tile120Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile120Tile);

                Frame tile121Frame = new FrameBuilder(getSubImage(12,1)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile121Tile = new MapTileBuilder(tile121Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile121Tile);

                 Frame tile122Frame = new FrameBuilder(getSubImage(12,2)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile122Tile = new MapTileBuilder(tile122Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile122Tile);

                 Frame tile123Frame = new FrameBuilder(getSubImage(12,3)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile123Tile = new MapTileBuilder(tile123Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile123Tile);

                 Frame tile124Frame = new FrameBuilder(getSubImage(12,4)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile124Tile = new MapTileBuilder(tile124Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile124Tile);

                 Frame tile125Frame = new FrameBuilder(getSubImage(12,5)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile125Tile = new MapTileBuilder(tile125Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile125Tile);

                 Frame tile126Frame = new FrameBuilder(getSubImage(12,6)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile126Tile = new MapTileBuilder(tile126Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile126Tile);

                 Frame tile127Frame = new FrameBuilder(getSubImage(12,7)).withScale(tileScale).build();
                MapTileBuilder tile127Tile = new MapTileBuilder(tile127Frame);
                mapTiles.add(tile127Tile);

                 Frame tile128Frame = new FrameBuilder(getSubImage(12,8)).withScale(tileScale).build();
                MapTileBuilder tile128Tile = new MapTileBuilder(tile128Frame);
                mapTiles.add(tile128Tile);

                 Frame tile129Frame = new FrameBuilder(getSubImage(12,9)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile129Tile = new MapTileBuilder(tile129Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile129Tile);

                 Frame tile1210Frame = new FrameBuilder(getSubImage(12,10)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1210Tile = new MapTileBuilder(tile1210Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1210Tile);

                 Frame tile1211Frame = new FrameBuilder(getSubImage(12,11)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1211Tile = new MapTileBuilder(tile1211Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1211Tile);

                 Frame tile1212Frame = new FrameBuilder(getSubImage(12,12)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1212Tile = new MapTileBuilder(tile1212Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1212Tile);

                 Frame tile1213Frame = new FrameBuilder(getSubImage(12,13)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1213Tile = new MapTileBuilder(tile1213Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1213Tile);

                 Frame tile1214Frame = new FrameBuilder(getSubImage(12,14)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1214Tile = new MapTileBuilder(tile1214Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1214Tile);

                 Frame tile1215Frame = new FrameBuilder(getSubImage(12,15)).withBounds(0,0,40,18).withScale(tileScale).build();
                MapTileBuilder tile1215Tile = new MapTileBuilder(tile1215Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1215Tile);

            //row 13

                Frame tile130Frame = new FrameBuilder(getSubImage(13,0)).withBounds(0,0,5,37).withScale(tileScale).build();
                MapTileBuilder tile130Tile = new MapTileBuilder(tile130Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile130Tile);

                Frame tile131Frame = new FrameBuilder(getSubImage(13,1)).withBounds(0,36,40,1).withScale(tileScale).build();
                MapTileBuilder tile131Tile = new MapTileBuilder(tile131Frame);
                mapTiles.add(tile131Tile);

                 Frame tile132Frame = new FrameBuilder(getSubImage(13,2)).withBounds(7,18,33,19).withScale(tileScale).build();
                MapTileBuilder tile132Tile = new MapTileBuilder(tile132Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile132Tile);

                 Frame tile133Frame = new FrameBuilder(getSubImage(13,3)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile133Tile = new MapTileBuilder(tile133Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile133Tile);

                 Frame tile134Frame = new FrameBuilder(getSubImage(13,4)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile134Tile = new MapTileBuilder(tile134Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile134Tile);

                 Frame tile135Frame = new FrameBuilder(getSubImage(13,5)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile135Tile = new MapTileBuilder(tile135Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile135Tile);

                 Frame tile136Frame = new FrameBuilder(getSubImage(13,6)).withBounds(0,18,20,19).withScale(tileScale).build();
                MapTileBuilder tile136Tile = new MapTileBuilder(tile136Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile136Tile);

                 Frame tile137Frame = new FrameBuilder(getSubImage(13,7)).withScale(tileScale).build();
                MapTileBuilder tile137Tile = new MapTileBuilder(tile137Frame);
                mapTiles.add(tile137Tile);

                 Frame tile138Frame = new FrameBuilder(getSubImage(13,8)).withScale(tileScale).build();
                MapTileBuilder tile138Tile = new MapTileBuilder(tile138Frame);
                mapTiles.add(tile138Tile);

                 Frame tile139Frame = new FrameBuilder(getSubImage(13,9)).withBounds(5,18,35,19).withScale(tileScale).build();
                MapTileBuilder tile139Tile = new MapTileBuilder(tile139Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile139Tile);

                 Frame tile1310Frame = new FrameBuilder(getSubImage(13,10)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile1310Tile = new MapTileBuilder(tile1310Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1310Tile);

                 Frame tile1311Frame = new FrameBuilder(getSubImage(13,11)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile1311Tile = new MapTileBuilder(tile1311Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1311Tile);

                 Frame tile1312Frame = new FrameBuilder(getSubImage(13,12)).withBounds(0,18,40,19).withScale(tileScale).build();
                MapTileBuilder tile1312Tile = new MapTileBuilder(tile1312Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1312Tile);

                 Frame tile1313Frame = new FrameBuilder(getSubImage(13,13)).withBounds(0,18,20,19).withScale(tileScale).build();
                MapTileBuilder tile1313Tile = new MapTileBuilder(tile1313Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1313Tile);

                 Frame tile1314Frame = new FrameBuilder(getSubImage(13,14)).withBounds(0,36,40,1).withScale(tileScale).build();
                MapTileBuilder tile1314Tile = new MapTileBuilder(tile1314Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1314Tile);

                 Frame tile1315Frame = new FrameBuilder(getSubImage(13,15)).withBounds(20,0,20,37).withScale(tileScale).build();
                MapTileBuilder tile1315Tile = new MapTileBuilder(tile1315Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile1315Tile);

            //row 14

                Frame tile140Frame = new FrameBuilder(getSubImage(14,0)).withScale(tileScale).build();
                MapTileBuilder tile140Tile = new MapTileBuilder(tile140Frame);
                mapTiles.add(tile140Tile);

                Frame tile141Frame = new FrameBuilder(getSubImage(14,1)).withScale(tileScale).build();
                MapTileBuilder tile141Tile = new MapTileBuilder(tile141Frame);
                mapTiles.add(tile141Tile);

                 Frame tile142Frame = new FrameBuilder(getSubImage(14,2)).withScale(tileScale).build();
                MapTileBuilder tile142Tile = new MapTileBuilder(tile142Frame);
                mapTiles.add(tile142Tile);

                 Frame tile143Frame = new FrameBuilder(getSubImage(14,3)).withScale(tileScale).build();
                MapTileBuilder tile143Tile = new MapTileBuilder(tile143Frame);
                mapTiles.add(tile143Tile);

                 Frame tile144Frame = new FrameBuilder(getSubImage(14,4)).withScale(tileScale).build();
                MapTileBuilder tile144Tile = new MapTileBuilder(tile144Frame);
                mapTiles.add(tile144Tile);

                 Frame tile145Frame = new FrameBuilder(getSubImage(14,5)).withScale(tileScale).build();
                MapTileBuilder tile145Tile = new MapTileBuilder(tile145Frame);
                mapTiles.add(tile145Tile);

                 Frame tile146Frame = new FrameBuilder(getSubImage(14,6)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile146Tile = new MapTileBuilder(tile146Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile146Tile);

                 Frame tile147Frame = new FrameBuilder(getSubImage(14,7)).withScale(tileScale).build();
                MapTileBuilder tile147Tile = new MapTileBuilder(tile147Frame);
                mapTiles.add(tile147Tile);

                 Frame tile148Frame = new FrameBuilder(getSubImage(14,8)).withBounds(39,0,1,37).withScale(tileScale).build();
                MapTileBuilder tile148Tile = new MapTileBuilder(tile148Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile148Tile);

                 Frame tile149Frame = new FrameBuilder(getSubImage(14,9)).withScale(tileScale).build();
                MapTileBuilder tile149Tile = new MapTileBuilder(tile149Frame);
                mapTiles.add(tile149Tile);

                 Frame tile1410Frame = new FrameBuilder(getSubImage(14,10)).withScale(tileScale).build();
                MapTileBuilder tile1410Tile = new MapTileBuilder(tile1410Frame);
                mapTiles.add(tile1410Tile);

                 Frame tile1411Frame = new FrameBuilder(getSubImage(14,11)).withScale(tileScale).build();
                MapTileBuilder tile1411Tile = new MapTileBuilder(tile1411Frame);
                mapTiles.add(tile1411Tile);

                 Frame tile1412Frame = new FrameBuilder(getSubImage(14,12)).withScale(tileScale).build();
                MapTileBuilder tile1412Tile = new MapTileBuilder(tile1412Frame);
                mapTiles.add(tile1412Tile);

                 Frame tile1413Frame = new FrameBuilder(getSubImage(14,13)).withScale(tileScale).build();
                MapTileBuilder tile1413Tile = new MapTileBuilder(tile1413Frame);
                mapTiles.add(tile1413Tile);

                 Frame tile1414Frame = new FrameBuilder(getSubImage(14,14)).withScale(tileScale).build();
                MapTileBuilder tile1414Tile = new MapTileBuilder(tile1414Frame);
                mapTiles.add(tile1414Tile);

                 Frame tile1415Frame = new FrameBuilder(getSubImage(14,15)).withScale(tileScale).build();
                MapTileBuilder tile1415Tile = new MapTileBuilder(tile1415Frame);
                mapTiles.add(tile1415Tile);

            //row 15

                Frame tile150Frame = new FrameBuilder(getSubImage(15,0)).withScale(tileScale).build();
                MapTileBuilder tile150Tile = new MapTileBuilder(tile150Frame);
                mapTiles.add(tile150Tile);

                Frame tile151Frame = new FrameBuilder(getSubImage(15,1)).withScale(tileScale).build();
                MapTileBuilder tile151Tile = new MapTileBuilder(tile151Frame);
                mapTiles.add(tile151Tile);

                 Frame tile152Frame = new FrameBuilder(getSubImage(15,2)).withScale(tileScale).build();
                MapTileBuilder tile152Tile = new MapTileBuilder(tile152Frame);
                mapTiles.add(tile152Tile);

                 Frame tile153Frame = new FrameBuilder(getSubImage(15,3)).withScale(tileScale).build();
                MapTileBuilder tile153Tile = new MapTileBuilder(tile153Frame);
                mapTiles.add(tile153Tile);

                 Frame tile154Frame = new FrameBuilder(getSubImage(15,4)).withScale(tileScale).build();
                MapTileBuilder tile154Tile = new MapTileBuilder(tile154Frame);
                mapTiles.add(tile154Tile);

                 Frame tile155Frame = new FrameBuilder(getSubImage(15,5)).withScale(tileScale).build();
                MapTileBuilder tile155Tile = new MapTileBuilder(tile155Frame);
                mapTiles.add(tile155Tile);

                 Frame tile156Frame = new FrameBuilder(getSubImage(15,6)).withScale(tileScale).build();
                MapTileBuilder tile156Tile = new MapTileBuilder(tile156Frame);
                mapTiles.add(tile156Tile);

                 Frame tile157Frame = new FrameBuilder(getSubImage(15,7)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile157Tile = new MapTileBuilder(tile157Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile157Tile);

                 Frame tile158Frame = new FrameBuilder(getSubImage(15,8)).withBounds(0,0,40,37).withScale(tileScale).build();
                MapTileBuilder tile158Tile = new MapTileBuilder(tile158Frame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(tile158Tile);

                 Frame tile159Frame = new FrameBuilder(getSubImage(15,9)).withScale(tileScale).build();
                MapTileBuilder tile159Tile = new MapTileBuilder(tile159Frame);
                mapTiles.add(tile159Tile);

                 Frame tile1510Frame = new FrameBuilder(getSubImage(15,10)).withScale(tileScale).build();
                MapTileBuilder tile1510Tile = new MapTileBuilder(tile1510Frame);
                mapTiles.add(tile1510Tile);

                 Frame tile1511Frame = new FrameBuilder(getSubImage(15,11)).withScale(tileScale).build();
                MapTileBuilder tile1511Tile = new MapTileBuilder(tile1511Frame);
                mapTiles.add(tile1511Tile);

                 Frame tile1512Frame = new FrameBuilder(getSubImage(15,12)).withScale(tileScale).build();
                MapTileBuilder tile1512Tile = new MapTileBuilder(tile1512Frame);
                mapTiles.add(tile1512Tile);

                 Frame tile1513Frame = new FrameBuilder(getSubImage(15,13)).withScale(tileScale).build();
                MapTileBuilder tile1513Tile = new MapTileBuilder(tile1513Frame);
                mapTiles.add(tile1513Tile);

                 Frame tile1514Frame = new FrameBuilder(getSubImage(15,14)).withScale(tileScale).build();
                MapTileBuilder tile1514Tile = new MapTileBuilder(tile1514Frame);
                mapTiles.add(tile1514Tile);

                 Frame tile1515Frame = new FrameBuilder(getSubImage(15,15)).withScale(tileScale).build();
                MapTileBuilder tile1515Tile = new MapTileBuilder(tile1515Frame);
                mapTiles.add(tile1515Tile);

            return mapTiles;
        }

    }

