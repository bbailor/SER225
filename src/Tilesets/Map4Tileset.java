package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import Level.TileType;
import Level.Tileset;
import java.util.ArrayList;

public class Map4Tileset extends Tileset {

    public Map4Tileset() {
        super(ImageLoader.load("DepressionTileset.png"), 50, 50, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        int columns = 7; // confirmed correct
        int rows = 6;

        // 🌿 Animated grass (base)
        Frame[] grassFrames = new Frame[] {
            new FrameBuilder(getSubImage(0, 0), 150).withScale(tileScale).build(),
            new FrameBuilder(getSubImage(1, 0), 150).withScale(tileScale).build()
        };
        MapTileBuilder grassTile = new MapTileBuilder(grassFrames);
        mapTiles.add(grassTile);

        Frame grassBase = grassFrames[0];

        // 💧 Animated water (solid)
        Frame[] waterFrames = new Frame[] {
            new FrameBuilder(getSubImage(2, 0), 300).withScale(tileScale).build(),
            new FrameBuilder(getSubImage(3, 0), 300).withScale(tileScale).build(),
            new FrameBuilder(getSubImage(4, 0), 300).withScale(tileScale).build(),
            new FrameBuilder(getSubImage(5, 0), 300).withScale(tileScale).build()
        };
        mapTiles.add(new MapTileBuilder(waterFrames).withTileType(TileType.NOT_PASSABLE));

        // 🧩 Now automatically make every other tile appear over grass
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                // Skip grass and water tiles
                if (y == 0 && x <= 5) continue;

                try {
                    Frame tileFrame = new FrameBuilder(getSubImage(x, y))
                        .withScale(tileScale)
                        .build();

                    // Make the tile appear over grass
                    MapTileBuilder layeredTile = new MapTileBuilder(grassBase)
                        .withTopLayer(tileFrame)
                        .withTileType(TileType.PASSABLE);

                    mapTiles.add(layeredTile);
                } catch (Exception e) {
                    System.out.println("Skipped invalid tile (" + x + ", " + y + ")");
                }
            }
        }

        System.out.println("Depression tileset loaded successfully. Total tiles: " + mapTiles.size());
        return mapTiles;
    }
}
