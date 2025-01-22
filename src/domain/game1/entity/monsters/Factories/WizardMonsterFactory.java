package domain.game1.entity.monsters.Factories;

import domain.game1.entity.monsters.BaseMonster;
import domain.game1.entity.monsters.MonsterFactory;
import domain.game1.entity.monsters.MonsterManager;
import domain.game1.entity.monsters.WizardMonster;

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
