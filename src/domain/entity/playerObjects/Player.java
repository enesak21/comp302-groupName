package domain.entity.playerObjects;

import domain.entity.Direction;
import domain.game.CollisionChecker;
import domain.game.Tile;
import domain.entity.Entity;
import domain.panels.PlayModePanel;
import main.PlayerInputHandler;

public class Player extends Entity {
    private String name;
    private int health;
    private Inventory inventory;
    private Tile location;
    private boolean moving = false;
    private int pixelCounter = 0;
    private static Player instance;

    PlayerInputHandler playerInputHandler;
    private CollisionChecker collisionChecker;

    private Player(String name, int gridX, int gridY, int tileSize, PlayerInputHandler playerInputHandler) {
        super(gridX + 2, gridY + 2, tileSize); // +2 is for the offset
        this.name = name;
        this.playerInputHandler = playerInputHandler;
        this.speed = 4;
        this.health = 4;
        this.direction = Direction.RIGHT;
        updatePixelPosition();
    }


    public static Player getInstance(String name, int gridX, int gridY, int tileSize, PlayerInputHandler playerInputHandler) {
        if (instance==null) {
            return new Player(name, gridX, gridY, tileSize, playerInputHandler);
        }
        return instance;
    }

    /**
     * Restarts the player with the specified parameters.
     * This method is called when the user wants to restart the game.
     *
     * @param name the name of the player
     * @param gridX the x-coordinate of the player's position on the grid
     * @param gridY the y-coordinate of the player's position on the grid
     * @param tileSize the size of each tile in the game
     * @param playerInputHandler the handler for player input
     */
    public void restart(String name, int gridX, int gridY, int tileSize, PlayerInputHandler playerInputHandler) {
        this.name = name;
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = tileSize;
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
                // eski kod burada sorun çıkarsa belki döneriz.
                /*
                //Update old grid isSolid(false)
                playModePanel.getGame().getGrid().getTileAt(gridX - 2, gridY - 2).setSolid(false);

                switch (direction) {
                    case UP -> gridY--;
                    case DOWN -> gridY++;
                    case LEFT -> gridX--;
                    case RIGHT -> gridX++;
                }

                //Update new grid isSolid(true)
                playModePanel.getGame().getGrid().getTileAt(gridX - 2, gridY - 2).setSolid(true);

                pixelCounter = 0;
                moving = false;
                updatePixelPosition();

                 */
                collisionChecker.updateGridSolidState(gridX, gridY, direction); //bu kısım yazılması gerekiyor solid muhabbeti
                pixelCounter = 0;
                moving = false;
                updatePixelPosition();
            }
        }
    }

    public PlayerInputHandler getPlayerInputHandler() {
        return playerInputHandler;
    }

    public void setPlayerInputHandler(PlayerInputHandler playerInputHandler) {
        this.playerInputHandler = playerInputHandler;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Reduces the player's health by one. If the player's health reaches zero,
     * it sets the remaining game time to zero and triggers the game over handler.
     */
    public void reduceHealth() {
        health--;
        if(health<0){
            health=0; //health can't be negative
        }
    }

    public boolean isDead() {
        return health == 0;
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