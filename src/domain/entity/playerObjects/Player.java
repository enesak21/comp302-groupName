package domain.entity.playerObjects;

import domain.entity.Direction;
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

    PlayModePanel playModePanel;
    PlayerController playerController;

    public Player(String name, int gridX, int gridY, int tileSize, PlayModePanel playModePanel, PlayerController playerController) {
        super(gridX, gridY, tileSize);
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerController = playerController;
        this.speed = 4;
        this.health = 3;
        this.direction = Direction.DOWN;
        getPlayerImage();
        updatePixelPosition();
    }

    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if (!moving) {
            if (playerController.upPressed) {
                direction = Direction.UP;
                moving = true;
            } else if (playerController.downPressed) {
                direction = Direction.DOWN;
                moving = true;
            } else if (playerController.leftPressed) {
                direction = Direction.LEFT;
                moving = true;
            } else if (playerController.rightPressed) {
                direction = Direction.RIGHT;
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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case UP, DOWN, LEFT, RIGHT -> image = down1;
        }

        g2.drawImage(image, pixelX, pixelY, playModePanel.getTileSize(), playModePanel.getTileSize(), null);
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public boolean useEnchantment(String enchantmentType) {
        return false;
    }
}