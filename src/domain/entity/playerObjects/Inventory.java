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
    private HashMap<String, ArrayList<BaseEnchantment>> content; // Holds the counts of enchantments

    public Inventory() {
        this.content = new HashMap<>();
        ArrayList<BaseEnchantment> revealList = new ArrayList<>();
        ArrayList<BaseEnchantment> speedUpsList = new ArrayList<>();
        ArrayList<BaseEnchantment> cloaksList = new ArrayList<>();
        content.put("Reveal", revealList);
        content.put("Speed Up", speedUpsList);
        content.put("Cloak of Protection", cloaksList);
    }

    /**
     * Adds an enchantment to the inventory.
     *
     * @param enchantment The enchantment to be added
     */
    public void addItem(BaseEnchantment enchantment) {
        // Add the enchantment to the corresponding list
        content.get(enchantment.getName()).add(enchantment);
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
        // Check if the inventory contains the item and its value is greater than 0
        return content.containsKey(enchantmentType) && content.get(enchantmentType) > 0;
    }



    public HashMap<String, Integer> getContent() {
        return content;
    }
}
