package domain.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Structure {
    String type;
    Tile position;
    private BufferedImage image;

    public Structure(String type, Tile position) {
        this.type = type;
        this.position = position;
    }

    public void getStructureImage(){

        try {
            BufferedImage structureImage = ImageIO.read(getClass().getResourceAsStream("/resources/structure/skull.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2, int offsetX, int offsetY) {

    }
}