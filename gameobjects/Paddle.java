package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 */
public class Paddle extends GameObject {

    // ========================== private constant ==========================

    private static final float MOVEMENT_SPEED = 300;

    // =============================== fields ===============================

    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistanceFromEdge;

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       - Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          - Width and height in window coordinates.
     * @param renderable          - The renderable representing the object. Can be null, in which case.
     * @param inputListener       - Input from the user.
     * @param windowDimensions    - Pixel dimensions for game window height and width.
     * @param minDistanceFromEdge - Border for paddle movement.
     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                  danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  danogl.gui.UserInputListener inputListener,
                  danogl.util.Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * Update in class danogl.GameObject
     *
     * @param deltaTime - Time between updates.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector2 movementDir = Vector2.ZERO;
        if (this.inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (this.inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        // permitted distance from left corner
        if (getTopLeftCorner().x() < minDistanceFromEdge) {
            transform().setTopLeftCornerX(minDistanceFromEdge);
        }
        // permitted distance from right corner
        if (getTopLeftCorner().x() > (windowDimensions.x() - minDistanceFromEdge - getDimensions().x())) {
            transform().setTopLeftCornerX(windowDimensions.x() - minDistanceFromEdge - getDimensions().x());
        }
    }
}
