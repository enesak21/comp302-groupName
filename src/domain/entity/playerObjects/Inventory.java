//package domain.entity.playerObjects;
//
//import java.util.HashMap;
//
//public class Inventory {
//    private HashMap<String, Integer> content; // Holds the counts of enchantments
//
//    public Inventory() {
//
//    }
//
//    /**
//     * Adds an enchantment to the inventory.
//     *
//     * @param enchantmentType The type of the enchantment to be added
//     */
//    public void addItem(String enchantmentType) {
//
//    }
//
//    /**
//     * Removes an enchantment from the inventory
//     *
//     * @param enchantmentType The type of the enchantment to be removed
//     * @return true if the enchantment was removed
//     */
//    public boolean removeItem(String enchantmentType) {
//        return false;
//    }
//
//    /**
//     * Checks if an enchantment is in the inventory
//     * @param enchantmentType The type of the enchantment to be checked
//     * @return true if the enchantment is in the inventory
//     */
//    public boolean isInInventory(String enchantmentType) {
//        return false;
//    }
//}




package domain.entity.playerObjects;

import domain.enchantments.BaseEnchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {
    private HashMap<String, Integer> content; // Holds the counts of enchantments

    public Inventory() {
        this.content = new HashMap<>();
        content.put("Reveal", 0);
        content.put("Speed Up", 0);
        content.put("Cloak of Protection", 0);
    }

    /**
     * Adds an enchantment to the inventory.
     *
     * @param enchantmentType The type of the enchantment to be added
     */
    public void addItem(String enchantmentType) {
        content.put(enchantmentType, content.getOrDefault(enchantmentType, 0) + 1);
    }

    /**
     * Removes an enchantment from the inventory
     *
     * @param enchantmentType The type of the enchantment to be removed
     * @return true if the enchantment was removed
     */
    public boolean removeItem(String enchantmentType) {
        if (content.containsKey(enchantmentType)) {
            int count = content.get(enchantmentType);
            if (count > 1) {
                content.put(enchantmentType, count - 1);
            } else {
                content.put(enchantmentType, 0);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if an enchantment is in the inventory
     * @param enchantmentType The type of the enchantment to be checked
     * @return true if the enchantment is in the inventory
     */
    public boolean isInInventory(String enchantmentType) {
        return content.containsKey(enchantmentType);
    }


    public HashMap<String, Integer> getContent() {
        return content;
    }
}
