=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: jsarihan
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D ARRAY
  Each level will be represented as a 2-D array of GameObj. 
  I will create a LevelController class that is responsible for making sure all GameObj’s stay within 
  the bounds of the level and follow the constraints of the game. The bounds will be the bounds of 2-D array. 
  Using a 2-D array allows instant referencing when moving objects since each movement will occur in a 
  blockwise fashion either left, right, up, or down. I can also iterate through the array to redraw each GameObj. 
  Since the array is relatively small and the pixelcount is low, there should be no tearing of images.

  2. COLLECTIONS
  The objects with different types of actions will be stored in Sets to 
  procedurally update them with each tick of the game. For example, boulders will be stored in one collection, 
  and calling updateBoulders would update all members of that collection. It would follow similarly for Enemies and Loot. 
  The benefit of using a Collection means each group of objects can be referenced separately, and if only 
  boulders or enemies need to be updated, it’s easy to have them all accessible. 

  3. IO 
  The game levels/custom-made game levels created by the player will be stored as a .txt file in a standardized/documented format. 
  This way, players can use the in-built level creator or directly edit the text documents to create new levels. 
  This requires writing a parser to parse the .txt files from the format and additionally write to a .txt 
  if the player creates a new level using the level builder. 

  4. DYNAMIC DISPATCH
  All objects of the level extend the GameObj class and use dynamic dispatch when their “move” method is 
  called because each class implement it differently (player movement is controlled, enemies follow an algorithm, 
  boulders go down). There will be a movableObj abstract class that extends GameObj for objects that are allowed to move/be moved. 
  There will be a “Crushable” interface for objects that can be destroyed on impact by a boulder. 
  An “Enemy” abstract class extend MovableObj and be helpful in identifying if the player runs 
  into an enemy and have attack methods


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  The Game class initailizes the main frames and thread for the game.
  Most of the Swing components are handled by the Game Class and acts as the overarching
  view for the entire game. All butttons pass down information to the GameCourt.
  
  The GameCourt class initializes the default state. It loads the first level by default and
  has its own board and player. Additionally, the GameCourt class is tasked with updating
  the labels passed by Game to make sure time played, lives, etc. are all up to date with
  the board state. The GameCourt class takes in user mousepresses to set the direction of the
  player and then attempts to move it through the Board class.
  
  The GameObj class is the overarching class for all objects in the board. This abstract class
  has an x,y coordinate, and delegates the draw, and onImpact methods to the subclasses. All
  GameObjects need to be able to draw themselves when passed a graphics context, and should
  be able to react appropriately when hit by a boulder. All GameObj also have a toString method
  when the board is saved as a text file. GameObj's reference the SpriteSheet to be drawn. GameObj's
  have a boolean destroyable field that can be true or false.
  
  The Board class is a 2-D array that contains all the GameObj's and is tasked with updating their
  state and calling their draw methods. Each tick of the timer in GameCourt will update the board
  and call all the draw methods of the objects in the board. The collections of boulders and enemies are iterated through
  and their move methods are called.
  
  The MovableObj class is an abstract class extends GameObj that has the move method.
  
  The Boulder class has a falling property and when called to move can impact the objects below it in the Board.
  
  The abstract Enemy class extends MovableObj and can be dead/alive and has an integer attackDmg.
  These fields have getters and setters and are used when the the board is updated.
  
  The Bat class extends the Enemy class with a specific implementation of the move method and a set dmg.
  
  The FireFly class extends Enemy class with a different implementation of the move method and set dmg.
  
  The Diamond class has destroyable field true and gives points to the player through its destroy method.
  
  The Dirt class field has destroyable true and gives no points to the player through its destroy method.
  
  The Empty class field has destroyable true and gives no points to the player through its destroy method.
  
  The Exit class can be destroyed only if the minimum condition of the level is met e.g. the player has
  collected enough diamonds to move on to the next level.
  
  Both the Wall classes are non-destroyable and only implement the draw with different sprites
  
  LevelLoader parses a text file and returns a 2-D array of char's that is passed to the GameCourt.
  The GameCourt uses this to construct a Board.
  
  LevelSaver converts the 2-D GameObj array to a text file by using the GameObj dynamic dispatch of toString.
  
  SpriteLoader loads the spriteSheet based on the screen resolution and stores this as an array of BufferedImages
  that each class can access in a static way.
  
  The Player class has health and direction and is the only object that can destroy other objects.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  Yes, I had to refactor my code multiple times to make it more object oriented and add functionality.
  I had difficulty creating a workaround for instanceOf, but dynamic dispatch of the move method in
  MovableObj helped remove most of the switch/if statements in the Board class.
  Importing sprites and properly scaling them was very difficult as well, since graphics context is hard
  to contextualize when nothing loads properly.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  Yes, I think I followed the MVC style well and almost all fields are encapsulated through
  private state and can only be accessed by getter and setter methods. Each of the classes
  are indepedent of eachother and only the Board can access separate classes. The UI is also
  separated from the backend through GameCourt and Board to make sure invariants are maintained.
  I would refactor how the board is drawn and updated. This would make it easier to have a modular
  way of updating/drawing different collections at different tick rates without causing desync issues.



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

	My spritesheet was used with permission and can be found online at:
	https://www.spriters-resource.com/commodore_64/boulderdash/sheet/74444/
	
