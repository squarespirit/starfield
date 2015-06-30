package game;

import java.awt.Graphics2D; import java.awt.Image;

/**
 * Organizes the rendering of all Renderable objects.
 */
public class RenderEngine 
{
    private GamePanel gamePanel;
	private Image bufferImage; 
	private Graphics2D bufferGraphics;
	
	/**
	 * Constructs a new RenderEngine that will perform rendering on the 
	 * specified GamePanel.
	 */
	public RenderEngine (GamePanel gamePanel)
	{
	    this.gamePanel = gamePanel;
	}	
	
	/**
	 * Gets new buffer image from the GamePanel and translates its origin. Renders all the 
	 * Renderables in the specified array of renderItems, and draws the 
	 * buffer image onto the GamePanel.
	 */
	public void render (Renderable[] renderItems)
	{
	    bufferImage = gamePanel.getBufferImage();
		renderAgain(renderItems);
	}
	
	/**
	 * Uses the stored buffer image to render all the Renderables in the 
	 * specified array of renderItems. Then, draws the buffer image onto 
	 * the GamePanel. 	
	 */
	public void renderAgain (Renderable[] renderItems)
	{
        bufferGraphics = (Graphics2D) bufferImage.getGraphics();
        bufferGraphics.translate(Screen.HALF_WIDTH, Screen.HALF_HEIGHT);
		renderOnGraphics(bufferGraphics, renderItems);		
		gamePanel.drawBufferImage(bufferImage);
		bufferGraphics.dispose();
	}
		
	/**
	 * Renders all Renderables in the specified array onto the specified
	 * Graphics2D.
	 */
	private void renderOnGraphics (Graphics2D g, Renderable[] renderItems)
	{
	    for (Renderable r : renderItems)
	        r.render(bufferGraphics);
	}
	
	/**
	 * Renders all specified Renderables onto the Game 
	 */
	public void renderDummy (Renderable[] renderItems)
	{
	    bufferImage = gamePanel.getBufferImage();
        bufferGraphics.translate(Screen.HALF_WIDTH, Screen.HALF_HEIGHT);
        renderOnGraphics(bufferGraphics, renderItems);      
        bufferGraphics.dispose();
	}
}