package huditems;

import java.io.IOException;

/**
 * Contains public references to all HUD items.
 */
public class HUDItems 
{
    public SpeedDisplay speedDisplay;
    public DensityDisplay densityDisplay;
    public PauseDisplay pauseDisplay;
    public HelpDisplay helpDisplay;
    public StatsDisplay statsDisplay;
    
    /**
     * Initializes all HUD items.
     */
    public HUDItems () throws IOException {
        speedDisplay = new SpeedDisplay();
        densityDisplay = new DensityDisplay();
        pauseDisplay = new PauseDisplay();
        helpDisplay = new HelpDisplay();
        statsDisplay = new StatsDisplay();
    }
}
