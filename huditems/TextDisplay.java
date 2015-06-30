package huditems;

import game.Renderable;
import java.awt.Color; import java.awt.Font; import java.awt.Graphics2D;

/**
 * Contains properties common to all HUD items which are one or a few lines 
 * of text.
 */
public abstract class TextDisplay implements Renderable 
{
	public static final Color TEXT_COLOR = new Color(0, 220, 0);
	public static final Font FONT = 
	        new Font(Font.MONOSPACED, Font.BOLD, 20);
	
	/**
	 * Sets the text color and font, and calls renderText().
	 */
	public void render (Graphics2D g)
	{
        g.setColor(TEXT_COLOR);
        g.setFont(FONT);
        renderText(g);
	}
	
	/**
	 * Renders the single line of text onto the given graphic. The 
	 * graphic's text color and font have already been set.
	 */
	public abstract void renderText(Graphics2D g);
}
