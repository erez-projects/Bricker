package bricker.main;

import bricker.outOfScreenStrategies.RemoveOutOfScreenStrategy;
import bricker.outOfScreenStrategies.OutOfScreenStrategy;
import bricker.gameobjects.*;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.Constants.*;
/**
 * BrickerGameManager class manages the main gameplay logic for the Bricker game.
 * It initializes game objects, handles game updates, and manages user input.
 */
public class BrickerGameManager extends GameManager {
    private final Vector2 userPaddleLocation;
    private final Vector2 secondaryPaddleLocation;


    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private final int numRows;
    private final int bricksPerRow;
    private int numBricks;
    private KeyboardInput keyboardInput;
    private HealthBar healthBar;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private Paddle userPaddle;
    private Paddle secondaryPaddle;
    private final CollisionStrategy collisionStrategy;
    private final OutOfScreenStrategy outOfScreenStrategy;


    /**
     * Constructs a new BrickerGameManager instance.
     *
     * @param windowTitle The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numRows The number of rows of bricks.
     * @param bricksPerRow The number of bricks per row.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows, int bricksPerRow) {
        super(windowTitle, windowDimensions);

        this.numRows = numRows;
        this.bricksPerRow = bricksPerRow;
        this.numBricks = numRows * bricksPerRow;
        this.userPaddleLocation = new Vector2(windowDimensions.x() / 2,
                (int) windowDimensions.y() - PADDLE_MARGIN_FROM_SCREEN_BOTTOM);
        this.secondaryPaddleLocation = new Vector2(windowDimensions.x() / 2,  windowDimensions.y() / 2);
        this.collisionStrategy = new BasicCollisionStrategy(this);
        this.outOfScreenStrategy = new RemoveOutOfScreenStrategy(this);
        this.secondaryPaddle = null;

    }

    /**
     * Initializes the game using initialization from GameManager, and creates the needed objects to begin the
     * game.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        initializeSettings(imageReader, soundReader, inputListener, windowController);
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.keyboardInput = new KeyboardInput(inputListener);

        createBall();

        this.userPaddle = createPaddle(USER_PADDLE_TAG);

        createBorders();

        addBackground();

        createAllBricks();

        createHealthBar();

    }

    /**
     * Gets the dimensions of the game window.
     *
     * @return The dimensions of the game window.
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    private void initializeSettings(ImageReader imageReader, SoundReader soundReader,
                                    UserInputListener inputListener,
                                    WindowController windowController) {
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
    }

    private void createHealthBar() {
        this.healthBar = new HealthBar(new Vector2(BORDER_WIDTH ,
                windowDimensions.y() - HEALTH_BAR_MARGIN_FROM_SCREEN_BOTTOM),
                new Vector2(windowDimensions.x() - (2 * BORDER_WIDTH), HEALTH_BAR_HEIGHT),
                null, imageReader, STARTING_LIVES, MAX_LIVES);
        gameObjects().addGameObject(healthBar, Layer.UI);
        createHearts(healthBar);
        gameObjects().addGameObject(healthBar.getNumericLives(), Layer.UI);

    }

    private void createHearts(HealthBar healthBar) {
        Heart[] hearts = healthBar.getHearts();
        for (int i = 0; i < this.healthBar.getCurLives(); i++) {
            gameObjects().addGameObject(hearts[i], Layer.UI);
        }
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        secondaryPaddleRemoval();
        catchFallingHeart();
        mainBallCameraBehavior();
    }


    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if (numBricks == 0|| this.keyboardInput.isWinKeyPressed()) {
            prompt = WINNING_PROMPT;
        }
        if (ballHeight > this.windowDimensions.y()) {

            prompt = onLoss();
        }
        if (this.healthBar.getCurLives() == 0 || prompt.equals(WINNING_PROMPT)) {

            prompt += PLAY_AGAIN_PROMPT;
            if (windowController.openYesNoDialog(prompt)) {
                this.resetGame();
            } else
                this.windowController.closeWindow();
        }
    }

    private String onLoss() {
        String prompt;
        prompt = LOSING_PROMPT;
        if (this.healthBar.getCurLives() > 0) {
            this.healthBar.decreaseCurLives();
            this.setBallDirection();
            Heart[] hearts = this.healthBar.getHearts();
            removeObject(hearts[healthBar.getCurLives()]);
        }
        return prompt;
    }

    private void resetGame() {
        this.numBricks = numRows * bricksPerRow;
        this.windowController.resetGame();
    }

    private void createAllBricks() {
        float brickLength =
                (this.windowController.getWindowDimensions().x() - 2 * (BORDER_WIDTH
                        + MARGIN_FROM_BORDER)) / this.bricksPerRow; //calculate
        double margin = brickLength * 0.1;
        brickLength -= (float) margin;

        for (int i = 1; i <= this.numRows; i++) {
            float yRow = i * (BRICK_HEIGHT + ROW_MARGIN);
            float xRow = BORDER_WIDTH + MARGIN_FROM_BORDER;
            for (int j = 0; j < this.bricksPerRow; j++) {
                Vector2 startBrick = new Vector2(xRow, yRow);
                createBrick(startBrick, new Vector2(brickLength, BRICK_HEIGHT));
                xRow += (float) (brickLength + margin);
            }
        }
    }

    private void createBrick(Vector2 startBrick, Vector2 brickSize) {
        Renderable brickImage =
                this.imageReader.readImage(ASSETS_BRICK_PNG, false);

        GameObject brick =
                new Brick(startBrick, brickSize, brickImage, this.collisionStrategy);
        gameObjects().addGameObject(brick);
    }

    private void createBall() {
        Renderable ballImage =
                this.imageReader.readImage(ASSETS_BALL_PNG, true);
        Sound collisionSound = this.soundReader.readSound(ASSETS_BLOP_WAV);
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound,
                collisionStrategy);
        ball.setTag(MAIN_BALL_TAG);
        setBallDirection();
        gameObjects().addGameObject(ball);
    }


    private void setBallDirection() {
        ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Adds additional pucks to the game at the specified center position.
     *
     * @param center The center position to add the pucks.
     */
    public void additionalPucks(Vector2 center) {
        createPuck(center);
        createPuck(center);
    }

