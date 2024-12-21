package domain.game;

import domain.entity.Entity;

public class CollisionChecker {

    private Grid grid;

    public CollisionChecker(Grid grid) {
        this.grid = grid;
    }

    /**
     * Checks if the given entity collides with the given tile when moving
     * @param entity
     * @return true if the entity cannot move into tile
     */
    public boolean checkCollision(Entity entity) {
        int gridX = entity.getGridX();// Get the gridX position of the entity
        int gridY = entity.getGridY();// Get the gridY position of the entity

        int nextX = gridX;
        int nextY = gridY;
        switch (entity.getDirection()) { // Find the next position of the entity according to its direction
            case UP -> nextY--;
            case DOWN -> nextY++;
            case LEFT -> nextX--;
            case RIGHT -> nextX++;
            default -> {
                return false;
            }
        }
        Tile nexTile = grid.getTileAt(nextX, nextY); // Get the at the next position
        if (nexTile == null) { //The tile is out of bounds
            return true;
        }
        return nexTile.isSolid(); // Return true if the tile is solid
    }
}
