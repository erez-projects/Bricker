package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.outOfScreenStrategies.OutOfScreenStrategy;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Heart class represents a heart object in the game.
 * It handles collisions with the user paddle and applies specified strategies for collisions and
 * out-of-screen events.
 */
public class Heart extends GameObject {
    private static final String USER_PADDLE_TAG = "userPaddle";
    private OutOfScreenStrategy outOfScreenStrategy;


    /**
     * Construct a new Heart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered..
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);

    }
    /**
     * Construct a new Heart instance with an out-of-screen strategy.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy The strategy to apply when the heart is out of the screen.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  OutOfScreenStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.outOfScreenStrategy = strategy;

    }

    /**
     * Determine whether this object should collide with another object.
     *
     * @param other The other GameObject.
     * @return true if the other GameObject has the user paddle tag, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(USER_PADDLE_TAG);
    }

    /**
     * Update the state of the heart.
     *
     * @param deltaTime The time that has passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.outOfScreenStrategy != null){
            this.outOfScreenStrategy.outOfScreen(this);
        }
    }
}
