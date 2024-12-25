package domain.entity.playerObjects;

import domain.entity.Direction;
import domain.game.CollisionChecker;
import domain.game.Tile;
import domain.entity.Entity;
import domain.panels.PlayModePanel;
import main.PlayerController;

public class Player extends Entity {
    private String name;
    private int health;
    private Inventory inventory;
    private Tile location;
    boolean moving = false;
    int pixelCounter = 0;

    PlayModePanel playModePanel;
    PlayerController playerController;
    private CollisionChecker collisionChecker;

    public Player(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerController playerController) {
        super(gridX, gridY, tileSize);
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerController = playerController;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.DOWN;
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