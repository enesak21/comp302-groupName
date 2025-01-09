package domain.UI;

import domain.entity.playerObjects.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * PlayerView is a class that extends EntityView and is responsible for drawing the player on the screen.
 * It contains the player images and the draw method that draws the player on the screen.
 */
public class PlayerView extends EntityView {
    private BufferedImage upImage, downImage, leftImage, rightImage ,leftProtectedImage,rightProtectedImage; // Player images
    private BufferedImage prevImage;
    /**
     * Constructor for PlayerView.
     * @param player Player object
     */
    public PlayerView(Player player) {
        super(player);
        loadEntityImages(); // Load the player images
    }

    /**
     * Load the player images.
     */
    @Override
    public void loadEntityImages() {

        try {
            leftImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerLeft.png"));
            rightImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerRight.png"));
            leftProtectedImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerLeft_protected.png"));
            rightProtectedImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerRight_protected.png"));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load player images.", e);
        }
    }

    /**
     * Draw the player on the screen.
     * @param g2 Graphics2D object
     */
    @Override
    public void draw(Graphics2D g2) {
        Player player = (Player) entity; // Cast Entity to Player
        if (!player.getIsInvisibleToArchers()) {
            BufferedImage currentImage = switch (player.getDirection()) {
                case UP -> prevImage;
                case DOWN -> prevImage;
                case LEFT -> leftImage;
                case RIGHT -> rightImage;
            };

            prevImage = currentImage;

            g2.drawImage( // Draw the player image
                    currentImage,
                    player.getPixelX(),
                    player.getPixelY(),
                    player.getTileSize(),
                    player.getTileSize(),
                    null
            );
        }
        else{BufferedImage currentImage = switch (player.getDirection()) {
            case UP -> prevImage;
            case DOWN -> prevImage;
            case LEFT -> leftProtectedImage;
            case RIGHT -> rightProtectedImage;
        };

            prevImage = currentImage;

            g2.drawImage( // Draw the player image
                    currentImage,
                    player.getPixelX(),
                    player.getPixelY(),
                    player.getTileSize(),
                    player.getTileSize(),
                    null
            );
        }

        }



}