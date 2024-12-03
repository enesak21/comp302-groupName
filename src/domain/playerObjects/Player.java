package domain.playerObjects;

import domain.game.Tile;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class Player {
    private String name;
    private int health;
    private Direction direction;
    private Inventory inventory;
    private Tile location;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Uses an enchantment
     * 
     * @param enchantmentType The type of the enchantment to be used
     * @return true if the enchantment was used succesfully
     */
    public boolean useEnchantment(String enchantmentType) {
        return false;
    }
}