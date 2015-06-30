package star;

import game.Screen;
import java.awt.geom.Rectangle2D;

public class Perspective 
{
	/**
	 * The approximate z-position of the eyes in 3-space. Since 
	 * the eyes are in front of the screen, EYES_Z should be negative. 
	 */
	public static double EYES_Z = -1000;
	
	/**
	 * Calculates the ratio of the size of the object at the specified
	 * z-position to the size of the object if it were moved to a 
	 * z-position of 0. By experience, we know that objects farther 
	 * away are smaller. Likewise, for z > 0, the object's scale is 
	 * less than 1.
	 */
	public static double calculateScale (double z)
	{
		return - EYES_Z / (z - EYES_Z); 
	}
	
	/**
	 * Calculates the necessary z-position of an object with the 
	 * specified scale. This is the inverse function of 
	 * calculateScale(z).
	 */
	public static double calculateZ (double scale)
	{
		return EYES_Z * (1 - 1 / scale);
	}

	/**
	 * Returns a rectangle that represents the area at the specified 
	 * z-position which can be viewed from the screen, in other words,
	 * the slice of the view frustum at the specified z-position.
	 */
	public static Rectangle2D.Double calculateSlice (double z)
	{
		double scale = calculateScale(z);
		return new Rectangle2D.Double(
		    - Screen.HALF_WIDTH / scale, - Screen.HALF_HEIGHT / scale,
		    Screen.WIDTH / scale, Screen.HEIGHT / scale);
	}
}
