package domain.entity.playerObjects;

import domain.entity.Direction;
import domain.game.CollisionChecker;
import domain.game.Tile;
import domain.entity.Entity;
import main.PlayModePanel;
import main.PlayerController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private String name;
    private int health;
    private Inventory inventory;
    private Tile location;
    boolean moving = false;
    int pixelCounter = 0;
    private static Player instance;

    PlayModePanel playModePanel;
    PlayerController playerController;
    private CollisionChecker collisionChecker;

    private Player(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerController playerController) {
        super(gridX, gridY, tileSize);
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerController = playerController;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.DOWN;
        updatePixelPosition();
    }


    public static Player getInstance(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerController playerController) {
        if (instance==null) {
            return new Player(name, gridX, gridY, tileSize, playModePanel, playerController);
        }
        return instance;
    }

    public void restart(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerController playerController) {
        this.name = name; //if user wants to restart the game, this method will be called
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = tileSize;
        this.playModePanel = playModePanel;
        this.playerController = playerController;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.DOWN;
        this.moving = false;
        this.pixelCounter = 0;
        updatePixelPosition();
    }

    @Override
    public void update() {
        if (!moving) {
            if (playerController.upPressed) {
                direction = Direction.UP;
            } else if (playerController.downPressed) {
                direction = Direction.DOWN;
            } else if (playerController.leftPressed) {
                direction = Direction.LEFT;
            } else if (playerController.rightPressed) {
                direction = Direction.RIGHT;
            }
            if (playerController.movePressed() && !collisionChecker.checkCollision(this)) {
                moving = true;
            }
        }
        if (moving) {
            switch (direction) {
                case UP -> pixelY -= speed;
                case DOWN -> pixelY += speed;
                case LEFT -> pixelX -= speed;
                case RIGHT -> pixelX += speed;
            }
            pixelCounter += speed;

            if (pixelCounter >= tileSize) {
                switch (direction) {
                    case UP -> gridY--;
                    case DOWN -> gridY++;
                    case LEFT -> gridX--;
                    case RIGHT -> gridX++;
                }
                pixelCounter = 0;
                moving = false;
                updatePixelPosition();
            }
        }
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getHealth() {
        return health;
    }


public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    /**
     * Uses an enchantment
     * 
     * @param enchantmentType The type of the enchantment to be used
     * @return true if the enchantment was used succesfully
     */
    public boolean useEnchantment(String enchantmentType) {
        return false;
    }
}