package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

import static bricker.main.Constants.*;

/**
 * The Paddle class represents a paddle object in the game.
 * It handles user input for movement, checks for border crossing, and applies a collision strategy
 * on collisions.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private final UserInputListener inputListener;
    private final Vector2 windowDim;
    private final int borderSize;
    private int numCollisionsSecondaryPaddle;
    private GameObject CollidedGameObject;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null.
     * @param inputListener The UserInputListener for detecting user input.
     * @param windowDim     The dimensions of the game window.
     * @param borderSize    The size of the border within which the paddle can move.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, Vector2 windowDim, int borderSize) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDim = windowDim;
        this.borderSize = borderSize;
        this.numCollisionsSecondaryPaddle = 0;
        this.CollidedGameObject = null;
    }

    /**
     * Update the state of the paddle.
     *
     * @param deltaTime The time that has passed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        movementDir = isCrossedBoarder(movementDir);
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
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
        secondaryPaddleCollisionBehavior(other);
        UserPaddleHeartCollisionBehavior(other);
    }
    /**
     * Resets the collidedGameObject to null.
     */
    public void resetCollidedGameObject(){
        this.CollidedGameObject = null;
    }

    /**
     * Gets the GameObject that the paddle has most recently collided with.
     *
     * @return The GameObject that the paddle collided with.
     */
    public GameObject getCollidedGameObject() {
        return this.CollidedGameObject;
    }

    /**
     * Gets the number of collisions with a secondary paddle.
     *
     * @return The number of collisions with a secondary paddle.
     */
    public int getNumCollisionsSecondaryPaddle() {
        return this.numCollisionsSecondaryPaddle;
    }
    /**
     * Handles the collision behavior when the paddle collides with a heart object.
     *
     * @param other The other GameObject involved in the collision.
     */
    private void UserPaddleHeartCollisionBehavior(GameObject other) {
        if(other.getTag().equals(FALLING_HEART_TAG) && this.getTag().equals(USER_PADDLE_TAG))
        {
            this.CollidedGameObject = other;
        }
    }

    private void secondaryPaddleCollisionBehavior(GameObject o2){
        if(o2.getTag().equals(MAIN_BALL_TAG) || o2.getTag().equals(PUCK_BALL_TAG)){
            if(numCollisionsSecondaryPaddle <= MAX_COLLISIONS_WITH_SECONDARY_PADDLE){
                numCollisionsSecondaryPaddle++;
            }
        }

    }
    private Vector2 isCrossedBoarder(Vector2 movementDir) {
        if (super.getTopLeftCorner().x() < borderSize) {
            movementDir = Vector2.ZERO;
            super.setTopLeftCorner(new Vector2(borderSize,
                    super.getTopLeftCorner().y()));

        }
        else if(super.getTopLeftCorner().x() + super.getDimensions().x() > windowDim.x() - borderSize) {

            movementDir = Vector2.ZERO;
            super.setTopLeftCorner(new Vector2(windowDim.x() - borderSize - super.getDimensions().x(),
                    super.getTopLeftCorner().y()));
        }
        return movementDir;
    }

}
