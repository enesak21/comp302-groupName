package domain.theOutsideGame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.animation.WalkAnimationPlayer;
import domain.theOutsideGame.animation.IdleAnimationPlayer;
import domain.theOutsideGame.animation.WalkAnimation;
import domain.theOutsideGame.world.GameWorld;

public class Player {

    private int x, y;
    private int width, height;
    private int speed;
    private WalkAnimation currentAnimation;
    private InputHandler inputHandler;
    private boolean isMoving;
    private boolean isMovingLeft;
    private GameWorld gameWorld;

    public Player(int startX, int startY, int width, int height, int speed, InputHandler inputHandler, GameWorld gameWorld) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.inputHandler = inputHandler;
        this.gameWorld = gameWorld;

        currentAnimation = new IdleAnimationPlayer(false);  // Default idle animation (facing right)
        isMoving = false;
        isMovingLeft = false;
    }

    public void update() {
        move();

        // Change animation based on movement
        if (isMoving) {
            if (!(currentAnimation instanceof WalkAnimationPlayer)) {
                currentAnimation = new WalkAnimationPlayer(isMovingLeft);  // Update with the correct direction
            }
        } else {
            // Switch to idle animation depending on the direction
            if (isMovingLeft) {
                currentAnimation = new IdleAnimationPlayer(true);  // Left idle animation
            } else {
                currentAnimation = new IdleAnimationPlayer(false);  // Right idle animation
            }
        }

        currentAnimation.update();  // Update the current animation frame
    }

    private void move() {

        boolean wasMoving = isMoving;
        isMoving = false;  // Reset movement state before checking

        int deltaX = 0;
        int deltaY = 0;

        if (inputHandler.isUp()) {
            if (isEnteringCave()) {
                // Close the game window if the player is entering the cave
                gameWorld.closeWindow();

            }
            deltaY = -speed;
            isMoving = true;
        }
        if (inputHandler.isDown()) {
            deltaY = speed;
            isMoving = true;
        }
        if (inputHandler.isLeft()) {
            deltaX = -speed;
            isMoving = true;
            isMovingLeft = true;
        }
        if (inputHandler.isRight()) {
            deltaX = speed;
            isMoving = true;
            isMovingLeft = false;
        }

        // Check for collisions before moving
        Rectangle playerBounds = new Rectangle(x + deltaX, y + deltaY, width, height);
        if (!gameWorld.checkCollision(playerBounds)) {
            // No collision, so update the position
            x += deltaX;
            y += deltaY;
        }

        // If the player is not moving anymore, switch to idle animation
        if (!isMoving && wasMoving) {
            if (isMovingLeft) {
                currentAnimation = new IdleAnimationPlayer(true);  // Left idle animation
            } else {
                currentAnimation = new IdleAnimationPlayer(false);  // Right idle animation
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), x, y, width, height, null);  // Render current animation frame
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isEnteringCave() {
        return x >= 445 && x <= 475 && y >= 110 && y <= 150;
    }
}
