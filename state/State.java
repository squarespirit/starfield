package state;

import game.GameResources;

/**
 * The superclass for all game states.
 */
public abstract class State 
{
	/**
	 * Runs this state with access to the specified StateResources, and 
	 * returns the next state which should have control.
	 */
	public abstract State run (GameResources gr);
}