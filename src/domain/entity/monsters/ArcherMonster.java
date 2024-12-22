package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.Game;


public class ArcherMonster extends BaseMonster{
    private float arrowRange = 4;
    private final long SHOOT_FREQUENCY = 1000;
    private long lastAttackTime;

    public ArcherMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    @Override
    public void update() {

    }

    public void throwArrow() {}

    public void move() {}

    @Override
    public void update(Player player) {
         attack(player);
    }

    @Override
    public void attack(Player player) {
        if (Game.isInRange(this.getGridX(), this.getGridY(), player.getGridX(), player.getGridY(), arrowRange)) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastAttackTime) > SHOOT_FREQUENCY) {
                throwArrow();
                lastAttackTime = currentTime;
            }
        }
    }

}