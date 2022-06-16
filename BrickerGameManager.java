package src;

import danogl.util.Counter;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import src.brick_strategies.RemoveBrickStrategy;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

/**
 * Game manager - this class is responsible for game initialization, holding references for
 * game objects and calling update methods for every update iteration.
 */
public class BrickerGameManager extends GameManager {

    // ========================== Public constant ==========================

    public static final int BORDER_WIDTH = 20;

    // ========================== private constant ==========================

    private static final Color BORDER_Color = Color.BLACK;
    private static final float BALL_SPEED = 200;
    private static final int BRICKS_LINES = 5;
    private static final int BRICKS_COL = 8;
    private static final int NUM_OF_LIVES = 4;
    private static final int FRAMERATE = 80;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static final float BRICK_HEIGHT = 15;
    private static final float NUMERIC_LIFE_COUNTER_WIDTH = 5;
    private static final float NUMERIC_LIFE_COUNTER_HEIGHT = 20;
    private static final float GRAPHIC_LIFE_COUNTER_WIDTH = 15;
    private static final float GRAPHIC_LIFE_COUNTER_HEIGHT = 15;
    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_HEIGHT = 15;
    private static final float BALL_WIDTH = 20;
    private static final float BALL_HEIGHT = 20;
    private static final float WINDOW_WIDTH = 700;
    private static final float WINDOW_HEIGHT = 500;
    private static final float NUM_TO_MULT_FOR_SET_CENTER = 0.5f;
    private static final int NUMERIC_LIFE_COUNTER_FROM_BORDER = 10;
    private static final float NUMERIC_LIFE_COUNTER_FROM_WINDOW = 120;
    private static final int GRAPHIC_LIFE_COUNTER_FROM_BORDER = 10;
    private static final float GRAPHIC_LIFE_COUNTER_FROM_WINDOW = 80;
    private static final float DIST_BETWEEN_BRICKS = 1;
    private static final int PADDLE_FROM_WINDOW = 30;
    private static final float RIGHT_BORDER_Y_COORDINATE = 0;
    private static final String GRAPHIC_LIFE_COUNTER_IMAGE = "assets/heart.png";
    private static final String BRICK_IMAGE = "assets/brick.png";
    private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String SOUND_FILE = "assets/blop_cut_silenced.wav";
    private static final String BALL_IMAGE = "assets/ball.png";
    private static final String WINDOW_TITLE = "Bouncing Ball";
    private static final String LOSE_MSG = "You Lose!";
    private static final String WIN_MSG = "You Win!";
    private static final String RESET_MSG = " Play again?";
    private static final String PUCK = "puck";
    private static final String BALL = "ball";
    private static final String PADDLE = "paddle";
    private static final String STATUS_DEFINER = "statusDefiner";

    // =============================== fields ===============================

    private GameObject ball;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private Counter livesCounter;
    private Counter brickCounter;

    // =========================== public methods ===========================

    /**
     * Constructor
     *
     * @param windowTitle      - The  title when the window is open.
     * @param windowDimensions - pixel dimensions for game window height x width.
     */
    public BrickerGameManager(java.lang.String windowTitle, danogl.util.Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        livesCounter = new Counter(NUM_OF_LIVES);
        brickCounter = new Counter(BRICKS_LINES * BRICKS_COL);
    }

    /**
     * Calling this function should initialize the game window.
     * It should initialize objects in the game window - ball, paddle, walls, life counters, bricks.
     * This version of the game has 5 rows, 8 columns of bricks.
     *
     * @param imageReader      - An ImageReader instance for reading images from files for rendering
     *                         of objects.
     * @param soundReader      - A SoundReader instance for reading soundclips from files for
     *                         rendering event sounds.
     * @param inputListener    - An InputListener instance for reading user input.
     * @param windowController - Controls visual rendering of the game window and object renderables.
     */
    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader, danogl.gui.SoundReader soundReader,
                               danogl.gui.UserInputListener inputListener,
                               danogl.gui.WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(FRAMERATE);
        this.windowController = windowController;
        // gets the size of our window.
        windowDimensions = windowController.getWindowDimensions();

        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(
                gameObjects(),
                this,
                imageReader, soundReader,
                inputListener,
                windowController,
                windowDimensions);

        //________________________creating background________________________
        createBackground(imageReader);

        //________________________creating ball________________________
        createBall(imageReader, soundReader);

        //________________________creating paddles________________________
        createPaddles(imageReader, inputListener);

        //________________________creating borders________________________
        createBorders();

        //________________________creating bricks________________________
        createBricks(imageReader, brickStrategyFactory);

        //________________________creating GraphicLifeCounter________________________
        createGraphicLifeCounter(imageReader);

