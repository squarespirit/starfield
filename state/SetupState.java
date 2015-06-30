package state;

import game.GameResources;

/**
 * Perform setup actions on game resources, just before the application
 * begins.
 */
public class SetupState extends State
{
    public State run (GameResources gr)
    {
        gr.gameFrame.setVisible(true);
        return gr.states.gameState;
    }
}
