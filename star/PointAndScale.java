package star;

import java.awt.Point;

/**
 * An object combining a 2D point in the screen with a scale factor.
 * Every PointAndScale corresponds to a single Point3D. Unlike 
 * Point3D, the coordinates of the two-dimensional Point are ints.
 * The scale factor is a double.
 */
public class PointAndScale extends Point
{
	public double scale;
	
	/**
	 * Constructs a new PointAndScale with the specified integral
	 * coordinates (x, y) and scale factor.
	 */
	public PointAndScale (int x, int y, double scale)
	{
	    super(x, y);
		this.scale = scale;
	}
	
	/**
	 * Returns a string representation of this PointAndScale.
	 */
	public String toString () 
	{
	    return "PointAndScale (" + x + ", " + y + ") scale: " + scale;
	}
	
}
