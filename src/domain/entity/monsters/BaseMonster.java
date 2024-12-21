package domain.entity.monsters;

import domain.entity.Entity;

public abstract class BaseMonster extends Entity {

    public BaseMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public abstract void update();  //mandatory for all Monster classes
    public abstract void attack();  //mandatory for specific attack

}
