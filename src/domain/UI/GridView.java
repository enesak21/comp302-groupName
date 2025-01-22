package domain.UI;
import domain.game.Grid;
import domain.game.Tile;
import domain.structures.Structure;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
    * This class is responsible for drawing the grid and its structures.
 */
public class GridView {
    private Grid grid; // The grid to be drawn
    final int SCALE = 2; // The scale of the structure

    /*
        * Constructor for the GridView class.
        * @param grid The grid to be drawn
     */
    public GridView(Grid grid) {
        this.grid = grid;
    }

    /*
        * Draws the grid and its structures.
        * @param g2 The graphics object to draw on
        * @param offsetX The x-coordinate offset
        * @param offsetY The y-coordinate offset
     */
    public void draw(Graphics2D g2, int offsetX, int offsetY) {
        for (int column = 0; column < grid.getColumns(); column++) {
            for (int row = 0; row < grid.getRows(); row++) {
                Tile tile = grid.getTileAt(column, row);

                if (tile != null && tile.getImage() != null) {
                    drawTile(g2, tile, column, row, offsetX, offsetY);

                    if (tile.getStructure() != null && tile.getStructure().getImage() != null) {
                        drawStructure(g2, tile.getStructure(), column, row, offsetX, offsetY);
                    }
                }
            }
        }
    }

    /*
        * Draws a tile on the grid.
        * @param g2 The graphics object to draw on
        * @param tile The tile to be drawn
        * @param column The column of the tile
        * @param row The row of the tile
        * @param offsetX The x-coordinate offset
        * @param offsetY The y-coordinate offset
     */
    private void drawTile(Graphics2D g2, Tile tile, int column, int row, int offsetX, int offsetY) {
        int tileSize = grid.getTileSize();
        BufferedImage image = tile.getImage();
        int x = offsetX + column * tileSize;
        int y = offsetY + row * tileSize;

        g2.drawImage(image, x, y, tileSize, tileSize, null);

        // if you wanna debug the grid, you can uncomment this.
        /*
        g2.setColor(Color.GRAY);
        g2.drawRect(x, y, tileSize, tileSize);
         */
    }

    /*
        * Draws a structure on the grid.
        * @param g2 The graphics object to draw on
        * @param structure The structure to be drawn
        * @param column The column of the structure
        * @param row The row of the structure
        * @param offsetX The x-coordinate offset
        * @param offsetY The y-coordinate offset
     */
    private void drawStructure(Graphics2D g2, Structure structure, int column, int row, int offsetX, int offsetY) {
        int tileSize = grid.getTileSize();
        BufferedImage image = structure.getImage();
        int x = offsetX + column * tileSize; // Calculate the x-coordinate
        int y = offsetY + row * tileSize; // Calculate the y-coordinate

        int width = structure.getWidth();
        int height = structure.getHeight();
        g2.drawImage(image, x, y - height * SCALE + tileSize, width * SCALE, height * SCALE, null); // Draw the structure
    }

    /*
        * Draws the structures on the grid.
        * @param g2 The graphics object to draw on
        * @param offsetX The x-coordinate offset
        * @param offsetY The y-coordinate offset
     */
    public void drawStructures(Graphics2D g2, int offsetX, int offsetY) {
        for (int column = 0; column < grid.getColumns(); column++) { // Iterate through the columns
            for (int row = 0; row < grid.getRows(); row++) { // Iterate through the rows
                Tile tile = grid.getTileAt(column, row); // Get the tile at the current column and row

                if (tile != null && tile.getStructure() != null && tile.getStructure().getImage() != null) {
                    drawStructure(g2, tile.getStructure(), column, row, offsetX, offsetY); // If the tile has a structure, draw it
                }
            }
        }
    }
    
    public int getColumns() {
        return grid.getColumns();
    }

    public int getRows() {
        return grid.getRows();
    }

    public int getTileSize() {
        return grid.getTileSize();
    }
}

