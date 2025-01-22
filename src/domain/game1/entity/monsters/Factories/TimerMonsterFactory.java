package domain.game1.entity.monsters.Factories;

import domain.game1.entity.monsters.BaseMonster;
import domain.game1.entity.monsters.MonsterFactory;
import domain.game1.entity.monsters.TimerMonster;

public class TimerMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new TimerMonster(gridX, gridY, tileSize);
    }
}
