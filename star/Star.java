package star;

import game.Screen;

import java.awt.Color; import java.awt.Graphics2D;

/**
 * Represents a single star in the Starfield. Knows its location and 
 * can render itself as a white square.
 */
public class Star 
{
	/**
	 * The full-size side length of a star; that is, its side length if 
	 * it were displayed at a z-position of 0. 
	 */
	public static final double SIDE_LENGTH = 18;
	/**
	 * Half the full-size side length of a star.
	 */
	public static final double HALF_SIDE = SIDE_LENGTH / 2;
	
	/**
	 * The color of a star; white, obviously.
	 */
	public static final Color COLOR_STAR = Color.white;
	
	private Point3D point3D;
	
	/**
	 * Constructs a Star at the specified point in 3-space.
	 */
	public Star (Point3D point3D)
	{
		this.point3D = point3D;
	}
	
	/**
	 * Translates the 3-space position of the star by the given 
	 * amount z. (Negative values make the star look like it is 
	 * coming toward the screen.)
	 */
	public void translateZ (double dz)
	{
		point3D.translate(0, 0, dz);
	}
	
	/**
	 * If Z < 0 (star is in front of the screen), or X, Y are such that 
	 * no portion of the star is visible on the screen, does not render 
	 * the star and returns false. Otherwise, renders the star on the 
	 * specified graphic and returns true.
	 */
	public boolean renderIfOnscreen (Graphics2D g)
	{
		if (point3D.z > 0)
		{
			PointAndScale ps = point3D.project();
			
			//The exact side length of the projected star
			double sideExact = SIDE_LENGTH * ps.scale;
			int side = (int) Math.round(sideExact);
			int halfSide = (int) Math.round(sideExact / 2);
			
			if (Math.abs(ps.x) < Screen.HALF_WIDTH + halfSide
					&& Math.abs(ps.y) < Screen.HALF_HEIGHT + halfSide)
			{
			    g.setColor(COLOR_STAR);
				g.fillRect(ps.x - halfSide, ps.y - halfSide, side, side);				
				return true;
			}
		}
		return false;
	}
}