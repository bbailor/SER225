package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Represents the game's textbox
// Will display the text it is given to its textQueue
// Each String in the textQueue will be displayed in the textbox, and hitting the interact key will cycle between additional Strings in the queue
// Use the newline character in a String in the textQueue to break the text up into a second line if needed
// Also supports adding options for a player to select from
public class Textbox {
    protected boolean isActive;

    // textbox constants
    protected final int x = 22;
    protected final int bottomY = 460;
    protected final int topY = 22;
    protected final int fontX = 35;
    protected final int fontBottomY = 472;
    protected final int fontTopY = 34;
    protected final int width = 750;
    protected final int height = 100;

    // SMALL options textbox constants (for Yes/No, simple choices)
    protected final int optionSmallX = 680;
    protected final int optionSmallBottomY = 350;
    protected final int optionSmallTopY = 130;
    protected final int optionSmallWidth = 92;
    protected final int optionSmallHeight = 100;
    protected final int fontOptionSmallX = 706;
    protected final int fontOptionSmallBottomYStart = 365;
    protected final int fontOptionSmallTopYStart = 145;
    protected final int fontOptionSmallSpacing = 35;
    protected final int optionPointerSmallX = 690;
    protected final int optionPointerSmallYBottomStart = 378;
    protected final int optionPointerSmallYTopStart = 158;

    // LARGE options textbox constants (for longer text like battle actions)
    protected final int optionLargeX = 400;
    protected final int optionLargeBottomY = 350;
    protected final int optionLargeTopY = 160;
    protected final int optionLargeWidth = 372;
    protected final int optionLargeHeight = 130;
    protected final int fontOptionLargeX = 426;
    protected final int fontOptionLargeBottomYStart = 365;
    protected final int fontOptionLargeTopYStart = 145;
    protected final int fontOptionLargeSpacing = 35;
    protected final int optionPointerLargeX = 410;
    protected final int optionPointerLargeYBottomStart = 375;
    protected final int optionPointerLargeYTopStart = 158;

    // Track which size to use
    protected boolean useSmallOptions = true;  // Default to small

    // core vars that make textbox work
    private Queue<TextboxItem> textQueue;
    private TextboxItem currentTextItem;
    protected int selectedOptionIndex = 0;
    private SpriteFont text = null;
    private ArrayList<SpriteFont> options = null;
    private KeyLocker keyLocker = new KeyLocker();
    private Key interactKey = Key.SPACE;

    private Map map;

    public Textbox(Map map) {
        this.map = map;
        this.textQueue = new LinkedList<>();
    }

    public void addText(String text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        textQueue.add(new TextboxItem(text));
    }

    public void addText(String[] text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        for (String textItem : text) {
            textQueue.add(new TextboxItem(textItem));
        }
    }

    public void addText(TextboxItem text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        textQueue.add(text);
    }

    public void addText(TextboxItem[] text) {
        if (textQueue.isEmpty()) {
            keyLocker.lockKey(interactKey);
        }
        for (TextboxItem textItem : text) {
            textQueue.add(textItem);
        }
    }

    // returns whether the textQueue is out of items to display or not
    // useful for scripts to know when to complete
    public boolean isTextQueueEmpty() {
        return textQueue.isEmpty();
    }

