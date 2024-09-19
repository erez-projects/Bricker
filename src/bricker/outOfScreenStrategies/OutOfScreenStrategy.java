package bricker.outOfScreenStrategies;

import danogl.GameObject;
/**
 * OutOfScreenStrategy is an interface that defines the behavior for handling game objects that move out of
 * the screen boundaries.
 */
public interface OutOfScreenStrategy {
    /**
     * This method is called when a game object moves out of the screen.
     *
     * @param object The game object that moved out of the screen.
     */
    void outOfScreen(GameObject object);
}
