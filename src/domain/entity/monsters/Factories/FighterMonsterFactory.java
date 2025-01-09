package domain.entity.monsters.Factories;

import domain.entity.monsters.BaseMonster;
import domain.entity.monsters.FighterMonster;
import domain.entity.monsters.MonsterFactory;

public class FighterMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new FighterMonster(gridX, gridY, tileSize);
    }
}
