package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import java.awt.*;

// This class is for the credits screen
public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected MouseLocker mouseLocker = new MouseLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont createdByLabel1, createdByLabel2, managedByLabel;
    protected SpriteFont returnInstructionsLabel;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        creditsLabel = new SpriteFont("Credits", 15, 7, "Times New Roman", 30, Color.white);
        createdByLabel1 = new SpriteFont("Created by: Alex Thimineur, Ben Bailor, Jackson Sennhenn", 145, 185, "Times New Roman", 20, Color.white);
        createdByLabel2 = new SpriteFont("Dean Acheampong, Aurélien Buisine, and Ryan Davis.", 145, 220, "Times New Roman", 20, Color.white);
        managedByLabel = new SpriteFont("Managed by Isabela Ayers", 145, 255, "Times New Roman", 20, Color.white);
        returnInstructionsLabel = new SpriteFont("Click anywhere or press SPACE to return to menu.", 20, 532, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.SPACE);
        mouseLocker.lockMouse();
    }
 
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if(Mouse.isClickUp())
        {
            mouseLocker.unlockMouse();
        }

        // if space is pressed or mouse is clicked, go back to main menu
        if ((!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) || Mouse.isClickDown()) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        creditsLabel.draw(graphicsHandler);
        createdByLabel1.draw(graphicsHandler);
        createdByLabel2.draw(graphicsHandler);
        managedByLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
