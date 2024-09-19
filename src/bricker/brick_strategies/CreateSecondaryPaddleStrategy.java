package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * CreateSecondaryPaddleStrategy class defines a collision strategy
 * that creates a secondary paddle when a collision occurs.
 * It implements the CollisionStrategy interface.
 */
public class CreateSecondaryPaddleStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;
    /**
     * Constructor for CreateSecondaryPaddleStrategy.
     * Initializes the strategy with a reference to the game manager.
     *
     * @param brickerGameManager Instance of BrickerGameManager to interact with game objects and game state.
     */
    public CreateSecondaryPaddleStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects.
     * This method creates a secondary paddle in the game.
     *
     * @param o1 The first game object involved in the collision.
     * @param o2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        brickerGameManager.createSecondaryPaddle();
    }
}