    private void createPuck(Vector2 center) {
        Renderable puckImage =
                imageReader.readImage(ASSETS_MOCK_BALL_PNG, true);
        Sound collisionSound = soundReader.readSound(ASSETS_BLOP_WAV);
        Ball puck = new Ball(
                Vector2.ZERO, new Vector2(PUCK_BALL_RATIO * BALL_RADIUS, PUCK_BALL_RATIO * BALL_RADIUS),
                puckImage, collisionSound, collisionStrategy, outOfScreenStrategy);
        puck.setTag(PUCK_BALL_TAG);
        gameObjects().addGameObject(puck);
        setPuckDirection(puck, center);
    }

    private void setPuckDirection(Ball puck, Vector2 center) {
        puck.setCenter(center);
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * BALL_SPEED;

        puck.setVelocity(new Vector2(velocityX, velocityY));

    }

    private void addBackground() {
        Vector2 windowDimensions = windowController.getWindowDimensions();
        Renderable backgroundImage =
                imageReader.readImage(ASSETS_DARK_BG_2_SMALL_JPEG, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private Paddle createPaddle(String tag) {
        Renderable paddleImage = this.imageReader.readImage(
                ASSETS_PADDLE_PNG, false);
        Paddle paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener, windowDimensions, BORDER_WIDTH);
        paddle.setTag(tag);
        if(tag.equals(USER_PADDLE_TAG)){
            paddle.setCenter(
                    userPaddleLocation);
        }
        else if(tag.equals(SECONDARY_PADDLE_TAG)){
            paddle.setCenter(
                    secondaryPaddleLocation);

        }
        gameObjects().addGameObject(paddle);
        return paddle;
    }

    /**
     * Creates a secondary paddle if it does not already exist.
     */
    public void createSecondaryPaddle(){
        if(secondaryPaddle == null){
            this.secondaryPaddle = createPaddle(SECONDARY_PADDLE_TAG);
        }
    }

    private void secondaryPaddleRemoval(){
        if(this.secondaryPaddle != null){
            if(this.secondaryPaddle.getNumCollisionsSecondaryPaddle() == 4){
                this.removeObject(this.secondaryPaddle);
                this.secondaryPaddle = null;
            }
        }
    }

    private void createBorders() {
        //left border
        gameObjects().addGameObject(
                new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, this.windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        //right border
        gameObjects().addGameObject(
                new GameObject(new Vector2(this.windowDimensions.x() - BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH,
                        this.windowDimensions.y()), BORDER_RENDERABLE)
        );
        //upper border
        gameObjects().addGameObject(
                new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(), UPPER_BORDER_HEIGHT),
                        BORDER_RENDERABLE)
        );
    }

