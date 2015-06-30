package event;

import game.GameFrame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener; import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Responsible for all actions related to the GameFrame's window, focus, 
 * and key listening.
 */
public class GameListener extends KeyFrameAdapter 
        implements WindowListener, FocusListener
{
	private boolean isWindowClosing;
	private GameFrame gameFrame;
	
	/**
	 * Adds itself to the specified GameFrame as a key, focus, and window 
	 * listener.
	 */
	public GameListener (GameFrame g) 
	{
	    super(g);
	    this.gameFrame = g;
	    gameFrame.addWindowListener(this);
	    gameFrame.addFocusListener(this);
		isWindowClosing = false;
	}
	
	/**
	 * Returns true if the GameFrame has registered a window closing event.
	 */
	public boolean isWindowClosing ()
	{
		return isWindowClosing;
	}
	
	/**
	 * Returns true if the game is terminating, because the GameFrame has
	 * registered a window closing event or a quit key is first pressed. 
	 */
	public boolean isTerminating ()
	{
	    return isWindowClosing || isKeyFirstPressed(KeyBindings.KEY_QUIT);
	}
	
	/**
	 * Returns true if the GameFrame has focus. This is the same value as 
	 * calling hasFocus() on the GameFrame itself.
	 */
	public boolean hasFocus ()
	{
		return gameFrame.hasFocus();
	}
	
	/**
	 * Sets isClosing to true.
	 */
	public void windowClosing(WindowEvent e) 
	{
		isWindowClosing = true;
	}
	
	//Unused WindowListener methods
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}

    /**
     * Place the GameFrame on top when focus is gained.
     */
	public void focusGained(FocusEvent e) 
	{
	    gameFrame.setAlwaysOnTop(true);
	}
    /**
     * When focus is lost, the GameFrame is no longer on top.
     */
	public void focusLost(FocusEvent e) 
	{
        gameFrame.setAlwaysOnTop(false);
	}
}