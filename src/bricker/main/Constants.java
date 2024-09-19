package bricker.main;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;

import java.awt.*;

/**
 * Public constants used in the Bricker game.
 */
public class Constants {
    //sizes of objects
    /**
     * Width of the game border in pixels.
     */
    public static final int BORDER_WIDTH = 10;

    /**
     * Height of the paddle in pixels.
     */
    public static final int PADDLE_HEIGHT = 15;

    /**
     * Width of the paddle in pixels.
     */
    public static final int PADDLE_WIDTH = 100;

    /**
     * Radius of the ball in pixels.
     */
    public static final int BALL_RADIUS = 20;


    /**
     * Margin between rows of bricks in pixels.
     */
    public static final int ROW_MARGIN = 3;

    /**
     * Margin between the bricks and the border in pixels.
     */
    public static final int MARGIN_FROM_BORDER = 5;

    /**
     * Height of each brick in pixels.
     */
    public static final int BRICK_HEIGHT = 15;

    /**
     * Height of the health bar in pixels.
     */
    public static final int HEALTH_BAR_HEIGHT = 15;

    /**
     * Height of the upper border as a fraction of the screen height.
     */
    public static final float UPPER_BORDER_HEIGHT = 0.1f;

    /**
     * Margin from the bottom of the screen to the paddle in pixels.
     */
    public static final int PADDLE_MARGIN_FROM_SCREEN_BOTTOM = 30;

    /**
     * Margin from the bottom of the screen to the health bar in pixels.
     */
    public static final int HEALTH_BAR_MARGIN_FROM_SCREEN_BOTTOM = 20;

    /**
     * Width of the game screen in pixels.
     */
    public static final int SCREEN_WIDTH = 700;

    /**
     * Height of the game screen in pixels.
     */
    public static final int SCREEN_HEIGHT = 500;

    /**
     * Renderable the color of the border.
     */
    public static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));


    //speed settings
    /**
     * Speed of the falling heart item.
     */
    public static final int FALLING_HEART_SPEED = 100;

    /**
     * Speed of the ball in the game.
     */
    public static final float BALL_SPEED = 100;

    //tags
    /**
     * Tag for the user's paddle.
     */
    public static final String USER_PADDLE_TAG = "userPaddle";

    /**
     * Tag for the secondary paddle.
     */
    public static final String SECONDARY_PADDLE_TAG = "secondaryPaddle";

    /**
     * Tag for the puck ball.
     */
    public static final String PUCK_BALL_TAG = "puckBall";

    /**
     * Tag for the main ball.
     */
    public static final String MAIN_BALL_TAG = "mainBall";

    /**
     * Tag for the falling heart item.
     */
    public static final String FALLING_HEART_TAG = "fallingHeart";

    /**
     * Tag for the heart in the health bar.
     */
    public static final String HEALTH_BAR_HEART_TAG = "healthBarHeart";

    /**
     * Tag for the brick elements.
     */
    public static final String BRICK_TAG = "brick";

    /**
     * Starting number of lives for the player.
     */
    public static final int STARTING_LIVES = 3;

    /**
     * Maximum number of lives the player can have.
     */
    public static final int MAX_LIVES = 4;
    /**
     * Maximum number of collision with secondary paddle before it's deleted.
     */
    public static final int MAX_COLLISIONS_WITH_SECONDARY_PADDLE = 4;

    /**
     * Maximum number of collisions the ball has before the camera is reset.
     */
    public static final int MAX_COLLISIONS_FOR_CAMERA = 4;

    /**
     * Factor for adjusting the camera view.
     */
    public static final float CAMERA_FACTOR = 1.2f;

    /**
     * Ratio of the puck ball size relative to the main ball.
     */
    public static final float PUCK_BALL_RATIO = 0.75f;


    /**
     * Default number of rows of bricks.
     */
    public static final int DEFAULT_NUM_ROWS = 7;

    /**
     * Default number of bricks per row.
     */
    public static final int DEFAULT_BRICKS_PER_ROW = 8;


    //messages
    /**
     * Message displayed when the player wins.
     */
    public static final String WINNING_PROMPT = "You win!";

    /**
     * Message prompting the player to play again.
     */
    public static final String PLAY_AGAIN_PROMPT = "Play again?";

    /**
     * Message displayed when the player loses.
     */
    public static final String LOSING_PROMPT = "You Lose!";

    //image paths
    /**
     * Path to the brick image asset.
     */
    public static final String ASSETS_BRICK_PNG = "assets/brick.png";

    /**
     * Path to the heart image asset.
     */
    public static final String ASSETS_HEART_PNG = "assets/heart.png";

    /**
     * Path to the ball image asset.
     */
    public static final String ASSETS_BALL_PNG = "assets/ball.png";

    /**
     * Path to the sound effect for blop.
     */
    public static final String ASSETS_BLOP_WAV = "assets/blop.wav";

    /**
     * Path to the mock ball image asset.
     */
    public static final String ASSETS_MOCK_BALL_PNG = "assets/mockBall.png";

    /**
     * Path to the dark background image asset.
     */
    public static final String ASSETS_DARK_BG_2_SMALL_JPEG = "assets/DARK_BG2_small.jpeg";

    /**
     * Path to the paddle image asset.
     */
    public static final String ASSETS_PADDLE_PNG = "assets/paddle.png";

    /**
     * Title of the game window.
     */
    public static final String BRICKER_WINDOW_TITLE = "bricker";
}

