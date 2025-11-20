package Screens.components;

import java.util.Arrays;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import Engine.GraphicsHandler;
import Utils.Globals;
import Utils.Resources;
import Utils.SaveData;
import Utils.TailwindColorScheme;

public class SaveSlot extends Slot {

    @Nullable
    protected SaveData data;
    protected int maxTextLength = (this.width - 30) / FONT_SIZE_BASE;
    protected int index = 0;
    protected int page = 1;

    public static int SAVE_SLOT_WIDTH = 200;
    protected static int FONT_SIZE_BASE = 12;
    public static int SAVE_SLOT_HEIGHT = 100;

    public SaveSlot(int index) {
        super(SAVE_SLOT_WIDTH, SAVE_SLOT_HEIGHT);
        this.index = index;
        this.refreshData();
    }

    /* public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    } */

    public SaveData getSaveData() {
        return this.data;
    }

    // public void setPage(int page) {
    //     this.page = page;
    //     this.data = Globals.loadSave(this.index * page);
    // }

    public void setIndex(int index) {
        this.index = index;
        this.refreshData();
    }

    public void refreshData() {
        this.data = Globals.loadSave(this.index);
    }

    @Override
    public void draw(GraphicsHandler handler, int x, int y) {
        super.draw(handler, x, y);
        int draw_start_y = y + 30;
        // int draw_start_x = x + 15; // if not centered
        int header_margin = 20;
        int text_margin = 8;
        int line = 0;
        String header = "Save " + ((this.index + 1) * this.page);
        header = header.substring(0, Math.min(this.width / (FONT_SIZE_BASE + 6), header.length()));
        handler.drawString(header, x + (this.width - header.length() * (FONT_SIZE_BASE + 6))/2, draw_start_y, Resources.PRESS_START.deriveFont((float)FONT_SIZE_BASE + 6), TailwindColorScheme.white);
        if (this.data == null) {
            String noSave = "No Save Data";
            noSave = noSave.substring(0, Math.min(this.width / (FONT_SIZE_BASE), noSave.length()));
            handler.drawString(noSave, x + (this.width - noSave.length() * FONT_SIZE_BASE) / 2, (int) (y + (this.height - FONT_SIZE_BASE) * .75), Resources.PRESS_START.deriveFont((float)FONT_SIZE_BASE), TailwindColorScheme.white);
            return;
        }
        String playerHealth = "HP: " + this.data.player.getEntity().getHealth() + "/" + this.data.player.getEntity().getMaxHealth();
        String playerMana = "MP: " + this.data.player.getEntity().getMana() + "/" + this.data.player.getEntity().getMaxMana();
        String mapName = "Map: " + String.join(" ", Arrays.stream(this.data.map.getMapFileName().replace(".txt", "").split("_")).<String>map(v -> v.substring(0, 1).toUpperCase() + v.substring(1)).toList());
        playerHealth = playerHealth.substring(0, Math.min(this.width / FONT_SIZE_BASE, playerHealth.length()));
        mapName = mapName.substring(0, Math.min(this.width / FONT_SIZE_BASE, mapName.length()));
        playerMana = playerMana.substring(0, Math.min(this.width / FONT_SIZE_BASE, playerMana.length()));
        handler.drawString(mapName, x + (this.width - (mapName.length() * FONT_SIZE_BASE))/2, draw_start_y + header_margin + (FONT_SIZE_BASE) * (line++), Resources.PRESS_START.deriveFont((float)FONT_SIZE_BASE), TailwindColorScheme.white);
        handler.drawString(playerHealth, x + (this.width - (playerHealth.length() * FONT_SIZE_BASE))/2, draw_start_y + header_margin + (FONT_SIZE_BASE + text_margin) * (line++), Resources.PRESS_START.deriveFont((float)FONT_SIZE_BASE), TailwindColorScheme.white);
        handler.drawString(playerMana, x + (this.width - (playerMana.length() * FONT_SIZE_BASE))/2, draw_start_y + header_margin + (FONT_SIZE_BASE + text_margin) * (line++), Resources.PRESS_START.deriveFont((float)FONT_SIZE_BASE), TailwindColorScheme.white);
    }
    
}
