package state;

import game.GameResources;

public class StateEngine 
{	
	/**
	 * Acts as a state machine for the game. Begins at the specified first 
	 * state, runs the current state with the specified GameResources,
	 * and stores the returned state as the new currentState. The method
	 * ends when a TerminateState is returned.
	 */
	public void run (GameResources gr, State firstState)
	{
	    State currentState = firstState;
		while (! (currentState instanceof TerminateState)) 
		    currentState = currentState.run(gr);
	}

}
