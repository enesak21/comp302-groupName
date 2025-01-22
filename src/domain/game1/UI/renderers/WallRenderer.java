package domain.game1.UI.renderers;

import domain.game1.game.Hall;

import java.awt.*;
import javax.imageio.ImageIO;

public class WallRenderer {

    private final int tileSize;
    private Hall hall;
    private Image leftWall, rightWall, topWall, bottomWall;
    private Image topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
    private Image openedDoor, closedDoor;
    private Image hallOfAirFlag, hallOfEarthFlag, hallOfFireFlag, hallOfWaterFlag;


    // Constructor
    public WallRenderer(Hall hall, int tileSize) {
        this.tileSize = tileSize;
        this.hall = hall;
        loadWallImages();
    }

    // Load wall images
    private void loadWallImages() {
        try {
            leftWall = ImageIO.read(getClass().getResource("/resources/Walls/leftWall.png"));
            rightWall = ImageIO.read(getClass().getResource("/resources/Walls/rightWall2.png"));
            topWall = ImageIO.read(getClass().getResource("/resources/Walls/frontWall.png"));
            bottomWall = ImageIO.read(getClass().getResource("/resources/Walls/frontWall.png"));
            topLeftCorner = ImageIO.read(getClass().getResource("/resources/Walls/topLeftCorner.png"));
            topRightCorner = ImageIO.read(getClass().getResource("/resources/Walls/topRightCorner.png"));
            bottomLeftCorner = ImageIO.read(getClass().getResource("/resources/Walls/BottomLeftCorner.png"));
            bottomRightCorner = ImageIO.read(getClass().getResource("/resources/Walls/BottomRightCorner.png"));
            hallOfAirFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfAirFlag.png"));
            hallOfEarthFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfEarthFlag.png"));
            hallOfFireFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfFireFlag.png"));
            hallOfWaterFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfWaterFlag.png"));


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading wall images. Check file paths.");
        }
    }

    // Draw walls
    public void drawWalls(Graphics2D g2, int offsetX, int offsetY, int gridColumns, int gridRows) {
        int leftX = offsetX * tileSize - leftWall.getWidth(null);
        int rightX = (offsetX + gridColumns) * tileSize;
        int topY = offsetY * tileSize - topWall.getHeight(null);
        int bottomY = (offsetY + gridRows) * tileSize;

        // Draw top and bottom walls
        for (int col = 0; col < gridColumns; col++) {
            int x = (offsetX + col) * tileSize;
            g2.drawImage(topWall, x, topY, tileSize, topWall.getHeight(null), null);
            g2.drawImage(bottomWall, x, bottomY, tileSize, bottomWall.getHeight(null), null);

            if (col == 10) {
                Image flag = switch (hall.getName()) {
                    case "Hall of Fire" -> hallOfFireFlag;
                    case "Hall of Earth" -> hallOfEarthFlag;
                    case "Hall of Water" -> hallOfWaterFlag;
                    case "Hall of Air" -> hallOfAirFlag;
                    default -> null;
                };
                g2.drawImage(flag, x, bottomY, tileSize, bottomWall.getHeight(null), null);
            }
        }

        // Draw left and right walls
        for (int row = 0; row < gridRows; row++) {
            int y = (offsetY + row) * tileSize;
            g2.drawImage(leftWall, leftX, y, leftWall.getWidth(null), tileSize, null);
            g2.drawImage(rightWall, rightX, y, rightWall.getWidth(null), tileSize, null);
        }
    }

    // Draw corners
    public void drawCorners(Graphics2D g2, int offsetX, int offsetY, int gridColumns, int gridRows) {
        int leftX = offsetX * tileSize - leftWall.getWidth(null);
        int rightX = (offsetX + gridColumns) * tileSize;
        int topY = offsetY * tileSize - topWall.getHeight(null);
        int bottomY = (offsetY + gridRows) * tileSize;

        g2.drawImage(topLeftCorner, leftX, topY, null);
        g2.drawImage(topRightCorner, rightX - 11, topY, null);
        g2.drawImage(bottomLeftCorner, leftX, bottomY, null);
        g2.drawImage(bottomRightCorner, rightX - 11, bottomY, null);
    }
}