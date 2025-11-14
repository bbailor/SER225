package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import Level.TileType;
import Level.Tileset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Map5Tileset extends Tileset {

    public Map5Tileset() {
        super(ImageLoader.load("AcceptanceTileset.png"), 50, 50, 2);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        int columns = 7;
        int rows = 6;

        // Grass animation
        Frame[] grassFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 0), 25).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(1, 0), 25).withScale(tileScale).build()
        };
        mapTiles.add(new MapTileBuilder(grassFrames));
        Frame grassBase = grassFrames[0];

        // Small water animation
        Frame[] waterFrames = new Frame[] {
                new FrameBuilder(getSubImage(6, 2), 100).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(0, 3), 100).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(1, 3), 100).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(2, 3), 100).withScale(tileScale).build()
        };
        mapTiles.add(
                new MapTileBuilder(grassBase)
                        .withTopLayer(waterFrames)
                        .withTileType(TileType.NOT_PASSABLE)
        );

        // Solid tiles (same as depression + gravestone)
        int[][] solidTiles = {
                {6,0}, {1,1}, {2,1}, {3,1}, {4,1},
                {3,4}, {3,5}, {4,4}, {4,5},
                {5,4}, {5,5}, {6,4}, {6,5},
                {0,4}, {1,4}, {2,4},
                {3,3}, {4,3}, {5,3}, {6,3},
                {1,5}       // ← gravestone (solid)
        };

        Set<String> solidSet = new HashSet<>();
        for (int[] s : solidTiles)
            solidSet.add(s[0] + "," + s[1]);

        // Top-layer tiles — same as Map4
        int[][] topLayerTiles = {
                {5,1}, {6,1},
                {0,2}, {1,2}, {2,2}, {3,2}, {4,2}, {5,2},
                {3,3}, {4,3}, {5,3}, {6,3}
        };

        Set<String> topLayerSet = new HashSet<>();
        for (int[] t : topLayerTiles)
            topLayerSet.add(t[0] + "," + t[1]);

        // Generate all static tiles
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                boolean isGrassFrame = (x == 0 && y == 0) || (x == 1 && y == 0);
                boolean isWaterFrame = (x == 6 && y == 2) || (x <= 2 && y == 3);
                if (isGrassFrame || isWaterFrame) continue;

                Frame tileFrame = new FrameBuilder(getSubImage(x, y))
                        .withScale(tileScale)
                        .build();

                TileType type = solidSet.contains(x + "," + y)
                        ? TileType.NOT_PASSABLE
                        : TileType.PASSABLE;

                // TOP layer tiles (same as Map4)
                if (topLayerSet.contains(x + "," + y)) {
                    mapTiles.add(
                            new MapTileBuilder(grassBase)
                                    .withTopLayer(tileFrame)
                                    .withTileType(type)
                    );
                }
                // EVERYTHING ELSE → Bottom layer (including gravestone at 1,5)
                else {
                    mapTiles.add(
                            new MapTileBuilder(grassBase)
                                    .withBottomLayer(tileFrame)
                                    .withTileType(type)
                    );
                }
            }
        }

        // Large pool animations
        Frame[] largePoolTopFrames = new Frame[] {
                new FrameBuilder(getSubImage(3, 4), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(4, 4), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(5, 4), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(6, 4), 120).withScale(tileScale).build()
        };
        mapTiles.add(new MapTileBuilder(grassBase)
                .withTopLayer(largePoolTopFrames)
                .withTileType(TileType.NOT_PASSABLE)
        );

        Frame[] largePoolBottomFrames = new Frame[] {
                new FrameBuilder(getSubImage(3, 5), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(4, 5), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(5, 5), 120).withScale(tileScale).build(),
                new FrameBuilder(getSubImage(6, 5), 120).withScale(tileScale).build()
        };
        mapTiles.add(new MapTileBuilder(grassBase)
                .withTopLayer(largePoolBottomFrames)
                .withTileType(TileType.NOT_PASSABLE)
        );

        System.out.println("✔ Acceptance tileset loaded. Total tiles: " + mapTiles.size());
        return mapTiles;
    }
}
