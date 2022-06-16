package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.StatusDefiner;

import java.util.Random;

/**
 * This class implements DefinerStrategy interface and creating status definer object if needed.
 */
public class ChangePaddleSizeStrategy implements DefinerStrategy {

    // ========================== private constant ==========================

    private static final String BUFF_WIDEN = "assets/buffWiden.png";
    private static final String BUFF_NARROW = "assets/buffNarrow.png";
    private static final String STATUS_DEFINER = "statusDefiner";
    private static final float NUM_TO_MULT_FOR_WIDENING = 1.5f;
    private static final float NUM_TO_MULT_FOR_NARROW = 0.5f;

    // =============================== fields ===============================

    private boolean wideOrNarrow = true; // true means wide
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private WindowController windowController;
    private boolean alreadyCollidedWith;
    private final Random random;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param toBeDecorated     - Collision strategy object to be decorated.
     * @param imageReader       - An ImageReader instance for reading images from files for rendering
     *                          of objects.
     * @param windowController- Controls visual rendering of the game window and object renderables.
     */
    public ChangePaddleSizeStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                                    WindowController windowController) {

        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.windowController = windowController;
        alreadyCollidedWith = false;
        random = new Random();
    }

    /**
     * Creating static definer object if needed and add it to game objects.
     *
     * @param thisObj  - the main GameObject instance participating in collision.
     * @param otherObj - other GameObject instance participating in collision.
     * @param counter  - global counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (alreadyCollidedWith)
            return;
        alreadyCollidedWith = true;
        toBeDecorated.onCollision(thisObj, otherObj, counter);
        GameObject statusDefiner;
        if (random.nextBoolean()) {
            statusDefiner = new StatusDefiner(
                    thisObj.getTopLeftCorner(),
                    thisObj.getDimensions(),
                    imageReader.readImage(BUFF_WIDEN, true),
                    this);
            wideOrNarrow = true;
        } else {
            statusDefiner = new StatusDefiner(
                    thisObj.getTopLeftCorner(),
                    thisObj.getDimensions(),
                    imageReader.readImage(BUFF_NARROW, true),
                    this);
            wideOrNarrow = false;
        }
        getGameObjectCollection().addGameObject(statusDefiner);
        // Find the exact speed of the ball what helps in update the statusDefiner velocity.
        statusDefiner.setVelocity(new Vector2(0,
                (float) Math.sqrt((float) Math.pow(otherObj.getVelocity().x(), 2)
                        + (float) Math.pow(otherObj.getVelocity().y(), 2))));
        statusDefiner.setTag(STATUS_DEFINER);
    }

    /**
     * @return - All the game objects in the game.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }

    /**
     * Widening or narrowing the paddle according to instructions while has collision with
     * static definer.
     *
     * @param otherObj - the paddle.
     */
    @Override
    public void onCollisionWithPaddle(GameObject otherObj) {
        if (wideOrNarrow) {     // true is wide
            if (otherObj.getDimensions().x() * NUM_TO_MULT_FOR_WIDENING <
                    windowController.getWindowDimensions().x() - 2 * BrickerGameManager.BORDER_WIDTH) {
                otherObj.setDimensions(
                        new Vector2(otherObj.getDimensions().x() * NUM_TO_MULT_FOR_WIDENING,
                                otherObj.getDimensions().y()));
            }
        } else {
            if (otherObj.getDimensions().x() * NUM_TO_MULT_FOR_NARROW > 0) {
                otherObj.setDimensions(
                        new Vector2(otherObj.getDimensions().x() * NUM_TO_MULT_FOR_NARROW,
                                otherObj.getDimensions().y()));
            }
        }
    }
}
