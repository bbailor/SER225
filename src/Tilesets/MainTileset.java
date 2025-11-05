package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class MainTileset extends Tileset {

    public MainTileset() {
        super(ImageLoader.load("MainTileset.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // snow
        Frame snowFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder snowTile = new MapTileBuilder(snowFrame);

        mapTiles.add(snowTile);

        // path
        Frame pathFrame = new FrameBuilder(getSubImage(5, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder pathTile = new MapTileBuilder(pathFrame);

        mapTiles.add(pathTile);

        // sign
        Frame signFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .withBounds(1, 2, 14, 14)
                .build();

        MapTileBuilder signTile = new MapTileBuilder(signFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(signTile);

        // cold plant
        Frame plantFrame = new FrameBuilder(getSubImage(2, 1))
                .withScale(tileScale)
                .withBounds(7, 7, 9, 9)
                .build();

        MapTileBuilder plantTile = new MapTileBuilder(plantFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(plantTile);

        // sand
        Frame sandFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder sandTile = new MapTileBuilder(sandFrame);

        mapTiles.add(sandTile);

        // rock
        Frame rockFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder rockTile = new MapTileBuilder(rockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rockTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(snowFrame)
                .withTopLayer(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(snowFrame)
                .withTopLayer(leftEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(snowFrame)
                .withTopLayer(rightEndBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightEndBranchTile);
        
        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(snowFrame)
                .withTopLayer(treeTrunkFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkTile);

        // tree top leaves
        Frame treeTopUpFrame = new FrameBuilder(getSubImage(5, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopUpTile = new MapTileBuilder(snowFrame)
                .withTopLayer(treeTopUpFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTopUpTile);

        Frame treeTopToLeftFrame = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopToLeftTile = new MapTileBuilder(snowFrame)
                .withTopLayer(treeTopToLeftFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTopToLeftTile);
        
        Frame treeTopToRightFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopToRightTile = new MapTileBuilder(snowFrame)
                .withTopLayer(treeTopToRightFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTopToRightTile);
        
        // wilted purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 4), 65)
                        .withScale(tileScale)
                        .build(),
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // berry sapling
        Frame berrySaplingFrame = new FrameBuilder(getSubImage(1, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder berrySaplingTile = new MapTileBuilder(berrySaplingFrame)
                .withTopLayer(treeTopToRightFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(berrySaplingTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(snowFrame)
                .withTopLayer(middleBranchFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBranchTile);

        // tree trunk bottom
        Frame treeTrunkBottomFrame = new FrameBuilder(getSubImage(2, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkBottomTile = new MapTileBuilder(treeTrunkBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkBottomTile);

        // stump
        Frame stumpFrame = new FrameBuilder(getSubImage(1, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder stumpTile = new MapTileBuilder(stumpFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stumpTile);

        // fallen log
        Frame fallenLogFrame = new FrameBuilder(getSubImage(1, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder fallenLogTile = new MapTileBuilder(fallenLogFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fallenLogTile);


        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(greyRockTile);

        // bush
        Frame bushFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder bushTile = new MapTileBuilder(bushFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bushTile);

        // house body
        Frame houseBodyFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseBodyTile = new MapTileBuilder(houseBodyFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(houseBodyTile);

        // house roof body
        Frame houseRoofBodyFrame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseRoofBodyTile = new MapTileBuilder(houseRoofBodyFrame)
                .withTopLayer(houseRoofBodyFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseRoofBodyTile);

        // left house roof
        Frame leftHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftHouseRoofTile = new MapTileBuilder(leftHouseRoofFrame)
                .withTopLayer(leftHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftHouseRoofTile);

        // right house roof
        Frame rightHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightHouseRoofTile = new MapTileBuilder(rightHouseRoofFrame)
                .withTopLayer(rightHouseRoofFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightHouseRoofTile);

        // left window
        Frame leftWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftWindowTile = new MapTileBuilder(leftWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftWindowTile);

        // right window
        Frame rightWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightWindowTile = new MapTileBuilder(rightWindowFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightWindowTile);

        // door
        Frame doorFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorTile = new MapTileBuilder(doorFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(doorTile);

        // top water
        Frame[] topWaterFrames = new Frame[] {
            new FrameBuilder(getSubImage(5, 3), 200)
                    .withScale(tileScale)
                    .build(),
            new FrameBuilder(getSubImage(5, 4), 200)
                    .withScale(tileScale)
                    .build()
        };

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(topWaterTile);


        return mapTiles;
    }
}
