package Screens.submenus;

import Utils.Globals;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import Level.FlagManager;
import SpriteFont.SpriteFont;
import Utils.Menu;
import Utils.MenuListener;
import Utils.Resources;
import Utils.TailwindColorScheme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls submenu based off quest submenu.
 */
public class ControlsSubMenu implements Menu {

    // UI Elements
    private List<SpriteFont> displayTexts;
    private List<Color> displayColors;
    
    // UI Configuration
    private static final int PADDING = 10;
    private static final int LINE_HEIGHT = 20;
    private static final int TITLE_SIZE = 20;
    private static final int TEXT_SIZE = 14;
    private static final int SECTION_SIZE = 12;
    private static final Color TITLE_COLOR = TailwindColorScheme.white;
    private static final Color SECTION_COLOR = Color.RED;
    private static final Color TEXT_COLOR = Color.white;

    // Scroll support
    private int scrollOffset = 0;
    private int maxVisibleLines = 17;
    private int pressCD = 0;

    private Map<String, MenuListener> listeners = new HashMap<>();

    public ControlsSubMenu() {
        this.displayTexts = new ArrayList<>();
        this.displayColors = new ArrayList<>();
        initializeControlsDisplay();
    }

    private void initializeControlsDisplay() {
        displayTexts.clear();
        displayColors.clear();

        int yPosition = PADDING + TITLE_SIZE + 35;

        addSectionHeader("MOVEMENT + INTERACTIONS", yPosition);
        yPosition += LINE_HEIGHT + 5;
        
        yPosition = addWrappedText("- Use WASD to move around the", yPosition);
        yPosition = addWrappedText("  game world.", yPosition);
        yPosition += 5;
        
        yPosition = addWrappedText("- Press SPACE to pick up items", yPosition);
        yPosition = addWrappedText("  and interact with objects.", yPosition);
        yPosition += 5;
        
        yPosition = addWrappedText("- Use SPACE to talk with NPCs", yPosition);
        yPosition = addWrappedText("  and skip through dialogue.", yPosition);
        yPosition += 10;

        // SCREENS Section
        addSectionHeader("SCREENS", yPosition);
        yPosition += LINE_HEIGHT + 5;
        
        yPosition = addWrappedText("- Press ESC to open the menu.", yPosition);
        yPosition += 5;
        
        yPosition = addWrappedText("- Interact with menus using", yPosition);
        yPosition = addWrappedText("  the mouse, or use arrow keys", yPosition);
        yPosition = addWrappedText("  with ENTER/SPACE and ESC.", yPosition);
        yPosition += 5;
        
        yPosition = addWrappedText("- In inventory, press U to use", yPosition);
        yPosition = addWrappedText("  items and BACKSPACE to remove", yPosition);
        yPosition = addWrappedText("  items from inventory.", yPosition);
        yPosition += 10;

        // SAVING Section
        addSectionHeader("SAVING", yPosition);
        yPosition += LINE_HEIGHT + 5;
        
        yPosition = addWrappedText("- Use the SAVE tab in the menu", yPosition);
        yPosition = addWrappedText("  to save your game progress.", yPosition);
        yPosition += 5;
        
        yPosition = addWrappedText("- To load a saved game, click", yPosition);
        yPosition = addWrappedText("  on the saved file in the", yPosition);
        yPosition = addWrappedText("  save menu.", yPosition);
    }


    private void addSectionHeader(String text, int yPosition) {
        SpriteFont sectionFont = new SpriteFont(
            text,
            PADDING,
            yPosition,
            Resources.PRESS_START.deriveFont((float) SECTION_SIZE),
            SECTION_COLOR
        );
        displayTexts.add(sectionFont);
        displayColors.add(SECTION_COLOR);
    }

