package domain.entity.playerObjects;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> content; // Holds the counts of enchantments

    public Inventory() {

    }

    /**
     * Adds an enchantment to the inventory.
     * 
     * @param enchantmentType The type of the enchantment to be added
     */
    public void addItem(String enchantmentType) {

    }

    /**
     * Removes an enchantment from the inventory
     * 
     * @param enchantmentType The type of the enchantment to be removed
     * @return true if the enchantment was removed
     */
    public boolean removeItem(String enchantmentType) {
        return false;
    }

    /**
     * Checks if an enchantment is in the inventory
     * @param enchantmentType The type of the enchantment to be checked
     * @return true if the enchantment is in the inventory
     */
    public boolean isInInventory(String enchantmentType) {
        return false;
    }
}