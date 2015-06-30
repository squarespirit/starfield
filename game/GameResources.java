package game;

import java.io.IOException;

import huditems.HUDItems;
import event.GameListener;
import star.Starfield;
import state.States;

/**
 * Contains public references to important game resources. An instance of
 * GameResources is passed from each State to the next.
 */
public class GameResources {
    
    public GameFrame gameFrame;
    public GamePanel gamePanel;
    public GameListener gameListener;
    public RenderEngine renderEngine;
    public Starfield starfield;
    public HUDItems hud;
    public States states;
    
    /**
     * Initializes all game resources. 
     */
    public GameResources () throws IOException {
        gamePanel = new GamePanel();
        gameFrame = new GameFrame(gamePanel);
        gameListener = new GameListener(gameFrame);
        renderEngine = new RenderEngine(gamePanel);
        starfield = new Starfield();
        hud = new HUDItems();
        states = new States();
    }    
}
