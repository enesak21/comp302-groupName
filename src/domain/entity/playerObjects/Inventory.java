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

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> content; // Holds the counts of enchantments

    public Inventory() {
        this.content = new HashMap<>();
    }

    /**
     * Adds an enchantment to the inventory.
     *
     * @param enchantmentType The type of the enchantment to be added
     */
    public void addItem(String enchantmentType) {
        content.put(enchantmentType, content.getOrDefault(enchantmentType, 0) + 1);
        System.out.println("Added to inventory: " + enchantmentType);
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
                content.remove(enchantmentType);
            }
            System.out.println("Removed from inventory: " + enchantmentType);
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

    // Optional: Display the inventory contents
    public void displayInventory() {
        System.out.println("Inventory Contents:");
        content.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
