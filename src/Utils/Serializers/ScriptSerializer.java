package Utils.Serializers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Level.Script;

public class ScriptSerializer implements JsonDeserializer<Script>, JsonSerializer<Script> {

    @Override
    public JsonElement serialize(Script src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getClass().getTypeName());
    }

    @Override
    public Script deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return (Script) Class.forName(json.getAsString()).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
