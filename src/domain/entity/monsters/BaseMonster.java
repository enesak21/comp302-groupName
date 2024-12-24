package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;

public abstract class BaseMonster extends Entity {

    private CollisionChecker collisionChecker;
    public BaseMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public abstract void update(Game game);  //mandatory for all Monster classes
    public abstract void attack(Player player);  //mandatory for specific attack
    public abstract void setCollisionChecker(CollisionChecker collisionChecker);
}
