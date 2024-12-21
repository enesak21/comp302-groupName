package domain.game;

import java.awt.image.BufferedImage;

public class Tile {
    private int x;
    private int y;
    private boolean solid; // The tile is solid (e.g walls)
    private BufferedImage image;

    //not sure about parameters
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Tile(){

    }
    //check whether location is empty
    public boolean isEmpty() {
        return false;
        //test
    }

    public boolean isSolid() {
        return solid;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
