package state;

import game.GameResources;

/**
 * The super class for states which run on a frame basis; that is, 
 * they execute a repeated action many times per second. Contains the gameloop.
 */
public abstract class FrameState extends State
{
	/**
	 * The target framerate of the game.
	 */
	public static final double FRAMERATE = 60;
	/**
	 * The closest integer to the number of nanoseconds that each frame should last.
	 */
	public static final long TARGET_TIME = Math.round(1e9 / FRAMERATE);
	
	/**
	 * Defines the runFrame()/sleep loop that repeats at a framerate equal to
	 * FrameMode.FRAMERATE.
	 */
	public State run (GameResources resources)
	{
		//The mode that follows this FrameMode in game execution.
		State nextMode = null;
		
		long startTime; //initial value of timer in ns, so timeTaken is System.nanoTime() - startTime
		long timeTaken; //The amount of time that the previous frame took in ns, NOT including time slept
		long sleepTime; //If timeTaken < targetTime, this is the time taken to sleep
		long sleepMillis; int sleepNanos; 
		
		//the amount of time the previous frame took in ns, INCLUDING time slept
		//The first frame is assumed to take targetTime ns, as an initial value
		long previousTime = TARGET_TIME; 
		
		runInit(resources);
		
		while (nextMode == null)
		{			
			startTime = System.nanoTime();	
			
			/*
			 * Call runFrame() with the amount of time that the previous 
			 * frame took, in seconds. Divide by 1e9 since previousTime is 
			 * in nanoseconds. Store resulting mode in nextMode.
			 */
			nextMode = runFrame(resources, previousTime / 1e9);
			
			timeTaken = System.nanoTime() - startTime;
			/*
			 * If the frame took less than targetTime nanoseconds, sleep for 
			 * the time difference.
			 */
			if (timeTaken < TARGET_TIME)
			{
				try
				{
					sleepTime = TARGET_TIME - timeTaken; 
					//split sleepTime into milliseconds and nanoseconds
					sleepMillis = sleepTime / 1000000;
					sleepNanos = (int) sleepTime % 1000000;
					Thread.sleep(sleepMillis, sleepNanos);
				}
				catch (InterruptedException e) {}
			}
			
			/*
			 * How long did all this take? Get difference in nanoTime 
			 * counter and the original starting time.
			 */
			previousTime = System.nanoTime() - startTime;
		}
		
		return nextMode;
	}
	
	/**
	 * Called by run() just before the first call to runFrame(). Subclasses
	 * must override runInit to perform any initialization work.
	 */
	public abstract void runInit (GameResources resources);
	
	/**
	 * The run() method aims to call this method FRAMERATE times per second. 
	 * If null is returned, execution stays in the same state. 
	 */
	public abstract State runFrame (GameResources resources, double secs);
	
}