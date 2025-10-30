package Players;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;


// This is the class for the Gnome player character
// basically just sets some values for physics and then defines animations
public class Gnome extends Player {

    private static int boundY = 21;
    private static int boundX = 4;
    private static int boundWidth = 23;
    private static int boundHeight = 37;


    public Gnome(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("gnome.png"), 31, 63), x, y, "STAND_RIGHT");
        walkSpeed = 2.3f;
    }

    private Gnome() {
        this(0, 0);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(2)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(2)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(2)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(2)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(2)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(boundX, boundY, boundWidth, boundHeight)
                            .build()
            });
        }};
    }
}
