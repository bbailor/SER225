package Utils;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import javax.swing.filechooser.FileSystemView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import Engine.Inventory;
import Level.Item;
import Level.ItemStack;
import Level.Map;
import Level.Player;
import Level.Script;
import Utils.Serializers.ItemSerializer;
import Utils.Serializers.ItemStackSerializer;
import Utils.Serializers.MapSerializer;
import Utils.Serializers.PlayerSerializer;
import Utils.Serializers.ScriptSerializer;

public abstract class Globals {
    
    /** Base hover color for ui's */
    public static final Color HOVER_COLOR = TailwindColorScheme.amber600;
    /** Base unfocused hover color for ui's */
    public static final Color UNFOCUS_HOVER_COLOR = TailwindColorScheme.slate400;
    /** Base select color for ui's */
    public static final Color SELECT_COLOR = TailwindColorScheme.sky400;
    /** UI interact cooldowns */
    public static final int KEYBOARD_CD = 12;
    /** The Gson serializer */
    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Item.class, new ItemSerializer())
        .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
        .registerTypeAdapter(Script.class, new ScriptSerializer())
        .registerTypeAdapter(Map.class, new MapSerializer())
        .registerTypeAdapter(Player.class, new PlayerSerializer())
        .excludeFieldsWithoutExposeAnnotation()
        .serializeNulls()
        .disableJdkUnsafe()
        // .serializeNulls()
        .setPrettyPrinting()
        .create();
    /** Path for any game data that is creating at runtime */
    public static final Path GAMEDATAPATH = FileSystemView.getFileSystemView().getDefaultDirectory().toPath().resolve(".gnomeo");
    /** Save path */
    public static final Path SAVEPATH = GAMEDATAPATH.resolve("saves");
    public static final boolean DEBUG = true;

    public static boolean saveToFile(SaveData data, int index) {
        String json = GSON.toJson(data);
        Path file = SAVEPATH.resolve("save" + index + ".json");
        // if (index >= Globals.saves.size()) {
        //     var _saves = new ArrayList<Path>(index+1);
        //     _saves.addAll(Globals.saves);
        //     Globals.saves = _saves;
        // }
        try {
            Files.writeString(file, json);
            Globals.saves.put(index, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static SaveData loadSave(int index) {
        if (!saves.containsKey(index) || saves.get(index) == null) {
            return null;
        }
        try {
            return GSON.fromJson(Files.readString(saves.get(index)), SaveData.class);
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static java.util.Map<Integer, Path> saves = new HashMap<>();
    
    private Globals() {}

    static {
        if (!Files.exists(GAMEDATAPATH)) {
            try {
                Files.createDirectories(GAMEDATAPATH);
                Files.createDirectories(SAVEPATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (var file : SAVEPATH.toFile().listFiles((dir, filename) -> filename.endsWith(".json"))) {
            saves.put(Integer.parseInt(file.getName().replace(".json", "").replace("save", "")), file.toPath());
        }
    }
}
