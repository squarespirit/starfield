package state;

import game.GameResources;

/**
 * This state indicates the game is about to terminate. 
 * Calls to run() are not supported.
 */
public class TerminateState extends State {

	/**
	 * Throws an UnsupportedOperationException.
	 */
	public State run (GameResources gr) {
	    throw new UnsupportedOperationException(
	            "Calls to TerminateState.run() are not supported");
	}

}
