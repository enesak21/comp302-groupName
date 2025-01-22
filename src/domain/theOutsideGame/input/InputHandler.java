package domain.theOutsideGame.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;

public class InputHandler extends KeyAdapter {

    // Constants for key inputs
    public static final int KEY_UP = KeyEvent.VK_W;
    public static final int KEY_DOWN = KeyEvent.VK_S;
    public static final int KEY_LEFT = KeyEvent.VK_A;
    public static final int KEY_RIGHT = KeyEvent.VK_D;
    public static final int KEY_INTERACT = KeyEvent.VK_E; // 'E' key for interaction
    public static boolean dialogueTriggered = false; // Flag to prevent infinite dialogue creation

    private boolean[] keys;
    private boolean up, down, left, right, interact;

    public InputHandler() {
        keys = new boolean[256]; // Array to track all possible key states
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void update() {
        up = keys[KEY_UP];
        down = keys[KEY_DOWN];
        left = keys[KEY_LEFT];
        right = keys[KEY_RIGHT];
        interact = keys[KEY_INTERACT];
    }

    public boolean isKeyPressed(int key) {
        return keys[key];
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    // Method to check if 'E' key is pressed and if the player is near Arwen
    public void isInteractNearArwen(int playerX, int playerY) {
        // Check if 'E' is pressed and the player is near Arwen, and if dialogue hasn't been triggered yet
        if (interact && !dialogueTriggered && isNearArwen(playerX, playerY)) {
            domain.TheDialogue.DialogueSetup.createInitialDialogue(); // Create the initial dialogue
            dialogueTriggered = true; // Set the flag to true to prevent infinite dialogue creation
        }
    }

    // Method to check if the mouse is within 50 pixels of Arwen
    private boolean isNearArwen(int playerX, int playerY) {
        // Arwen's position and size
        int arwenX = 400;
        int arwenY = 400;
        int arwenWidth = 70;
        int arwenHeight = 70;

        // Check if the mouse click is within 50 pixels of Arwen
        int arwenCenterX = arwenX + arwenWidth / 2;
        int arwenCenterY = arwenY + arwenHeight / 2;
        int distance = (int) Math.sqrt(Math.pow(playerX - arwenCenterX, 2) + Math.pow(playerY - arwenCenterY, 2));

        return distance <= 50; // Return true if the click is within 50 pixels


    }
}
