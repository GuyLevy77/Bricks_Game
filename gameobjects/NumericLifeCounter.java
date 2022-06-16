package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {

    // ========================== private constant ==========================

    private static final String LIVES_MSG = "Lives: %d";

    // =============================== fields ===============================

    private final GameObject gameObject;
    private TextRenderable textRenderable;
    private Counter livesCounter;
    private GameObjectCollection gameObjectCollection;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param livesCounter         - Global lives counter of game.
     * @param topLeftCorner        - Top left corner of renderable.
     * @param dimensions           - Dimensions of renderable.
     * @param gameObjectCollection - Global game object collection.
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                              danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection) {

        super(topLeftCorner, dimensions, null);
        this.gameObjectCollection = gameObjectCollection;
        this.livesCounter = livesCounter;

        textRenderable = new TextRenderable(String.format(LIVES_MSG, livesCounter.value()));
        gameObject = new GameObject(topLeftCorner, dimensions, textRenderable);
        gameObjectCollection.addGameObject(gameObject, Layer.UI);
    }

    /**
     * Update in class danogl.GameObject
     *
     * @param deltaTime - Time between updates.
     */
    @Override
    public void update(float deltaTime) {
        this.textRenderable.setString(String.format(LIVES_MSG, livesCounter.value()));
    }
}
