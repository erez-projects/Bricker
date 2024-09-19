package bricker.gameobjects;

import danogl.gui.UserInputListener;

/**
 * KeyboardInput class handles keyboard input for the game.
 * It listens for specific key presses and provides methods to check if those keys are pressed.
 */
public class KeyboardInput {
    private final UserInputListener inputListener;
    private static final char WIN_KEY = 'W';

    /**
     * Constructor for KeyboardInput.
     * @param inputListener An instance of UserInputListener to listen for keyboard inputs.
     */
    public KeyboardInput(UserInputListener inputListener){
        this.inputListener = inputListener;
    }

    /**
     * Checks if the win key ('W') is pressed.
     * @return true if the win key is pressed, false otherwise.
     */
    public boolean isWinKeyPressed(){
        return inputListener.isKeyPressed(WIN_KEY);
    }

}
