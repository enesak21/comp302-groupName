package domain.game1.entity.playerObjects;

import domain.game1.enchantments.BaseEnchantment;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private HashMap<String, ArrayList<BaseEnchantment>> content; // Holds the counts of enchantments

    public Inventory() {
        this.content = new HashMap<>();
        ArrayList<BaseEnchantment> revealList = new ArrayList<>();
        ArrayList<BaseEnchantment> speedUpsList = new ArrayList<>();
        ArrayList<BaseEnchantment> cloaksList = new ArrayList<>();
        ArrayList<BaseEnchantment> gemList = new ArrayList<>();
        content.put("Reveal", revealList);
        content.put("Speed Up", speedUpsList);
        content.put("Cloak of Protection", cloaksList);
        content.put("Luring Gem", gemList);
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
     * @param enchantment The enchantment to be removed
     * @return true if the enchantment was removed
     */
    public boolean removeItem(BaseEnchantment enchantment) {
        // Check if the enchantment type exists in the inventory
        String enchantmentType = enchantment.getName();
        if (content.containsKey(enchantmentType)) {
            ArrayList<BaseEnchantment> enchantmentList = content.get(enchantmentType);

            // Remove the enchantment from the list
            return enchantmentList.remove(enchantment); // Successfully removed the enchantment
        }
        return false; // Enchantment not found or type does not exist
    }


    /**
     * Checks if an enchantment is in the inventory
     * @param enchantmentType The type of the enchantment to be checked
     * @return true if the enchantment is in the inventory
     */
    public boolean isInInventory(String enchantmentType) {
        // Check if the inventory contains the enchantment type and its list is not empty
        return content.containsKey(enchantmentType) && !content.get(enchantmentType).isEmpty();
    }

    public HashMap<String, ArrayList<BaseEnchantment>> getContent() {
        return content;
    }
}
