package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import static bricker.main.Constants.*;


/**
 * The HealthBar class represents a health bar in the game, displaying hearts and numerical lives.
 * It manages the current number of lives, updates the display of hearts, and reflects changes in lives.
 */
public class HealthBar extends GameObject {
    private final Heart[] hearts;
    private int curLives;
    private final Vector2 barObjectSize;
    private NumericLives numericLives;
    private final ImageReader imageReader;
    private final int maxLives;
    private final int startingLives;

    /**
     * Construct a new HealthBar instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param imageReader   An ImageReader instance for loading images.
     * @param startingLives The initial number of lives.
     * @param maxLives      The maximum number of lives.
     */
    public HealthBar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,ImageReader imageReader,
                     int startingLives, int maxLives) {
        super(topLeftCorner, dimensions, renderable);

        this.startingLives = startingLives;
        this.maxLives = maxLives;
        this.imageReader = imageReader;
        curLives = 0;
        this.barObjectSize = new Vector2(dimensions.y(), dimensions.y());
        this.hearts = new Heart[maxLives];
        this.createNumericLives();
        initializeHearts();
    }

    /**
     * Get the current number of lives.
     *
     * @return The current number of lives.
     */
    public int getCurLives() {
        return this.curLives;
    }

    /**
     * Decrease the current number of lives by one and update the numeric display.
     */
    public void decreaseCurLives() {
        this.curLives--;
        this.setNumLives();
    }

    /**
     * Get the array of heart objects representing lives.
     *
     * @return An array of Heart objects.
     */
    public Heart[] getHearts() {
        return hearts;
    }


    /**
     * Add a heart to the health bar if there is room.
     *
     * @return true if a heart was added, false if the health bar is full.
     */
    public boolean addHeart() {
        if (curLives < this.maxLives) {
            Renderable heartImage =
                    imageReader.readImage(ASSETS_HEART_PNG, true);
            this.hearts[curLives] = new Heart(new Vector2((curLives + 2) * (barObjectSize.x() + 5),
                    super.getTopLeftCorner().y()), barObjectSize, heartImage);
            this.hearts[curLives].setTag(HEALTH_BAR_HEART_TAG);
            curLives++;
            numericLives.setNumLives(curLives);
            return true;
        }
        return false;
    }


    /**
     * Get the NumericLives object representing the numerical display of lives.
     *
     * @return The NumericLives object.
     */
    public NumericLives getNumericLives() {
        return numericLives;
    }

    /**
     * Update the numeric display of lives to reflect the current number of lives.
     */
    public void setNumLives() {
        this.numericLives.setNumLives(this.curLives);
    }


    private void createNumericLives() {
        TextRenderable numLives = new TextRenderable(String.valueOf(this.curLives));
        this.numericLives = new NumericLives(new Vector2(barObjectSize.x() + 5,
                super.getTopLeftCorner().y()), barObjectSize, numLives, this.hearts.length);
    }

    private void initializeHearts() {

        for (int i = 0; i < startingLives; i++) {
            this.addHeart();
        }
    }

}
