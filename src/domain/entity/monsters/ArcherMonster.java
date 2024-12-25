package domain.entity.monsters;

import domain.entity.Direction;
import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.audio.AudioManager;
import java.util.Random;


public class ArcherMonster extends BaseMonster{
    private float arrowRange = 4;
    private final long SHOOT_FREQUENCY = 1000;
    private final long INITIAL_DELAY = 2000;
    private long lastAttackTime;
    private int pixelCounter = 0;
    private final int SPEED = 1;
    private Random random = new Random();
    private boolean moving = false;
    private CollisionChecker collisionChecker;
    private int moveCounter = 0;
    private AudioManager audioManager = new AudioManager();


    public ArcherMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.lastAttackTime = System.currentTimeMillis() + INITIAL_DELAY; // Initial delay for the first attack
        this.direction = getPatrolDirection(); //It initializes the direction of the monster rando
    }

    @Override
    public void update() {

    }


    public Direction getPatrolDirection(){
        int random_direction = random.nextInt(4);//0: UP, 1: DOWN, 2: LEFT, 3:RIGHT

        if(random_direction == 0){
             return Direction.UP;
        }else if(random_direction == 1){
            return Direction.DOWN;
        }else if(random_direction == 2){
            return Direction.LEFT;
        }
        else if(random_direction == 3){
            return Direction.RIGHT;
        }
        return Direction.UP;
    }

    public void move(Game game) {

        if(!moving){
            if (!collisionChecker.checkCollision(this)) {
                moving = true;
            }else {
                direction = getPatrolDirection(); //If there is a collision, change the direction randomly.
            }
        }

        if(moving){
            switch (direction){
                case UP -> pixelY -= SPEED;
                case DOWN -> pixelY += SPEED;
                case LEFT -> pixelX -= SPEED;
                case RIGHT -> pixelX += SPEED;
            }
            pixelCounter += SPEED;

            if (pixelCounter >= tileSize) {

                //Update old grid isSolid(false)
                game.getGrid().getTileAt(gridX - 2, gridY - 2).setSolid(false);

                switch (direction) {
                    case UP -> gridY--;
                    case DOWN -> gridY++;
                    case LEFT -> gridX--;
                    case RIGHT -> gridX++;
                }

                //Update new grid isSolid(true)
                game.getGrid().getTileAt(gridX - 2, gridY - 2).setSolid(true);

                moving = false;
                updatePixelPosition();

                pixelCounter = 0;
            }
        }
    }

    /* This method includes a dodge mechanic for throwArrow method DO NOT DELETE YET.
    public void throwArrow(Player player) {
        lastPlayerX = player.getGridX();
        lastPlayerY = player.getGridY();
        new Thread(() -> {
            try {
                Thread.sleep(SHOOT_FREQUENCY);
                if (player.getGridX() == lastPlayerX && player.getGridY() == lastPlayerY) {
                    player.reduceHealth();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
     */

    public void throwArrow(Player player){
        player.reduceHealth();
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    @Override
    public void update(Game game)
    {
        moveCounter++;
        if(moveCounter >= SPEED * 2){
            move(game);
            moveCounter = 0;
        }
        attack(game.getPlayer());
    }

    @Override
    public void attack(Player player) {
        if (Game.isInRange(this.getGridX(), this.getGridY(), player.getGridX(), player.getGridY(), arrowRange)) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastAttackTime) > SHOOT_FREQUENCY) {
                throwArrow(player);
                lastAttackTime = currentTime;
                audioManager.playArcherSound();
            }
        }
    }

}