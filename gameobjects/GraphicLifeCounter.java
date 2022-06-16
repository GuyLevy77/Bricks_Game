package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import danogl.util.Vector2;

import static src.BrickerGameManager.BORDER_WIDTH;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {

    // ========================== private constant ==========================

    private static final int FIRST_HEART_FROM_BORDER = 10;
    private static final int DIST_BETWEEN_HEARTS = 20;

    // =============================== fields ===============================

    private Counter counter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;
    private GameObject[] arrOfLives;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param widgetTopLeftCorner   - Top left corner of left most life widgets.
     *                              Other widgets will be displayed to its right, aligned in height.
     * @param widgetDimensions      - Dimensions of widgets to be displayed.
     * @param livesCounter          - Global lives counter of game.
     * @param widgetRenderable      - Image to use for widgets.
     * @param gameObjectsCollection - Global game object collection managed by game manager.
     * @param numOfLives            - Global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                              danogl.util.Vector2 widgetDimensions,
                              danogl.util.Counter livesCounter,
                              danogl.gui.rendering.Renderable widgetRenderable,
                              danogl.collisions.GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.counter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;

        arrOfLives = new GameObject[numOfLives];
        for (int i = 0; i < arrOfLives.length; i++) {
            GameObject obj = new GameObject(
                    new Vector2(BORDER_WIDTH + FIRST_HEART_FROM_BORDER + i * DIST_BETWEEN_HEARTS,
                            widgetTopLeftCorner.y()),
                    widgetDimensions,
                    widgetRenderable);
            arrOfLives[i] = obj;
            gameObjectsCollection.addGameObject(obj, Layer.UI);
        }
    }

    /**
     * Update in class danogl.GameObject
     *
     * @param deltaTime - Time between updates.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (counter.value() != numOfLives) {
            numOfLives--;
            gameObjectsCollection.removeGameObject(arrOfLives[counter.value()], Layer.UI);
        }
    }
}
