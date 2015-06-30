package state;

import java.util.ArrayList;
import event.KeyBindings;
import game.GameResources; import game.Renderable;

public class GameState extends FrameState implements KeyBindings
{	
    /**
     * Does nothing.
     */
    public void runInit (GameResources gr) {}
    
    public State runFrame (GameResources gr, double secs) 
    {
        long updateStart = System.nanoTime();
        
        gr.gameListener.update();
        gr.starfield.update(secs, gr.gameListener);
        
        //Assemble list of items to be rendered
        ArrayList<Renderable> renderItems = new ArrayList<Renderable>();
        renderItems.add(gr.starfield);
        
        if (gr.gameListener.isKeyDown(KEY_SPEED))
        {
            gr.hud.speedDisplay.update(gr.starfield);
            renderItems.add(gr.hud.speedDisplay);
        }
        if (gr.gameListener.isKeyDown(KEY_DENSITY))
        {
            gr.hud.densityDisplay.update(gr.starfield);
            renderItems.add(gr.hud.densityDisplay);
        }
        if (gr.gameListener.isKeyDown(KEY_HELP))
        {
            renderItems.add(gr.hud.helpDisplay);
        }
        if (gr.gameListener.isKeyDown(KEY_STATS))
        {
            renderItems.add(gr.hud.statsDisplay);
        }
        
        long renderStart = System.nanoTime();
        
        gr.renderEngine.render( renderItems.toArray(
                new Renderable[renderItems.size()]) );        
        
        long end = System.nanoTime();
        
        //StatsDisplay is always updated.
        gr.hud.statsDisplay.update(secs, (renderStart - updateStart) / 1e9,
                (end - renderStart) / 1e9);
        
        //Move to next state
        if (gr.gameListener.isTerminating())
            return gr.states.terminateState;
        else if (gr.gameListener.isKeyFirstPressed(KEY_PAUSE))
            return gr.states.keyPauseState;
        else
            return null;
    }


}

/*
GameMode extends FrameMode
constructor (GameListener gameListener, RenderEngine renderEngine)
	Calls super(gameListener) to store game listener
	Stores the rendering engine
	Constructs a Starfield.
Mode runFrame (double secs)
	Checks if the game is closing and returns a TerminateMode 
	if not and the game has lost focus, returns a FocusPauseMode
	If not, checks if the game has lost focus. If so, renderEngine will draw the PauseDisplay and returns a PauseMode
	If not, calls update(secs) and render() and returns null
void update (double secs)
	Updates the game listener.
	Next, uses keyDown to determine whether to toggle Starfield speed and density values.
	Finally, updates Starfield.
void render () 
*/
