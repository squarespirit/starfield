package huditems;

import game.Screen; import star.Starfield; 
import java.awt.Graphics2D;

/**
 * Displays the Starfield's speed in the top left corner of the game.
 */
public class SpeedDisplay extends TextDisplay
{
    /**
     * The location (X, Y) of the bottom left corner of the SpeedDisplay, 
     * relative to the top left corner of the screen. 
     */
    public static final int RELATIVE_X = 40, RELATIVE_Y = 60;
    /**
     * The actual location (X, Y) of the bottom left corner of the 
     * SpeedDisplay, taking into account that the origin of the Graphic is
     * at the center of the screen. 
     */
    public static final int ACTUAL_X = RELATIVE_X - Screen.HALF_WIDTH, 
            ACTUAL_Y = RELATIVE_Y - Screen.HALF_HEIGHT;
    
    private String output;
    
    public void update (Starfield starfield)
    {
        output = "Speed: " + Math.round(starfield.getSpeed());
    }
    
    public void renderText (Graphics2D g) 
    {
        g.drawString(output, ACTUAL_X, ACTUAL_Y);
    }
}
