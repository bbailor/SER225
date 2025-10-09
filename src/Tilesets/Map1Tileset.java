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
public class Map1Tileset extends Tileset {

    public Map1Tileset() {

        super(ImageLoader.load("map_one_tileset.png"),16, 16, 3);
    }
        @Override
        public ArrayList<MapTileBuilder> defineTiles() {
            ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

            //WallUpperCorner1
                Frame wallTLCornerFrame = new FrameBuilder(getSubImage(0,0)).withScale(tileScale).withBounds(0,0,16,16).build();
                MapTileBuilder wallTLCornerTile = new MapTileBuilder(wallTLCornerFrame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(wallTLCornerTile);


            //WallTopWithBeam
                Frame wallTWBFrame = new FrameBuilder(getSubImage(0,1)).withBounds(0,0,16,16).withScale(tileScale).build();
                MapTileBuilder wallTWBTile = new MapTileBuilder(wallTWBFrame).withTileType(TileType.NOT_PASSABLE);
                mapTiles.add(wallTWBTile);
            
            //WallTopLeftWindow
                Frame wallTLWindowFrame = new FrameBuilder(getSubImage(0,2)).withBounds(0,0,16,16).withScale(tileScale).build();
                MapTileBuilder wallTLWindowTile = new MapTileBuilder(wallTLWindowFrame);
                mapTiles.add(wallTLWindowTile);

            //WallTopCenterWindow
                Frame wallTCWindowFrame = new FrameBuilder(getSubImage(0,3)).withBounds(0,0,16,16).withScale(tileScale).build();
                MapTileBuilder wallTCWindowTile = new MapTileBuilder(wallTCWindowFrame);
                mapTiles.add(wallTCWindowTile);
            
            //wallTopRightWindow
                Frame wallTRWindowFrame = new FrameBuilder(getSubImage(0,4)).withScale(tileScale).build();
                MapTileBuilder wallTRWindowTile = new MapTileBuilder(wallTRWindowFrame);
                mapTiles.add(wallTRWindowTile);
            
            //wallTopBlank
                Frame wallTopBlankFrame = new FrameBuilder(getSubImage(0, 5)).withScale(tileScale).build();
                MapTileBuilder wallTopBlankTile = new MapTileBuilder(wallTopBlankFrame);
                mapTiles.add(wallTopBlankTile);

            //wallTopRightCorner
                Frame wallTRCornerFrame = new FrameBuilder(getSubImage(0,6)).withScale(tileScale).build();
                MapTileBuilder wallTRCornerTile = new MapTileBuilder(wallTRCornerFrame);
                mapTiles.add(wallTRCornerTile);

            //wallCenterLeftEdged
                Frame wallCLEFrame = new FrameBuilder(getSubImage(1,0)).withScale(tileScale).build();
                MapTileBuilder wallCLETile = new MapTileBuilder(wallCLEFrame);
                mapTiles.add(wallCLETile);

            //wallCenterPillar
                Frame wallCPFrame = new FrameBuilder(getSubImage(1,1)).withScale(tileScale).build();
                MapTileBuilder wallCPTile = new MapTileBuilder(wallCPFrame);
                mapTiles.add(wallCPTile);
            
            //wallLowerLeftWindow
                Frame wallLLWFrame = new FrameBuilder(getSubImage(1,2)).withScale(tileScale).build();
                MapTileBuilder wallLLWTile = new MapTileBuilder(wallLLWFrame);
                mapTiles.add(wallLLWTile);

            //wallLowerCenterWindow
                Frame wallLCWFrame = new FrameBuilder(getSubImage(1,3)).withScale(tileScale).build();
                MapTileBuilder wallLCWTile = new MapTileBuilder(wallLCWFrame);
                mapTiles.add(wallLCWTile);

            //wallLowerRightWindow
                Frame wallLRWFrame = new FrameBuilder(getSubImage(1,4)).withScale(tileScale).build();
                MapTileBuilder wallRWTile = new MapTileBuilder(wallLRWFrame);
                mapTiles.add(wallRWTile);

            //wallLowerBlank
                Frame wallLBFrame = new FrameBuilder(getSubImage(1,5)).withScale(tileScale).build();
                MapTileBuilder wallLBTile = new MapTileBuilder(wallLBFrame);
                mapTiles.add(wallLBTile);

            //wallLowerRightEdge
                Frame wallLREFrame = new FrameBuilder(getSubImage(1, 6)).withScale(tileScale).build();
                MapTileBuilder wallLRETile = new MapTileBuilder(wallLREFrame);
                mapTiles.add(wallLRETile);
            
            //wallFLoorLowLeftCorner
                Frame wallFLLCFrame = new FrameBuilder(getSubImage(2,0)).withScale(tileScale).build();
                MapTileBuilder wallFLLCTile = new MapTileBuilder(wallFLLCFrame);
                mapTiles.add(wallFLLCTile);

            //wallFloorLowPillar
                Frame wallFLPFrame = new FrameBuilder(getSubImage(2,1)).withScale(tileScale).build();
                MapTileBuilder wallFLPTile = new MapTileBuilder(wallFLPFrame);
                mapTiles.add(wallFLPTile);

            //wallFloorTrapDoorTL
                Frame wallFTTLFrame = new FrameBuilder(getSubImage(2,2)).withScale(tileScale).build();
                MapTileBuilder wallFTTLTile = new MapTileBuilder(wallFTTLFrame);
                mapTiles.add(wallFTTLTile);
            
            //wallFloorTrapdoorCenter
                Frame wallFTCenterFrame = new FrameBuilder(getSubImage(2,3)).withScale(tileScale).build();
                MapTileBuilder wallFTCenterTile = new MapTileBuilder(wallFTCenterFrame);
                mapTiles.add(wallFTCenterTile);

            //wallFloorTrapdoorTR
                Frame wallFTTRFrame = new FrameBuilder(getSubImage(2,4)).withScale(tileScale).build();
                MapTileBuilder wallFTTRTile = new MapTileBuilder(wallFTTRFrame);
                mapTiles.add(wallFTTRTile);

            //wallFloorPlain
                Frame wallFloorPlainFrame = new FrameBuilder(getSubImage(2,5)).withScale(tileScale).build();
                MapTileBuilder wallFloorPlainTile = new MapTileBuilder(wallFloorPlainFrame);
                mapTiles.add(wallFloorPlainTile);

            //wallFloorLowRightCorner
                Frame wallFLRCFrame = new FrameBuilder(getSubImage(2,6)).withScale(tileScale).build();
                MapTileBuilder wallFLRCTile = new MapTileBuilder(wallFLRCFrame);
                mapTiles.add(wallFLRCTile);

            //floorLeftWall1
                Frame floorLeftWall1Frame = new FrameBuilder(getSubImage(3,0)).withScale(tileScale).build();
                MapTileBuilder floorLeftWallTile1 = new MapTileBuilder(floorLeftWall1Frame);
                mapTiles.add(floorLeftWallTile1);
            
            //floorGeneric1
                Frame floorGeneric1Frame = new FrameBuilder(getSubImage(3,1)).withScale(tileScale).build();
                MapTileBuilder floorGeneric1Tile = new MapTileBuilder(floorGeneric1Frame);
                mapTiles.add(floorGeneric1Tile);

            //floorLeftCenterTrapdoor
                Frame floorLCTFrame = new FrameBuilder(getSubImage(3, 2)).withScale(tileScale).build();
                MapTileBuilder floorLCTTile = new MapTileBuilder(floorLCTFrame);
                mapTiles.add(floorLCTTile);

            //floorCenterTrapdoor
                Frame floorCTFrame = new FrameBuilder(getSubImage(3, 3)).withScale(tileScale).build();
                MapTileBuilder floorCTTile = new MapTileBuilder(floorCTFrame);
                mapTiles.add(floorCTTile);

            //floorCenterRightTrapdoor
                Frame floorCRTFrame = new FrameBuilder(getSubImage(3, 4)).withScale(tileScale).build();
                MapTileBuilder floorCRTTile = new MapTileBuilder(floorCRTFrame);
                mapTiles.add(floorCRTTile);

            //floorRightWall1
                Frame floorRightWallFrame = new FrameBuilder(getSubImage(3,6)).withScale(tileScale).build();
                MapTileBuilder floorRightWallTile = new MapTileBuilder(floorRightWallFrame);
                mapTiles.add(floorRightWallTile);
            //floorLeftWall2
                Frame floorLeftWall2Frame = new FrameBuilder(getSubImage(4,0)).withScale(tileScale).build();
                MapTileBuilder floorLeftWall2Tile = new MapTileBuilder(floorLeftWall2Frame);
                mapTiles.add(floorLeftWall2Tile);

            //floorGeneric2
                Frame floorGeneric2Frame = new FrameBuilder(getSubImage(4,1)).withScale(tileScale).build();
                MapTileBuilder floorGeneric2Tile = new MapTileBuilder(floorGeneric2Frame);
                mapTiles.add(floorGeneric2Tile);

            //floorWallBottomLeftCorner
                Frame floorWBLCFrame = new FrameBuilder(getSubImage(6,0)).withScale(tileScale).build();
                MapTileBuilder floorWBLCTile = new MapTileBuilder(floorWBLCFrame);
                mapTiles.add(floorWBLCTile);

            //floorWallBottom
                Frame floorWallBottomFrame = new FrameBuilder(getSubImage(6,1)).withScale(tileScale).build();
                MapTileBuilder floorWallBottomTile = new MapTileBuilder(floorWallBottomFrame);
                mapTiles.add(floorWallBottomTile);

            //floorTrapdoorBottomLeft
                Frame floorTBLFrame = new FrameBuilder(getSubImage(4,2)).withScale(tileScale).build();
                MapTileBuilder floorTBLTile = new MapTileBuilder(floorTBLFrame);
                mapTiles.add(floorTBLTile);

            //floorTrapdoorBottomCenter
                Frame floorTBCFrame = new FrameBuilder(getSubImage(4,3)).withScale(tileScale).build();
                MapTileBuilder floorTBCTile = new MapTileBuilder(floorTBCFrame);
                mapTiles.add(floorTBCTile);
            
            //floorTrapdoorBottomRight
                Frame floorTBRFrame = new FrameBuilder(getSubImage(4,4)).withScale(tileScale).build();
                MapTileBuilder floorTBRTile = new MapTileBuilder(floorTBRFrame);
                mapTiles.add(floorTBRTile);
            
            //DeskTopLeft
                Frame deskTopLeftFrame = new FrameBuilder(getSubImage(0, 7)).withScale(tileScale).build();
                MapTileBuilder deskTopLeftTile = new MapTileBuilder(deskTopLeftFrame);
                mapTiles.add(deskTopLeftTile);

            //DesktopCenter
                Frame deskTopCenterFrame = new FrameBuilder(getSubImage(0,8)).withScale(tileScale).build();
                MapTileBuilder deskTopCenterTile = new MapTileBuilder(deskTopCenterFrame);
                mapTiles.add(deskTopCenterTile);

            //DeskTopRight
                Frame deskTopRightFrame = new FrameBuilder(getSubImage(0,9)).withScale(tileScale).build();
                MapTileBuilder deskTopRighTile = new MapTileBuilder(deskTopRightFrame);
                mapTiles.add(deskTopRighTile);

            //deskTopBottomLeft
                Frame deskTopBLFrame = new FrameBuilder(getSubImage(1,7)).withScale(tileScale).build();
                MapTileBuilder deskTopBLTile = new MapTileBuilder(deskTopBLFrame);
                mapTiles.add(deskTopBLTile);

            //deskTopBottomCenter
                Frame deskTopBCFrame = new FrameBuilder(getSubImage(1,8)).withScale(tileScale).build();
                MapTileBuilder deskTopBCTile = new MapTileBuilder(deskTopBCFrame);
                mapTiles.add(deskTopBCTile);
            
            //deskTopBottomRight
                Frame deskTopBRFrame = new FrameBuilder(getSubImage(1,9)).withScale(tileScale).build();
                MapTileBuilder deskTopBRTile = new MapTileBuilder(deskTopBRFrame);
                mapTiles.add(deskTopBRTile);

            //chairLeft
                Frame chairLeftFrame = new FrameBuilder(getSubImage(2, 7)).withScale(tileScale).build();
                MapTileBuilder chairLeftTile = new MapTileBuilder(chairLeftFrame);
                mapTiles.add(chairLeftTile);

            //chairLeftBottom
                Frame chairLeftBottomFrame = new FrameBuilder(getSubImage(3,7)).withScale(tileScale).build();
                MapTileBuilder chairLeftBottomTile = new MapTileBuilder(chairLeftBottomFrame);
                mapTiles.add(chairLeftTile);

            //chairFront
                Frame chairFrontFrame = new FrameBuilder(getSubImage(2,8)).withScale(tileScale).build();
                MapTileBuilder chairFrontTile = new MapTileBuilder(chairFrontFrame);
                mapTiles.add(chairFrontTile);

            //chairFrontBottom
                Frame chairFrontBottomFrame = new FrameBuilder(getSubImage(3, 8)).withScale(tileScale).build();
                MapTileBuilder chairFrontBottomTile = new MapTileBuilder(chairFrontBottomFrame);
                mapTiles.add(chairFrontBottomTile);

            //doorTopLeft
                Frame doorTopLeftFrame = new FrameBuilder(getSubImage(2,9)).withScale(tileScale).build();
                MapTileBuilder doorTopLeftTile = new MapTileBuilder(doorTopLeftFrame);
                mapTiles.add(doorTopLeftTile);

            //doorTopRight
                Frame doorTopRightFrame = new FrameBuilder(getSubImage(2, 10)).withScale(tileScale).build();
                MapTileBuilder doorTopRightTile = new MapTileBuilder(doorTopRightFrame);
                mapTiles.add(doorTopRightTile);

            //doorBottomLeft
                Frame doorBottomLeftFrame = new FrameBuilder(getSubImage(3,9)).withScale(tileScale).build();
                MapTileBuilder doorBottomLeftTile = new MapTileBuilder(doorBottomLeftFrame);
                mapTiles.add(doorBottomLeftTile);

            //doorBottomRight
                Frame doorBottomRightFrame = new FrameBuilder(getSubImage(3,10)).withScale(tileScale).build();
                MapTileBuilder doorBottomRightTile = new MapTileBuilder(doorBottomRightFrame);
                mapTiles.add(doorBottomRightTile);

            //openDoorTL
                Frame openDoorTLFrame = new FrameBuilder(getSubImage(2,11)).withScale(tileScale).build();
                MapTileBuilder openDoorTLTile = new MapTileBuilder(openDoorTLFrame);
                mapTiles.add(openDoorTLTile);

            //openDoorBL
                Frame openDoorBLFrame = new FrameBuilder(getSubImage(3,11)).withScale(tileScale).build();
                MapTileBuilder openDoorBLTile = new MapTileBuilder(openDoorBLFrame);
                mapTiles.add(openDoorBLTile);

            //openDoorTR
                Frame openDoorTRFrame = new FrameBuilder(getSubImage(2,12)).withScale(tileScale).build();
                MapTileBuilder openDoorTRTile = new MapTileBuilder(openDoorTRFrame);
                mapTiles.add(openDoorTRTile);

            //openDoorBR
                Frame openDoorBRFrame = new FrameBuilder(getSubImage(3,12)).withScale(tileScale).build();
                MapTileBuilder openDoorBRTile = new MapTileBuilder(openDoorBRFrame);
                mapTiles.add(openDoorBRTile);

            //shelfTopLeft
                Frame shelfTopLeftFrame = new FrameBuilder(getSubImage(4,7)).withScale(tileScale).build();
                MapTileBuilder shelfTopLeftTile = new MapTileBuilder(shelfTopLeftFrame);
                mapTiles.add(shelfTopLeftTile);

            //shelfTopRight
                Frame shelfTopRightFrame = new FrameBuilder(getSubImage(4,8)).withScale(tileScale).build();
                MapTileBuilder shelfTopRightTile = new MapTileBuilder(shelfTopRightFrame);
                mapTiles.add(shelfTopRightTile);

            //shelfBottomLeft
                Frame shelfBLFrame = new FrameBuilder(getSubImage(5,7)).withScale(tileScale).build();
                MapTileBuilder shelfBLTile = new MapTileBuilder(shelfBLFrame);
                mapTiles.add(shelfBLTile);

            //shelfBottomRight
                Frame shelfBRFrame = new FrameBuilder(getSubImage(5,8)).withScale(tileScale).build();
                MapTileBuilder shelfBRTile = new MapTileBuilder(shelfBRFrame);
                mapTiles.add(shelfBRTile);

            //CarpetTopLeftCorner
                Frame carpetTLCFrame = new FrameBuilder(getSubImage(6, 7)).withScale(tileScale).build();
                MapTileBuilder carpetTLCTile = new MapTileBuilder(carpetTLCFrame);
                mapTiles.add(carpetTLCTile);

            //CarpetCenterLeft1
                Frame carpetCL1Frame = new FrameBuilder(getSubImage(7,7)).withScale(tileScale).build();
                MapTileBuilder carpetCL1Tile = new MapTileBuilder(carpetCL1Frame);
                mapTiles.add(carpetCL1Tile);
            
            //CarpetCenterLeft2
                Frame carpetCL2Frame = new FrameBuilder(getSubImage(8,7)).withScale(tileScale).build();
                MapTileBuilder carpetCL2Tile = new MapTileBuilder(carpetCL2Frame);
                mapTiles.add(carpetCL2Tile);

            //CarpetBottomLeftCorner
                Frame carpetBLCFrame = new FrameBuilder(getSubImage(9, 7)).withScale(tileScale).build();
                MapTileBuilder carpetBLCTile = new MapTileBuilder(carpetBLCFrame);
                mapTiles.add(carpetBLCTile);

            //CarpetTop1
                Frame carpetTop1Frame = new FrameBuilder(getSubImage(6,8)).withScale(tileScale).build();
                MapTileBuilder carpetTop1Tile = new MapTileBuilder(carpetTop1Frame);
                mapTiles.add(carpetTop1Tile);

            //Carpettop2
                Frame carpetTop2Frame = new FrameBuilder(getSubImage(6, 9)).withScale(tileScale).build();
                MapTileBuilder carpetTop2Tile = new MapTileBuilder(carpetTop2Frame);
                mapTiles.add(carpetTop2Tile);

            //CarpetTop3
                Frame carpetTop3Frame = new FrameBuilder(getSubImage(6,10)).withScale(tileScale).build();
                MapTileBuilder carpetTop3Tile = new MapTileBuilder(carpetTop3Frame);
                mapTiles.add(carpetTop3Tile);

            //CarpetTopRightCorner
                Frame carpetTRCFrame = new FrameBuilder(getSubImage(6,11)).withScale(tileScale).build();
                MapTileBuilder carpetTRCTile = new MapTileBuilder(carpetTRCFrame);
                mapTiles.add(carpetTRCTile);

            //CarpetCenterRight
                Frame carpetCRFrame = new FrameBuilder(getSubImage(7, 11)).withScale(tileScale).build();
                MapTileBuilder carpetCRTile = new MapTileBuilder(carpetCRFrame);
                mapTiles.add(carpetCRTile);

            //CarpetBottomRight
                Frame carpetBRFrame = new FrameBuilder(getSubImage(8,11)).withScale(tileScale).build();
                MapTileBuilder carpetBRTile = new MapTileBuilder(carpetBRFrame);
                mapTiles.add(carpetBRTile);

            //CarpetBottomRightCorner
                Frame carpetBRCFrame = new FrameBuilder(getSubImage(9,11)).withScale(tileScale).build();
                MapTileBuilder carpetBRCTIle = new MapTileBuilder(carpetBRCFrame);
                mapTiles.add(carpetBRCTIle);

            //carpetBottom1
                Frame carpetBottom1Frame = new FrameBuilder(getSubImage(9,8)).withScale(tileScale).build();
                MapTileBuilder carpetBottom1Tile = new MapTileBuilder(carpetBottom1Frame);
                mapTiles.add(carpetBottom1Tile);

            //CarpetBottom2
                Frame carpetBottom2Frame = new FrameBuilder(getSubImage(9,9)).withScale(tileScale).build();
                MapTileBuilder carpetBottom2Tile = new MapTileBuilder(carpetBottom2Frame);
                mapTiles.add(carpetBottom2Tile);
            
            //CarperBottom3
                Frame carpetBottom3Frame = new FrameBuilder(getSubImage(9,10)).withScale(tileScale).build();
                MapTileBuilder carpetBottom3Tile = new MapTileBuilder(carpetBottom3Frame);
                mapTiles.add(carpetBottom3Tile);

            //CarpetCenter1
                Frame carpetCenter1Frame = new FrameBuilder(getSubImage(7,8)).withScale(tileScale).build();
                MapTileBuilder carpetCenter1Tile = new MapTileBuilder(carpetCenter1Frame);
                mapTiles.add(carpetCenter1Tile);

            //CarpetCenter2
                Frame carpetCenter2Frame = new FrameBuilder(getSubImage(7,9)).withScale(tileScale).build();
                MapTileBuilder carpetCenter2Tile = new MapTileBuilder(carpetCenter2Frame);
                mapTiles.add(carpetCenter2Tile);

            //CarpetCenter3
                Frame carpetCenter3Frame = new FrameBuilder(getSubImage(7,10)).withScale(tileScale).build();
                MapTileBuilder carpetCenter3Tile = new MapTileBuilder(carpetCenter3Frame);
                mapTiles.add(carpetCenter3Tile);

            //CarpetCenter4
                Frame carpetCenter4Frame = new FrameBuilder(getSubImage(8,8)).withScale(tileScale).build();
                MapTileBuilder carpetCenter4Tile = new MapTileBuilder(carpetCenter4Frame);
                mapTiles.add(carpetCenter4Tile);

            //CarpetCenter5
                Frame carpetCenter5Frame = new FrameBuilder(getSubImage(8,9)).withScale(tileScale).build();
                MapTileBuilder carpetCenter5Tile = new MapTileBuilder(carpetCenter5Frame);
                mapTiles.add(carpetCenter5Tile);

            //CarpetCenter6
                Frame carpetCenter6Frame = new FrameBuilder(getSubImage(8,10)).withScale(tileScale).build();
                MapTileBuilder carpetCenter6Tile = new MapTileBuilder(carpetCenter6Frame);
                mapTiles.add(carpetCenter6Tile);

            //floorWallBottomRightCorner
                Frame floorWallBottomRightCornerFrame = new FrameBuilder(getSubImage(6,6)).withScale(tileScale).build();
                MapTileBuilder floorWallBRCTile = new MapTileBuilder(floorWallBottomRightCornerFrame);
                mapTiles.add(floorWallBRCTile);

            return mapTiles; 
        }

    }

