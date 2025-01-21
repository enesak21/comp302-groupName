package domain.config.InformationExpertPattern;

import domain.enchantments.CloakOfProtection;
import domain.enchantments.LuringGem;
import domain.enchantments.Reveal;
import domain.enchantments.SpeedUp;
import domain.entity.monsters.BaseMonster;
import domain.entity.playerObjects.Inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Integer> inventory;

    public InventoryInfo(HashMap<String, Integer> inventory){
        this.inventory = inventory;
    }

    public HashMap<String, Integer> getInventory(){
        return inventory;
    }

    public void setInventory(HashMap<String, Integer> inventory){
        this.inventory = inventory;
    }

    public Inventory InfoToInventory(){
        Inventory inv = new Inventory();
        for (String enchantmentType : inventory.keySet()) {
            switch (enchantmentType) {
                case "Reveal":
                    for (int i = 0; i < inventory.get(enchantmentType); i++) {
                        inv.addItem(new Reveal(0, 0, 0));
                    }
                    break;
                case "Speed Up":
                    for (int i = 0; i < inventory.get(enchantmentType); i++) {
                        inv.addItem(new SpeedUp(0, 0, 0));
                    }
                    break;
                case "Cloak of Protection":
                    for (int i = 0; i < inventory.get(enchantmentType); i++) {
                        inv.addItem(new CloakOfProtection(0, 0, 0));
                    }
                    break;
                case "Luring Gem":
                    for (int i = 0; i < inventory.get(enchantmentType); i++) {
                        inv.addItem(new LuringGem(0, 0, 0));
                    }
                    break;
            }
        }
        return inv;
    }

    @Override
    public String toString() {
        return inventory.toString();
    }
}
