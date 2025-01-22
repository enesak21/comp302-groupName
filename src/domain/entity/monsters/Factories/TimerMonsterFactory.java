package domain.entity.monsters.Factories;

import domain.entity.monsters.BaseMonster;
import domain.entity.monsters.MonsterFactory;
import domain.entity.monsters.TimerMonster;

public class TimerMonsterFactory implements MonsterFactory {
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new TimerMonster(gridX, gridY, tileSize);
    }
}
