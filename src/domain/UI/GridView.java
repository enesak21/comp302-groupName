package domain.UI;
import domain.game.Grid;
import domain.game.Tile;
import domain.structures.Structure;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GridView {
    private Grid grid;

    public GridView(Grid grid) {
        this.grid = grid;
    }

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

    private void drawStructure(Graphics2D g2, Structure structure, int column, int row, int offsetX, int offsetY) {
        int tileSize = grid.getTileSize();
        BufferedImage image = structure.getImage();
        int x = offsetX + column * tileSize;
        int y = offsetY + row * tileSize;

        g2.drawImage(image, x, y, tileSize, tileSize, null);
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

