package event;
import game.GameFrame;
import java.awt.event.KeyAdapter; import java.awt.event.KeyEvent;
import java.util.LinkedList; import java.util.Arrays; 
import java.util.ListIterator;

/**
 * Determines whether keys were recently pressed down or released.
 */
public class KeyFrameAdapter extends KeyAdapter
{
	/* All keys supported by this KeyFrameAdapter have key codes less
	 * than MAX_KEY. */
	public static final int MAX_KEY = 256;
	/*
	 * If a KeyPressed event occurs within this number of milliseconds after
	 * a KeyReleased event, neither down[] nor released[] for that key is
	 * altered.
	 */
	public static final long RELEASE_DELAY_MS = 15;
	
	private boolean[] firstPressed, down, released;
	private LinkedList<KeyEvent>
	/**
	 * A queue of all incoming events, oldest first. All events in the queue 
	 * have key code less than MAX_KEY.
	 */
	incomingEvents,
	/**
	 * Key release events popped from incomingEvents are temporarily added 
	 * to this list. Events are sorted in the queue oldest first. Since all 
	 * events were first popped from incomingEvents, they are all older than 
	 * the oldest event in incomingEvents.
     * 
     * Release events are removed from this queue if
	 * 1) A key press event on the same key occurs within a specified delay, 
	 * in which case neither event has an effect
	 * 2) That doesn't happen, in which case that key is deemed "released"
	 */
	pendingReleaseEvents;
	
	/**
	 * Construct a new KeyFrameAdapter, adding itself to the given component 
	 * as a key listener.
	 */
	public KeyFrameAdapter (GameFrame g)
	{
		g.addKeyListener(this);
		firstPressed = new boolean[MAX_KEY];
		down = new boolean[MAX_KEY];
		released = new boolean[MAX_KEY];
		incomingEvents = new LinkedList<KeyEvent>();
		pendingReleaseEvents = new LinkedList<KeyEvent>();
	}
	
	/**
	 * Return true if the key code is within bounds and the corresponding
	 * key was first pressed as of the most recent update.
	 */
	public boolean isKeyFirstPressed (int keyCode)
	{
        if (keyCode < MAX_KEY) {
            return firstPressed[keyCode];
        }
        return false;
	}
	
	/**
	 * Return true if *any* of the keys in the given array were first pressed
	 * as of the most recent update.
	 */
	public boolean isKeyFirstPressed (int[] keyCodes)
	{
        for (int keyCode : keyCodes)
            if (isKeyFirstPressed(keyCode))
                return true;
        return false;
	}
	
	/**
	 * Return true if the key code is within bounds and the corresponding
	 * key is down as of the most recent update.
	 */
	public boolean isKeyDown (int keyCode)
	{
		if (keyCode < MAX_KEY) {
			return down[keyCode];
		}
		return false;
	}
	
	/**
	 * Returns true if *any* of the keys in the given array were held down as
	 * of the most recent update.
	 */
	public boolean isKeyDown (int[] keyCodes)
	{
		for (int keyCode : keyCodes)
			if (isKeyDown(keyCode))
				return true;
		return false;
	}
	
	/**
	 * Returns true if the key code is within bounds and the corresponding
	 * key is released as of the most recent update.
	 */
	public boolean isKeyReleased (int keyCode)
	{
		if (keyCode < MAX_KEY) {
			return released[keyCode];
		}
		return false;
	}
	
	/**
	 * Returns true if *any* of the keys in the given array were released as
	 * of the most recent update.
	 */
	public boolean isKeyReleased (int[] keyCodes)
	{
		for (int keyCode : keyCodes)
			if (isKeyReleased(keyCode))
				return true;
		return false;
	}
	
