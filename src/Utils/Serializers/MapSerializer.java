package Utils.Serializers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import EnhancedMapTiles.CollectableItem;
import Level.EnhancedMapTile;
import Level.ItemStack;
import Level.Map;
import Level.MapEntity;
import Level.MapTile;
import Level.NPC;
import Level.Script;
import Level.Tileset;
import Level.Trigger;
import Utils.Point;

/**
     * @see Map#reset()
     * @see Map#tileset
     */
public class MapSerializer implements JsonDeserializer<Map>, JsonSerializer<Map> {

    @Override
    public JsonElement serialize(Map map, Type typeOfSrc, JsonSerializationContext context) {
        var obj = new JsonObject();
        obj.addProperty("tileset_class", map.getTileset().getClass().getTypeName());
        obj.addProperty("map_file_name", map.getMapFileName()); // Used so Map#reset works
        obj.add("active_script", context.serialize(map.getActiveScript(), Script.class));
        // Shouldn't need due to tileset being set in the root map
        // obj.addProperty("tileset_class", map.getTileset().getClass().getName());
        obj.addProperty("width", map.getWidth());
        obj.addProperty("height", map.getHeight());
        obj.add("start_pos", context.serialize(map.getPlayerStartPosition()));
        obj.add("triggers", context.serialize(map.getTriggers(), ArrayList.class));
        obj.addProperty("camera_x", map.getCamera().getX());
        obj.addProperty("camera_y", map.getCamera().getY());
        obj.addProperty("end_bound_x", map.getEndBoundX());
        obj.addProperty("end_bound_y", map.getEndBoundY());
        
        try {
            Field xMidPoint = Map.class.getDeclaredField("xMidPoint");
            Field yMidPoint = Map.class.getDeclaredField("yMidPoint");
            xMidPoint.setAccessible(true);
            yMidPoint.setAccessible(true);
            obj.addProperty("x_mid_point",(int) xMidPoint.get(map));
            obj.addProperty("y_mid_point",(int) yMidPoint.get(map));
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        var tiles = new JsonArray();
        for (var tile : map.getMapTiles()) {
            var tile_obj = new JsonObject();
            // a p i .... wait a second API
            tile_obj.addProperty("a", tile.isAnimated());
            tile_obj.add("p", context.serialize(tile.getLocation()));
            tile_obj.addProperty("i", tile.getTileIndex());
            tile_obj.add("script", context.serialize(tile.getInteractScript(), Script.class));
            tiles.add(tile_obj);
        }
        obj.add("tiles", tiles);

        var etiles = new JsonArray();
        for (var tile : map.getEnhancedMapTiles()) {
            var tile_obj = new JsonObject();
            
            tile_obj.addProperty("class", tile.getClass().getName());
            tile_obj.add("pos", context.serialize(tile.getLocation()));
            tile_obj.add("script", context.serialize(tile.getInteractScript(), Script.class));
            etiles.add(tile_obj);
        }
        obj.add("etiles", etiles);

        var collectables = new JsonArray();
        for (var collectable : map.getCollectableItems()) {
            var collectable_obj = new JsonObject();
            
            collectable_obj.addProperty("class", collectable.getClass().getName());
            collectable_obj.add("pos", context.serialize(collectable.getLocation()));
            collectable_obj.add("script", context.serialize(collectable.getInteractScript(), Script.class));
            collectable_obj.add("stack", context.serialize(collectable.getStack(), ItemStack.class));
            collectables.add(collectable_obj);
        }
        obj.add("collectables", collectables);

        try {
            var field =  Map.class.getDeclaredField("adjustCamera");
            field.setAccessible(true);
            obj.addProperty("adjust_camera", (boolean) field.get(map));
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        // obj.add("flag_manager", context.serialize(map.getFlagManager(), FlagManager.class));
        // obj.addProperty("tiles", );
        
        JsonArray npcArray = new JsonArray();
        for (NPC npc : map.getNPCs()) {
            var npc_obj = context.serialize(npc, MapEntity.class).getAsJsonObject();
            npc_obj.addProperty("class", npc.getClass().getName());
            npc_obj.addProperty("id", npc.getId());
            npc_obj.add("pos", context.serialize(npc.getLocation(), Point.class));
            npcArray.add(npc_obj);
        }
        obj.add("npcs", npcArray);

        return obj;
    }

    //TODO: Active Script
    @Override
    public Map deserialize(JsonElement _json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = _json.getAsJsonObject();
        try {

            var tileset = Class.forName(json.get("tileset_class").getAsString()).asSubclass(Tileset.class).getConstructor().newInstance();
            // Create a new empty map class
            var map_constructor = Map.class.getDeclaredConstructor(Tileset.class, String.class);
            map_constructor.setAccessible(true);
            Map map = map_constructor.newInstance(tileset, json.get("map_file_name").getAsString());

            map.setWidth(json.get("width").getAsInt());
            map.setHeight(json.get("height").getAsInt());
            map.setAdjustCamera(json.get("adjust_camera").getAsBoolean());
            map.setMapTiles(new MapTile[map.getWidth()*map.getHeight()]);
            // map.setFlagManager(context.deserialize(json.get("flag_manager"), FlagManager.class));

            try {
                Field startPos = Map.class.getDeclaredField("playerStartPosition");
                Field endBoundX = Map.class.getDeclaredField("endBoundX");
                Field endBoundY = Map.class.getDeclaredField("endBoundY");
                Field xMidPoint = Map.class.getDeclaredField("xMidPoint");
                Field yMidPoint = Map.class.getDeclaredField("yMidPoint");
                startPos.setAccessible(true);
                endBoundX.setAccessible(true);
                endBoundY.setAccessible(true);
                xMidPoint.setAccessible(true);
                yMidPoint.setAccessible(true);
                startPos.set(map, context.deserialize(json.get("start_pos"), Point.class));
                xMidPoint.set(map, json.get("x_mid_point").getAsInt());
                yMidPoint.set(map,json.get("y_mid_point").getAsInt() );
                endBoundX.set(map, json.get("end_bound_x").getAsInt());
                endBoundY.set(map,json.get("end_bound_y").getAsInt() );
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            
            /* try {
                var animatedMapTiles = Map.class.getDeclaredField("animated_map_tiles");
                var enhancedMapTiles = Map.class.getDeclaredField("enhanced_map_tiles");
                var triggers = Map.class.getDeclaredField("triggers");
                var npcs = Map.class.getDeclaredField("npcs");
                animatedMapTiles.setAccessible(true);
                enhancedMapTiles.setAccessible(true);
                npcs.setAccessible(true);
                triggers.setAccessible(true);
                animatedMapTiles.set(map, new ArrayList<>());
                enhancedMapTiles.set(map, new ArrayList<>());
                npcs.set(map, new ArrayList<>());
                triggers.set(map, new ArrayList<>());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } */

            for (var _tile_json : json.get("tiles").getAsJsonArray()) {
                var tile_json = _tile_json.getAsJsonObject();
                var location = context.<Point>deserialize(tile_json.get("p"), Point.class);
                var tile = map.getTileset().getTile(tile_json.get("i").getAsInt()).build(location);
                // tile.setMap(map);
                if (!tile_json.get("script").isJsonNull()) {
                    tile.setInteractScript(context.deserialize(tile_json.get("script"), Script.class));
                }
                map.setMapTile((int)location.x / tileset.getScaledSpriteWidth(), (int)location.y / tileset.getScaledSpriteHeight(), tile);
                if (tile_json.get("a").getAsBoolean()) {
                    map.getAnimatedMapTiles().add(tile);
                }
            }

            for (var _tile_json : json.get("etiles").getAsJsonArray()) {
                var tile_json = _tile_json.getAsJsonObject();
                var tile = Class.forName(tile_json.get("class").getAsString())
                    .asSubclass(EnhancedMapTile.class)
                    .getConstructor(Point.class)
                    .newInstance(context.<Point>deserialize(tile_json.get("pos"), Point.class));
                if (!tile_json.get("script").isJsonNull()) {
                    tile.setInteractScript(context.deserialize(tile_json.get("script"), Script.class));
                }
                map.addEnhancedMapTile(tile);
            }

            for (var _collectable_json : json.get("collectables").getAsJsonArray()) {
                var collectable_json = _collectable_json.getAsJsonObject();
                var collectable = Class.forName(collectable_json.get("class").getAsString())
                    .asSubclass(CollectableItem.class)
                    .getConstructor(Point.class, ItemStack.class)
                    .newInstance(context.<Point>deserialize(collectable_json.get("pos"), Point.class), context.<ItemStack>deserialize(collectable_json.get("stack"), ItemStack.class));
                if (!collectable_json.get("script").isJsonNull()) {
                    collectable.setInteractScript(context.deserialize(collectable_json.get("script"), Script.class));
                }
                map.addCollectableItem(
                    collectable
                );
            }

            for (var trigger : context.<ArrayList<Trigger>>deserialize(json.get("triggers"), new TypeToken<ArrayList<Trigger>>(){}.getType())) {
                map.addTrigger(trigger);
            }

            for (var _npc : json.get("npcs").getAsJsonArray()) {
                var npc_json = _npc.getAsJsonObject();
                var clazz = Class.forName(npc_json.get("class").getAsString()).asSubclass(NPC.class);
                var npc = clazz.getDeclaredConstructor(int.class, Point.class).newInstance(npc_json.get("id").getAsInt(), context.deserialize(npc_json.get("pos"), Point.class));
                npc.setInteractScript(context.deserialize(npc_json.get("interactScript"), Script.class));
                // npc.setIsUpdateOffScreen(npc_json.get("isUpdateOffScreen").getAsBoolean());
                // npc.setIsHidden(npc_json.get("isHidden").getAsBoolean());
                // npc.setMapEntityStatus(context.deserialize(npc_json.get("mapEntityStatus"), MapEntityStatus.class));
                // npc.setExistenceFlag(npc_json.get("existenceFlag").getAsString());
                // npc.setIsUncollidable(npc_json.get("isUncollidable").getAsBoolean());
                for (var fieldName : npc_json.keySet()) {
                    if (fieldName.equals("interactScript") || fieldName.equals("class") || fieldName.equals("pos")) {
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
                    field.set(npc, context.deserialize(npc_json.get(fieldName), field.getType()));

                }
                
                map.addNPC(npc);
                // npc.setMap(map);
            }

            map.setupMapWithoutFile(json.get("camera_x").getAsInt(), json.get("camera_y").getAsInt());
            return map;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        // return context.deserialize(json, typeOfT);
    }
    
}
