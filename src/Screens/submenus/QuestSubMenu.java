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

public class QuestSubMenu implements Menu {

    // Quest tracking data
    private FlagManager flagManager;
    private List<QuestEntry> questEntries;
    private Map<String, MenuListener> listeners = new HashMap<>();

    // UI Elements
    private List<SpriteFont> displayTexts;
    private List<Color> displayColors;

    // UI Configuration
    private static final int PADDING = 10;
    private static final int LINE_HEIGHT = 20;
    private static final int TITLE_SIZE = 20;
    private static final int TEXT_SIZE = 14;
    private static final Color ACTIVE_COLOR = TailwindColorScheme.yellow400;
    private static final Color COMPLETED_COLOR = TailwindColorScheme.green400;
    private static final Color TITLE_COLOR = TailwindColorScheme.white;

    // Scroll support
    private int scrollOffset = 0;
    private int maxVisibleLines = 25;
    private int pressCD = 0;

    public QuestSubMenu() {
        this.questEntries = new ArrayList<>();
        this.displayTexts = new ArrayList<>();
        this.displayColors = new ArrayList<>();
        initializeQuests();
    }

    /**
     * Define all quest entries with trigger flags and custom display text.
     * Only entries whose trigger flag is set will be displayed.
     */
    private void initializeQuests() {
        // Main Story Progression

        questEntries.add(new QuestEntry(
            "hasEnteredDenial",
            "Entered the Denial Stage",
            "hasDefeatedDenial",
            "Progress through the bulding."
        ));

        questEntries.add(new QuestEntry(
            "hasTalkedToDenialBoss",
            "Quest: Defeat the Denial Boss",
            "hasDefeatedDenial",
            "Battle the boss."
        ));

        questEntries.add(new QuestEntry(
            "hasDefeatedDenial",
            "Denial Boss Defeated!",
            null,
            "Continue exploring to find the next stage"
        ));

        questEntries.add(new QuestEntry(
            "hasEnteredAnger",
            "Entered the Anger Stage",
            "hasDefeatedAnger",
            "Explore the Anger stage"
        ));

        questEntries.add(new QuestEntry(
            "hasTalkedToAngerBoss",
            "Quest: Defeat the Anger Boss",
            "hasDefeatedAnger",
            "Battle the boss in the Anger area"
        ));

        questEntries.add(new QuestEntry(
            "hasDefeatedAnger",
            "Anger Boss Defeated!",
            null,
            "Continue exploring to find the next stage"
        ));

        questEntries.add(new QuestEntry(
            "hasEnteredDepression",
            "Entered the Depression Stage",
            "hasDefeatedDepression",
            "Explore the Depression stage"
        ));

        questEntries.add(new QuestEntry(
            "hasTalkedToDepressionBoss",
            "Quest: Defeat the Depression Boss",
            "hasDefeatedDepression",
            "Battle the Depression Boss"
        ));

        questEntries.add(new QuestEntry(
            "hasDefeatedDepression",
            "Depression Boss Defeated!",
            null,
            "Continue exploring to find the next stage"
        ));

        questEntries.add(new QuestEntry(
            "hasStartedBargaining",
            "Entered the Bargaining Stage",
            "hasDefeatedBargaining",
            "Explore the Bargaining stage"
        ));

        questEntries.add(new QuestEntry(
            "hasTalkedToBargainingBoss",
            "Quest: Defeat the Bargaining Boss",
            "hasDefeatedBargaining",
            "Battle the Bargaining Boss"
        ));

        questEntries.add(new QuestEntry(
            "hasDefeatedBargaining",
            "Bargaining Boss Defeated!",
            null,
            "Continue exploring to find the next stage"
        ));

        questEntries.add(new QuestEntry(
            "hasEnteredAcceptance",
            "Entered the Acceptance Stage",
            null,
            "Explore the final stage"
        ));

        // Wizard Quest Line
        questEntries.add(new QuestEntry(
            "wizardQuestStarted",
            "Quest: Help the Wizard",
            "wizardRewardGiven",
            "Find and rescue the wizard"
        ));

        questEntries.add(new QuestEntry(
            "wizardSaved",
            "Wizard Saved! Return for reward.",
            "wizardRewardGiven",
            "Talk to the wizard again. \n Perhaps he has something for you?"
        ));
    }

    @Override
    public void open() {
        this.scrollOffset = 0;
        this.pressCD = 0;
        refreshQuestDisplay();
    }

    /**
     * Set the flag manager to track quest progress
     */
    public void setFlagManager(FlagManager flagManager) {
        this.flagManager = flagManager;
        refreshQuestDisplay();
    }

