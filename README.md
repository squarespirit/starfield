# Starfield

![Starfield Demo](http://i1278.photobucket.com/albums/y519/nitrile/starfield-demo_zpswinaewgn.png)

Starfield is a full-screen shooting star animation written in Java. It is based on the Starfield screensaver that came with earlier versions of Windows, but unlike the screensaver, it is interactive and comes with the abilities to:
* Change the speed and star density 
* Pause the animation

### Controls
Press H to display the in-game help. This brings up the full list of available controls, which is also provided here:
* S: Display the speed
* D: Display the star density
* S+↑, S+↓, D+↑, D+↓: Increase or decrease values of speed or star density while they are displayed
* P: Pause the game
* Q, Esc: Quit application

### Download and run
This application requires [Java](http://java.com/inc/BrowserRedirect1.jsp?locale=en), so make sure it is installed. Then, download and extract the .zip file from the [latest release of Starfield](https://github.com/squarespirit/starfield/releases). Finally, run Starfield.jar, which may be a little tricky:
* If you are lucky, double clicking on the file may work.
* Right-click the file, look for an "Open With" option, and select the Java runtime. (Such an option may not exist on all operating systems.)
* If all else fails, open a terminal and enter `java -jar`, followed by the path to Starfield.jar. For example, if Starfield.jar is located in `C:\Documents\Starfield_v1.0`, enter `java -jar C:\Documents\Starfield_v1.0\Starfield.jar`.

### Under the hood
I used the state pattern to organize game logic into states. All states inherit from the `State` class, and `StateEngine` provides a state machine. `GameState` handles normal Starfield execution, and `KeyPauseState` handles execution when the game is paused using P. `GameState` and `KeyPauseState` both inherit from `FrameState`, which provides a gameloop that repeats at 60 FPS. `TerminateState` stops the state machine.

The state pattern makes it easy to transition between states. For more complicated games, additional states can be easily added by creating subclasses of `State` or `FrameState`.
