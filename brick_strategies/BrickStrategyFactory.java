package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies
 */
public class BrickStrategyFactory {

    // ========================== private constant ==========================

    private static final int NUM_OF_STRATEGIES = 6;

    // =============================== fields ===============================

    private GameObjectCollection gameObjectCollection;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private Random random;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param gameObjectCollection - All the game objects in the game.
     * @param gameManager-         The game manager of our game.
     * @param imageReader-         An ImageReader instance for reading images from files for rendering
     *                             of objects.
     * @param soundReader-         A SoundReader instance for reading soundclips from files for
     *                             rendering event sounds.
     * @param inputListener-       Input from the user.
     * @param windowController-    Controls visual rendering of the game window and object renderables.
     * @param windowDimensions-    Pixel dimensions for game window height and width.
     */
    public BrickStrategyFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                danogl.gui.ImageReader imageReader,
                                danogl.gui.SoundReader soundReader,
                                danogl.gui.UserInputListener inputListener,
                                danogl.gui.WindowController windowController,
                                danogl.util.Vector2 windowDimensions) {

        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        random = new Random();
    }


    /**
     * Method randomly selects between 5 strategies and returns one CollisionStrategy
     * object which is a RemoveBrickStrategy decorated by one of the decorator strategies,
     * or decorated by two randomly selected strategies, or decorated by one of the decorator
     * strategies and a pair of additional two decorator strategies.
     *
     * @return - CollisionStrategy object.
     */
    public CollisionStrategy getStrategy() {
        int strategyRand = random.nextInt(NUM_OF_STRATEGIES); // rand in range 0 to 5
        CollisionStrategy decorated = new RemoveBrickStrategy(gameObjectCollection);

        if (strategyRand <= 3)     // strategy between 0 - 3. we chose ordinary strategy.
            return chooseStrategy(decorated, strategyRand);

        else if (strategyRand == 4)  // we chose RemoveBrickStrategy.
            return decorated;

        else {      // strategy = 5, means double.

            strategyRand = random.nextInt(NUM_OF_STRATEGIES - 1);
            int strategyRand1 = random.nextInt(NUM_OF_STRATEGIES - 2);
            int strategyRand2 = random.nextInt(NUM_OF_STRATEGIES - 2);
            CollisionStrategy oneStrategy = chooseStrategy(decorated, strategyRand1);
            CollisionStrategy twoStrategy = chooseStrategy(oneStrategy, strategyRand2);

            if (strategyRand < 4)       // means two strategies.
                return twoStrategy;
            else {       // means three strategies.
                int strategyRand3 = random.nextInt(NUM_OF_STRATEGIES - 2);
                return chooseStrategy(twoStrategy, strategyRand3);
            }
        }
    }

    /**
     * Auxiliary function for getStrategy.
     *
     * @param decorated    - Collision strategy object to be decorated.
     * @param strategyRand - Random number that symbolize strategy.
     * @return - chosen strategy.
     */
    public CollisionStrategy chooseStrategy(CollisionStrategy decorated, int strategyRand) {
        switch (strategyRand) {
            case 0:
                return new AddPaddleStrategy(decorated, imageReader, inputListener, windowDimensions);
            case 1:
                return new PuckStrategy(decorated, imageReader, soundReader);
            case 2:
                return new ChangeCameraStrategy(decorated, windowController, gameManager);
            default:
                return new ChangePaddleSizeStrategy(decorated, imageReader, windowController);
        }
    }
}
