package domain.UI;

import domain.entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EntityView {
    protected Entity entity; // Model
    private BufferedImage upImage, downImage, leftImage, rightImage;

    public EntityView(Entity entity) {
        this.entity = entity;
        loadEntityImages();
    }

    private void loadEntityImages() {
        try {
            downImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            upImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            leftImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
            rightImage = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (entity.getDirection()) {
            case UP -> upImage;
            case DOWN -> downImage;
            case LEFT -> leftImage;
            case RIGHT -> rightImage;
        };

        g2.drawImage(image, entity.getPixelX(), entity.getPixelY(), entity.getTileSize(), entity.getTileSize(), null);
    }
}
