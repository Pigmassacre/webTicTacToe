webTicTacToe aka Tic Tac Pro
============
Online multiplayer Tic-Tac-Toe game in your browser, using Java EE with Jersey / Atmosphere.

WebTicTacToe is a Java Web Application that runs on GlassFish 3.1.2. It uses the Atmosphere framework together with Jersey to create an online, realtime multiplayer Tic Tac Toe service. Players can register accounts, login and play games of Tic Tac Toe versus each other. 

Programmed for the course Web Applications at Chalmers / Gothenburg University.

Clarifications
==============
In the TicTacToe model, the main class is Lobby.java. It takes care of all the other parts of the model:

* Logging in / out users.
* Registration, which creates a new player object (which is saved to the database).
* Finding games, which maps two players to a Game object by using a GameSession object.
* Provides methods for the WebApp to access lists of active/online players and such.

NOTE: The Game.java class is NOT the main class of the model, it is a GameBoard (or perhaps a GameInstance).

NOTE: GameSession is a "wrapper" which wraps two player objects to a Game object. This GameSession is then used by the WebApp to perform moves, access the gameboard and see if a player has won or not.

Notes
=====
We've had some merge problems, so code blame may have shifted inadvertently.
Some clarifications (everything else should be OK in gitinspector):

* Tests have been written by Anton Westberg.
* Website design and most javascript code have been made by Emil Eriksson. Some javascript code (most stuff that has to do with Atmosphere) has been written by Olof Karlsson.
* GameCanvas.js and all game graphics have been made by Kristofer Yffén.
* The REST / Atmosphere backend has been written by Olof Karlsson.
* The model was written by everyone (not everyone has commits to it though).
* Documentation was written by everyone, though all model images were made by Kristofer Yffén.
