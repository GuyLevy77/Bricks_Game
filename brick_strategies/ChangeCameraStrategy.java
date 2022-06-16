package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {

    // ========================== private constant ==========================

    private static final int COUNT_DOWN_VALUE = 4;
    private static final String BALL = "ball";

    // =============================== fields ===============================

    private WindowController windowController;
    private BrickerGameManager gameManager;
    private GameObject ball;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param toBeDecorated    - Collision strategy object to be decorated.
     * @param windowController - Controls visual rendering of the game window and object renderables.
     * @param gameManager      - The game manager of our game.
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                danogl.gui.WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Change camera position on collision and delegate to held CollisionStrategy.
     *
     * @param thisObj  - the main GameObject instance participating in collision.
     * @param otherObj - other GameObject instance participating in collision.
     * @param counter  - global counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj,
                            danogl.util.Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        if (gameManager.getCamera() != null)
            return;
        if (otherObj.getTag().equals(BALL)) {
            ball = otherObj;

            gameManager.setCamera(
                    new Camera(
                            ball,            //object to follow
                            Vector2.ZERO,    //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions())   //share the window dimensions
            );
            ballCollisionCountdownAgent =
                    new BallCollisionCountdownAgent((Ball) ball, this, COUNT_DOWN_VALUE);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);
        }

    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(ballCollisionCountdownAgent);
    }
}
