package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

import static bricker.main.Constants.MAIN_BALL_TAG;

/**
 * The ChangeCameraStrategy class implements the CollisionStrategy interface.
 * When a collision occurs with the main ball, it triggers a camera change in the game.
 */
public class ChangeCameraStrategy implements CollisionStrategy{
    private final BrickerGameManager brickerGameManager;

    /**
     * Constructs a ChangeCameraStrategy with the specified BrickerGameManager.
     *
     * @param brickerGameManager the game manager responsible for managing game state and behavior.
     */
    public ChangeCameraStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }
    /**
     * Handles the collision event between two game objects. When a collision occurs and the second
     * game object has the tag MAIN_BALL_TAG, this method triggers a camera change to follow the ball.
     *
     * @param o1 the first game object involved in the collision.
     * @param o2 the second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject o1, GameObject o2) {
        if (o2.getTag().equals(MAIN_BALL_TAG)) {
            brickerGameManager.changeCamera((Ball) o2);
        }
    }
}
