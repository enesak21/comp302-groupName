package domain.entity.monsters;

public interface MonsterFactory {
    BaseMonster createMonster(int gridX, int gridY, int tileSize);
}
