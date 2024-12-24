package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;


public class ArcherMonster extends BaseMonster{
    private float arrowRange = 4;
    private final long SHOOT_FREQUENCY = 1000;
    private long lastAttackTime;
    private int lastPlayerX;
    private int lastPlayerY;
    private CollisionChecker collisionChecker;

    public ArcherMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    @Override
    public void update() {

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
         attack(game.getPlayer());
    }

    @Override
    public void attack(Player player) {
        if (Game.isInRange(this.getGridX(), this.getGridY(), player.getGridX(), player.getGridY(), arrowRange)) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastAttackTime) > SHOOT_FREQUENCY) {
                throwArrow(player);
                lastAttackTime = currentTime;
            }
        }
    }

}