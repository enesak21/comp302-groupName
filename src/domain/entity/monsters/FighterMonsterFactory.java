package domain.entity.monsters;

public class FighterMonsterFactory implements MonsterFactory{
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new FighterMonster(gridX, gridY, tileSize);
    }
}
