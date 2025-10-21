package Utils.Serializers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import Level.Entity;
import Level.Player;

public class PlayerSerializer implements JsonSerializer<Player>, JsonDeserializer<Player> {

    @Override
    public Player deserialize(JsonElement _json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        var json = _json.getAsJsonObject();
        try {
            var clazz = Class.forName(json.get("class").getAsString()).asSubclass(Player.class);
            var con = clazz.getDeclaredConstructor();
            con.setAccessible(true);
            var player = con.newInstance();
            var anim_field = Entity.class.getDeclaredField("animations");
            var animations = player.getEntity().getAllAnimations();
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
            anim_field.setAccessible(true);
            anim_field.set(player.getEntity(), animations);
            return player;
        } catch (NoSuchFieldException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        Class<?> clazz = src.getClass();
        while (!clazz.equals(Object.class)) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(Expose.class) == null) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    obj.add(field.getName(), context.serialize(field.get(src), field.getType()));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        obj.addProperty("class", src.getClass().getName());
        obj.get("entity").getAsJsonObject().addProperty("currentWeapon", src.getEntity().getCurrentWeapon().id);
        return obj;
    }
    
}
