package state;

import event.KeyBindings;
import game.GameResources; import game.Renderable;

/**
 * The mode where keys are 
 */
public class KeyPauseState extends FrameState implements KeyBindings
{
    /**
     * Renders the pause display using the current graphic.
     */
    public void runInit (GameResources gr) 
    {
        Renderable[] renderItems = {gr.hud.pauseDisplay};
        gr.renderEngine.renderAgain(renderItems);
    }

    /**
     * If the game is terminating, return a TerminateState. Else if 
     * the pause key is *released*, resume the game and return a 
     * GameState. Otherwise, stay in KeyPauseState and return null.
     */
    public State runFrame(GameResources gr, double secs) 
    {
        gr.gameListener.update();
        
        if (gr.gameListener.isTerminating())
            return gr.states.terminateState;
        //Unpause
        else if (gr.gameListener.isKeyFirstPressed(KEY_PAUSE))
            return gr.states.gameState;
        else
            return null;
    }

}
