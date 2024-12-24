package domain.entity.monsters;

import domain.entity.Direction;
import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;

import java.util.Random;

import static domain.game.Game.isInRange;

public class FighterMonster extends BaseMonster {
    private final int DAGGER_RANGE = 1;
    private Random random;
    private int pixelCounter = 0;
    private final int SPEED = 4;
    private final int ATTACK_FREQUENCY = 500;
    private long lastAttackTime;
    private boolean moving = false;
    private CollisionChecker collisionChecker;

    private int moveCounter = 0;

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


        if(!moving){
            if(random_direction == 0){
                direction = Direction.UP;
            }else if(random_direction == 1){
                direction = Direction.DOWN;
            }else if(random_direction == 2){
                direction = Direction.LEFT;
            }else if(random_direction == 3){
                direction = Direction.RIGHT;
            }

            if (!collisionChecker.checkCollision(this)) {
                moving = true;
            }

        }


        /*switch (random_direction){
            case 0 -> direction = Direction.UP;
            case 1 -> direction = Direction.DOWN;
            case 2 -> direction = Direction.LEFT;
            case 3 -> direction = Direction.RIGHT;
        }*/

        if(moving){
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
                moving = false;
                updatePixelPosition();
            }
        }




    }


    @Override
    public void update(Game game) {
        moveCounter++;
        if(moveCounter >= 16){
            move();
            moveCounter = 0;
        }
        attack(game.getPlayer());
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


    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }
}