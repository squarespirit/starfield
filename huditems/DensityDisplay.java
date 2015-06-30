package huditems;

import star.Starfield;
import java.awt.Graphics2D; import java.awt.FontMetrics; 
import java.text.DecimalFormat;

/**
 * Displays the Starfield's density flush left with and just below
 * the SpeedDisplay.
 */
public class DensityDisplay extends TextDisplay
{
    /**
     * The number of pixels between the bottom of the SpeedDisplay and the 
     * top of the DensityDisplay.
     */
    public static final int LINE_SPACING = 5;
    /**
     * Decimal formatter for DensityDisplay.
     */
    public static final DecimalFormat FORMAT = new DecimalFormat("0.00E0");
    
    /**
     * The complete text that will be displayed by the DensityDisplay.
     */
    private String output;
    
    public void update (Starfield starfield)
    {
        output = "Star density: " + FORMAT.format(starfield.getDensity());
        //Code here to replace "e" with "*10^"
        /*
        int eIndex = output.indexOf("e");
        output = "Density: " + output.substring(0, eIndex)
                + "*10^" + output.substring(eIndex + 1);
        */
    }
        
    public void renderText (Graphics2D g) 
    {
        FontMetrics metrics = g.getFontMetrics(FONT);
        g.drawString(output, SpeedDisplay.ACTUAL_X, 
                SpeedDisplay.ACTUAL_Y + metrics.getHeight() + LINE_SPACING);
    }
}
