package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Concrete brick strategy implementing CollisionStrategy interface.
 * Removes holding brick on collision.
 */
public class RemoveBrickStrategy implements CollisionStrategy {     // concreteComponent

    // =============================== fields ===============================

    private GameObjectCollection gameObjectCollection;

    // =========================== public methods ===========================

    /**
     * Constructor.
     *
     * @param gameObjectCollection - global game object collection
     */
    public RemoveBrickStrategy(danogl.collisions.GameObjectCollection gameObjectCollection) {

        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Removes brick from game object collection on collision.
     *
     * @param thisObj  - the object of this class.
     * @param otherObj - Other object.
     * @param counter  - Global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS))
            counter.decrement();
    }

    /**
     * All collision strategy objects should hold a reference to the global
     * game object collection and be able to return it.
     *
     * @return - global game object collection whose reference is held in object.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

}
