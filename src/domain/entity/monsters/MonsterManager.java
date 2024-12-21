package domain.entity.monsters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterManager {

    private List<BaseMonster> monsters;
    private Random random;
    private int tileSize;

    public MonsterManager(int tileSize) {
        monsters = new ArrayList<>();
        random = new Random();
        this.tileSize = tileSize;
    }



    public void spawnMonster(int gridWidth, int gridHeight) {
        int type = random.nextInt(3); // 0: Archer, 1: Fighter, 2: Wizard
        int gridX = random.nextInt(gridWidth);
        int gridY = random.nextInt(gridHeight);

        BaseMonster monster;
        switch (type) {
            case 0 -> monster = new ArcherMonster(gridX, gridY, tileSize);
            case 1 -> monster = new FighterMonster(gridX, gridY, tileSize);
            default -> monster = new WizardMonster(gridX, gridY, tileSize);
        }
        monsters.add(monster);
    }

    public void updateMonsters(){
        for (BaseMonster monster : monsters) {
            monster.update();
        }
    }

    public List<BaseMonster> getMonsters() {
        return monsters;
    }
}