# Assignment 3 – Tank Game  
# CSC 413 Project Documentation Summer 2020

## Student Name  : Rajdeep Singh
## Student ID    : 920258882


## Project Information
#### This documentation will give you in-depth information about the term project. Each section will provide you with a general overview of the tank game project. Some sections are extremely important such as How to Build/Import Project, “How to Run the Project.” “Game Rules and Guide” and Game Control.” Also, read the class descriptions of the tank game of specific classes carefully because it explains each class in detail. This project was a little hard due to less time period. This documentation will reflect on my struggles and time managed to complete the tank game with proper Object-Oriented Principle (OOP) during the project.

## Introduction
#### The goal of this term project was to build a 2D tank game written in Java language. The tank game goal was to destroy the enemy tank. The Player needs to bring the other tank player life to zero. A player who brings another player life to the zero twice that, a person will win the game. This was my first game. I never built or coded the game before. I did so much research on the Object-Oriented Principle (OOP). This tank game includes bullet, health, lives, walls, etc. The tank game was implemented in java, where great attention was paid to ensure that good OOP and Single Responsibility Principle was followed.

## Background Overview
#### The tank game was implemented with a set of requirements defined by instructor Anthony Souza. The main purpose of this program is to implement a java tank game with two players. Each tank has two lives and unlimited bullets. Each tank has lives with 100 points health. Tank game includes special items such as speed booster, and health picks up items. Speed booster Players need to destroy the enemy tank in order to win. The tank game has a split-screen and mini-map so that each player can track their tanks. I do not add any sound effects because after adding the sound, I noticed some lag while loading and playing games. The tank game has two health bars for each tank that show on top of the screen. This term project was a good practice of Object Oriented Principle (OOP) because this tank game was created from scratch without using any game engine such as unity, unreal, etc.

## Technical Overview
#### The term project consisted of writing java code to build a tank game. In this program, there has one “TankGame” package with 12 java classes. Most programmers use C# and C++ because it is a popular in-game engine such as Unreal Game, Unity, etc. In this team, I am using JAVA programming language, which provides rich features to design and build feature-rich 2D and 3D games. I do not add any sound effects because after adding the sound, I noticed some lag while loading and playing games. My game performance got smooth after removing the sound effects. Most of the time, I made the draft class diagrams of projects because it gives some idea of java classes and sub-classes.

## Scope of Work
#### The scope of my work was to practice and work with decent Object Oriented Principle (OOP) principles and structure of the project. My focus during the project was to write a minimize line of code, and the game should play smoothly. I follow the fundamental principles and different methodology.

## Summary of Work Completed
#### > Two players tank game.
#### > Breakable wall (wall which can break by tanks) Unbreakable Wall (wall which cannot break by tanks) Bullet fire (Bullet which need to attack on each other. Start and end menu screen.
#### > Tank game has Split screen to track each player tanks. Mini map to track walls, special items, etc.
#### > Display health, special items, etc.
#### > Tank can move left, right, forward and back direction. Bullet fire in x-axis
#### > Smooth performance
#### > Special items drop

## Development Environment
#### For this project, I use IntelliJ IDEA Ultimate to compile it, java version 2020.1.2. Java version 13.0.2 was used to develop this project in IntelliJ IDEA was used to develop this project.

## How to Build/Import your Project
#### To be able to build the project in IntelliJ IDEA Ultimate, go to GitHub link where you can see the repository is located. Copy the link to import, or you can download the zip file containing the whole project.
#### Click on “Clone or Download” button. Then select the HTTPS or SSH if you have an SSH keys set up.
#### Once the repo is cloned, follow the following steps to import your project into IntelliJ. Steps are very easy, just follow the direction.
#### Click on “Import Project”. On the next screen, select where you extracted the zip file and click “OK”.
#### Click on “Create project from existing resources” and then click “Next” button.
#### Leave the all default fields alone. Also, check for project format matches and then click “Next”.
#### Click “Next”.
#### Mark both items. One is the source code folder and second folder contain source of unit tests. Then, click on “Next” button.
#### Click “Next” and all default fields can be left alone here.
#### If you see a screen like this then click “Next” otherwise java JDK need to be installed before going forward. If you could not see this screen then click on + symbol on the top left. After JDK add click “Next”.
#### Click “Next” because this window should empty because we do not have any framework.
#### Right click on “resources” folder, then click “Mark Directory as” and then click on “Resources Root”. For “src” folder, do the same thing and then click on “Sources Root”.
#### Another way, go to the project structure and make sure folder name “src” is selected as a “Source Root” and “resources” folder is selected as a “Resources Root” as show in the above.
#### Click “Build” and then “Build Project” to build the project.

## How to Run the Project
#### To run the project. We can run the project with two different types. The first option is to run the game through the “GameWorld” class, and the second option is to run the game with the jar. Both options are similar and straightforward.

