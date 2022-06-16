package src.brick_strategies;

/**
 * An interface that extends CollisionStrategy, created for strategies that
 * designed for static definer objects.
 */
public interface DefinerStrategy extends CollisionStrategy {

    /**
     * To be called on static definer collision.
     *
     * @param otherObj - an object that participating in collision.
     */
    void onCollisionWithPaddle(danogl.GameObject otherObj);
}
