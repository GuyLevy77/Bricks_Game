package src.gameobjects;

/**
 * One of the types of objects that can be set loose when a brick is hit.
 */
public class Puck extends Ball {

    // =========================== public methods ===========================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner  - Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     - Width and height in window coordinates.
     * @param renderable     - The renderable representing the object. Can be null, in which case.
     * @param collisionSound
     */
    public Puck(danogl.util.Vector2 topLeftCorner,
                danogl.util.Vector2 dimensions,
                danogl.gui.rendering.Renderable renderable,
                danogl.gui.Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
    }

}
