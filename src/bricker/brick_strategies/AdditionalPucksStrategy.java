package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;



/**
 * The AdditionalPucksStrategy class implements the CollisionStrategy interface.
 * When a collision occurs, it triggers the addition of extra pucks in the game.
 */
public class AdditionalPucksStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs an AdditionalPucksStrategy with the specified BrickerGameManager.
     *
     * @param brickerGameManager the game manager responsible for managing game state and behavior.
     */
    public AdditionalPucksStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }
    /**
     * Handles the collision event between two game objects. When a collision occurs,
     * this method triggers the addition of extra pucks at the center of the first game object.
     *
     * @param o1 the first game object involved in the collision.
     * @param o2 the second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        brickerGameManager.additionalPucks(o1.getCenter());
    }
}
