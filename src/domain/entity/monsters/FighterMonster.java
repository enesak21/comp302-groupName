package domain.entity.monsters;

import domain.entity.Direction;
import domain.entity.Entity;
import domain.entity.playerObjects.Player;

import java.util.Random;

import static domain.game.Game.isInRange;

public class FighterMonster extends BaseMonster {
    private final int DAGGER_RANGE = 1;
    private Random random;
    private int pixelCounter = 0;
    private final int SPEED = 4;
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

        int random_direction = random.nextInt(4);//0: UP, 1: DOWN, 2: LEFT, 3:RIGHT

        switch (random_direction){
            case 0 -> direction = Direction.UP;
            case 1 -> direction = Direction.DOWN;
            case 2 -> direction = Direction.LEFT;
            case 3 -> direction = Direction.RIGHT;
        }

        switch (direction){
            case UP -> pixelY -= speed;
            case DOWN -> pixelY += speed;
            case LEFT -> pixelX -= speed;
            case RIGHT -> pixelX += speed;
        }
        pixelCounter += SPEED;

        if (pixelCounter >= tileSize) {
            switch (direction) {
                case UP -> gridY--;
                case DOWN -> gridY++;
                case LEFT -> gridX--;
                case RIGHT -> gridX++;
            }
            pixelCounter = 0;
            updatePixelPosition();
        }

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