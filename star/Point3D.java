package star;

/**
 * A 3-tuple of doubles that represents a point in 3-space.
 * +X is to the right, +Y is down, and +Z is into the screen.
 * (0, 0, 0) is the center of the screen in the plane of the screen.
 */
public class Point3D 
{
	public double x, y, z;
	
	/**
	 * Constructs a new Point3D with the specified coordinates (x, y, z).
	 */
	public Point3D (double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Translates the point by the given amount in each direction.
	 */
	public void translate (double dx, double dy, double dz)
	{
		x += dx;
		y += dy;
		z += dz;
	}
	
	/**
	 * Projects this 3D point into the nearest 2d PointAndScale.
	 * The PointAndScale's scale is exact, but its coordinates
	 * are rounded to the nearest integer.
	 */
	public PointAndScale project ()
	{
		double scale = Perspective.calculateScale(z);
		return new PointAndScale((int) Math.round(x * scale),
				(int) Math.round(y * scale), scale);
	}

	/**
	 * Returns a string representation of the Point3D.
	 */
	public String toString ()
	{
	    return "Point3D (" + x + ", " + y + ", " + z + ")";
	}
}
