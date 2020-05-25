Game
Authors: Kevin Rossel, Samuel Leistiko, and Andres Carranza Moreno
Revision: 5/25/2020


Introduction: 
[In a few paragraphs totalling about ½ page, introduce the high-level concept of your program. What this looks like depends a lot on what type of thing you are making. An introduction for an application will look different than one for a game. In general, your introduction should address questions like these:
What does your program do?
What problem does it solve? Why did you write it?
What is the story?
What are the rules? What is the goal?
Who would want to use your program?
What are the primary features of your program?]




This program will be a 2D game where the objective is to destroy your opponents by reducing their hitpoints to zero. You, the player, will be spawned into an arena that contains walls and enemies. Enemies will only be visible if they are in your direct line of sight (ie not behind a wall). Enemies will either be a CPU controlled programmed bot, neural network bots(possibly), or another human playing the game. We hope that this program will solve the problem of boredom, as lots of people have a lot of extra free time right now. We wrote this game because we were assigned a final project in our APCS class, and this program seems like it will be a fun challenge to write, and will have a cool end product. The rules of this game are simple; try to destroy your enemies without being destroyed yourself. Our game does not have a well defined story, it is simply a PVP battleground that is meant to be a fun way to spend some time. The only rule of this game is that you cannot use any internal/external software or hardware to modify the game’s memory in a way that gives you an unfair advantage over others. The group of individuals who would be most likely to use our program are bored people, because they are looking for an entertaining way to spend some of their time. The primary features of our program are moving, shooting, and taking Epic W’s.




Instructions:
LBUTTON - Select/Fire
W - Move up
A - Move left
S - Move down
D - Move right
ESCAPE - Exit


Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
[These are features that we agree you will definitely have by the project due date. A good final project would have all of these completed. At least 5 are required. Each feature should be fully described (at least a few full sentences for each)]
* A menu with clickable buttons that change the state of the game. 
* A game environment with basic obstacles like walls that will block actors and bullets from moving through, as well as obstructing the player’s line of sight.
* Enemies that act and deal damage to the player so that the game can end when your health reaches zero
* A settings page that lets the user change different aspects of the game.
* Single Player mode where the player can move, shoot, and die while trying to destroy bots.


Want-to-have Features:
[These are features that you would like to have by the project due date, but you’re unsure whether you’ll hit all of them. A good final project would have perhaps half of these completed. At least 5 are required. Again, fully describe each.]
* Map files so we don’t have to hard code maps into different classes.
* Multiplayer networking where players can fight against each other over LAN and the internet.
* Neural network bots where the user can train their own bots and even watch them compete in real time. Might use reinforcement learning.
* Different types of weapons, tools, and abilities that the player can use like grenades, placeable walls, and player “xray” that shows the enemy players position temporarily. 
* Destructible environment objects that can be damaged by weapons or items like grenades.


Stretch Features:
[These are features that we agree a fully complete version of this program would have, but that you probably will not have time to implement. A good final project does not necessarily need to have any of these completed at all. At least 3 are required. Again, fully describe each.]
* Map editor. Allows a user to modify a map file and export it as a new one. The user should also be able to play this map with their friends by automatically sending the file over the network.
* Client side anti cheat that makes sure the user doesn’t try to mess with the games memory in weird ways.
*  An adjustable aimbot that can lock onto enemies and improve the user’s aim to varying degrees. Hopefully to assist the player when against super hard bots.
* Ability to play with people outside LAN




Class List:
[This section lists the Java classes that make up the program and very briefly describes what each represents. It’s totally fine to put this section in list format and not to use full sentences.]


Launcher - Just the main()
Game - Main game loop. Stores instance of singleplayer, multiplayer, and local player
Entity - Abstract class that represents an entity
LocalPlayer - The local player
RayBullet - A ray casted bullet
Zombie - A Zombie
Screen - Represents a screen
MainMenu - The main menu
Settings - The settings screen
Singleplayer - Single player instance (keeps track of entities)
Button - A button
Colors - Global color values
RayCasting - A ray casting utility class
Vector2D - A 2D vector


Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
* List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
* Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]


Kevin - Initial game base and debugging
Sam - GUI, in game classes, sound
Andres - Enemies and gameplay


Resources Used:
Random sound effects from YouTube