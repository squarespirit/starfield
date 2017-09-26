# Starfield

![Starfield Demo](http://i1278.photobucket.com/albums/y519/nitrile/starfield-demo_zpswinaewgn.png)

Be a space cadet as you speed through this 3D, full-screen starfield! The animation is interactive, and you can:
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
Get the `.zip` file from [Releases](https://github.com/squarespirit/starfield/releases). It contains a `.jar` file which can be run directly.

### A bit about how it works
The project is written in Java from scratch with the standard library, with no third-party game frameworks.

A state machine is used to separate logic for different parts of the game (normal execution, pausing, etc.). I thought this made it easier to organize the logic of each state and transitions between states. This is done in the `State` package, and the used states are:
- `FrameState` is a superclass for all `State`s that run something 60 times per second, i.e. every frame.
- `GameState` is a `FrameState` which handles normal execution of the game; it updates and renders the Starfield each frame.
- `KeyPauseState` is a `FrameState` that takes over when the game is paused by pressing P.
- `TerminateState`: When the current state is an instance of `TerminateState`, the application cleans up and quits.

Projection of stars from 3D space to the 2D screen was also done from scratch. In the `star` package, `Point3D` is a general class for 3-dimensional points. A `Point3D` can be `project()`ed down to a `PointAndScale`, which represents a 2D screen coordinate with a relative scale (nearer objects are larger).

Note that the 3D space that the viewer can see from their screen is limited to the view frustum (https://en.wikipedia.org/wiki/Viewing_frustum), which is shaped like the bottom portion of a pyramid. One of the most challenging parts of this project was to generate random 3D points in this funky shape in a way that looks uniform, without generating points outside the shape (since they wouldn't be visible). Remarkably, it's doable! This is implemented in `star.Starfield.generateStars()`.
