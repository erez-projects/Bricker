package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * The FallingHeartsStrategy class implements the CollisionStrategy interface.
 * When a collision occurs, it triggers the creation of a falling heart in the game.
 */
public class FallingHeartsStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;


    /**
     * Constructor for the FallingHeartsStrategy class.
     * Initializes the strategy with a reference to the BrickerGameManager.
     *
     * @param brickerGameManager The game manager used to manage game objects and state.
     */
    public FallingHeartsStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }


    /**
     * Handles the collision event between two game objects. When a collision occurs,
     * this method triggers the creation of a falling heart at the center of the first game object.
     *
     * @param o1 the first game object involved in the collision.
     * @param o2 the second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        brickerGameManager.createFallingHeart(o1.getCenter());
    }
}
