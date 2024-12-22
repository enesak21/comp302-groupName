package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;

import java.util.Random;

import static domain.game.Game.isInRange;

public class FighterMonster extends BaseMonster {
    private final int DAGGER_RANGE = 1;
    private Random random;
    private final int ATTACK_FREQUENCY = 500;
    private long lastAttackTime;

    public FighterMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.random = new Random();
    }

    @Override
    public void update() {

    }


    public void move() {
        //simple random movement code
        int direction = (random.nextInt(4)); //0: UP, 1: DOWN, 2: LEFT, 3:RIGHT
        switch (direction){
            case 0 -> gridY--; //Move Up
            case 1 -> gridY++; //Move Down
            case 2 -> gridX--; //Move Left
            case 3 -> gridX++; //Move Right
        }
        updatePixelPosition(); //This method could be in MonsterView
    }


    @Override
    public void update(Player player) {
        move();
        attack(player);
    }

    @Override
    public void attack(Player player) {
        if (isInRange(this.getGridX(),this.getGridX(),player.getGridX(),player.getGridY(), DAGGER_RANGE)) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastAttackTime) > ATTACK_FREQUENCY) {
                player.reduceHealth();
                lastAttackTime = currentTime;
            }
        }
    }
}