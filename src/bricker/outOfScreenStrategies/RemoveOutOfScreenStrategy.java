package bricker.outOfScreenStrategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * BasicOutOfScreenStrategy class handles the behavior of game objects when they move out of the screen bounds
 * It implements the OutOfScreenStrategy interface and provides a response for objects that go out of screen.
 */
public class RemoveOutOfScreenStrategy implements OutOfScreenStrategy {
    private final BrickerGameManager brickerGameManager;
    /**
     * Constructor for BasicOutOfScreenStrategy.
     * @param brickerGameManager Instance of BrickerGameManager to interact with game objects.
     */
    public RemoveOutOfScreenStrategy(BrickerGameManager brickerGameManager){
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handles the behavior when a game object moves out of the screen bounds.
     * @param object The game object that has moved out of the screen bounds.
     */
    public void outOfScreen(GameObject object){
        if(object.getCenter().y() > brickerGameManager.getWindowDimensions().y()){
            brickerGameManager.removeObject(object);
        }
    }
}
