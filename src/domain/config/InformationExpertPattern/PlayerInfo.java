package domain.config.InformationExpertPattern;

import java.io.Serializable;

public class PlayerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;
    private int health;

    public PlayerInfo(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
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

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
