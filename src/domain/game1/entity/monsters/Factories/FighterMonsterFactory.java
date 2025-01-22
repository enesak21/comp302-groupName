package domain.game1.entity.monsters.Factories;

import domain.game1.entity.monsters.BaseMonster;
import domain.game1.entity.monsters.FighterMonster;
import domain.game1.entity.monsters.MonsterFactory;

public class FighterMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new FighterMonster(gridX, gridY, tileSize);
    }
}
