package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;

// This class is for the Controls screen
public class ControlsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont ControlsLabel;
    protected SpriteFont MoveLabel, InteractLabel;
    protected SpriteFont returnInstructionsLabel;

    public ControlsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        ControlsLabel = new SpriteFont("Controls", 15, 7, "Times New Roman", 30, Color.white);
        MoveLabel = new SpriteFont("To move, use the arrow keys.", 150, 191, "Times New Roman", 20, Color.white);
        InteractLabel = new SpriteFont("To interact with characters and objects, or to skip text, use the space bar.", 150, 221, "Times New Roman", 20, Color.white);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
    }
 
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        ControlsLabel.draw(graphicsHandler);
        MoveLabel.draw(graphicsHandler);
        InteractLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
