package Items;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import EnhancedMapTiles.CollectableItem;
import EnhancedMapTiles.CollectableItems;
import GameObject.Frame;
import GameObject.Rectangle;
import Level.Item;
import GameObject.SpriteSheet;
import Level.Entity;
import Level.ItemStack;
import Level.Map;
import Level.Weapon;
import ScriptActions.TextboxScriptAction;
import Scripts.Interactable;
import Utils.Point;

public class KnifeOfLife extends CollectableItem {

    protected Rectangle bounds;
    protected String name;
    protected int maxStack;
    protected Item item;
    public KnifeOfLife(Point location) {
        //super(location.x, location.y, Item.ItemList.knife_of_life);
        super(location.x, location.y, Item.ItemList.knife_of_life);

        name = "Knife of Life";
        maxStack = 1;
        // this.setItem();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }

    public String getName()
    {
        return name;
    }

    // public void setItem() {
    //     item = new Weapon("knifeOfLife", "Knife of Life", "Trusty knife, gifted to you by Osiris.", 2.0) {

    //         @Override
    //         public boolean canUse(ItemStack stack, Entity targetedEntity) {
    //             return true;
    //         }

    //         @Override
    //         public void use(ItemStack stack, Entity targetedEntity) {
    //             System.out.println("Knife Equipped!");
    //             targetedEntity.setCurrentWeapon((Weapon)(item));
    //         }
    //     };

    //     // SpriteSheet sheet = new SpriteSheet(ImageLoader.load("weapons/knifeOfLife.png"), 32, 32);
    //     // Frame[] invFrames = new Frame[] {
    //     //     new FrameBuilder(sheet.getSprite(0, 0)).withScale(2.0f).build()
    //     // };
    //     // item.addAnimation("inventory", invFrames);

    //     // super.setItem(item);
    // }    
}
