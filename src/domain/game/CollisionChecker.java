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
     * @param tile
     * @return true if the entity cannot move into tile
     */
    public boolean checkCollision(Entity entity, Tile tile) {
        return tile.isEmpty();
    }
}
