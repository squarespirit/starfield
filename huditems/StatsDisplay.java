package huditems;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.LinkedList;

import state.FrameState;
import game.Screen;

/**
 * Calculates and displays statistics about app performance.
 */
public class StatsDisplay extends TextDisplay
{
    /**
     * The location (X, Y) of the bottom left corner of the first line of
     * the StatsDisplay, relative to the top right corner of the screen. 
     */
    public static final int RELATIVE_X = 210, RELATIVE_Y = 60;
    /**
     * The actual location (X, Y) of the bottom left corner of the 
     * first line of the StatsDisplay, taking into account that the 
     * origin of the Graphic is at the center of the screen. 
     */
    public static final int ACTUAL_X = Screen.HALF_WIDTH - RELATIVE_X, 
            ACTUAL_Y = RELATIVE_Y - Screen.HALF_HEIGHT;
    /**
     * Decimal formatter for FramerateDisplay.
     */
    public static final DecimalFormat FRAME_FORMAT = 
            new DecimalFormat("0.0");
    /**
     * Percent formatter for updateAvg and renderAvg.
     */
    public static final DecimalFormat PERCENT_FORMAT = 
            new DecimalFormat("0.0%");
    
    private AveragedQuantity frameAvg, updateAvg, renderAvg;
    
    public StatsDisplay ()
    {
        frameAvg = new AveragedQuantity();
        updateAvg = new AveragedQuantity();    
        renderAvg = new AveragedQuantity();
    }
    
    public void update (double secs, double updateSecs, double renderSecs)
    {
        frameAvg.add(secs);
        updateAvg.add(updateSecs);
        renderAvg.add(renderSecs);
    }
    
    /**
     * Displays frames per second as a decimal, and average update time and
     * average rendering time per target frame time as a percent.
     */
    public void renderText (Graphics2D g) 
    {
        FontMetrics metrics = g.getFontMetrics(FONT); 
                
        g.drawString("FPS: " + 
                FRAME_FORMAT.format(1 / frameAvg.getAverage()),
                ACTUAL_X, ACTUAL_Y);
        g.drawString("Update: " + 
        PERCENT_FORMAT.format(updateAvg.getAverage() * FrameState.FRAMERATE),
                ACTUAL_X, ACTUAL_Y + metrics.getHeight());
        g.drawString("Render: " + 
        PERCENT_FORMAT.format(renderAvg.getAverage() * FrameState.FRAMERATE),
                ACTUAL_X, ACTUAL_Y + 2 * metrics.getHeight());

    }    
}

/**
 * A quantity, represented in double precision, that is averaged over
 * multiple frames.
 */
class AveragedQuantity 
{
    /**
     * Statistics will be calculated by averaging over this number of 
     * values.
     */
    public static final int AVERAGE_OVER = 15;
    
    private LinkedList<Double> values;
    
    public AveragedQuantity ()
    {
        values = new LinkedList<Double>();
    }
    
    /**
     * Stores the specified value, and removes the oldest value if there
     * are more than AVERAGE_OVER values.
     */
    public void add (double value)
    {
        values.add(value);
        if (values.size() > AVERAGE_OVER)
        {
            values.removeFirst();
        }
    }
    
    /**
     * Returns the arithmetic mean of all stored values, or 0 if there
     * are no stored values.
     */
    public double getAverage ()    {
        if (values.size() == 0)
            return 0;
        
        double total = 0;
        for (double value : values) 
        {
            total += value;
        }
        return total / values.size();
    }
}