    /**
     * Refresh the quest display based on current flag states.
     * Only shows entries whose trigger flag is active.
     */
    private void refreshQuestDisplay() {
        displayTexts.clear();
        displayColors.clear();

        if (flagManager == null) {
            return;
        }

        int yPosition = PADDING + TITLE_SIZE + 35;

        for (QuestEntry entry : questEntries) {
            // Only display if trigger flag is set
            if (!flagManager.isFlagSet(entry.triggerFlag)) {
                continue;
            }

            // Determine if quest is complete
            boolean isComplete = entry.completionFlag != null &&
                               flagManager.isFlagSet(entry.completionFlag);

            // Choose color based on completion status
            Color textColor = isComplete ? COMPLETED_COLOR : ACTIVE_COLOR;
            String prefix = isComplete ? "✓ " : "• ";

            SpriteFont textFont = new SpriteFont(
                prefix + entry.displayText,
                PADDING,
                yPosition,
                Resources.PRESS_START.deriveFont((float) TEXT_SIZE),
                textColor
            );

            displayTexts.add(textFont);
            displayColors.add(textColor);
            yPosition += LINE_HEIGHT;

            // Add location hint for active (incomplete) quests
            if (!isComplete && entry.locationHint != null) {
                SpriteFont hintFont = new SpriteFont(
                    "    → " + entry.locationHint,
                    PADDING + 5,
                    yPosition,
                    Resources.PRESS_START.deriveFont((float) (TEXT_SIZE - 2)),
                    TailwindColorScheme.cyan300
                );

                displayTexts.add(hintFont);
                displayColors.add(TailwindColorScheme.cyan300);
                yPosition += LINE_HEIGHT - 2;
            }
        }
    }

    @Override
    public void update() {
        handleInput();

        if (pressCD > 0) {
            pressCD--;
        }
    }

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

        // Refresh display
        if (Keyboard.isKeyDown(Key.R)) {
            refreshQuestDisplay();
            pressCD = Globals.KEYBOARD_CD;
        }

        // Send status message
        String statusMsg = displayTexts.isEmpty()
            ? "No active quests yet.\nComplete actions to unlock quests!"
            : "Quest Log\nW/S: Scroll | R: Refresh";
        this.sendEvent(STATUS_EVENT, statusMsg);
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler, int x, int y) {
        // Draw title
        SpriteFont titleText = new SpriteFont(
            "Quest Log",
            x + PADDING,
            y + PADDING,
            Resources.PRESS_START.deriveFont((float) TITLE_SIZE),
            TITLE_COLOR
        );
        titleText.draw(graphicsHandler);

        // Draw quest count summary
        if (flagManager != null) {
            int activeQuests = 0;
            int completedQuests = 0;

            for (QuestEntry entry : questEntries) {
                if (!flagManager.isFlagSet(entry.triggerFlag)) {
                    continue;
                }

                boolean isComplete = entry.completionFlag != null &&
                                   flagManager.isFlagSet(entry.completionFlag);

                if (isComplete) {
                    completedQuests++;
                } else {
                    activeQuests++;
                }
            }

            String summaryText = String.format("Active: %d | Completed: %d",
                activeQuests, completedQuests);

            SpriteFont summary = new SpriteFont(
                summaryText,
                x + PADDING,
                y + PADDING + TITLE_SIZE + 10,
                Resources.PRESS_START.deriveFont((float) (TEXT_SIZE - 2)),
                TailwindColorScheme.slate300
            );
            summary.draw(graphicsHandler);
        }

        // Draw "no quests" message if empty
        if (displayTexts.isEmpty()) {
            SpriteFont noQuestsText = new SpriteFont(
                "No active quests yet...",
                x + PADDING,
                y + PADDING + TITLE_SIZE + 50,
                Resources.PRESS_START.deriveFont((float) TEXT_SIZE),
                TailwindColorScheme.gray500
            );
            noQuestsText.draw(graphicsHandler);

            SpriteFont hintText = new SpriteFont(
                "Explore and interact to unlock!",
                x + PADDING,
                y + PADDING + TITLE_SIZE + 75,
                Resources.PRESS_START.deriveFont((float) (TEXT_SIZE - 2)),
                TailwindColorScheme.gray600
            );
            hintText.draw(graphicsHandler);
            return;
        }

        // Draw quest entries with scroll offset
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
                y + 520,
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

    /**
     * Internal class to store quest entry information.
     * A quest entry is displayed when triggerFlag is set,
     * and marked complete when completionFlag is set (if not null).
     */
    private static class QuestEntry {
        String triggerFlag;      // Flag that must be set for this entry to appear
        String displayText;      // The text to display
        String completionFlag;   // Optional flag that marks this entry as complete
        String locationHint;     // Optional hint about where to go next

        QuestEntry(String triggerFlag, String displayText, String completionFlag) {
            this(triggerFlag, displayText, completionFlag, null);
        }

        QuestEntry(String triggerFlag, String displayText, String completionFlag, String locationHint) {
            this.triggerFlag = triggerFlag;
            this.displayText = displayText;
            this.completionFlag = completionFlag;
            this.locationHint = locationHint;
        }
    }
}
