package domain.config.InformationExpertPattern;

import java.io.Serializable;
import java.util.HashMap;

public class PlayerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private int health;
    private InventoryInfo inventoryInfo;

    public PlayerInfo(int x, int y, int health, HashMap<String, Integer> inventoryInfo) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.inventoryInfo = new InventoryInfo(inventoryInfo);
    }

    public int getGridX() {
        return x;
    }

    public int getGridY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public InventoryInfo getInventoryInfo() {
        return inventoryInfo;
    }

    public void setInventoryInfo(InventoryInfo inventoryInfo) {
        this.inventoryInfo = inventoryInfo;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
