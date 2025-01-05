package domain.enchantments;

import domain.game.Game;

/**
 * Parent class for enchantments
 */

import java.awt.Image;

public abstract class Enchantment {
    private long spawnTime; // When the enchantment was spawned
    private int gridX;      // X position on the grid
    private int gridY;      // Y position on the grid
    private Image icon;     // Enchantment icon

    public Enchantment(int gridX, int gridY, Image icon) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.icon = icon;
        this.spawnTime = System.currentTimeMillis(); // Record spawn time
    }

    public Enchantment(String type, float visibilityDuration) {
    }

    public Enchantment() {

    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Image getIcon() {
        return icon;
    }

    public abstract void applyEffect(Game game); // Apply the effect to the game

    public String getName() {
        return "ENCHANTMENT NAMEEE";
    }
}