	/**
	 * Updates the arrays of which keys are down and which keys 
	 * are released by polling key events from the incomingEvents queue.
	 * Adds key released events to pendingReleaseEvents and determines
	 * whether they take effect.
	 */
	public void update ()
	{	    
	    /*
		 * All keys that were released or first pressed in the previous frame 
		 * are no longer so in this frame.
		 */
	    Arrays.fill(firstPressed, false);
		Arrays.fill(released, false);
		
		ListIterator<KeyEvent> pendReleaseIterator;
		long timeDiff;		
		KeyEvent incomingEvent, pendReleaseEvent;
		
	    //Get the time just before obtaining the lock
        long updateTime = System.currentTimeMillis();
        
        synchronized (incomingEvents)
        {
    		while ((incomingEvent = incomingEvents.poll()) != null)
    		{
    		    //Store incoming key released events in pendingReleaseEvents
    		    if (incomingEvent.getID() == KeyEvent.KEY_RELEASED)
    		    {
    		        pendingReleaseEvents.add(incomingEvent);
    		    }
    		    else if (incomingEvent.getID() == KeyEvent.KEY_PRESSED)
    		    {
    		        /*
    		         * Do the key press event anyway; if the key was recently
    		         * released, its entry in down[] is true anyway.
    		         */
                    doPressEvent(incomingEvent);

    		        pendReleaseIterator = pendingReleaseEvents.listIterator();
    	            /*
    	             * Search through the released event queue to see if the 
    	             * same key was recently released.
    	             */
    	            while (pendReleaseIterator.hasNext())
    	            {
    	                pendReleaseEvent = pendReleaseIterator.next();	
                        timeDiff = incomingEvent.getWhen() - 
                                pendReleaseEvent.getWhen();
                        
                        //Release event delay has passed. Do release event
                        if (timeDiff > RELEASE_DELAY_MS)
                        {
                            pendReleaseIterator.remove();
                            doReleaseEvent(pendReleaseEvent);
                        }
                        /*
                         * Otherwise, delay has not passed yet. If this press 
                         * event happened right after a release
                         * event of the same key, disregard both events.
                         */                        
                        else if (pendReleaseEvent.getKeyCode() == 
                                incomingEvent.getKeyCode())
    	                {    	 
    	                    pendReleaseIterator.remove();
    	                }
    	            }//end iteration through pendingReleaseEvents
    		    }//end if key event id == KEY_PRESSED
    		}
        }
        
        /*
         * Compare times of pending release events to updateTime to see if
         * delay has passed by the end of the frame. 
         * (Without this, pending release events would not be removed if 
         * there were no other key events.)
         */
        pendReleaseIterator = pendingReleaseEvents.listIterator();
        while (pendReleaseIterator.hasNext())
        {
            pendReleaseEvent = pendReleaseIterator.next();
            //Release event delay has passed. Do release event
            if (updateTime - pendReleaseEvent.getWhen() > RELEASE_DELAY_MS)
            {
                pendReleaseIterator.remove();
                doReleaseEvent(pendReleaseEvent);
            }
            else
                /*
                 * If delay has not passed for this event, it will not have
                 * passed for later events. So no other release events in 
                 * the queue can be processed.
                 */
                break;
        }
	}
	
	/**
     * Updates down[] to reflect that the key of the specified pressEvent 
     * has been released. If the value in down[] was previously false,
     * also updates firstPressed[]. Assumes specified KeyEvent has key 
     * code less than MAX_KEY, and the KeyEvent is a key press event.
     */
    private void doPressEvent (KeyEvent pressEvent)
    {
        if (down[pressEvent.getKeyCode()] == false)
        {
            firstPressed[pressEvent.getKeyCode()] = true;
        }
        down[pressEvent.getKeyCode()] = true;
    }
    
	
	/**
	 * Updates down[] and released[] to reflect that the key of the 
	 * specified releaseEvent has been released. 
	 */
	private void doReleaseEvent (KeyEvent releaseEvent)
	{
        down[releaseEvent.getKeyCode()] = false;
        released[releaseEvent.getKeyCode()] = true;
	}
	
	/**
     * Obtains a lock on the incomingEvents queue and adds the event to the 
     * queue.
     */
	public void addIncomingEvent (KeyEvent event)
	{
        if (event.getKeyCode() < MAX_KEY)
        {
            synchronized (incomingEvents)
            {
                incomingEvents.add(event);
            }
        }
	}
	
	/**
	 * Obtains a lock on the incomingEvents queue and adds the event to the 
	 * queue.
	 */
	public void keyPressed (KeyEvent event)
	{
	    addIncomingEvent(event);
	}
	
    /**
     * Obtains a lock on the incomingEvents queue and adds the event to the 
     * queue.
     */
	public void keyReleased (KeyEvent event)
	{
        addIncomingEvent(event);		
    }
}

/*
KeyFrameAdapter extends KeyAdapter
	KeyAdapter with additional capability to track which keys are held down or released
constructor (GameFrame gameFrame)
	Calls component.addKeyListener(this)
void update (double secs)
	Updates arrays of which keys are down and which keys are released by polling key events from the list of incoming key events
boolean isKeyDown (int keyCode)
	Returns whether the key was held down, as of the most recent update
boolean isKeyDown (int[] keyCodes) 
	Returns whether any of the keys in the array were held down
boolean isKeyReleased (int keyCode)
	Returns whether the key was released, as of the most recent update
boolean isKeyReleased (int[] keyCodes)
	Returns whether any of the keys in the array were released
*/
