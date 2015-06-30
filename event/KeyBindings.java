package event;

import java.awt.event.KeyEvent;

/**
 * Contains arrays of key codes corresponding to the keys which the application
 * uses.
 */
public interface KeyBindings 
{
    public static final int[] 
            KEY_HELP = {KeyEvent.VK_H},
            KEY_QUIT = {KeyEvent.VK_Q, KeyEvent.VK_ESCAPE},
            KEY_PAUSE = {KeyEvent.VK_P},
            KEY_SPEED = {KeyEvent.VK_S},
            KEY_DENSITY = {KeyEvent.VK_D},
            KEY_STATS = {KeyEvent.VK_F},
            KEY_TOGGLE_UP = {KeyEvent.VK_UP},
            KEY_TOGGLE_DOWN = {KeyEvent.VK_DOWN};
}
