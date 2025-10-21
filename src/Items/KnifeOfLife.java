package Items;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
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

    protected Rectangle bounds;
    protected String name;
    protected int maxStack;
    protected Item item;
    public KnifeOfLife(Point location) {
        //this.animations.put('battle...')
        //add battle animation battlescreen

        super("knifeoflife", "Knife of Life", "desc", 2.0);

        name = "Knife of Life";
        maxStack = 1;
    }
}
