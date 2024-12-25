package domain.entity.monsters;

public class ArcherMonsterFactory implements MonsterFactory{
    @Override
    public BaseMonster createMonster(int gridX, int gridY, int tileSize) {
        return new ArcherMonster(gridX, gridY, tileSize);
    }
}
