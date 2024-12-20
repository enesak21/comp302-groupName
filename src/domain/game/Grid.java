package domain.game;

import main.PlayModePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Grid {

    private Tile[] tiles;
    private int width;
    private int height;
    private int columns;
    private int rows;
    private int scale;
    private int numberOfTiles;
    PlayModePanel playModePanel;

    public final int SCALE = 3; // Scale of Tile
    public final int TILE_SIZE = 16 * SCALE; // Size of each Tile

    public Grid(int width, int height, int scale, PlayModePanel playModePanel) { // this case width = column * tilesize
        // column = 16, tilesize = 16*3
        this.width = width;
        this.height = height;
        columns = width / TILE_SIZE;
        rows = height / TILE_SIZE;
        this.scale = scale;
        this.playModePanel = playModePanel;

        this.numberOfTiles = rows * columns;
        this.tiles = new Tile[numberOfTiles];

        tileGenerator();
    }

    public void tileGenerator() {

        try {

            int x = 0;
            int y = 0;
            int column = 0;
            int row = 0;
            int tileSize = 16 * scale;

            for (int i = 0; i < numberOfTiles; i++) {

                if (column < (width / tileSize) && row < (height / tileSize)) {

                    tiles[i] = new Tile(x, y);
                    tiles[i].setImage(ImageIO.read(getClass().getResourceAsStream("/resources/tiles/background.png")));
                    column++;
                    x += 16 * scale;

                    if (column == (width / tileSize)) {
                        column = 0;
                        x = 0;
                        row++;
                        y += 16 * scale;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the tile at location x,y. If the location is out of bounds, it returns null.
     * @param x x location
     * @param y y location
     * @return The tile at x,y
     */
    public Tile getTileAt(int x, int y) {
        int column = x / TILE_SIZE;
        int row = y / TILE_SIZE;

        if (column < 0 || row < 0 || column >= columns || row >= rows) {
            return null;
        }

        return tiles[row * columns + column];
    }

    public void draw(Graphics2D g2){

        for (int i = 0; i < numberOfTiles; i++){
            g2.drawImage(tiles[i].getImage(), tiles[i].getX(), tiles[i].getY(), playModePanel.getTileSize(), playModePanel.getTileSize(), null);
        }
    }

}