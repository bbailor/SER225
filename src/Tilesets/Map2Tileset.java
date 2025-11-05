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
        Frame rightDarkPillarFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withBounds(8, 0, 21, 30)
                .build();

        MapTileBuilder rightDarkPillarTile = new MapTileBuilder(baseFrame)
                .withTopLayer(rightDarkPillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightDarkPillarTile);

        Frame leftDarkPillarFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withBounds(8, 0, 21, 30)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder leftDarkPillarTile = new MapTileBuilder(baseFrame)
                .withTopLayer(leftDarkPillarFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftDarkPillarTile);
        
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
                new FrameBuilder(getSubImage(4, 0), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(4, 1), 65)
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
                new FrameBuilder(getSubImage(4, 0), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(4, 1), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
        };

        MapTileBuilder rightRiverTile = new MapTileBuilder(rightRiverFrames)
                .withTopLayer(rightRiverFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightRiverTile);

        // bridge over river
        Frame rightBridgeFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightBridgeTile = new MapTileBuilder(rightRiverFrames)
                .withTopLayer(rightBridgeFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightBridgeTile);

        Frame leftBridgeFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBridgeTile = new MapTileBuilder(leftRiverFrames)
                .withTopLayer(leftBridgeFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftBridgeTile);

        // cave
        Frame leftCaveFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftCaveTile = new MapTileBuilder(leftCaveFrame)
                .withTopLayer(leftCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftCaveTile);

        Frame rightCaveFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightCaveTile = new MapTileBuilder(rightCaveFrame)
                .withTopLayer(rightCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightCaveTile);

        // big cave
        Frame leftTopCaveFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftTopCaveTile = new MapTileBuilder(leftTopCaveFrame)
                .withTopLayer(leftTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftTopCaveTile);

        Frame middleTopCaveFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleTopCaveTile = new MapTileBuilder(middleTopCaveFrame)
                .withTopLayer(middleTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(middleTopCaveTile);

        Frame rightTopCaveFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightTopCaveTile = new MapTileBuilder(rightTopCaveFrame)
                .withTopLayer(rightTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightTopCaveTile);

        Frame leftMiddleCaveFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .withBounds(0, 0, 22, 32)
                .build();

        MapTileBuilder leftMiddleCaveTile = new MapTileBuilder(leftMiddleCaveFrame)
                .withTopLayer(leftMiddleCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftMiddleCaveTile);

        Frame middleMiddleCaveFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleMiddleCaveTile = new MapTileBuilder(middleMiddleCaveFrame)
                .withTopLayer(middleMiddleCaveFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleMiddleCaveTile);

        Frame rightMiddleCaveFrame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .withBounds(10, 0, 22, 32)
                .build();

        MapTileBuilder rightMiddleCaveTile = new MapTileBuilder(rightMiddleCaveFrame)
                .withTopLayer(rightMiddleCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightMiddleCaveTile);

        Frame leftBottomCaveFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .withBounds(0, 0, 24, 32)
                .build();

        MapTileBuilder leftBottomCaveTile = new MapTileBuilder(leftBottomCaveFrame)
                .withTopLayer(leftBottomCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftBottomCaveTile);

        Frame middleBottomCaveFrame = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBottomCaveTile = new MapTileBuilder(middleBottomCaveFrame)
                .withTopLayer(middleBottomCaveFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBottomCaveTile);

        Frame rightBottomCaveFrame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .withBounds(8, 0, 24, 32)
                .build();

        MapTileBuilder rightBottomCaveTile = new MapTileBuilder(rightBottomCaveFrame)
                .withTopLayer(rightBottomCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightBottomCaveTile);

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

        // Extra fire tiles
        MapTileBuilder fireLeftRiverEdgeTile = new MapTileBuilder(rightEdgeBaseFrame)
                .withTopLayer(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireLeftRiverEdgeTile);

        MapTileBuilder fireRightRiverEdgeTile = new MapTileBuilder(leftEdgeBaseFrame)
                .withTopLayer(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireRightRiverEdgeTile);

        MapTileBuilder fireLeftBottomCaveTile = new MapTileBuilder(leftBottomCaveFrame)
                .withTopLayer(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireLeftBottomCaveTile);

        MapTileBuilder fireRightBottomCaveTile = new MapTileBuilder(rightBottomCaveFrame)
                .withTopLayer(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireRightBottomCaveTile);

        return mapTiles;
    }
}
