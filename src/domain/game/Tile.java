package domain.game;

import java.awt.image.BufferedImage;

public class Tile {
    private int gridX;
    private int gridY;
    private boolean solid; // The tile is solid (e.g walls)

    private BufferedImage image;

    //not sure about parameters
    public Tile(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }
    public Tile(){

    }
    //check whether location is empty
    public boolean isEmpty() {
        return false;
        //test
    }

    public int getGridY() {
        return gridY;

    public boolean isSolid() {
        return solid;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
