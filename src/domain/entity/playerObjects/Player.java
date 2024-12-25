package domain.entity.playerObjects;

import domain.entity.Direction;
import domain.game.CollisionChecker;
import domain.game.Tile;
import domain.entity.Entity;
import domain.panels.PlayModePanel;
import main.PlayerController;
import main.PlayerInputHandler;


public class Player extends Entity {
    private String name;
    private int health;
    private Inventory inventory;
    private Tile location;
    boolean moving = false;
    int pixelCounter = 0;
    private static Player instance;

    PlayModePanel playModePanel;
    PlayerInputHandler playerInputHandler;
    private CollisionChecker collisionChecker;

    private Player(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerInputHandler playerInputHandler) {
        super(gridX + 2, gridY + 2, tileSize); // +2 is for the offset
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerInputHandler = playerInputHandler;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.RIGHT;
        updatePixelPosition();
    }


    public static Player getInstance(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerInputHandler playerInputHandler) {
        if (instance==null) {
            return new Player(name, gridX, gridY, tileSize, playModePanel, playerInputHandler);
        }
        return instance;
    }

    public void restart(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerInputHandler playerInputHandler) {
        this.name = name; //if user wants to restart the game, this method will be called
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = tileSize;
        this.playModePanel = playModePanel;
        this.playerInputHandler = playerInputHandler;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.RIGHT;
        this.moving = false;
        this.pixelCounter = 0;
        updatePixelPosition();
    }

    @Override
    public void update() {
        if (!moving) {
            if (playerInputHandler.upPressed) {
                direction = Direction.UP;
            } else if (playerInputHandler.downPressed) {
                direction = Direction.DOWN;
            } else if (playerInputHandler.leftPressed) {
                direction = Direction.LEFT;
            } else if (playerInputHandler.rightPressed) {
                direction = Direction.RIGHT;
            }
            if (playerInputHandler.movePressed() && !collisionChecker.checkCollision(this)) {
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

    public PlayerInputHandler getPlayerInputHandler() {
        return playerInputHandler;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth() {
        health--;
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