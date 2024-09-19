package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Vector2;

import java.util.Random;

/**
 * DoubleBehaviorStrategy class defines a collision strategy that can apply two or more behaviors when a
 * collision
 * occurs.
 * It implements the CollisionStrategy interface.
 */
public class DoubleBehaviorStrategy implements CollisionStrategy{
    private static final int SPECIAL_BEHAVIOR_UPPER_BOUND = 5;
    private static final int SINGLE_SPECIAL_BEHAVIOR_UPPER_BOUND = 4;
    private static final int DOUBLE_SPECIAL_BEHAVIOR = 4;


    CollisionStrategy[] strategies;

    /**
     * Constructor for DoubleBehaviorStrategy.
     * Initializes the strategy with different behaviors for collision events.
     *
     * @param brickerGameManager Instance of BrickerGameManager to interact with game objects and game state.
     */
    public DoubleBehaviorStrategy(BrickerGameManager brickerGameManager){
        FallingHeartsStrategy fallingHeartsStrategy = new FallingHeartsStrategy(brickerGameManager);
        AdditionalPucksStrategy additionalPucksStrategy = new AdditionalPucksStrategy(brickerGameManager);
        ChangeCameraStrategy changeCameraStrategy = new ChangeCameraStrategy(brickerGameManager);
        CreateSecondaryPaddleStrategy createSecondaryPaddleStrategy =
                new CreateSecondaryPaddleStrategy(brickerGameManager);
        this.strategies =
                new CollisionStrategy[]{fallingHeartsStrategy,
                        additionalPucksStrategy, changeCameraStrategy, createSecondaryPaddleStrategy};
    }
    /**
     * Handles the collision between two game objects.
     * This method randomly selects and applies one or more special behaviors based on predefined
     * probabilities.
     *
     * @param o1 The first game object involved in the collision.
     * @param o2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        Random random = new Random();
        int strategy1Index = random.nextInt(SPECIAL_BEHAVIOR_UPPER_BOUND);
        int strategy2Index = random.nextInt(SPECIAL_BEHAVIOR_UPPER_BOUND);
        if(strategy1Index == DOUBLE_SPECIAL_BEHAVIOR || strategy2Index == DOUBLE_SPECIAL_BEHAVIOR){
            strategy1Index = random.nextInt(SINGLE_SPECIAL_BEHAVIOR_UPPER_BOUND);
            strategy2Index = random.nextInt(SINGLE_SPECIAL_BEHAVIOR_UPPER_BOUND);
            int strategy3Index = random.nextInt(SINGLE_SPECIAL_BEHAVIOR_UPPER_BOUND);
            this.strategies[strategy1Index].onCollision(o1, o2);
            this.strategies[strategy2Index].onCollision(o1, o2);
            this.strategies[strategy3Index].onCollision(o1, o2);
        }
        else{
            this.strategies[strategy1Index].onCollision(o1, o2);
            this.strategies[strategy2Index].onCollision(o1, o2);
        }

    }


}
