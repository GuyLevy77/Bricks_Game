package src.gameobjects;

import danogl.collisions.GameObjectCollection;

/**
 * Paddle that creating in the middle of the game window.
 */
public class MockPaddle extends Paddle {

    // ========================== public constant ==========================

    public static boolean isInstantiated;

    // ========================== private constant ==========================

    private GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner            - Position of the object, in window coordinates (pixels).
     *                                 Note that (0,0) is the top-left corner of the window.
     * @param dimensions               - Width and height in window coordinates.
     * @param renderable               - The renderable representing the object. Can be null, in which case
     * @param inputListener            - listener object for user input.
     * @param windowDimensions         - dimensions of game window.
     * @param gameObjectCollection-    Global game object collection managed by game manager.
     * @param minDistanceFromEdge-     border for paddle movement.
     * @param numCollisionsToDisappear - number of collision can happen before paddle disappear.
     */
    public MockPaddle(danogl.util.Vector2 topLeftCorner,
                      danogl.util.Vector2 dimensions,
                      danogl.gui.rendering.Renderable renderable,
                      danogl.gui.UserInputListener inputListener,
                      danogl.util.Vector2 windowDimensions,
                      danogl.collisions.GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);

        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
    }

    /**
     * @param other - the other object participating the collision.
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        numCollisionsToDisappear--;
        if (numCollisionsToDisappear == 0) {
            gameObjectCollection.removeGameObject(this);
        }
    }

}
