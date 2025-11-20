package Screens;

import Engine.*;
import SpriteFont.SpriteFont;
import Utils.Resources;
import Utils.TailwindColorScheme;

import java.awt.*;

// This class is for the win level screen
public class LoseScreen extends Screen {
    protected SpriteFont winMessage;
    protected SpriteFont instructions;
    protected KeyLocker keyLocker = new KeyLocker();
    protected PlayLevelScreen playLevelScreen;

    public LoseScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("You Lose!", 300, 239, Resources.PRESS_START.deriveFont(30f), Color.white);
        instructions = new SpriteFont("Press Space or Escape to go back to the main menu", 120, 279, Resources.PRESS_START.deriveFont(12f), Color.white);
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, reset level. if escape is pressed, go back to main menu
        if ((Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) || (Keyboard.isKeyDown(Key.ESC) && !keyLocker.isKeyLocked(Key.ESC))) {
            playLevelScreen.goBackToMenu();
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), TailwindColorScheme.slate950);
        winMessage.draw(graphicsHandler);
        instructions.draw(graphicsHandler);
    }
}
