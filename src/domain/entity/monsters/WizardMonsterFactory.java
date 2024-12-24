package domain.entity.monsters;

import domain.game.Game;

public class WizardMonsterFactory implements MonsterFactory{
    private Game game;

    public WizardMonsterFactory(Game game) {
        this.game = game;
    }

    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new WizardMonster(gridX, gridY, tileSize, game);
    }
}
