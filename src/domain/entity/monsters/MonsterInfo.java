package domain.entity.monsters;

import java.io.Serializable;

public class MonsterInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int x;
    private final int y;

    public MonsterInfo(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getMonsterType() {
        return name;
    }

    public int getGridX() {
        return x;
    }

    public int getGridY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + name + ", x: " + x + ", y: " + y + ")";
    }
}
