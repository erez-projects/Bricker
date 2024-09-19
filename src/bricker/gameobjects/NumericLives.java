package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * NumericLives is a game object that displays the number of lives left in the game.
 * It updates the text and color based on the current number of lives.
 */
public class NumericLives extends GameObject {

    TextRenderable numLives;

    /**
     * Construct a new NumericLives instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param textRenderable The renderable representing the text of the object.
     * @param maxLives       The initial number of lives.
     */
    public NumericLives(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable textRenderable,
                        int maxLives) {

        super(topLeftCorner, dimensions, textRenderable);
        numLives = textRenderable;
        this.setColor(maxLives);
    }

    private void setColor(int curLives) {
        if (curLives >= 3) {
            numLives.setColor(Color.green);
        } else if (curLives == 2) {
            numLives.setColor(Color.yellow);
        } else if (curLives == 1) {
            numLives.setColor(Color.red);
        }
    }

    /**
     * Updates the color of the text based on the current number of lives.
     *
     * @param curLives The current number of lives.
     */
    public void setNumLives(int curLives) {
        this.numLives.setString(String.valueOf(curLives));
    }
}
