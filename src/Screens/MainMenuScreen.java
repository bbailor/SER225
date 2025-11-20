package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Globals;
import Utils.Resources;
import Utils.SoundThreads.Type;
import Utils.TailwindColorScheme;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// This is the class for the main menu screen
public class MainMenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected SpriteFont controls;
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    protected MouseLocker mouseLocker = new MouseLocker();

    private final int baseX = 100;
    private final int baseY = 260;
    private final int itemSpacing = 100;
    private final int hoverShiftX = 30;

    private float playX, playY;
    private float creditsX, creditsY;
    private float controlsX, controlsY;

    private float playTargetX, playTargetY;
    private float creditsTargetX, creditsTargetY;
    private float controlsTargetX, controlsTargetY;

    private float pointerX, pointerY;
    private float pointerTargetX, pointerTargetY;

    private Color playGameColor;
    private Color creditsColor;
    private Color controlsColor;

    public MainMenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("PLAY GAME", baseX, baseY, "Arial", 30, new Color(49, 207, 240));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);

        credits = new SpriteFont("CREDITS", baseX, baseY + itemSpacing, "Arial", 30, new Color(49, 207, 240));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);

        controls = new SpriteFont("CONTROLS", baseX, baseY + itemSpacing * 2, "Arial", 30, new Color(49, 207, 240));
        controls.setOutlineColor(Color.black);
        controls.setOutlineThickness(3);

        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
        mouseLocker.lockMouse();

        playX = baseX;
        playY = baseY;

        creditsX = baseX;
        creditsY = baseY + itemSpacing;
        controlsX = baseX;
        controlsY = baseY + itemSpacing * 2;

        pointerX = baseX - 30;
        pointerY = baseY + 7;

        // Play main menu music when main menu loads
        try {
            Globals.SOUND_SYSTEM.play(Type.Music, Globals.MUSIC_TRACK, new File("Resources/Sounds/Music/menuSong.wav"));
            Globals.SOUND_SYSTEM.getTrack(Globals.MUSIC_TRACK).setLoopPoint(0, -1, true);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        background.update(null);

        // keyboard up/down selection
        if (Keyboard.isKeyDown(Key.DOWN) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.UP) && keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        if (currentMenuItemHovered > 2) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 2;
        }

        if (currentMenuItemHovered == 0) {
            playGameColor = TailwindColorScheme.blue300;
            creditsColor = Color.white;
            controlsColor = Color.white;
        } else if (currentMenuItemHovered == 1) {
            playGameColor = Color.white;
            creditsColor = TailwindColorScheme.blue300;
            controlsColor = Color.white;
        } else {
            playGameColor = Color.white;
            creditsColor = Color.white;
            controlsColor = TailwindColorScheme.blue300;
        }

        // compute targets based on which item is hovered
        playTargetX = baseX + (currentMenuItemHovered == 0 ? -hoverShiftX : 0);
        playTargetY = baseY;

        creditsTargetX = baseX + (currentMenuItemHovered == 1 ? -hoverShiftX : 0);
        creditsTargetY = baseY + itemSpacing;

        controlsTargetX = baseX + (currentMenuItemHovered == 2 ? -hoverShiftX : 0);
        controlsTargetY = baseY + itemSpacing * 2;

        pointerTargetX = (currentMenuItemHovered == 0 ? playTargetX : (currentMenuItemHovered == 1 ? creditsTargetX : controlsTargetX)) - 30;
        pointerTargetY = (currentMenuItemHovered == 0 ? playTargetY : (currentMenuItemHovered == 1 ? creditsTargetY : controlsTargetY)) + 7;

        // Lerp movement
        final float lerp = 0.2f;
        playX = lerp(playX, playTargetX, lerp);
        playY = lerp(playY, playTargetY, lerp);

        creditsX = lerp(creditsX, creditsTargetX, lerp);
        creditsY = lerp(creditsY, creditsTargetY, lerp);

        controlsX = lerp(controlsX, controlsTargetX, lerp);
        controlsY = lerp(controlsY, controlsTargetY, lerp);

        pointerX = lerp(pointerX, pointerTargetX, lerp);
        pointerY = lerp(pointerY, pointerTargetY, lerp);

        playGame.setPosition(Math.round(playX), Math.round(playY));
        credits.setPosition(Math.round(creditsX), Math.round(creditsY));
        controls.setPosition(Math.round(controlsX), Math.round(controlsY));

        // Space key selection
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            //// Will be stopped when playing the next map
            // Stop menu music when leaving main menu
            // try {
            //     Globals.SOUND_SYSTEM.getTrack(Globals.MUSIC_TRACK).setSound(null);
            // } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            //     e.printStackTrace();
            // }

            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            } else if (menuItemSelected == 2) {
                screenCoordinator.setGameState(GameState.CONTROLS);
            }
        }

        // Mouse bounds match animated positions
        Rectangle playGameBounds = new Rectangle(Math.round(playX - 5), Math.round(playY - 25), 220, 90);
        Rectangle creditsBounds  = new Rectangle(Math.round(creditsX - 5), Math.round(creditsY - 25), 220, 90);
        Rectangle controlsBounds = new Rectangle(Math.round(controlsX - 5), Math.round(controlsY - 25), 220, 90);

        Point mousePos = Mouse.getCurrentPosition();

        if (playGameBounds.contains(mousePos)) currentMenuItemHovered = 0;
        else if (creditsBounds.contains(mousePos)) currentMenuItemHovered = 1;
        else if (controlsBounds.contains(mousePos)) currentMenuItemHovered = 2;

        if (!mouseLocker.isMouseLocked() && Mouse.isLeftClickDown()) {
            mouseLocker.lockMouse();
            Point clickPos = Mouse.getLastPressedPosition();

            //// Will be stopped when playing the next map
            // Stop menu music when leaving main menu
            // try {
            //     Globals.SOUND_SYSTEM.getTrack(Globals.MUSIC_TRACK).setSound(null);
            // } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            //     e.printStackTrace();
            // }

            if (playGameBounds.contains(clickPos)) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (creditsBounds.contains(clickPos)) {
                screenCoordinator.setGameState(GameState.CREDITS);
            } else if (controlsBounds.contains(clickPos)) {
                screenCoordinator.setGameState(GameState.CONTROLS);
            }
        }

        if (Mouse.isLeftClickUp()) mouseLocker.unlockMouse();
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);

        // Backing rectangles
        graphicsHandler.drawFilledRectangleWithBorder(Math.round(playX - 5), Math.round(playY), 190, 40, TailwindColorScheme.black, Color.black, 2);
        graphicsHandler.drawFilledRectangleWithBorder(Math.round(creditsX - 5), Math.round(creditsY), 150, 40, TailwindColorScheme.black, Color.black, 2);
        graphicsHandler.drawFilledRectangleWithBorder(Math.round(controlsX - 5), Math.round(controlsY), 175, 40, TailwindColorScheme.black, Color.black, 2);

        graphicsHandler.drawStringWithOutline(
                playGame.getText(),
                Math.round(playX),
                Math.round(playY + 32),
                Resources.PRESS_START.deriveFont(20f),
                Color.black,
                playGameColor,
                2
        );

        graphicsHandler.drawStringWithOutline(
                credits.getText(),
                Math.round(creditsX),
                Math.round(creditsY + 32),
                Resources.PRESS_START.deriveFont(20f),
                Color.black,
                creditsColor,
                2
        );

        graphicsHandler.drawStringWithOutline(
                controls.getText(),
                Math.round(controlsX),
                Math.round(controlsY + 32),
                Resources.PRESS_START.deriveFont(20f),
                Color.black,
                controlsColor,
                2
        );

        // Title
        graphicsHandler.drawFilledRectangleWithBorder(22, 10, 740, 60, TailwindColorScheme.black, Color.black, 3);
        graphicsHandler.drawStringWithOutline(
                "Requiem for a Gnome",
                30,
                62,
                Resources.PRESS_START.deriveFont(38f),
                TailwindColorScheme.black,
                TailwindColorScheme.blue300,
                6
        );

        // Pointer
        graphicsHandler.drawFilledRectangleWithBorder(
                Math.round(pointerX),
                Math.round(pointerY),
                20,
                20,
                new Color(49, 207, 240),
                Color.black,
                2
        );
    }

    // small helper lerp
    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
