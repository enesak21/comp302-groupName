package domain.Structures;

import domain.game.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Structure {
    String type;
    Tile position;
    BufferedImage image;

    public Structure(String type, Tile position) {
        this.type = type;
        this.position = position;
    }

    public BufferedImage getStructureImage(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/structures/" + this.type + ".png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics2D g2, int offsetX, int offsetY) {

    }
}