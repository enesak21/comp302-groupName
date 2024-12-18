package domain.game;

import main.PlayModePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Grid{
    private Tile[] tiles;
    private int width;
    private int height;
    private int scale;
    private int tile_number;
    PlayModePanel playModePanel;

    public Grid(int width, int height, int scale, PlayModePanel playModePanel) { // this case width = column * tilesize
                                         // column = 16, tilesize = 16*3
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.playModePanel = playModePanel;

        this.tile_number = (width / (16 * scale)) * (height / (16 * scale));
        this.tiles = new Tile[tile_number];

        tileGenerator();
    }




    public void tileGenerator(){

        try {

            int x = 0;
            int y = 0;
            int column = 0;
            int row = 0;
            int tileSize = 16 * scale;


            for (int i = 0; i < tile_number; i++){

                if (column < (width / tileSize) && row < (height / tileSize)){

                    tiles[i] = new Tile(x, y);
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/background.png"));
                    column++;
                    x += 16 * scale;

                    if(column == (width / tileSize)){
                        column = 0;
                        x = 0;
                        row++;
                        y += 16 * scale;
                    }

                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        for (int i = 0; i < tile_number; i++){
            g2.drawImage(tiles[i].image, tiles[i].getX(), tiles[i].getY(), playModePanel.tileSize, playModePanel.tileSize, null);
        }
    }


}