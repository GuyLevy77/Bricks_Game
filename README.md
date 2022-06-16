# Bricks Game
Writing a program for a game in Java including designing modules and classes that implement principles in Object-Oriented Programming. 

Choosing the Class's structure:
Those modules, classes, and packages designed properly and have logic:
All of the game objects collected together, we have package for brick strategies,
what gives us the option of building more strategies and put them under this package,
and we separate the main class - BrickerGameManager

Regarding implementations:
GraphicLifeCounter implementation - it make sense to me creating the hearts(disqualifications)
in GraphicLifeCounter and not in BrickerGameManager.
That because it easier to manage the counter and to delete those objects,
especially when we are doing override to update(function), what help us to remove specific heart/object.

Brick instruction - At the beginning I implemented the bricks in BrickerGameManager,
so in order to remove the bricks that collided I decremented the brickCounter by one, everytime the
onCollisionEnter function was called.

Choosing designing pattern for static definers:
I choose to create a new interface called DefinerStrategy that extend CollisionStrategy interface.
In addition I created a new class called ChangePaddleSizeStrategy which implements DefinerStrategy,
and created a new class for the object status definer.
In that way every new strategy class that deals with status definers objects can implements
DefinerStrategy interface what makes it very simple:
All it (the strategy) needs to do is to create a new static definer and sends itself to the constructor
of this static definer object. If there is any collision with this static definer,
it calls to onCollisionWithPaddle (function in the strategy) and some operation is done.
