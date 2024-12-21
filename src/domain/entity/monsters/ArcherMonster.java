package domain.entity.monsters;

import domain.entity.Entity;

public class ArcherMonster extends BaseMonster{
    private float arrowRange;
    private float shootFrequency;

    public ArcherMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public void throwArrow() {}

    public void move() {}

    @Override
    public void update() {

    }

    @Override
    public void attack() {

    }
}