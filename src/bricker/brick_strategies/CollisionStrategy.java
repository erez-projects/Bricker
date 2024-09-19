package bricker.brick_strategies;

import danogl.GameObject;
/**
 * CollisionStrategy is an interface that defines the behavior for handling collisions between game objects.
 */
public interface CollisionStrategy {
    /**
     * This method is called when two game objects collide.
     *
     * @param o1 The first game object involved in the collision.
     * @param o2 The second game object involved in the collision.
     */
    void onCollision(GameObject o1, GameObject o2);
}
