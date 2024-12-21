package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.Game;


public class ArcherMonster extends BaseMonster{
    private float arrowRange = 4;
    private float shootFrequency;

    public ArcherMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public void throwArrow() {}

    public void move() {}

    @Override
    public void update() {
        attack(); //Archer Monster cannot move.
    }

    @Override
    public void attack() {


    }
}