## Run the game
#### First option. Running the game with “GameWorld” class.
#### Right click on “GameWorld” and then click “Run ‘GameWorld.main()’.
#### Option two. Running the game with jar file.
#### Right click on “csc413-tankgame-Rajdeep0303.jar” and then click “Run ‘csc413-tankgame- Rajdeep0303.jar’.
#### Click “START” to start game or “HELP” button to learn rulers and control.

## Game Rules and Guide
#### The goal of the tank war is for two players to fight until the end.
#### Players need to bring the other player life to the zero in order to win.
#### Each tank has three lives and 100 health points.
#### Each bullet hit at 10 point damage.
#### The tank will respawn at its original position when tank health gets to zero.
#### The breakable wall will be destroyed in 2 hits. The health of the breakable wall is 20 points. This game includes two different special items such as health items and speed booster items.

## Game Controls

#### Up, Down, Right, Left, and Shoot button for players

####    Player One
#### 1. Up arrow key to move the tank up or attack when a player in the up direction
#### 2. Down arrow key moves the tank down or attack when a player in down direction.
#### 3. Left arrow key to move the tank left or attack when a player in the left direction.
#### 4. Right arrow key to move the tank right or attack when a player in the right direction.
#### 5. Return the key to shoot the player or break the walls.

####    Player Two
#### 1. W key to move the tank up or attack when a player in the up direction
#### 2. S key move the tank down or attack when a player in the down direction.
#### 3. A key to move the tank left or attack when a player in the left direction.
#### 4. D arrow key to move the tank right or attack when a player in the right direction.
#### 5. Q key to shoot the player or break the walls.

## Assumption Made
#### • Two players 2D game build on JAVA.
#### • Tank game has requirements assign by instructor in his documentation.
#### • Breakable Wall
#### • Unbreakable Wall
#### • Special feature
#### • Player can move left, right, back and forward direction.
#### • Mathematics used in this game.
#### • Online material which are provided by professor.
#### • Generated the .jar file.

## Tank Game Class Diagram and Implementation Discussion
#### The tank game shared a lot of classes. The figure below shows the Tank Game class diagram. GameObject class is extended by various classes such as Bullet, BreakableWall, PowerUp, Wall, Tank, etc. The wall class is used to make border and background walls. BreakableWall class is used to create temporary walls. TankControl is used to control the tanks with keyboard keys. Menu class controls all the display and text button in the main menu. GameOverMenu class controls all the display and text buttons in the end screen menu. I was so careful about button areas because most of the time, we cannot be able to click the button due to position. The MouseRead class is used to read mouse input and select the appropriate option to select. The GameWorld class controls the status of the tank game. CollisonHandler class handles the collisions between the various object. The tank class triggers the launch of a bullet via the Bullet class constructor. The GameObject class provides main functions such as the position of tank, a special object, etc. For example, x and y positions, angle, and velocities. The PowerUp class consists of the speed boost and health boost. I was checking on Inheritance, Encapsulation, and Polymorphism that if I am following it or not. All classes were enforced with proper access modifier to check if all open for extension or not but also closed for modification. The subclass was to inherit the superclass. Mainly, the sub-class was designed to substitute for third base. In the “Description of Tank Game-Specific Classes” I described all classes in much great detail use in Tank Game.

## Description of Tank Game Specific Classes
## Bullet Class
#### This “Bullet” class extends “GameObject” because it implements the functionality to the abstract class of “GameObject.” I used the String to indicate the owner of the bullet. I used an isInActive Boolean expression variable to mark if a bullet is inactive or not. Inactive bullets get removed from the game object array list in the main method of the “GameWorld.” Also, I store three images in this class as a static field. Three images are explosion_img, bullet_img, and big_explosion_img. The explosion_img is used to show the image of an explosion that appears on the breakable wall. The big_explosion_img use to show the image of a big explosion when tanks shoot each other. The Bullet_img is used to show the image of the bullet.

## Breakable Wall
#### The “BreakableWall” class represents a breakable wall. The “BreakableWall” class adds functionality to the wall. The “BreakableWall” class extends the class of “Wall” because it adds some extra function. I used the Boolean expression variable to mark it as dead or alive, and later on, it can clear up and remove from the game object array list.

## CollisionHandler Class
#### The “CollisionHandler” class is responsible for handler the various variables in the tank game. This “CollisionHander” has a single method. HandleCollisions(ArrayList<GameObject> game_object) was the only single method used in this class. I used the nested loop in this method to get an object and compare them. This method takes the array list of the game object and handles the collision. And then give a new array list of a game object.

## GameObject Class
#### The “GameObject” class is an abstract class that holds x and y location and x and y velocities as well. This “GameObject” class includes three abstract methods such as collision(), drawImage(Graphics2D), and update(). All three abstracts method used by the GameObjects in this game. I used the rectangle, which is pivotal to implementation in the collisions. The setters and getters from the old variables but without rectangle because it needs getter.

