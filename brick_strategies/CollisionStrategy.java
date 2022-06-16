package src.brick_strategies;   // component

import danogl.GameObject;
import danogl.util.Counter;

/**
 * General type for brick strategies, part of decorator pattern implementation.
 * All brick strategies implement this interface.
 */
public interface CollisionStrategy {

    /**
     * To be called on brick collision.
     *
     * @param thisObj  - the main GameObject instance participating in collision.
     * @param otherObj - other GameObject instance participating in collision.
     * @param counter  - global brick counter.
     */
    void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj, danogl.util.Counter counter);

    /**
     * All collision strategy objects should hold a reference to the global
     * game object collection and be able to return it.
     *
     * @return - global game object collection whose reference is held in object.
     */
    danogl.collisions.GameObjectCollection getGameObjectCollection();
}
