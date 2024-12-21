package domain.game;

import main.PlayModePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Grid {
    private Tile[][] tiles;
    private int columns = 16;
    private int rows = 16;
    private int tileSize;
    PlayModePanel playModePanel;

    public Grid(int tileSize, PlayModePanel playModePanel) {
        this.tileSize = tileSize;
        this.playModePanel = playModePanel;
        this.tiles = new Tile[columns][rows];

        tileGenerator();
    }

    public void tileGenerator() {
        try {
            BufferedImage defaultImage = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/background.png"));
            for (int column = 0; column < columns; column++) {
                for (int row = 0; row < rows; row++) {
                    tiles[column][row] = new Tile(column, row, false, defaultImage); // Updated constructor call
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, int offsetX, int offsetY) {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Tile tile = tiles[column][row];
                if (tile != null && tile.getImage() != null) {
                    g2.drawImage(
                            tile.getImage(),
                            offsetX + column * tileSize,
                            offsetY + row * tileSize,
                            tileSize, tileSize,
                            null
                    );
                }
            }
        }
    }

    public Tile getTileAt(int nextX, int nextY) {
        return tiles[nextX][nextY];
    }
}