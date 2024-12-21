package domain.game;

import java.awt.image.BufferedImage;

public class Tile {
    private int gridX;
    private int gridY;
    private BufferedImage image;
    private boolean hasStructure;
    
    //not sure about parameters
    public Tile(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.hasStructure = false;
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

    public boolean containsStructure() { return hasStructure; }

    public void setStructure(boolean hasStructure) { this.hasStructure = hasStructure; }
}

