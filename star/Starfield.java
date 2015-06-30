package star;

import event.GameListener; import event.KeyBindings;
import game.Screen; import game.Renderable;
import java.awt.Color; import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList; import java.util.ListIterator;

public class Starfield implements Renderable, KeyBindings
{
    /**
     * The background color of the Starfield; black, obviously.
     */
	public static final Color COLOR_BG = Color.black;
	
	/**
	 * Values of starfield speed, in units of z-position per second. 
	 */
	public static final double SPEED_START = 5000, SPEED_MIN = 100,
			SPEED_MAX = 100000,
			/**
			 * When S+up or S+down are held, speed is increased/decreased
			 * exponentially. If S+up is held for one second, the ratio 
			 * between new and old speed is this number. The same
			 * applies to density.
			 */
			SPEED_MULTIPLY = 1.35;
	/**
	 * Values of star density, in number of stars per unit of 3-space volume.
	 */
	public static final double DENSITY_START = 4e-10, DENSITY_MIN = 1e-11, 
			DENSITY_MAX = 3e-9, DENSITY_MULTIPLY = 1.27;

	/**
	 * The maximum z-position that stars are generated. This is calibrated
	 * in terms of the visible size of the star when it is first generated.
	 * That size should be at least 0.5, since star side lengths are rounded
	 * to the nearest integer.
	 */
	public static final double MAX_GEN_Z = 
	        Perspective.calculateZ(0.9 / Star.SIDE_LENGTH);
	
	private double speed, density; 
	private double genZ;
	private ArrayList<Star> starList;

	/**
	 * Sets the Starfield speed and star density to their starting values. 
	 * Fills the Starfield with stars by setting genZ to 0 and calling
	 * generateStars(); 
	 */
	public Starfield () 
	{
		speed = SPEED_START;
		density = DENSITY_START;
		starList = new ArrayList<Star>();
		genZ = 0;
		generateStars();
	}
	
	/**
	 * Returns the current speed.
	 */
	public double getSpeed ()
	{
	    return speed;
	}
	/**
	 * Returns the current star density.
	 */
	public double getDensity ()
	{
	    return density;
	}

	/**
	 * Updates speed and star density based on whether the corresponding
	 * keys are down, according to the specified GameListener.
	 * Translates all stars forward by a distance based on the current speed 
	 * and specified time elapsed. Also, translates the generation z-position
	 * by the same amount and calls generateStars();
	 */
	public void update (double secs, GameListener gameListener) 
	{
		if (gameListener.isKeyDown(KEY_SPEED))
		{
		    if (gameListener.isKeyDown(KEY_TOGGLE_UP))
		        speed = Math.min(speed * Math.pow(SPEED_MULTIPLY, secs), 
		                SPEED_MAX);
		    else if (gameListener.isKeyDown(KEY_TOGGLE_DOWN))
		        speed = Math.max(speed / Math.pow(SPEED_MULTIPLY, secs), 
		                SPEED_MIN);
		}
		if (gameListener.isKeyDown(KEY_DENSITY))
        {
            if (gameListener.isKeyDown(KEY_TOGGLE_UP))
                density = Math.min(density * Math.pow(DENSITY_MULTIPLY, secs), 
                        DENSITY_MAX);
            else if (gameListener.isKeyDown(KEY_TOGGLE_DOWN))
                density = Math.max(density / Math.pow(DENSITY_MULTIPLY, secs), 
                        DENSITY_MIN);
        }
	
	    //Each star is moved forward, so dz is negative.
		double dz = -speed * secs;
		for (Star star : starList)
		{
			star.translateZ(dz);
		}
		genZ += dz;		
		generateStars();		
	}
	
	/**
	 * Generates stars with uniform density from the current value of genZ to 
     * MAX_GEN_Z. After the method is called, the new value of genZ will be 
     * greater than MAX_GEN_Z. 
	 */
	public void generateStars ()
	{
		/*
		 * Let e be EYES_Z, D be the density, and A be the area of the screen. 
		 * Suppose the nth star has a z-position of z_n, and
		 * a_n = (z_n - e)^3
		 * To generate the (n+1)th star with uniform density D, we have 
		 * a_n+1 = 3e^2 / DA + a_n
		 * and z_n+1 can be calculated from a_n+1.
		 */
		double increment = 3 * Perspective.EYES_Z * Perspective.EYES_Z 
		    / density / Screen.AREA;
		double zMinusE = genZ - Perspective.EYES_Z;
		double zMinusECubed = zMinusE * zMinusE * zMinusE;
		
		Rectangle2D.Double slice;
		double x, y;

		while (genZ < MAX_GEN_Z) {
			//Generate a random (x, y) in the slice of genZ
			slice = Perspective.calculateSlice(genZ);
			x = slice.x + Math.random() * slice.width;
			y = slice.y + Math.random() * slice.height;

			/*
			 * Make sure the resulting star will not fly into the screen.
			 * It cannot be true that both its x and y coordinates are within
			 * the screen. 
			 */
			if (!( Math.abs(x) <= (double) Screen.HALF_WIDTH + Star.HALF_SIDE &&
			    Math.abs(y) <= (double) Screen.HALF_HEIGHT + Star.HALF_SIDE ))
			{
				//Construct the star and add to starList
				starList.add(new Star(new Point3D(x, y, genZ)));
			}

			//Find a_n+1 and calculate new value of genZ
			zMinusECubed += increment;
			genZ = Math.cbrt(zMinusECubed) + Perspective.EYES_Z;
		}
	}

    /**
     * Sets the background color to black. Renders all the stars in this 
     * Starfield on the specified graphic. Removes all stars which are 
     * not onscreen and can no longer be rendered.
     */
    public void render (Graphics2D g) 
    {
        g.setColor(COLOR_BG);
        g.fillRect(- Screen.HALF_WIDTH, - Screen.HALF_HEIGHT, 
                Screen.WIDTH, Screen.HEIGHT);
        
        ListIterator<Star> iterator = starList.listIterator();
        Star star;
        while (iterator.hasNext())
        {
            star = iterator.next();
            if (star.renderIfOnscreen(g) == false)
                iterator.remove();
        }
    }
}