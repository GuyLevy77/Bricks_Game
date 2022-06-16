package src.gameobjects;

import danogl.util.Counter;
import danogl.GameObject;
import src.brick_strategies.CollisionStrategy;


public class Brick extends GameObject {

    // =============================== fields ===============================

    private CollisionStrategy collisionStrategy;
    private Counter counter;

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(danogl.util.Vector2 topLeftCorner,
                 danogl.util.Vector2 dimensions,
                 danogl.gui.rendering.Renderable renderable,
                 CollisionStrategy collisionStrategy,
                 danogl.util.Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    /**
     * On collision, object velocity is reflected about the normal vector of the surface it collides with.
     *
     * @param other     - other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, counter);
    }
}
