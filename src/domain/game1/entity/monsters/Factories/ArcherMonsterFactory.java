package domain.game1.entity.monsters.Factories;

import domain.game1.entity.monsters.ArcherMonster;
import domain.game1.entity.monsters.BaseMonster;
import domain.game1.entity.monsters.MonsterFactory;

public class ArcherMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new ArcherMonster(gridX, gridY, tileSize);
    }
}
