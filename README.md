# Starfield

![Starfield Demo](http://i1278.photobucket.com/albums/y519/nitrile/starfield-demo_zpswinaewgn.png)

Starfield is a full-screen shooting star animation written in Java. It is based on the Starfield screensaver that came with earlier versions of Windows, but unlike the screensaver, it is interactive and comes with the abilities to:
* Change the speed and star density 
* Pause the animation

### Controls
Press H to display the in-game help. This brings up the list of available controls, which is also provided here:
* S: Display the speed
* D: Display the star density
* S+↑, S+↓, D+↑, D+↓: Increase or decrease values of speed or star density while they are displayed
* P: Pause the game
* Q, Esc: Quit application

### Download and run
This application requires [Java](http://java.com/inc/BrowserRedirect1.jsp?locale=en), so please ensure it is installed. Then, download and extract the .zip file from the [latest release of Starfield](https://github.com/squarespirit/starfield/releases). Finally, run Starfield.jar, which may be a little tricky:
* Double clicking on the file may work.
* Right-click the file, look for an "Open With" option, and select the Java runtime. (Such an option may not exist on all operating systems.)
* Open a terminal and enter `java -jar`, followed by the path to Starfield.jar. For example, if Starfield.jar is located in `C:\Documents\Starfield_v1.0`, enter `java -jar C:\Documents\Starfield_v1.0\Starfield.jar`.

### Under the hood
I used the state pattern to organize game logic into states. All states inherit from the `State` class and must define `run()`, which returns another instance of `State` to which control is passed. `FrameState` inherits directly from `State` and provides a gameloop. Its subclasses must define `runFrame()`, which is called 60 times per second by `run()`. These subclasses are `GameState`, which handles normal Starfield execution, and `KeyPauseState`, which handles execution when the game is paused using P. `TerminateState`, a subclass of `State`, represents the end of the game, and its `run()` method throws an exception.

`StateEngine` provides a state machine, which stores the current state in a value called `currentState`. While `currentState` is not an instance of `TerminateState`, its `run()` method is called, and the return value is assigned back to `currentState`.

The state pattern makes it easy to decouple logic of different states and transition between states. For more complex games, additional states can easily be added by creating subclasses of `State` or `FrameState`.
