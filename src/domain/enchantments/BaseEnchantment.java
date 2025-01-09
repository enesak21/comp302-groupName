package domain.enchantments;

import domain.game.CollisionChecker;
import domain.game.Game;
import java.awt.*;

public abstract class BaseEnchantment {
    private int gridX;
    private int gridY;
    private int tileSize;
    private Image icon;
    private long spawnTime;
    private CollisionChecker collisionChecker;
    private String name;
    private boolean isActive;

    public BaseEnchantment(int gridX, int gridY, int tileSize) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = tileSize;
        this.icon = icon;
        this.spawnTime = System.currentTimeMillis();
    }



    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public abstract void applyEffect(Game game);

    public void update(Game game) {
        // Logic to update enchantment state, e.g., check for collisions
        if (collisionChecker != null) {
            // Perform collision checking or additional updates if needed
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
