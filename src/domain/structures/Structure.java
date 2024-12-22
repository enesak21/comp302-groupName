package domain.structures;

import domain.game.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Structure {
    String type;
    Tile position;
    BufferedImage image;
    private boolean hasRune; // Whether this structure contains the rune
    private int width;
    private int height;

    public Structure(String type, Tile position) {
        this.type = type;
        this.position = position;
        this.hasRune = false;
    }

    public BufferedImage loadImage(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/structures/" + this.type + ".png"));
            if (image != null) {
                width = image.getWidth();
                height = image.getHeight();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean hasRune() {
        return hasRune;
    }
}