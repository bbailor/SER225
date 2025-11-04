package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;

// Using tileset from "AngerTileSet.png"
public class Map2Tileset extends Tileset {

    public Map2Tileset() {
        super(ImageLoader.load("AngerTileset.png"), 32, 32, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // base
        Frame baseFrame = new FrameBuilder(getSubImage(0, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder baseTile = new MapTileBuilder(baseFrame);

        mapTiles.add(baseTile);

        // fossils
        Frame leftFossilsFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftFossilsTile = new MapTileBuilder(leftFossilsFrame);

        mapTiles.add(leftFossilsTile);

        Frame rightFossilsFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightFossilsTile = new MapTileBuilder(rightFossilsFrame);

        mapTiles.add(rightFossilsTile);

        // base against river
        Frame rightEdgeBaseFrame = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightEdgeBaseTile = new MapTileBuilder(rightEdgeBaseFrame);

        mapTiles.add(rightEdgeBaseTile);

        Frame leftEdgeBaseFrame = new FrameBuilder(getSubImage(0, 4))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder leftEdgeBaseTile = new MapTileBuilder(leftEdgeBaseFrame);

        mapTiles.add(leftEdgeBaseTile);

        // sign
        Frame signFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .withBounds(1, 2, 14, 14)
                .build();

        MapTileBuilder signTile = new MapTileBuilder(signFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(signTile);

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

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(baseFrame)
                .withTopLayer(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // Fat pillar
        Frame pillarFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillarTile = new MapTileBuilder(baseFrame)
                .withTopLayer(pillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillarTile);

        Frame pillarTopFrame = new FrameBuilder(getSubImage(0, 5))
                .withScale(tileScale)
                .withBounds(8, 13, 18, 13)
                .build();

        MapTileBuilder pillarTopTile = new MapTileBuilder(baseFrame)
                .withTopLayer(pillarTopFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(pillarTopTile);

        // Skinny dark pillar
        Frame rightDarkPillarFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .withBounds(8, 0, 21, 30)
                .build();

        MapTileBuilder rightDarkPillarTile = new MapTileBuilder(baseFrame)
                .withTopLayer(rightDarkPillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightDarkPillarTile);

         Frame leftDarkPillarFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .withBounds(8, 0, 21, 30)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder leftDarkPillarTile = new MapTileBuilder(baseFrame)
                .withTopLayer(leftDarkPillarFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftDarkPillarTile);
        
        // // tree trunk
        // Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder treeTrunkTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(treeTrunkFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(treeTrunkTile);

        // // tree top leaves
        // Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder treeTopLeavesTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(treeTopLeavesFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(treeTopLeavesTile);
        
        // left edge river
        Frame[] leftRiverFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 0), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 1), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 3), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 4), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 0), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 1), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 3), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 4), 65)
                    .withScale(tileScale)
                    .build(),
        };

        MapTileBuilder leftRiverTile = new MapTileBuilder(leftRiverFrames)
                .withTopLayer(leftRiverFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftRiverTile);

        // right edge river
        Frame[] rightRiverFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 0), 65)
                    .withScale(tileScale)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(1, 1), 65)
                    .withScale(tileScale)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(1, 2), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 3), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(1, 4), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 0), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 1), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 2), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 3), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(2, 4), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
        };

        MapTileBuilder rightRiverTile = new MapTileBuilder(rightRiverFrames)
                .withTopLayer(rightRiverFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightRiverTile);

        // fire on base
        Frame[] fireFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 0), 65)
                    .withScale(tileScale)
                    .withBounds(0, 0, 32, 25)
                    .build(),
                new FrameBuilder(getSubImage(1, 0), 65)
                    .withScale(tileScale)
                    .withBounds(0, 0, 32, 25)
                    .build(),
                new FrameBuilder(getSubImage(2, 0), 65)
                    .withScale(tileScale)
                    .withBounds(0, 0, 32, 25)
                    .build(),
        };

        MapTileBuilder fireTile = new MapTileBuilder(baseFrame)
                .withTopLayer(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireTile);

        // // purple flower
        // Frame[] purpleFlowerFrames = new Frame[] {
        //         new FrameBuilder(getSubImage(0, 2), 65)
        //                 .withScale(tileScale)
        //                 .build(),
        //         new FrameBuilder(getSubImage(0, 3), 65)
        //                 .withScale(tileScale)
        //                 .build(),
        //         new FrameBuilder(getSubImage(0, 2), 65)
        //                 .withScale(tileScale)
        //                 .build(),
        //         new FrameBuilder(getSubImage(0, 4), 65)
        //                 .withScale(tileScale)
        //                 .build()
        // };

        // MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        // mapTiles.add(purpleFlowerTile);



        // // middle branch
        // Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
        //         .withScale(tileScale)
        //         .withBounds(0, 6, 16, 4)
        //         .build();

        // MapTileBuilder middleBranchTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(middleBranchFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(middleBranchTile);

        // // tree trunk bottom
        // Frame treeTrunkBottomFrame = new FrameBuilder(getSubImage(2, 0))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder treeTrunkBottomTile = new MapTileBuilder(treeTrunkBottomFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(treeTrunkBottomTile);

        // // mushrooms
        // Frame mushroomFrame = new FrameBuilder(getSubImage(2, 1))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder mushroomTile = new MapTileBuilder(mushroomFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(mushroomTile);


        // // grey rock
        // Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(greyRockTile);

        // // bush
        // Frame bushFrame = new FrameBuilder(getSubImage(3, 3))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder bushTile = new MapTileBuilder(bushFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(bushTile);

        // // house body
        // Frame houseBodyFrame = new FrameBuilder(getSubImage(3, 4))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder houseBodyTile = new MapTileBuilder(houseBodyFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(houseBodyTile);

        // // house roof body
        // Frame houseRoofBodyFrame = new FrameBuilder(getSubImage(4, 0))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder houseRoofBodyTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(houseRoofBodyFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(houseRoofBodyTile);

        // // left house roof
        // Frame leftHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder leftHouseRoofTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(leftHouseRoofFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(leftHouseRoofTile);

        // // right house roof
        // Frame rightHouseRoofFrame = new FrameBuilder(getSubImage(4, 1))
        //         .withScale(tileScale)
        //         .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        //         .build();

        // MapTileBuilder rightHouseRoofTile = new MapTileBuilder(baseFrame)
        //         .withTopLayer(rightHouseRoofFrame)
        //         .withTileType(TileType.PASSABLE);

        // mapTiles.add(rightHouseRoofTile);

        // // left window
        // Frame leftWindowFrame = new FrameBuilder(getSubImage(4, 2))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder leftWindowTile = new MapTileBuilder(leftWindowFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(leftWindowTile);

        // // right window
        // Frame rightWindowFrame = new FrameBuilder(getSubImage(4, 2))
        //         .withScale(tileScale)
        //         .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
        //         .build();

        // MapTileBuilder rightWindowTile = new MapTileBuilder(rightWindowFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(rightWindowTile);

        // // door
        // Frame doorFrame = new FrameBuilder(getSubImage(4, 3))
        //         .withScale(tileScale)
        //         .build();

        // MapTileBuilder doorTile = new MapTileBuilder(doorFrame)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(doorTile);

        // // top water
        // Frame[] topWaterFrames = new Frame[] {
        //     new FrameBuilder(getSubImage(5, 0), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 1), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 2), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 1), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 0), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 3), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 4), 65)
        //             .withScale(tileScale)
        //             .build(),
        //     new FrameBuilder(getSubImage(5, 3), 65)
        //             .withScale(tileScale)
        //             .build()
        // };

        // MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrames)
        //         .withTileType(TileType.NOT_PASSABLE);

        // mapTiles.add(topWaterTile);


        return mapTiles;
    }
}
