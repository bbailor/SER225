package Items;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Inventory;
import EnhancedMapTiles.CollectableItem;
import GameObject.Frame;
import GameObject.Rectangle;
import Level.Item;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.ItemStack;
import Level.Map;
import Level.Weapon;
import ScriptActions.TextboxScriptAction;
import Utils.Point;

public class KnifeOfLife extends Weapon {

    public KnifeOfLife() {
        //this.animations.put('battle...')
        //add battle animation battlescreen
        super("knifeoflife", "Knife of Life", "desc", 2.0);

        addAnimation("default", new FrameBuilder[] {
            new FrameBuilder(ImageLoader.load("weapons/knifeOfLife.png"))
                .withScale(2.0f)
                .withBounds(8, 0, 16, 32)
        });
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return true;
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        targetedEntity.getInventory().setStack(Inventory.NamedSlot.Weapon, stack.copy());
        super.use(stack, targetedEntity);
        stack.removeItem();
    }
}
