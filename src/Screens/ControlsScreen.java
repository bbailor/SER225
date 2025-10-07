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
    protected MouseLocker mouseLocker = new MouseLocker();
    protected SpriteFont ControlsLabel;
    protected SpriteFont MoveLabel, InteractLabel;
    protected SpriteFont returnInstructionsLabel;
    protected SpriteFont inventoryLabel, inventoryLabel2, inventoryLabel3, inventoryLabel4, inventoryLabel5, inventoryLabel6;

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
        returnInstructionsLabel = new SpriteFont("Click anywhere or press SPACE to return to menu.", 20, 532, "Times New Roman", 30, Color.white);
        inventoryLabel = new SpriteFont("Press \"E\" to open your inventory", 150, 251, "Times New Roman", 20, Color.white);
        inventoryLabel2 = new SpriteFont("To move items in the inventory, press \"SPACE\" OR \"ENTER\"", 150, 281, "Times New Roman", 20, Color.white);
        inventoryLabel3 = new SpriteFont("To use items in the inventory, press \"U\"", 150, 311, "Times New Roman", 20, Color.white);
        inventoryLabel4 = new SpriteFont("To remove 1 item in the inventory, press \"DEL\"", 150, 341, "Times New Roman", 20, Color.white);
        inventoryLabel5 = new SpriteFont("To remove all items in the slot, press \"BACKSPACE\"", 150, 371, "Times New Roman", 20, Color.white);
        inventoryLabel6 = new SpriteFont("Use \"ESC\" or \"E\" to close your inventory", 150, 401, "Times New Roman", 20, Color.white);

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
        if ((!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) || ((!mouseLocker.isMouseLocked() && Mouse.isClickDown()))) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        ControlsLabel.draw(graphicsHandler);
        MoveLabel.draw(graphicsHandler);
        InteractLabel.draw(graphicsHandler);
        inventoryLabel.draw(graphicsHandler);
        inventoryLabel2.draw(graphicsHandler);
        inventoryLabel3.draw(graphicsHandler);
        inventoryLabel4.draw(graphicsHandler);
        inventoryLabel5.draw(graphicsHandler);
        inventoryLabel6.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}
