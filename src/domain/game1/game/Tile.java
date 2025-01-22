package domain.game1.game;

import java.awt.image.BufferedImage;
import domain.game1.structures.Structure;

/**
 * The Tile class represents a tile in a grid-based game.
 * Each tile has coordinates, an image, and can be solid or contain a structure.
 */
public class Tile {
    private int gridX;
    private int gridY;
    private boolean solid; // The tile is solid (e.g., walls)
    private BufferedImage image;
    private Structure structure;
    private boolean isHighlighted;

    // Constructor with parameters
    public Tile(int gridX, int gridY, boolean solid, BufferedImage image) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.solid = solid;
        this.image = image;
    }

    // Default constructor
    public Tile() {
    }

    // Check whether the location is empty
    public boolean isEmpty() {
        return !solid;
    }

    // Getters and setters
    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }
    
    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Structure getStructure() { return structure; }

    private void setStructure(Structure structure) { 
        this.structure= structure;
        structure.loadImage();
    }

    public void addStructure(Structure structure) {
        setStructure(structure);
        if (structure != null) {
            setSolid(true);
        }
    }

    public boolean containsStructure() { return structure != null; }

    public boolean isHighlighted() {

        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
    }


}

