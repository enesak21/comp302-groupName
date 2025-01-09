package domain.UI;

import domain.entity.monsters.ArcherMonster;
import domain.entity.playerObjects.Player;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ArrowAnimationView {

    private ArcherMonster archerMonster; // The archer monster who is shooting the arrow
    private int startX, startY, endX, endY;
    private int currentX, currentY;
    private double angle;
    private float arrowRange;
    private boolean finished;
    private int speed_Arrow = 20;
    private BufferedImage arrowImage;

    public ArrowAnimationView(ArcherMonster archerMonster, Player player) {
        this.archerMonster = archerMonster;
        this.arrowRange = archerMonster.getTileSize() * archerMonster.getArrowRange(); //pixel_based

        this.startX = (archerMonster.getGridX() + 2) * archerMonster.getTileSize();   //pixel_based
        this.startY = (archerMonster.getGridY() + 2) * archerMonster.getTileSize();   //pixel_based

        this.endX = (player.getGridX() + 2) * player.getTileSize();    //pixel_based
        this.endY = (player.getGridY() + 2) * player.getTileSize();    //pixel_based

        this.currentX = startX;
        this.currentY = startY;

        this.angle = Math.atan2(endY - startY, endX - startX);
        this.finished = false;
        uploadImage();
        System.out.println("ArrowAnimationView created");
    }


    public void update() {
        if (!finished) {
            currentX += speed_Arrow * Math.cos(angle);
            currentY += speed_Arrow * Math.sin(angle);

            if (Math.hypot(currentX - startX, currentY - startY) >= Math.hypot(endX - startX, endY - startY)) {
                currentX = endX;
                currentY = endY;
                finished = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (!finished) {
            System.out.println("Arrow drawing method is called");
            AffineTransform old = g2.getTransform();
            g2.translate(currentX, currentY);
            g2.rotate(angle);
            g2.drawImage(arrowImage, -arrowImage.getWidth() / 2, -arrowImage.getHeight() / 2, null);
            g2.setTransform(old);
        }
    }


    private void uploadImage() {
        try {
            arrowImage = ImageIO.read(getClass().getResourceAsStream("/resources/monsters/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load arrow image.", e);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
