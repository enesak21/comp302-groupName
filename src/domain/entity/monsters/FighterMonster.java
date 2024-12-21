package domain.entity.monsters;

import domain.entity.Entity;

public class FighterMonster extends Entity {
    private int daggerRange;

    public FighterMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public void attack() {}
    public void move() {}

    @Override
    public void update() {

    }
}