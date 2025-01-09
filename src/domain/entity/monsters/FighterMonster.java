package domain.entity.monsters;

import domain.enchantments.BaseEnchantment;
import domain.entity.Direction;
import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;
import java.util.Random;

import static domain.game.Game.isInRange;

public class FighterMonster extends BaseMonster {
    private final int DAGGER_RANGE = 1;
    private Random random;
    private int pixelCounter = 0;
    private final int SPEED = 1;
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

    // directionNum: //0: UP, 1: DOWN, 2: LEFT, 3:RIGHT
    public void move(Game game, int directionNum) {
        //simple random movement code
        int random_direction = random.nextInt(4);

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


    @Override
    public void update(Game game) {
        moveCounter++;
        for (BaseEnchantment enchantment : game.getActiveEnchantments()) {
            if (enchantment.getName().equals("Luring Gem")) {
                game.setLuringGemActive(true);
                break;
            }
        }
        // Speed of the monster is controlled by this if statement. The higher the number, the slower the monster.
        if (moveCounter >= SPEED * 2) {
            if (game.isLuringGemActive()) {
                // Use the direction from the luring gem logic (1..4)
                move(game, 3); // DIRECTION MUST BE TAKEN FROM KEY LISTENER. THIS IS ONLY FOR TEST
            } else {
                // Simple random movement
                int random_direction = random.nextInt(4); // 0: UP, 1: DOWN, 2: LEFT, 3: RIGHT
                move(game, random_direction);
            }
            moveCounter = 0;
        }
        attack(game.getPlayer());
    }

    @Override
    public void attack(Player player) {
        if (isInRange(this.getGridX(), this.getGridY(),player.getGridX() ,player.getGridY(), DAGGER_RANGE)) {
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