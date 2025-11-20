package FightAnimations;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Item;
import Utils.Point;

public class BagOfGoldAttackAnimation extends PlayerProjectileAttackAnimation {

    protected static int radius = 40;
    public BagOfGoldAttackAnimation(float startX, float startY, float targetX, float targetY) {
        super(Item.ItemList.bag_of_gold.sheet, startX, startY, targetX, targetY, 75, "ATTACK");
    }

    float offset = 0.2f;
    @Override
    protected void updatePosition(float progress) {
        float i = 1 - progress;
        var b_points = getBezierPoints();
        
        // https://en.wikipedia.org/wiki/B%C3%A9zier_curve (as linear interpolant)
        setLocation(
            (
                i * i * startX +
                2 * i * progress * b_points[0].x +
                progress * progress * targetX
            ),
            (
                i * i * startX + 
                2 * i * progress * b_points[0].y +
                progress * progress * targetY
            ));
        if (progress == 1) {
            this.onComplete();
        }
    }

    protected Point[] getBezierPoints() {
        float dx = targetX - startX;
        float dy = targetY - startY;
        float cx1 = (startX + dx) * .75f;
        float cy1 = -(startY + dy) / 16 + 40;
        float cx2 = targetX;
        float cy2 = targetY;
        System.out.println(cy1);
        System.out.println(cy2);

        return new Point[] {new Point(cx1, cy1), new Point(cx2, cy2)};
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return (HashMap<String, Frame[]>)Item.ItemList.bag_of_gold.getFrames();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangle((int) startX, (int )startY, 10, 10, Color.DARK_GRAY);
        graphicsHandler.drawFilledRectangle((int) targetX, (int) targetY, 10, 10, Color.DARK_GRAY);
        var b_points = getBezierPoints();
        graphicsHandler.drawFilledRectangle((int) b_points[0].x, (int) b_points[0].y, 10, 10, Color.DARK_GRAY);
        graphicsHandler.drawFilledRectangle((int) b_points[1].x, (int) b_points[1].y, 10, 10, Color.DARK_GRAY);
        var bag = this.animations.get("default");
        var frame = bag[this.currentFrameIndex % bag.length];
        frame.setLocation(this.startX, this.startY);
        frame.draw(graphicsHandler);
    }
}
