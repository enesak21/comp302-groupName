package domain.game;

import main.PlayModePanel;

import javax.imageio.ImageIO;

import domain.structures.Structure;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Grid {
    private Tile[][] tiles;
    private int columns = 16;
    private int rows = 16;
    private int tileSize;


    public Grid(int tileSize) {
        this.tileSize = tileSize;
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
        if (nextX < 0 || nextX >= columns || nextY < 0 || nextY >= rows) {
            return null;
        }
        return tiles[nextX][nextY];
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    /**
     * Returns an ArrayList of all structures on the grid.
     * @return ArrayList<Structure> of all structures on the grid.
     */
    public ArrayList<Structure> getStructures() {
        ArrayList<Structure> structures = new ArrayList<>();
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Tile tile = tiles[column][row];
                if (tile != null && tile.getStructure() != null) {
                    structures.add(tile.getStructure());
                }
            }
        }
        return structures;
    }
}