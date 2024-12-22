package domain.UI;

import domain.UI.EntityView;
import domain.entity.playerObjects.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerView extends EntityView {
    private BufferedImage upImage, downImage, leftImage, rightImage;

    public PlayerView(Player player) {
        super(player);
        loadEntityImages();;
    }

    @Override
    public void loadEntityImages() {

        try {
            downImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            upImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            leftImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            rightImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load player images.", e);
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        Player player = (Player) entity; // Cast Entity to Player
        BufferedImage currentImage = switch (player.getDirection()) {
            case UP -> upImage;
            case DOWN -> upImage;
            case LEFT -> upImage;
            case RIGHT -> upImage;
        };

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