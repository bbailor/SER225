package Engine;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/*
 * This class is used throughout the engine for detecting mouse state.
 * It includes tracking the mouse position, whether buttons are pressed or released,
 * and the position of mouse clicks/releases.
 */
 //Used the keyboard class as an outline.
public class Mouse {
    private static boolean clickDown = false;
    private static boolean clickUp = true;

    //Uses points to track the location of the mouse and its clicks/releases
    private static Point currentPosition = new Point(0, 0);
    private static Point lastPressedPosition = new Point(0, 0);
    private static Point lastReleasedPosition = new Point(0, 0);

    //Prevents default constructor from being made- prevents instantiation.
    private Mouse(){}

    private static final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mousePressed(MouseEvent e){
            clickDown = true;
            clickUp = false;

            lastPressedPosition = e.getPoint();
        }
        @Override
        public void mouseReleased(MouseEvent e){
            clickDown = false;
            clickUp = true;

            lastReleasedPosition = e.getPoint();
        }

        //Not needed
        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    };

    private static final MouseMotionListener mouseMotionListener = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            currentPosition = e.getPoint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            currentPosition = e.getPoint();
        }
    };

    //Allows for dragging items to be implemented
    public static boolean isClickDown() {
    return clickDown;
    }

    public static boolean isClickUp() {
        return clickUp;
    }

    //Setters and getters.
    public static Point getCurrentPosition() {
        return currentPosition;
    }

    public static Point getLastPressedPosition() {
        return lastPressedPosition;
    }

    public static Point getLastReleasedPosition() {
        return lastReleasedPosition;
    }

    public static MouseListener getMouseListener() {
        return mouseListener;
    }

    public static MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }
}

