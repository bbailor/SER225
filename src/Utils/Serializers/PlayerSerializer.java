package Utils.Serializers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Level.Item;
import Level.ItemStack;
import Level.Player;
import Level.Script;

public class PlayerSerializer implements JsonSerializer<Player>, JsonDeserializer<Player> {
    Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapter(Item.class, new ItemSerializer())
        .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
        .registerTypeAdapter(Script.class, new ScriptSerializer())
        .create();

    @Override
    public Player deserialize(JsonElement _json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        var json = _json.getAsJsonObject();
        try {
            var clazz = Class.forName(json.get("class").getAsString()).asSubclass(Player.class);
            var con = clazz.getDeclaredConstructor();
            con.setAccessible(true);
            var player = con.newInstance();
            for (var fieldName : json.keySet()) {
                if ( fieldName.equals("class")) {
                    continue;
                }
                Field field = null;
                Exception initalException = null;
                Class<?> testingClass = clazz;
                while (true) {
                    try {
                        field = testingClass.getDeclaredField(fieldName);
                        break;
                    } catch (NoSuchFieldException e) {
                        if (initalException == null) {
                            initalException = e;
                        }
                        testingClass = testingClass.getSuperclass();
                        if (testingClass.equals(Object.class)) {
                            initalException.printStackTrace();
                            break;
                        }
                    }
                }
                if (field == null) {
                    initalException.printStackTrace();
                    continue;
                }
                field.setAccessible(true);
                field.set(player, context.deserialize(json.get(fieldName), field.getType()));

            }
            return player;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = gson.toJsonTree(src).getAsJsonObject();
        obj.addProperty("class", src.getClass().getName());
        return obj;
    }
    
}
