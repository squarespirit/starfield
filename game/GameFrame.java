package game;

import java.awt.Container;
import javax.swing.JFrame;

public class GameFrame extends JFrame
{	
    /**
     * Constructs a new GameFrame which contains the specified GamePanel. 
     * Does not make the GameFrame visible; initialize all other game 
     * resources, then call setVisible(true) to make the GameFrame visible.
     */
    public GameFrame (GamePanel gamePanel)
    {
        super();
        setSize(Screen.WIDTH, Screen.HEIGHT);
        setUndecorated(true);
        setResizable(false);

        //event handling
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);

        //layout GamePanel using absolute layout
        Container c = getContentPane();
        c.setLayout(null);
        c.add(gamePanel);
        gamePanel.setBounds(0, 0, Screen.WIDTH, Screen.HEIGHT);
    }
}
/*
Functionality ===================================
Initializes full-screen game frame and game panel.
Initializes star generator
Displays stars streaming toward the user
P to pause
S to display speed
D to display star density
Up and down arrows to toggle speed or star density when either is displayed
Esc or Q to close

Constructors
GameMain.main(String[] args)
	new GameFrame()
		new GameListener(GameFrame gameFrame)
		new GamePanel(GameListener gameListener)
			new RenderEngine(GamePanel gamePanel)
			new ModeEngine(GameListener gameListener, RenderEngine renderEngine)
				new GameMode(GameListener gameListener, RenderEngine renderEngine)
					new Starfield(GameListener gl)
						new Star()
							new Point3D()
					new HUDItems(Starfield starfield)

Are static:
ScreenConstants
KeyBindings
Perspective

Construct themselves:
All HUD items

Run tree
GameMain.main(String[] args)
	GameFrame.run()
		GamePanel.run()
			ModeEngine.run()
--------------the above methods are only called once per game -----------
				GameMode.run() extends FrameMode.run() extends Mode.run()
					GameMode.runFrame(double secs)
						GameMode.update(double secs)
						GameMode.render()

Updating tree
GameMode.update(double secs)
	GameListener.update(double secs) (extends KeyDownAdapter.update(double secs))
	Starfield.update(double secs)

Game ===========================================
ScreenConstants 
	In a static initializer, uses toolkit to get fullscreen width and height
static final int WIDTH, HEIGHT, HALF_WIDTH, HALF_HEIGHT, AREA
	Self-explanatory fields of information about the dimensions of the screen

GameMain
	Contains the main method for the game
static void main (String[] args)
	Constructs a GameFrame, runs it, and then disposes it.

GameFrame extends JFrame
	The frame that is the container for a GamePanel
constructor ()
	Constructs GamePanel, and lays it out with no layout manager
	Constructs and adds GameListener to listen for frame events
	Sets size, visible, etc.
	A GameFrame does nothing on close
void run ()
	Calls run() on the GamePanel

GamePanel extends JPanel
	Provides the graphics context for the game
constructor (int width, int height, GameListener gameListener)
	Creates a GamePanel of the given size
	Constructs a RenderEngine that uses this GamePanel
	Constructs a GameMode using the given game listener. This mode is the one in which the game begins
Image getBufferImage()
void drawBufferImage(Image bufferImage)

RenderEngine
	Knows the GamePanel and organizes all rendering
constructor (GamePanel gamePanel)
	Stores the given GamePanel
void render (Renderable[] renderItems)
	Gets new buffer image from GamePanel, translates origin, renders all the Renderables in the array, and draws the 
	buffer image onto the GamePanel
	Order of Renderables in renderItems matters. Items will be rendered in the order provided.
void renderAgain (Renderable[] renderItems)
	Uses the current buffer image instead of getting a new one from GamePanel, renders all the Renderables in the array, and 
	draws the buffer image onto the GamePanel. Again, order matters

interface Renderable
	All items which are rendered *directly* by RenderEngine implement this interface. In particular, Starfield implements
	Renderable but Star does not, because only the Starfield and not the RenderEngine itself has access to Star. Furthermore,
	Star.render() is not void but returns a boolean.
void render (Graphics2D g)

Event listeners =================================
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

interface KeyBindings
	Contains arrays of ints with their functions. These are the keycodes of the keys that starfield pays attention to.
final int[] KEY_QUIT
	Q, Esc
final int[] KEY_PAUSE 
	P
final int[] KEY_SPEED
	S
final int[] KEY_STAR_DENSITY
	D
final int[] KEY_TOGGLE_UP
	Up arrow key
final int[] KEY_TOGGLE_DOWN
	Down arrow key

GameListener extends KeyFrameAdapter implements FocusListener, WindowListener 
	Listens for all events from the GameFrame, including key, focus, and window.
constructor (GameFrame frame)
	Calls super constructor for KeyFrameAdapter and adds this as focus listener and window listener
boolean hasFocus ()
	Returns whether the GameFrame currently has focus
boolean isWindowClosing ()
	Returns whether a window closing event has been registered

Modes ===========================================
ModeEngine
	Controls handling of mode information
	while (!(mode instanceof TerminateMode)) 
		mode = mode.run();

abstract Mode
	Parent class for all modes
abstract Mode run () 
	runs the mode and returns the next mode

abstract FrameMode extends Mode
	Parent class for modes which run on a frame basis; that is, a repeated action many times per second
static final double FRAMERATE = 60
constructor (GameListener gameListener)
	Constructs a FrameMode that knows the event listener for the game frame.
GameListener getListener ()
	Returns the associated game listener.
Mode run ()
	Defines the run-sleep loop that repeats at the specified frame rate. 
abstract Mode runFrame (double secs)
	This method is called in run() at the specified frame rate. The parameter secs specifies how many seconds long the 
	previous frame was. If runFrame returns a mode other than null, run() will return that mode and game execution 
	will enter that mode.

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

abstract PauseMode extends FrameMode
	Superclass for modes that pause the game. These are frame modes where runFrame() may return the previous mode.
	runFrame() must be defined in subclasses
constructor (Mode previousMode, GameListener gameListener, RenderEngine renderEngine)
	Calls super(gameListener) and stores the previous mode as private data
	Stores gameListener and renderEngine as private data
Mode getPreviousMode ()
	Returns the previous mode
RenderEngine getRenderEngine()
	Returns the render engine

KeyPauseMode extends PauseMode
	The resulting mode if the game was paused by *releasing* the pause key.
constructor (Mode previousMode, GameListener gameListener, RenderEngine renderEngine)
	super(previousMode, gameListener, renderEngine)
	Draws the PauseDisplay on the screen
Mode runFrame ()
 	If the game is closing, returns a TerminateMode
 	if not and the pause key was *released*, returns the previous mode
 	If not, returns null

FocusPauseMode extends PauseMode
	The resulting mode if the game was paused by losing focus
constructor (Mode previousMode, GameListener gameListener, RenderEngine renderEngine)
	super(previousMode, gameListener, renderEngine)
 *Does not* draw the pause display on screen.
Mode runFrame ()
 	If the game is closing, returns a TerminateMode
 	if not and the game has regained focus, returns the previous mode
 	If not, returns null

TerminateMode extends Mode
	Construction of this mode indicates the game is about to terminate
constructor ()
	Default empty constructor
Mode run ()
	Does nothing.

Heads-up display ================================
HUDItems
	A class containing one public reference to each HUD item.
SpeedDisplay speedDisplay
DensityDisplay densityDisplay
PauseDisplay pauseDisplay
HelpDisplay helpDisplay
	Public references to each HUD item
constructor (Starfield starfield)
	Constructs an instance of each HUD item. A reference to Starfield is needed to construct a SpeedDisplay and DensityDisplay.	

interface SingleLineText
	Common properties of the HUD items which are single lines of text
static final Font TEXT_FONT
	Bold monospace
static final FontMetrics TEXT_METRICS
static final Color TEXT_COLOR
	Green

SpeedDisplay implements Renderable, SingleLineText
	A display of the star speed in the top left corner of the screen.
	Speed: ###
constructor (Starfield starfield)
	Constructs a SpeedDisplay with a reference to Starfield.
void render (Graphics2D g)
	Draws the SpeedDisplay string in the appropriate location of the graphic

DensityDisplay implements Renderable, SingleLineText
	The star density is displayed one line below the speed.
	Star density: ###
constructor (Starfield starfield)
	Constructs a new density display which can access the starfield.
void render (Graphics2D g)
	Draws the DensityDisplay string in the appropriate location of the graphic

PauseDisplay implements Renderable, SingleLineText
	Displays:
	Pause--press P to resume
	near the bottom of the graphic.
	Contains positioning final constants.
constructor ()
void render (Graphics2D g)
	Draws the PauseDisplay string near the bottom of the graphic

HelpDisplay implements Renderable
	Lists the function of each key control. Has a translucent background and is rendered in the center of the GamePanel
static final Font TEXT_FONT
static final FontMetrics TEXT_METRICS
static final int PADDING, BORDER_THICKNESS, MARGIN
static final Color TEXT_COLOR, BORDER_COLOR, BACKGROUND_COLOR
	Font, color, and spacing properties of this multi-line help display
static final String[] HELP_CONTENTS
	The lines of text that are displayed in a HelpDisplay
constructor ()
	Pre-renders the help display on an appropriately-sized image
void render (Graphics2D g)

Star ============================================
Point3D
	A 3-tuple of floating-point numbers (x, y, z). Non-whole numbers allowed.
double x, y, z
	The point's coordinates, with public access
constructor (double x, double y, double z)
	Constructs a new point at (x, y, z).
translate (double dx, double dy, double dz)
	Translates the point by the given amounts in each direction.
PointAndScale project ()
	Projects the 3D point into a 2D PointAndScale
+X is to the right
+Y is down
+Z is into the screen
(0, 0, 0) is the center of the screen in the plane of the screen

PointAndScale extends Point
	An object combining a 2D point in the screen and a scale factor.
	Every PointAndScale corresponds to a single Point3D, and vice versa
	Unlike Point3D, the coordinates of the two-dimensional Point are integers. 
double x, y
int scale
	The coordinates of the 2D point and the scale, with public access
constructor (int x, int y, double scale)
	Constructs a new PointAndScale with point (x, y) and the given scale factor.

Perspective
	Contains static methods for manipulating 2D and 3D perspective.
static final double EYES_Z
	The z-position of the eyes. Should be a negative number
static double calculateScale (double z)
	Calculates the scale factor of an object with the given z-position
static double calculateZ (double scale)
	Calculates the z-position of an object with the given scale. The inverse function of calculateScale(double z).

Star
	A white square located at a position in 3-space
static final double SIDE_LENGTH
	The full-size side length of a star; its side length if it were displayed at a z-position of 0. 
static final Color COLOR_STAR
	The color of a star; white, obviously
constructor (Point3D point3D)
	Constructs a star at the given position in 3-space
void translateZ (double z)
	Translates the position of the star by the given amount z. (Negative values make the star look like it is streaming toward
	the screen.)
boolean renderIfOnscreen (Graphics2D g)
	Does not render the star and returns false if the star can't be rendered, which may occur if 1) Z < 0 (star is in front of the screen),
	or 2) X, Y are such that no portion of the star is visible on the screen.
	Otherwise, renders the star and returns true.

Starfield implements Renderable
	Knows a list of all the stars in the starfield, and can generate more stars.
static final double SPEED_START, SPEED_MIN, SPEED_MAX, SPEED_TOGGLE
static final double DENSITY_START, DENSITY_MIN, DENSITY_MAX, DENSITY_TOGGLE
	Values of speed and star density at the start of the game, their minimum and maximum values, and how much 
	the values change after	one call to toggleSpeed() or toggleDensity()
static final Color COLOR_BG
	Color of the background. Black, obviously
static final double MAX_GEN_Z
	The maximum z-position that stars are generated. This is the smallest Z-position at which a star is visible. 
	Since star sizes are rounded to the nearest integer, this is the z-position where the scale factor would 
	shrink a star so its side length is 0.5.
double getDensity ()
	Gets the current star density
double getSpeed ()
	Gets the current starfield speed
void toggleSpeed (boolean isUp)
	Allows toggling of speed both up and down
	If isUp is true, increases the speed a little bit up to the maximum speed
	If isUp is false, decreases the speed a little bit down to the minimum speed
void toggleDensity (boolean isUp)
	Similar to toggleSpeed, except with the value of star density
void update (double secs)
	Generate more stars by incrementing the current z-position of star generation by regions of constant volume.
void render (Graphics2D g)
	Sets the background black and calls renderOnscreen() on every star in the Starfield, using the provided graphic.
 */
