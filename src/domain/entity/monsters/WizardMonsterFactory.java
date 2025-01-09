package domain.entity.monsters;

import domain.game.Game;

public class WizardMonsterFactory implements MonsterFactory{
    private MonsterManager monsterManager;

    public WizardMonsterFactory(MonsterManager monsterManager) {
        this.monsterManager = monsterManager;
    }

    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new WizardMonster(gridX, gridY, tileSize, monsterManager);
    }
}