    private int addWrappedText(String text, int yPosition) {
        SpriteFont textFont = new SpriteFont(
            text,
            PADDING,
            yPosition,
            Resources.PRESS_START.deriveFont((float) TEXT_SIZE),
            TEXT_COLOR
        );
        displayTexts.add(textFont);
        displayColors.add(TEXT_COLOR);
        return yPosition + LINE_HEIGHT;
    }

    @Override
    public void open() {
        this.scrollOffset = 0;
        this.pressCD = 0;
        initializeControlsDisplay();
    }

    @Override
    public void update() {
        handleInput();

        if (pressCD > 0) {
            pressCD--;
        }
    }

    /**
     * Handle keyboard input for scrolling
     */
    private void handleInput() {
        if (pressCD > 0) {
            return;
        }

        // Scroll up
        if (Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) {
            scrollOffset = Math.max(0, scrollOffset - 1);
            pressCD = Globals.KEYBOARD_CD;
        }

        // Scroll down
        if (Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) {
            int maxScroll = Math.max(0, displayTexts.size() - maxVisibleLines);
            scrollOffset = Math.min(maxScroll, scrollOffset + 1);
            pressCD = Globals.KEYBOARD_CD;
        }

        // Send status message
        String statusMsg = "Controls\nW/S or Arrow Keys: Scroll";
        this.sendEvent(STATUS_EVENT, statusMsg);
    }

    /**
     * From QuestSubMenu
     */
    @Override
    public void draw(GraphicsHandler graphicsHandler, int x, int y) {
        // Draw title
        SpriteFont titleText = new SpriteFont(
            "CONTROLS",
            x + PADDING,
            y + PADDING,
            Resources.PRESS_START.deriveFont((float) TITLE_SIZE),
            TITLE_COLOR
        );
        titleText.draw(graphicsHandler);

        // Draw subtitle/instructions
        SpriteFont subtitleText = new SpriteFont(
            "Use W/S or Arrow Keys to scroll",
            x + PADDING,
            y + PADDING + TITLE_SIZE + 10,
            Resources.PRESS_START.deriveFont((float) (TEXT_SIZE - 2)),
            TailwindColorScheme.slate300
        );
        subtitleText.draw(graphicsHandler);

        // Draw control entries with scroll offset
        int startIndex = scrollOffset;
        int endIndex = Math.min(displayTexts.size(), scrollOffset + maxVisibleLines);

        for (int i = startIndex; i < endIndex; i++) {
            SpriteFont textFont = displayTexts.get(i);
            Color textColor = displayColors.get(i);

            // Calculate adjusted position
            int originalY = (int) textFont.getY();
            int adjustedY = originalY - (scrollOffset * LINE_HEIGHT);

            // Create new SpriteFont with adjusted position
            SpriteFont adjustedFont = new SpriteFont(
                textFont.getText(),
                x + PADDING,
                y + adjustedY,
                textFont.getFont(),
                textColor
            );
            adjustedFont.draw(graphicsHandler);
        }

        int contentStartY = y + PADDING + TITLE_SIZE + 35;
        int contentHeight = maxVisibleLines * LINE_HEIGHT;
        int contentBottomY = contentStartY + contentHeight + LINE_HEIGHT + PADDING;

        // Draw scroll indicators
        if (scrollOffset > 0) {
            SpriteFont upArrow = new SpriteFont(
                "^ More above",
                x + PADDING,
                y + PADDING + TITLE_SIZE + 30,
                Resources.PRESS_START.deriveFont(10f),
                TailwindColorScheme.white
            );
            upArrow.draw(graphicsHandler);
        }

        if (scrollOffset + maxVisibleLines < displayTexts.size()) {
            SpriteFont downArrow = new SpriteFont(
                "v More below",
                x + PADDING,
                contentBottomY + LINE_HEIGHT + 2,
                Resources.PRESS_START.deriveFont(10f),
                TailwindColorScheme.white
            );
            downArrow.draw(graphicsHandler);
        }
    }

    @Override
    public Map<String, MenuListener> getListeners() {
        return listeners;
    }
}
