package huditems;

import java.awt.Graphics2D; import java.awt.FontMetrics;

import game.Screen;

/**
 * Displays a single line of text, centered near the bottom of the screen,
 * indicating the game has been paused by a keypress.
 */
public class PauseDisplay extends TextDisplay 
{
    /**
     * Number of pixels between the bottom of the PauseDisplay and the 
     * bottom of the screen.
     */
    public static final int RELATIVE_HEIGHT = 100;
    /**
     * The actual y-position of the bottom of the PauseDisplay.
     */
    public static final int ACTUAL_Y = Screen.HALF_HEIGHT - RELATIVE_HEIGHT;
    private static final char EN = (char) 0x2013;
    /**
     * The text to be displayed.
     */
    public static final String TEXT = "Paused"+EN+EN+"press P to resume";
    
    public void renderText (Graphics2D g) 
    {
        FontMetrics metrics = g.getFontMetrics(FONT);
        g.drawString(TEXT, - metrics.stringWidth(TEXT) / 2, ACTUAL_Y);
    }
}
