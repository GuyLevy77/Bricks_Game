package src.gameobjects;

import danogl.GameObject;
import danogl.gui.Sound;

/**
 * Ball is the main game object.
 * It's positioned in game window as part of game initialization and given initial velocity.
 * On collision, it's velocity is updated to be reflected about the normal vector of
 * the surface it collides with.
 */
public class Ball extends GameObject {

    // =============================== fields ===============================

    private Sound collisionSound;
    private int numberOfCollision = 0;

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner - Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    - Width and height in window coordinates.
     * @param renderable    - The renderable representing the object. Can be null, in which case
     */
    public Ball(danogl.util.Vector2 topLeftCorner,
                danogl.util.Vector2 dimensions,
                danogl.gui.rendering.Renderable renderable,
                danogl.gui.Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
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
        numberOfCollision++;

        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
    }

    /**
     * Ball object maintains a counter which keeps count of collisions from start of game.
     * This getter method allows access to the collision count in case any behavior should
     * need to be based on number of ball collisions.
     *
     * @return - number of times ball collided with an object since start of game.
     */
    public int getCollisionCount() {
        return numberOfCollision;
    }
}
