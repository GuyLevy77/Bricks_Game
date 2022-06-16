package src.brick_strategies;

import danogl.collisions.GameObjectCollection;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 */
public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {

    // =============================== fields ===============================

    private CollisionStrategy toBeDecorated;

    // =========================== public methods ===========================

    /**
     * Constructor
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {

        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     *
     * @param thisObj  - the main GameObject instance participating in collision.
     * @param otherObj - other GameObject instance participating in collision.
     * @param counter  - global brick counter.
     */
    @Override
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj,
                            danogl.util.Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * return held reference to global game object. Delegate to held object to be decorated.
     *
     * @return
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }

}
