package domain.theOutsideGame.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {

    // Constants for key inputs
    public static final int KEY_UP = KeyEvent.VK_W;
    public static final int KEY_DOWN = KeyEvent.VK_S;
    public static final int KEY_LEFT = KeyEvent.VK_A;
    public static final int KEY_RIGHT = KeyEvent.VK_D;

    private boolean[] keys;
    private boolean up, down, left, right;

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
}
