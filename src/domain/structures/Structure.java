package domain.structures;

import domain.game.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Structure class is responsible for creating the structures in the game.
 */
public class Structure {
    String type; // The type of the structure
    Tile position; // The position of the structure
    BufferedImage image; // The image of the structure
    private boolean hasRune; // Whether this structure contains the rune
    private int width; // The width of the structure
    private int height; // The height of the structure


    /**
     * Constructor for Structure.
     * @param type The type of the structure
     * @param position The position of the structure
     */
    public Structure(String type, Tile position) {
        this.type = type;
        this.position = position;
        this.hasRune = false;
    }

    /**
     * Load the image of the structure.
     * @return The image of the structure
     */
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

    public void setHasRune(boolean hasRune) {
        this.hasRune = hasRune;
    }

    //TO HIGHLIGHT THE RUNE
    public Tile getTile() { return position; }
}