package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces extra paddle to game window which remains until colliding
 * NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {

    // ========================== private constant ==========================

    private static final float NUM_TO_MULT_FOR_SET_CENTER = 0.5f;
    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_HEIGHT = 15;
    private static final int NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE = 3;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String PADDLE = "paddle";

    // =============================== fields ===============================

    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    // =========================== public methods ===========================

    /**
     * constructor
     *
     * @param toBeDecorated    - Collision strategy object to be decorated.
     * @param imageReader      - An ImageReader instance for reading images from files for rendering
     *                         of objects.
     * @param inputListener    - Input from the user.
     * @param windowDimensions - Pixel dimensions for game window height and width.
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             danogl.gui.ImageReader imageReader,
                             danogl.gui.UserInputListener inputListener,
                             danogl.util.Vector2 windowDimensions) {
        super(toBeDecorated);

        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Adds additional paddle to game and delegates to held object.
     *
     * @param thisObj  - the main GameObject instance participating in collision.
     * @param otherObj - other GameObject instance participating in collision.
     * @param counter  - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj,
                            danogl.util.Counter counter) {
        super.onCollision(thisObj, otherObj, counter);

        GameObject paddle = new MockPaddle(
                windowDimensions.mult(NUM_TO_MULT_FOR_SET_CENTER),
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                imageReader.readImage(PADDLE_IMAGE, true),
                inputListener,
                windowDimensions,
                getGameObjectCollection(),
                MIN_DISTANCE_FROM_SCREEN_EDGE,
                NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE);
        getGameObjectCollection().addGameObject(paddle);
        paddle.setTag(PADDLE);
    }
}
