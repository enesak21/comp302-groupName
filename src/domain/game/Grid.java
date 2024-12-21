package domain.game;

import main.PlayModePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Grid{
    private Tile[][] tiles;
    private int columns= 16;
    private int rows= 16;
    private int tileSize;
    PlayModePanel playModePanel;


    public Grid(int tileSize, PlayModePanel playModePanel) {
        this.tileSize = tileSize;
        this.playModePanel = playModePanel;
        this.tiles = new Tile[columns][rows];
        tileGenerator();
        fillStructures();
    }

    public void tileGenerator() {
        try {
            for (int column = 0; column < columns; column++) {
                for (int row = 0; row < rows; row++) {
                    tiles[column][row] = new Tile(column, row); // Grid koordinatları atanır
                    tiles[column][row].setImage(ImageIO.read(
                            getClass().getResourceAsStream("/resources/tiles/background.png"))
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillStructures(Structure[][] structuresList) {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                if (structuresList[column][row] != null) {
                    if ( column == 9 && row == 9) {
                        Structure structure = new Structure("kafatasi", tiles[column][row]);
                        tiles[column][row].setStructure(true);
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2, int offsetX, int offsetY) {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Tile tile = tiles[column][row];
                g2.drawImage(
                        tile.getImage(),
                        offsetX + column * tileSize, //offset bizim gridin ekranda nerede oldugunu ayarlamak icin
                        offsetY + row * tileSize,
                        tileSize, tileSize,
                        null
                );
                if (tile.containsStructure() == true) {
                    Structure structure = new Structure("kafatasi", tiles[column][row]); // remove later
                    g2.drawImage(
                        structure.getStructureImage(),

                    )
                }

            }
        }
    }

}