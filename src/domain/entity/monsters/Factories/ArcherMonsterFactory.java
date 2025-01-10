package domain.entity.monsters.Factories;

import domain.entity.monsters.ArcherMonster;
import domain.entity.monsters.BaseMonster;
import domain.entity.monsters.MonsterFactory;

public class ArcherMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new ArcherMonster(gridX, gridY, tileSize);
    }
}
