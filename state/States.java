package state;

/**
 * Contains public references to an instance of each state.
 */
public class States 
{
    public SetupState setupState;
    public GameState gameState;
    public KeyPauseState keyPauseState;
    public TerminateState terminateState;
    
    /**
     * Initializes all states.
     */
    public States () 
    {
        setupState = new SetupState();
        gameState = new GameState();
        keyPauseState = new KeyPauseState();
        terminateState = new TerminateState();
    }
}
