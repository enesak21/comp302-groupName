package domain.entity;

import domain.UI.panels.PlayModePanel;

/**
 * The Entity class represents an abstract entity in a grid-based game.
 * It provides basic properties and methods for handling the entity's position,
 * movement, and direction.
 */
public abstract class Entity {
    protected int pixelX, pixelY; // for the drawing
    protected int gridX, gridY; // for the location on grid
    protected int speed; //for the pixel-based movement

    protected Direction direction = Direction.DOWN; //starting direction

    protected int tileSize; //size of the tile

    public Entity(int gridX, int gridY, int tileSize){
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = tileSize;

        this.pixelX = gridX * tileSize; //we calculated the pixel positions according to the grid
        this.pixelY = gridY * tileSize; //we calculated the pixel positions according to the grid
    }

    protected void updatePixelPosition() {
        this.pixelX = gridX * tileSize;
        this.pixelY = gridY * tileSize;
    }

    public abstract void update(); // we will implement this method in the subclasses

    public int getGridX() {
        return gridX-PlayModePanel.offsetX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX - PlayModePanel.offsetX;
    }

    public int getGridY() {
        return gridY-PlayModePanel.offsetY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY - PlayModePanel.offsetY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPixelX() {
        return pixelX;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
