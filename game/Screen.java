package game;
import java.awt.Dimension; import java.awt.Toolkit;

/**
 * Contains information about the game screen's dimensions.
 */
public class Screen 
{
    public static final int WIDTH, HEIGHT, HALF_WIDTH, HALF_HEIGHT, AREA;

    static
    {
        Dimension fullscreen = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int) fullscreen.getWidth();
        HEIGHT = (int) fullscreen.getHeight();
        HALF_WIDTH = WIDTH / 2;
        HALF_HEIGHT = HEIGHT / 2;
        AREA = WIDTH * HEIGHT;
    }
}
