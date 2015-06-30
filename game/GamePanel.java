package game;

import java.awt.Image; import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * The GamePanel contains the game canvas and graphics object where the 
 * game is actually displayed.
 */
public class GamePanel extends JPanel 
{		
	/**
	 * Constructs a full-screen GamePanel.
	 */
	public GamePanel ()
	{
		super();
		setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));
	}

	/**
	 * Returns a blank image of the same size as the GamePanel.
	 * This image's Graphics can be manipulated and then passed
	 * as the argument to drawBufferImage().
	 */
	public Image getBufferImage ()
	{
		return createImage(Screen.WIDTH, Screen.HEIGHT);
	}
	
	/**
	 * Draws the specified image to the GamePanel.
	 */
	public void drawBufferImage (Image bufferImage)
	{
		this.getGraphics().drawImage(bufferImage, 0, 0, null);
	}	
}