package game;

import java.awt.Graphics2D;

/**
 * All items which are rendered *directly* by RenderEngine implement this interface.
 */
public interface Renderable 
{
	/**
	 * Draws the Renderable on the given graphics object. 
	 */
	public void render (Graphics2D g);
}
