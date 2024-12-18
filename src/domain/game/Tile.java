package domain.game;

import java.awt.image.BufferedImage;

public class Tile {
    private int x;
    private int y;
    public BufferedImage image;

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
}
