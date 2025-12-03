package Level;

import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.annotations.Expose;

import org.jetbrains.annotations.Nullable;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Items.CloakOfConcealment;
import Items.DenialsStaff;
import Items.HealthPotion;
import Items.Apple;
import Items.BagOfGold;
import Items.Cherry;
import Items.KnifeOfLife;
import Items.PowerStone;
import Items.SwordOfRage;
import Items.TlalocsStorm;

/**
 * Definition for an item type
 */
public abstract class Item {
    
    @Expose public final String id;
    protected String name;
    protected String description = "";
    protected int maxStackSize = 25;
    protected java.util.Map<String, FrameBuilder[]> animations = new HashMap<>();

    protected Item(String name) {
        this(name.replace(' ', '_').toLowerCase(), name);
    }
    
    protected Item(String name, int maxStackSize) {
        this(name.replace(' ', '_'), name, maxStackSize);
    }

    protected Item(String id, String name) {
        this.id = id;
        this.name = name;
        if (ItemList.IDMap.containsKey(this.id)) {
            throw new RuntimeException("Duplicate ID found for items");
        }
        ItemList.IDMap.put(this.id, this);
    }
    
    protected Item(String id, String name, int maxStackSize) {
        this(id, name);
        this.maxStackSize = maxStackSize;
    }

    protected Item(String id, String name, String description, int maxStackSize) {
        this(id, name, maxStackSize);
        this.description = description;
    }

    public void use(ItemStack stack, Entity targetedEntity) {
        // Subclasses can use this for usable items
        System.out.println("Use from item class");
    }

    /**
     * 
     * @param stack stack that is being checked 
     * @param targetedEntity the entity that would use the item
     * @return if the item can be used
     */
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        // If the item to be able to be used
        return false;
    }

    public void addDefaultProperites(java.util.Map<String, Object> properties) {}
    
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    public java.util.Map<String, Frame[]> getFrames() {
        var map = new HashMap<String, Frame[]>();
        for (var entry : this.animations.entrySet()) {
            Frame[] values = new Frame[entry.getValue().length];
            Arrays.parallelSetAll(values, i -> entry.getValue()[i].build());
            map.put(entry.getKey(), values);
        }
        return map;
    }

    @SuppressWarnings("null")
    @Nullable
    public Frame[] getFrames(String name) {
        
        var array = !this.animations.containsKey(name) ? this.animations.get("default") : this.animations.get(name);
        if (array == null) {
            return null;
        }
        Frame[] frames = new Frame[array.length];
        Arrays.parallelSetAll(frames, i -> array[i].build());
        return frames;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item item) {
            return this.id == item.id;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void addAnimation(String name, FrameBuilder[] frames) {
        this.animations.put(name, frames);
    }

    public static class ItemList {
        protected static final java.util.Map<String, Item> IDMap = new HashMap<>();

        public static Item apple = new Apple();

        public static Item cloakOfConcealment = new CloakOfConcealment();

        public static Item cherry = new Cherry();
        
        public static Item powerStone = new PowerStone();
        
        public static Item health_potion = new HealthPotion();

        public static Weapon fist = new Weapon("fist", "Fist", "Your bare fist", 1d);

        public static Weapon knife_of_life = new KnifeOfLife();

        public static Weapon tlalocs_storm = new TlalocsStorm();
        
        public static Weapon sword_of_rage = new SwordOfRage();

        public static BagOfGold bag_of_gold = new BagOfGold();
        
        public static Weapon denials_staff = new DenialsStaff();

        //flowers
        public static Item flowers = new Item("flowers", "Flowers", "A bunch of flowers", 20) {

            {
                var worldSheet = new SpriteSheet(ImageLoader.load("item_imgs/Flowers.png"), 32, 32);
                FrameBuilder[] worldFrames = new FrameBuilder[] {
                    new FrameBuilder(worldSheet.getSprite(0, 0))
                        .withScale(1.6f)
                };
                addAnimation("world", worldFrames);
                FrameBuilder[] inventoryFrames = new FrameBuilder[] {
                    new FrameBuilder(worldSheet.getSprite(0, 0))
                };
                addAnimation("inventory", inventoryFrames);
            }
        };

        public static Item getFromID(String id) {
            return IDMap.get(id);
        }

        private ItemList() {}
    }
}

