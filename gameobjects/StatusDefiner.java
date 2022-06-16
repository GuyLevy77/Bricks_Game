package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import src.brick_strategies.DefinerStrategy;

/**
 * * One of the main game objects.
 */
public class StatusDefiner extends GameObject {

    // ========================== private constant ==========================

    private static final String PADDLE = "paddle";
    private DefinerStrategy definerStrategy;

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public StatusDefiner(danogl.util.Vector2 topLeftCorner,
                         danogl.util.Vector2 dimensions,
                         danogl.gui.rendering.Renderable renderable,
                         DefinerStrategy definerStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.definerStrategy = definerStrategy;
    }

    /**
     * @param other - object to check.
     * @return - true if it should collide with it, otherwise false.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(PADDLE);
    }

    /**
     * If the static definer collide with paddle in remove the static definer from game object.
     *
     * @param other - the object that participating in the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(PADDLE)) {
            definerStrategy.onCollisionWithPaddle(other);
            definerStrategy.getGameObjectCollection().removeGameObject(this);
        }
    }
}