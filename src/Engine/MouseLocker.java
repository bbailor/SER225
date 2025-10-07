package Engine;


// This class can be used to keep track of "locked" and "unlocked" mouse clikcs based on the class
// For example, it's often useful to "lock" a key if pressed down until its been released, since holding down a key will continually count as a "key press".
// This way, that "key press" will only be counted once per press.
// This class does NOT do anything to the keyboard class to prevent a key from actually being detected -- that is not advisable as multiple classes may be detecting key presses separately

//This class is a parralell to the keylocker class. Implementation remains the same.


public class MouseLocker {
    // lock mouse
    private boolean mouseLocked;
    public void lockMouse() {
        mouseLocked = true;
    }

    // unlock mouse
    public void unlockMouse() {
        mouseLocked = false;
    }

    // check if mouse is locked.
    public boolean isMouseLocked() {
        return mouseLocked;
    }
}
