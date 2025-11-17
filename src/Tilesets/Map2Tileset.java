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
        super(ImageLoader.load("AngerTileset.png"), 32, 32, 2);
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
                .withBounds(3, 8, 26, 24)
                .build();

        MapTileBuilder pillarTile = new MapTileBuilder(pillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillarTile);

        // Skinny dark pillar
        Frame rightDarkPillarFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withBounds(5, 6, 21, 26)
                .build();

        MapTileBuilder rightDarkPillarTile = new MapTileBuilder(rightDarkPillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightDarkPillarTile);

        Frame leftDarkPillarFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .withBounds(5, 6, 21, 26)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder leftDarkPillarTile = new MapTileBuilder(leftDarkPillarFrame)
                .withTileType(TileType.NOT_PASSABLE);

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
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightRiverTile);

        // bridge over river
        Frame[] rightBridgeFrames = new Frame[] {
                new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(5, 2), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 3), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 4), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 0), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 1), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 2), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 3), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 4), 65)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withScale(tileScale)
                    .build(),
        };

        MapTileBuilder rightBridgeTile = new MapTileBuilder(rightBridgeFrames)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightBridgeTile);

        Frame[] leftBridgeFrames = new Frame[] {
                new FrameBuilder(getSubImage(5, 0), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 1), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 3), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(5, 4), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 0), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 1), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 2), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 3), 65)
                    .withScale(tileScale)
                    .build(),
                new FrameBuilder(getSubImage(6, 4), 65)
                    .withScale(tileScale)
                    .build(),
        };

        MapTileBuilder leftBridgeTile = new MapTileBuilder(leftBridgeFrames)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftBridgeTile);

        // cave
        Frame leftCaveFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftCaveTile = new MapTileBuilder(leftCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftCaveTile);

        Frame rightCaveFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightCaveTile = new MapTileBuilder(rightCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightCaveTile);

        // big cave
        Frame leftTopCaveFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftTopCaveTile = new MapTileBuilder(leftTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftTopCaveTile);

        Frame middleTopCaveFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleTopCaveTile = new MapTileBuilder(middleTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(middleTopCaveTile);

        Frame rightTopCaveFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightTopCaveTile = new MapTileBuilder(rightTopCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightTopCaveTile);

        Frame leftMiddleCaveFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .withBounds(0, 0, 22, 32)
                .build();

        MapTileBuilder leftMiddleCaveTile = new MapTileBuilder(leftMiddleCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftMiddleCaveTile);

        Frame middleMiddleCaveFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleMiddleCaveTile = new MapTileBuilder(middleMiddleCaveFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleMiddleCaveTile);

        Frame rightMiddleCaveFrame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .withBounds(10, 0, 22, 32)
                .build();

        MapTileBuilder rightMiddleCaveTile = new MapTileBuilder(rightMiddleCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightMiddleCaveTile);

        Frame leftBottomCaveFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .withBounds(0, 0, 24, 32)
                .build();

        MapTileBuilder leftBottomCaveTile = new MapTileBuilder(leftBottomCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(leftBottomCaveTile);

        Frame middleBottomCaveFrame = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBottomCaveTile = new MapTileBuilder(middleBottomCaveFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBottomCaveTile);

        Frame rightBottomCaveFrame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .withBounds(8, 0, 24, 32)
                .build();

        MapTileBuilder rightBottomCaveTile = new MapTileBuilder(rightBottomCaveFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(rightBottomCaveTile);

        // fire on base
        Frame[] fireFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 0), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
                new FrameBuilder(getSubImage(0, 1), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
                new FrameBuilder(getSubImage(0, 2), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
        };

        MapTileBuilder fireTile = new MapTileBuilder(fireFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireTile);

        // Extra fire tiles
        Frame[] fireLeftEdgeFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 5), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
                new FrameBuilder(getSubImage(3, 1), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
                new FrameBuilder(getSubImage(5, 5), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .build(),
        };
        MapTileBuilder fireLeftRiverEdgeTile = new MapTileBuilder(fireLeftEdgeFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireLeftRiverEdgeTile);

        Frame[] fireRightEdgeFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 5), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(3, 1), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
                new FrameBuilder(getSubImage(5, 5), 65)
                    .withScale(tileScale)
                    .withBounds(0, 12, 32, 20)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build(),
        };
        MapTileBuilder fireRightRiverEdgeTile = new MapTileBuilder(fireRightEdgeFrames)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(fireRightRiverEdgeTile);


        return mapTiles;
    }
}
