package domain.entity.playerObjects;

import domain.enchantments.*;
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
    boolean moving = false;
    private int speed;
    int pixelCounter = 0;
    private static Player instance;
    private boolean invisibleToArchers = false;

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
        //Inventory is initialized here
        this.inventory = new Inventory();
    }


    public static Player getInstance(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerInputHandler playerInputHandler) {
        if (instance==null) {
            return new Player(name, gridX, gridY, tileSize, playModePanel, playerInputHandler);
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
     * @param playModePanel the panel used for play mode
     * @param playerInputHandler the handler for player input
     */
    public void restart(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerInputHandler playerInputHandler) {
        this.name = name;
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
    public void setSpeed(int speed) {this.speed = speed;}

    public void setHealth(int health){
        if (health <= 4) { // Health can't be more than 4
            this.health = health;
        }
    }

    /**
     * Reduces the player's health by one. If the player's health reaches zero,
     * it sets the remaining game time to zero and triggers the game over handler.
     */
    public void reduceHealth() {
        health--;
        if (health == 0) {
            playModePanel.getGame().getTimeController().setTimeLeft(0);
            playModePanel.getGameOverHandler().handle();
        }
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
    public Inventory getInventory() {
        return inventory;
    }
    public boolean getIsInvisibleToArchers() {
        return invisibleToArchers;
    }

    public void setInvisibleToArchers(boolean invisibleToArchers) {
        this.invisibleToArchers = invisibleToArchers;
    }

    public PlayModePanel getPlayModePanel() {
        return playModePanel;
    }
    public void useRevealEnchantment() {
        BaseEnchantment revealEnchantment = new Reveal(0, 0, tileSize); // Adjust parameters as needed
        revealEnchantment.applyEffect(playModePanel.getGame());
        this.getInventory().removeItem("Reveal");
        System.out.println("Reveal enchantment used. Highlighting region.");
    }

    public void useCloakOfProtectionEnchantment() {
        BaseEnchantment cloak = new CloakOfProtection(0, 0, tileSize); // Adjust parameters as needed
        cloak.applyEffect(playModePanel.getGame());
        this.getInventory().removeItem("Cloak of Protection");
        System.out.println("CLOAK OF PROTECTION enchantment used.");
    }

    public void useLuringGemEnchantment(int direction) {
        BaseEnchantment gem = new LuringGem(0, 0, tileSize, direction);
        gem.applyEffect(playModePanel.getGame());
        this.getInventory().removeItem("Luring Gem");
        System.out.println("Luring Gem enchantment used.");
    }

    public void useSpeedUpManagement() {
        BaseEnchantment revealEnchantment =
                new SpeedUp(0, 0, tileSize);  //MUST BE CHANGED
        revealEnchantment.applyEffect(playModePanel.getGame());
        this.getInventory().removeItem("Speed Up");
        System.out.println("Speed Up enchantment used.");

    }
}