    private void catchFallingHeart(){
        GameObject heart= this.userPaddle.getCollidedGameObject();
        if( heart != null){
            this.removeObject(heart);
            this.userPaddle.resetCollidedGameObject();
            this.addHeartToHealthBar();
        }
    }

    /**
     * Removes the specified game object from the game.
     *
     * @param object The game object to remove.
     * @return True if the object was successfully removed, false otherwise.
     */
    public boolean removeObject(GameObject object) {
        if (object.getTag().equals(BRICK_TAG)) {
            boolean isNotDeleted = gameObjects().removeGameObject(object);
            if (isNotDeleted) {
                this.numBricks--;
            }
            return isNotDeleted;
        }
        if (object.getTag().equals(HEALTH_BAR_HEART_TAG)) {
            return gameObjects().removeGameObject(object, Layer.UI);
        }

        return gameObjects().removeGameObject(object);
    }

    /**
     * Changes the camera to follow the specified ball.
     *
     * @param ball The ball to follow with the camera.
     */
    public void changeCamera(Ball ball){
        if(this.camera() == null){
            this.setCamera(new Camera(ball, Vector2.ZERO,
                    this.getWindowDimensions().mult(CAMERA_FACTOR),
                    this.getWindowDimensions()));
            ball.resetCollisionCounter();
        }
    }

    /**
     * Creates a falling heart at the specified center position.
     *
     * @param center The center position to create the falling heart.
     */
    public void createFallingHeart(Vector2 center){
        Renderable heartImage =
                imageReader.readImage(ASSETS_HEART_PNG, true);
        Heart fallingHeart = new Heart(
                Vector2.ZERO, new Vector2(HEALTH_BAR_HEIGHT,
                HEALTH_BAR_HEIGHT),
                heartImage, outOfScreenStrategy);
        fallingHeart.setTag(FALLING_HEART_TAG);
        gameObjects().addGameObject(fallingHeart);
        setFallingHeartDirections(fallingHeart, center);
    }

    private void setFallingHeartDirections(Heart fallingHeart, Vector2 center) {
        fallingHeart.setCenter(center);
        float velocityY = (float) FALLING_HEART_SPEED;
        fallingHeart.setVelocity(new Vector2(0, velocityY));
    }


    private void addHeartToHealthBar(){
        boolean addHeart = this.healthBar.addHeart();
        if(addHeart){
            gameObjects().addGameObject(healthBar.getHearts()[this.healthBar.getCurLives()-1], Layer.UI);
        }
    }

    private void mainBallCameraBehavior(){
        if(this.camera() != null){
            if(this.ball.getCollisionCounter() >= MAX_COLLISIONS_FOR_CAMERA){
                this.setCamera(null);
                this.ball.resetCollisionCounter();
            }
        }

    }

    /**
     * The main method serves as the entry point for the Bricker game application.
     * It initializes the game with the specified number of rows and bricks per row,
     * or defaults to predefined constants if no arguments are provided.
     *
     * @param args Command-line arguments where the first argument (args[0]) specifies the number of rows
     *             and the second argument (args[1]) specifies the number of bricks per row.
     */
    public static void main(String[] args) {
        int numRows = DEFAULT_NUM_ROWS;
        int bricksPerRow = DEFAULT_BRICKS_PER_ROW;
        if (args.length >= 2) {
            numRows = Integer.parseInt(args[0]);
            bricksPerRow = Integer.parseInt(args[1]);
        }
        new BrickerGameManager(
                BRICKER_WINDOW_TITLE,
                new Vector2(SCREEN_WIDTH, SCREEN_HEIGHT), numRows, bricksPerRow).run();

    }
}