        //________________________creating NumericLifeCounter________________________
        createNumericLifeCounter();
    }

    /**
     * Code in this function is run every frame update.
     *
     * @param deltaTime - Time between updates. For internal use by game engine.
     *                  You do not need to call this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        checkObjectsInBorders();
        checkForGameEnd();

    }

    /**
     * @param ball
     */
    public void repositionBall(danogl.GameObject ball) { }

    // =========================== private methods ===========================

    /**
     * Auxiliary function for update.
     */
    private void checkForGameEnd() {

        float ballHeight = ball.getCenter().y();
        String prompt = "";

        if (ballHeight > windowDimensions.y()) {
            ball.setCenter(windowDimensions.mult(NUM_TO_MULT_FOR_SET_CENTER));
            livesCounter.decrement();
            if (livesCounter.value() == 0)
                prompt = LOSE_MSG;
        }

        if (brickCounter.value() == 0)
            prompt = WIN_MSG;

        if (!prompt.isEmpty()) {
            prompt += RESET_MSG;
            if (windowController.openYesNoDialog(prompt)) {
                livesCounter.increaseBy(NUM_OF_LIVES - livesCounter.value());
                brickCounter.increaseBy(BRICKS_COL * BRICKS_LINES - brickCounter.value());
                windowController.resetGame();
            } else
                windowController.closeWindow();
        }
    }

    /**
     * Auxiliary function for initialization function.
     * Creates NumericLifeCounter object.
     */
    private void createNumericLifeCounter() {
        GameObject gameObject = new NumericLifeCounter(
                livesCounter,
                new Vector2(BORDER_WIDTH + NUMERIC_LIFE_COUNTER_FROM_BORDER,
                        windowDimensions.y() - NUMERIC_LIFE_COUNTER_FROM_WINDOW),
                new Vector2(NUMERIC_LIFE_COUNTER_WIDTH, NUMERIC_LIFE_COUNTER_HEIGHT),
                gameObjects());

        this.gameObjects().addGameObject(gameObject, Layer.UI);
    }

    /**
     * Auxiliary function for initialization function.
     * Creates GraphicLifeCounter objects.
     */
    private void createGraphicLifeCounter(ImageReader imageReader) {

        GameObject graphicLifeCounter = new GraphicLifeCounter(
                new Vector2(BORDER_WIDTH + GRAPHIC_LIFE_COUNTER_FROM_BORDER,
                        windowDimensions.y() - GRAPHIC_LIFE_COUNTER_FROM_WINDOW),
                new Vector2(GRAPHIC_LIFE_COUNTER_WIDTH, GRAPHIC_LIFE_COUNTER_HEIGHT),
                livesCounter,
                imageReader.readImage(GRAPHIC_LIFE_COUNTER_IMAGE, true),
                gameObjects(), NUM_OF_LIVES);

        this.gameObjects().addGameObject(graphicLifeCounter, Layer.UI);
    }

    /**
     * Auxiliary function for initialization function.
     * Create Bricks objects.
     */
    private void createBricks(ImageReader imageReader, BrickStrategyFactory brickStrategyFactory) {

        CollisionStrategy collisionStrategy = new RemoveBrickStrategy(gameObjects()); // not in use

        float widthOfBrick = (windowDimensions.x() - BRICKS_COL - (BORDER_WIDTH * 2)) / BRICKS_COL;

        for (int row = 0; row < BRICKS_LINES; row++) {
            for (int col = 0; col < BRICKS_COL; col++) {

                GameObject brick = new Brick(
                        new Vector2(BORDER_WIDTH + col * (widthOfBrick + DIST_BETWEEN_BRICKS),
                                BORDER_WIDTH + row * (BRICK_HEIGHT + DIST_BETWEEN_BRICKS)),
                        new Vector2(widthOfBrick, BRICK_HEIGHT),
                        imageReader.readImage(BRICK_IMAGE, false),
                        brickStrategyFactory.getStrategy(), brickCounter);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Auxiliary function for initialization function.
     * Creates background object.
     */
    private void createBackground(ImageReader imageReader) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                imageReader.readImage(BACKGROUND_IMAGE, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Auxiliary function for initialization function.
     * Creates background object.
     */
    private void createPaddles(ImageReader imageReader, UserInputListener inputListener) {

        GameObject userPaddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                imageReader.readImage(PADDLE_IMAGE, true),
                inputListener, windowDimensions, MIN_DISTANCE_FROM_SCREEN_EDGE);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,
                (int) windowDimensions.y() - PADDLE_FROM_WINDOW));
        this.gameObjects().addGameObject(userPaddle);
        userPaddle.setTag(PADDLE);
    }

    /**
     * Auxiliary function for initialization function.
     * Creates ball object.
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader) {
        // reading a sound file
        Sound collisionSound = soundReader.readSound(SOUND_FILE);

        ball = new Ball(
                Vector2.ZERO,
                new Vector2(BALL_WIDTH, BALL_HEIGHT),
                imageReader.readImage(BALL_IMAGE, true),
                collisionSound);

        // set the object to a new location - to the center of our window.
        ball.setCenter(windowDimensions.mult(NUM_TO_MULT_FOR_SET_CENTER));
        this.gameObjects().addGameObject(ball);
        ball.setTag(BALL);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();

        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;

        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Auxiliary function for initialization function.
     * Create borders.
     */
    private void createBorders() {
        // Left border.
        this.gameObjects().addGameObject(new GameObject(
                Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(BORDER_Color)));
        // Right border.
        this.gameObjects().addGameObject(new GameObject(
                new Vector2(windowDimensions.x() - BORDER_WIDTH, RIGHT_BORDER_Y_COORDINATE),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                new RectangleRenderable(BORDER_Color)));
        // Upper border.
        this.gameObjects().addGameObject(new GameObject(
                Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH),
                new RectangleRenderable(BORDER_Color)));
    }

    private void checkObjectsInBorders() {

        for (GameObject obj : gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (obj.getTag().equals(PUCK) || obj.getTag().equals(STATUS_DEFINER)) {
                if (obj.getCenter().y() > windowDimensions.y()) {
                    gameObjects().removeGameObject(obj);
                    break;
                }
            }
        }
    }

    // ================================ Main ================================

    /**
     * Entry point for game, runs the game.
     *
     * @param args - Array of strings.
     */
    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}


