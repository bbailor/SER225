package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import GameObject.SpriteSheetHorizontal;
import GameObject.ImageEffect;
import Level.NPC;
import Utils.Point;

import java.util.HashMap;


public class underworldSpirit extends NPC {

    protected String enemyType;

    public underworldSpirit(int id, Point location) {
        super(
            id,
            location.x,
            location.y,
            new SpriteSheetHorizontal(ImageLoader.load("UnderworldSpirit.png"), 32, 32),
            "STAND_RIGHT"
        );

        // Configure stats
        this.entity.setMaxHealth(12);
        this.entity.setBaseAttack(0);

        //add attacks
        this.addAttack("FireOrb", 60, "AngerSpiritAttack", 3.5);
//        this.addAttack("Explosion", 8, "DenialBossAttack", 8.0);

        enemyType = "An AngerSpirit";
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(GameObject.SpriteSheet spriteSheet) {
        Frame standRight = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withBounds(8, 8, 16, 24)
                .build();

        Frame standLeft = new FrameBuilder(spriteSheet.getSprite(0, 0), 9999)
                .withScale(3)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(8, 8, 16, 24)
                .build();

        HashMap<String, Frame[]> map = new HashMap<>();
        map.put("STAND_RIGHT", new Frame[] { standRight });
        map.put("STAND_LEFT",  new Frame[] { standLeft  });
        map.put("idle",        new Frame[] { standRight }); // Added idle animation
        return map;
    }

    public String getEnemyType() {
        return enemyType;
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
