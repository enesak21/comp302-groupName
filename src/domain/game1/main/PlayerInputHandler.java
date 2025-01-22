package domain.game1.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInputHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();



        if (keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }

        else if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        else if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        else if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }

        if (keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

    }

    public boolean movePressed() {
        return upPressed || downPressed || rightPressed || leftPressed;
    }

}
