package domain.game1.entity.monsters;

import domain.game1.entity.Entity;
import domain.game1.game.CollisionChecker;
import domain.game1.game.Game;

public abstract class BaseMonster extends Entity {

    private CollisionChecker collisionChecker;
    public BaseMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public abstract void update(Game game);  //mandatory for all Monster classes
    public abstract void attack(Game game);  //mandatory for specific attack
    public abstract void setCollisionChecker(CollisionChecker collisionChecker);
}
