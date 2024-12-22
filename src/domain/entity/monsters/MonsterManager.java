package domain.entity.monsters;

import domain.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterManager {

    private List<BaseMonster> monsters;
    private Random random;
    private int tileSize;
    private Game game;
    private long lastSpawnTime;
    private final int SPAWN_INTERVAL = 8000;

    public MonsterManager(Game game,int tileSize) {
        this.monsters = new ArrayList<>();
        this.random = new Random();
        this.tileSize = tileSize;
        this.game = game;
        this.lastSpawnTime = System.currentTimeMillis();
    }

    // bi tane fonksiyon yazılacak
    //bu fonksyion rastegele bir grid bulup buralara spawnMonster fonksiyonu ile monster oluşturacak.

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
        long currentTime = System.currentTimeMillis(); // Get the current time. An error might exist here.
        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) { // If 8 seconds have passed since the last spawn
            System.out.println("update monsters calisiyo");
            spawnMonster(game.getGrid().getColumns(), game.getGrid().getRows());
            lastSpawnTime = currentTime;
        }
        for (BaseMonster monster : monsters) {
            monster.update(game.getPlayer());
        }
    }

    public List<BaseMonster> getMonsters() {
        return monsters;
    }
}