    public void update() {
        if (!textQueue.isEmpty() && keyLocker.isKeyLocked(interactKey)) {
            currentTextItem = textQueue.peek();
            options = null;

            int fontY = !map.getCamera().isAtBottomOfMap() ? fontBottomY : fontTopY;
            text = new SpriteFont(currentTextItem.getText(), fontX, fontY, "Arial", 30, Color.black);

            if (currentTextItem.getOptions() != null) {
                // Determine which size to use based on the TextboxItem
                useSmallOptions = currentTextItem.useSmallOptions();
                
                // Choose appropriate constants based on size
                int fontOptionY = !map.getCamera().isAtBottomOfMap() ? 
                    (useSmallOptions ? fontOptionSmallBottomYStart : fontOptionLargeBottomYStart) :
                    (useSmallOptions ? fontOptionSmallTopYStart : fontOptionLargeTopYStart);
                
                int fontOptionXPos = useSmallOptions ? fontOptionSmallX : fontOptionLargeX;
                int fontSpacing = useSmallOptions ? fontOptionSmallSpacing : fontOptionLargeSpacing;

                options = new ArrayList<>();
                for (int i = 0; i < currentTextItem.getOptions().size(); i++) {
                    options.add(new SpriteFont(
                        currentTextItem.options.get(i), 
                        fontOptionXPos, 
                        fontOptionY + (i * fontSpacing), 
                        "Arial", 
                        30, 
                        Color.black
                    ));
                }
                selectedOptionIndex = 0;
            }
        }
        // if interact key is pressed, remove the current text from the queue to prepare for the next text item to be displayed
        if (Keyboard.isKeyDown(interactKey) && !keyLocker.isKeyLocked(interactKey)) {
            keyLocker.lockKey(interactKey);
            if (textQueue.poll() == null) {
                isActive = false;
            };

            // if an option was selected, set output manager flag to the index of the selected option
            // a script can then look at output manager later to see which option was selected and do with that information what it wants
            if (options != null) {
                map.getActiveScript().getScriptActionOutputManager().addFlag("TEXTBOX_OPTION_SELECTION", selectedOptionIndex);
            }
        }
        else if (Keyboard.isKeyUp(interactKey)) {
            keyLocker.unlockKey(interactKey);
        }

        if (options != null) {
            if (Keyboard.isKeyDown(Key.DOWN) && !keyLocker.isKeyLocked(Key.DOWN)) {
                keyLocker.lockKey(Key.DOWN);
                if (selectedOptionIndex < options.size() - 1) {
                    selectedOptionIndex++;
                }
            }
            if (Keyboard.isKeyDown(Key.UP) && !keyLocker.isKeyLocked(Key.UP)) {
                keyLocker.lockKey(Key.UP);
                if (selectedOptionIndex > 0) {
                    selectedOptionIndex--;
                }
            }
            if (Keyboard.isKeyUp(Key.DOWN)) {
                keyLocker.unlockKey(Key.DOWN);
            }
            if (Keyboard.isKeyUp(Key.UP)) {
                keyLocker.unlockKey(Key.UP);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        int y = !map.getCamera().isAtBottomOfMap() ? bottomY : topY;
        graphicsHandler.drawFilledRectangleWithBorder(x, y, width, height, Color.white, Color.black, 2);

        if (text != null) {
            text.drawWithParsedNewLines(graphicsHandler, 10);
            
            if (options != null) {
                // Use appropriate constants based on size
                int optionY = !map.getCamera().isAtBottomOfMap() ? 
                    (useSmallOptions ? optionSmallBottomY : optionLargeBottomY) :
                    (useSmallOptions ? optionSmallTopY : optionLargeTopY);
                
                int optX = useSmallOptions ? optionSmallX : optionLargeX;
                int optWidth = useSmallOptions ? optionSmallWidth : optionLargeWidth;
                int optHeight = useSmallOptions ? optionSmallHeight : optionLargeHeight;
                
                graphicsHandler.drawFilledRectangleWithBorder(
                    optX, optionY, optWidth, optHeight, 
                    Color.white, Color.black, 2
                );

                for (SpriteFont option : options) {
                    option.draw(graphicsHandler);
                }

                int optionPointerYStart = !map.getCamera().isAtBottomOfMap() ? 
                    (useSmallOptions ? optionPointerSmallYBottomStart : optionPointerLargeYBottomStart) :
                    (useSmallOptions ? optionPointerSmallYTopStart : optionPointerLargeYTopStart);
                
                int optPointerX = useSmallOptions ? optionPointerSmallX : optionPointerLargeX;
                int spacing = useSmallOptions ? fontOptionSmallSpacing : fontOptionLargeSpacing;
                
                graphicsHandler.drawFilledRectangle(
                    optPointerX, 
                    optionPointerYStart + (selectedOptionIndex * spacing), 
                    10, 10, 
                    Color.black
                );
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setInteractKey(Key interactKey) {
        this.interactKey = interactKey;
    }

}
