package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Resources;
import Utils.TailwindColorScheme;

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

        keyLocker.lockKey(Key.SPACE);
        mouseLocker.lockMouse();
    }
 
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if(Mouse.isLeftClickUp())
        {
            mouseLocker.unlockMouse();
        }

        // if space is pressed or mouse is clicked, go back to main menu
        if ((!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) || ((!mouseLocker.isMouseLocked() && Mouse.isLeftClickDown()))) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(1, 1, 786, 568, new Color(200, 200, 200, 225), Color.black, 3);

        //CONTROLS
        graphicsHandler.drawStringWithOutline("CONTROLS", 335, 50, Resources.press_start.deriveFont(22f), Color.RED, Color.black, 5);
        graphicsHandler.drawStringWithOutline("Click anywhere or press space to exit.", 30, 550, Resources.press_start.deriveFont(14f), Color.RED, Color.black, 2);


        //MOVEMENT
        graphicsHandler.drawStringWithOutline("MOVEMENT + INTERACTIONS", 30, 90, Resources.press_start.deriveFont(12f), Color.RED, Color.black, 3);
        graphicsHandler.drawStringWithOutline("- Use WASD to move. Press SPACE to pick up items.", 30, 120, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);
        graphicsHandler.drawStringWithOutline("- Use SPACE to interact with NPCs and skip dialouge.", 30, 150, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);

        //MENU SCREEN
        graphicsHandler.drawStringWithOutline("SCREENS", 30, 200, Resources.press_start.deriveFont(12f), Color.RED, Color.black, 3);
        graphicsHandler.drawStringWithOutline("- Press ESC to open the menu.", 30, 230, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);
        graphicsHandler.drawStringWithOutline("- Interact with menus using the mouse, or use", 30, 260, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);
        graphicsHandler.drawStringWithOutline("the arrow keys along with ENTER/SPACE and ESC.", 30, 290, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);
        graphicsHandler.drawStringWithOutline("- In the inventory, use U to use items, and BACKSPACE to remove items.", 30, 320, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);

        //SAVING
        graphicsHandler.drawStringWithOutline("SAVING", 30, 370, Resources.press_start.deriveFont(12f), Color.RED, Color.black, 3);
        graphicsHandler.drawStringWithOutline("- Use the SAVE tab in the menu to save your progress.", 30, 400, Resources.press_start.deriveFont(12f), Color.white, Color.black, 2);
        graphicsHandler.drawStringWithOutline("- To load saved progress, click on the saved file in the save menu.", 30, 430, Resources.press_start.deriveFont(11.2f), Color.white, Color.black, 2);

       //add mroe controls if needed
    }
}