## GameWorld Class
#### The “GameWorld” class represents the game world. This “GameWorld” class is an entry point of our game. This class handles the creation and also maintenance of the game. In the main method, I used the loop over an array list of game objects. Also, it updates them, used to set the Boolean to check if the tank is dead or alive, or need to be respawning, and it regularly updates the display. I made the game map in this class, int[] new_map_array. The map width was 48, and the height was 60. I used “1” as a permanent wall, “2” as a temporary or breakable wall, and “0” as a background image. The init() method was used to initialize other maps, game relating items such as a special object, creatures, tank, player, player control, etc. The init() methods were used to read and set the many images to provide the animations and various displays. Also, the drawImage() method was used to display the whole tank game. For example, drawImage() handles the display of tanks, players, a number of lives, displaying the health score and displaying end menu images where a person can restart or exit the game. I used the enumeration variable to store and monitor the state of the game.

## Wall Class
#### The “Wall” class extends the abstract “GameObject” class and also implements the abstract methods. This class allows the creation of the wall object. This class has a single Boolean variable that used to indicate the particular instance of the “Wall” class represents a wall or background image as well. Also, the “Wall” class extends GameObject,” and each wall has an x and y location, which initialized by the wall constructor.

## PowerUp class
#### The “PowerUp” class extends “GameObject” since it has a rectangle, image, and x and y location. I used two Boolean expression variables, such as isSpeedBoost and isHealthBoost. Both Boolean variable control over which image should be displayed and control the collision. I only used two special feature pickups items. Also, I used the Boolean variable isActive to false because during the game, when PowerUp object such as isSpeedBoost and isHealthBoost has to be used then, it will allow us to remove the used PowerUp object from the game.

## Tank Class
#### The “Tank” class is very important and the main class. This class extends the “GameObject” class and add other lots of functions. This class sets up the tank control, such as moving left, right, backward, and forwards. Mainly, this class used to draw the tank in the game, and it tracks when the tank dies, moves, turns, etc. I used Boolean expression variables, which were already set in the “TankControl” to move the tank in the update method. This class has methods for rotation and moving in various directions. Also, I set up the time variable to control the speed of the boost. I set up the speed around 1 second.

## TankControl Class
#### The “TankControl” class is responsible for manage the user inputs from the keyboard. This “TankControl” class implements KeyListerner interface because other methods such as keyPressed(), keyReleased() and keyTyped() methods also need to be implements. I used private final ints to hold a button from the keyboard. All methods for these data fields are set to Booleans because they have to been released or pressed.

## Menu Class
#### The “Menu” Class represents the start menu state of the game. This class displays the button in rectangles shape. Also, the button includes text, which is easy to use the options in the start menu. I implement the function of a start menu within the game. This class is just used to display the menu buttons of the game. I decided to use the keyboard to select the option, but later on, I end up adding the “MouseReader” class.

## GameOverMenu Class
#### The “GameOverMenu” class represents the end menu state of the game. This class displays the button in a rectangle shape. Also, the button includes text, which is easy to use the options in the end menu. I implement the function of an end menu within the game. This class is just used to display the menu buttons of the game.

## MouseReader Class
#### The “MouseReader” class is responsible for manage the mouse inputs. This class implements the MouseLister interface and the MouseMotionListener interface. I implemented the mousePressed() for mouse pressed. I also implemented the mouseReleased(), mouseEntered(), and mouseEcited() methods, but I leave all three methods empty. I did not leave the mousePressed() method empty because it is required. In mousePressed() method, I used game_state static variable from the “GameWorld” and check for clicks.

## Project Reflection
#### While working on this term project since the beginning, I learn so many things throughout the project. This was a big challenge for me to complete the game. I set up several milestones which I have to complete every week. I challenge myself in order to check what I am capable and what things I need to focus more. I had never built or code the game before. I did so much research on OOP principles. I am so happy after making my first game. Throughout the project, I faced so many problems, such as a map, object position, bullet direction, display health, and special items. I was able to be exposed to a proper understanding of java graphics. I was able to understand so many topics that I have not covered in class yet. I gained lots of experiences from the tank game project such as design, plan, UML diagram, etc. After I was able to fix the errors, I start enjoying the game making process. The term project helps me to understand better and review the java languages. My focus during the project was to try to minimize the coding line and game work without any bad glitches. I am very happy and proud of the fact that I have was able to complete the games with clear and nice OOP principles.

## Project Conclusion/Results
#### Overall, this term project gives me an excellent knowledge of OOP. The project itself performs very well. This term project teaches me how to use logic design and structure. For this term project, I create a couple of classes, methods, variables, and then connect all the coding pieces by pieces. In this project, I have to set up the jar, artifacts, modules, SDKs, resources folder, etc. which was my new experience. I met all my milestones on time, which I set up. This term project gives me more confidence about java and feels better after finishing this term project, and I have a better understanding of the OOP principles.

## Credits/References
#### 1. Instructor: Anthony J Souza
#### 2. Game sprites:-
####   a. https://opengameart.org
####   b. https://www.123rf.com
####   c. https://www.gameart2d.com
#### 3. Slack Channel: SFSU Computer Science






