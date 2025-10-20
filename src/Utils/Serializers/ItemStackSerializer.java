package Utils.Serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Level.Item;
import Level.ItemStack;

/**
 * This one only exists because gson always see's Itemstack.item as null 
 */
public class ItemStackSerializer implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement _json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        var json = _json.getAsJsonObject();
        return new ItemStack(Item.ItemList.getFromID(json.get("item").getAsString()), json.get("stackSize").getAsInt());
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        obj.addProperty("stackSize", src.getCount());
        obj.add("item", context.serialize(src.getItem(), Item.class));
        return obj;
    }
    
}
