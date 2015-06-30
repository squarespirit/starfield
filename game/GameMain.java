package game;

import javax.swing.JOptionPane;

import state.StateEngine;

public class GameMain {
    
	public static void main (String[] args)	{	
	    
	    //Use hires thread
        Thread hiResThread = new Thread () {
            public void run () {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                }
                catch (Exception e) {}
            }
        };
        hiResThread.setDaemon(true);
        hiResThread.start();
        
        GameResources gr = null;
        try {
    		//Initialize all game resources
    		gr = new GameResources();
    		
    		//Initialize state engine
    		StateEngine stateEngine = new StateEngine();
    				
    		//Run the game
    		stateEngine.run(gr, gr.states.setupState);
    	
        } catch (Exception e) {
            //Allow all exceptions to propagate up
            
            //Display an error dialog            
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            
        } finally {
            if (gr != null) {
                if (gr.gameFrame != null) {
                    gr.gameFrame.dispose();
                }
            }
        }
	}
}