package domain.theOutsideGame.entities;

import java.awt.Graphics;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.animation.WalkAnimationPlayer;
import domain.theOutsideGame.animation.IdleAnimationPlayer;
import domain.theOutsideGame.animation.WalkAnimation;

public class Player {

    private int x, y;
    private int width, height;
    private int speed;
    private WalkAnimation currentAnimation;
    private InputHandler inputHandler;
    private boolean isMoving;
    private boolean isMovingLeft;  // Track if the player is moving left

    public Player(int startX, int startY, int width, int height, int speed, InputHandler inputHandler) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.inputHandler = inputHandler;

        // Initially set the player to idle animation
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

        if (inputHandler.isUp()) {
            y -= speed;
            isMoving = true;
        }
        if (inputHandler.isDown()) {
            y += speed;
            isMoving = true;
        }
        if (inputHandler.isLeft()) {
            x -= speed;
            isMoving = true;
            isMovingLeft = true;  // Set to left when moving left
        }
        if (inputHandler.isRight()) {
            x += speed;
            isMoving = true;
            isMovingLeft = false;  // Set to right when moving right
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
}
