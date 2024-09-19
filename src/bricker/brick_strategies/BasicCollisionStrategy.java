package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

import static bricker.main.Constants.*;

import java.util.Random;

/**
 * BasicCollisionStrategy class defines the behavior for handling collisions in the Bricker game.
 * It implements the CollisionStrategy interface and provides specific behavior for brick collisions.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private static final int SPECIAL_BEHAVIOR_UPPER_BOUND = 4;
    private static final int BRICK_BEHAVIOR_UPPER_BOUND = 10;
    private final BrickerGameManager brickerGameManager;

    private final CollisionStrategy[] strategies;

    /**
     * Constructor for BasicCollisionStrategy.
     * Initializes the strategy with different behaviors for brick collisions.
     *
     * @param gameManager Instance of BrickerGameManager to interact with game objects and game state.
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager) {
        this.brickerGameManager = gameManager;
        AdditionalPucksStrategy additionalPucksStrategy = new AdditionalPucksStrategy(brickerGameManager);
        FallingHeartsStrategy fallingHeartsStrategy = new FallingHeartsStrategy(brickerGameManager);
        ChangeCameraStrategy changeCameraStrategy = new ChangeCameraStrategy(brickerGameManager);
        CreateSecondaryPaddleStrategy createSecondaryPaddleStrategy =
                new CreateSecondaryPaddleStrategy(brickerGameManager);
        DoubleBehaviorStrategy doubleBehaviorStrategy = new DoubleBehaviorStrategy(brickerGameManager);

        this.strategies =
                new CollisionStrategy[]{fallingHeartsStrategy,
                        additionalPucksStrategy, changeCameraStrategy,
                        createSecondaryPaddleStrategy, doubleBehaviorStrategy};
    }

    /**
     * Handles the collision between two game objects.
     * This method is called when a collision is detected and performs the appropriate behavior based
     * on the object tags
     *
     * @param o1 The first game object involved in the collision.
     * @param o2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        if (o1.getTag().equals(BRICK_TAG)) {
            brickCollisionBehavior(o1, o2);
        }

    }

    private void brickCollisionBehavior(GameObject o1, GameObject o2) {
        boolean isNotDeleted = brickerGameManager.removeObject(o1);
        if (isNotDeleted) {
            Random random = new Random();
            int rndNum = random.nextInt(BRICK_BEHAVIOR_UPPER_BOUND);
            if (rndNum <= SPECIAL_BEHAVIOR_UPPER_BOUND) {
                strategies[rndNum].onCollision(o1, o2);
            }
        }
    }


}
