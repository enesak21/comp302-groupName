package domain.entity.monsters.Factories;

import domain.entity.monsters.BaseMonster;
import domain.entity.monsters.MonsterFactory;
import domain.entity.monsters.MonsterManager;
import domain.entity.monsters.WizardMonster;

public class WizardMonsterFactory implements MonsterFactory {
    private MonsterManager monsterManager;

    public WizardMonsterFactory(MonsterManager monsterManager) {
        this.monsterManager = monsterManager;
    }

    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new WizardMonster(gridX, gridY, tileSize, monsterManager);
    }
}
