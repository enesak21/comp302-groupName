package domain.UI;

import domain.entity.playerObjects.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerView extends EntityView {
    private BufferedImage upImage, downImage, leftImage, rightImage;
    private BufferedImage prevImage;

    public PlayerView(Player player) {
        super(player);
        loadEntityImages();
    }

    @Override
    public void loadEntityImages() {

        try {
            leftImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerLeft.png"));
            rightImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/playerRight.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load player images.", e);
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        Player player = (Player) entity; // Cast Entity to Player
        BufferedImage currentImage = switch (player.getDirection()) {
            case UP -> prevImage;
            case DOWN -> prevImage;
            case LEFT -> leftImage;
            case RIGHT -> rightImage;
        };

        prevImage = currentImage;

        g2.drawImage(
                currentImage,
                player.getPixelX(),
                player.getPixelY(),
                player.getTileSize(),
                player.getTileSize(),
                null
        );
    }
}