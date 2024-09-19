package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.outOfScreenStrategies.OutOfScreenStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Ball class represents a ball object in the game.
 * It handles collisions with other game objects, plays a sound on collision,
 * and applies specified strategies for collisions and out-of-screen events.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter;
    private final CollisionStrategy collisionStrategy;
    private final OutOfScreenStrategy outOfScreenStrategy;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null.
     * @param collisionSound The sound to play on collision.
     * @param collisionStrategy The strategy to apply on collision.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound, CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.collisionStrategy = collisionStrategy;
        this.outOfScreenStrategy = null;
    }

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null.
     * @param collisionSound The sound to play on collision.
     * @param collisionStrategy The strategy to apply on collision.
     * @param strategy The strategy to apply when the ball is out of the screen.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, CollisionStrategy collisionStrategy, OutOfScreenStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.collisionStrategy = collisionStrategy;
        this.outOfScreenStrategy = strategy;
    }

    /**
     * Get the number of collisions that have occurred.
     *
     * @return The number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Reset the collision counter to zero.
     */
    public void resetCollisionCounter() {
        this.collisionCounter = 0;
    }

    /**
     * Called when this object starts colliding with another object.
     *
     * @param other The other GameObject this is colliding with.
     * @param collision The Collision object containing details about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        this.collisionCounter++;
        this.collisionStrategy.onCollision(this, other);
    }

    /**
     * Update the state of the ball.
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
