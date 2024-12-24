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
    private final int SPAWN_INTERVAL = 2000;

    private List<MonsterFactory> factories;

    public MonsterManager(Game game,int tileSize) {
        this.monsters = new ArrayList<>();
        this.random = new Random();
        this.tileSize = tileSize;
        this.game = game;
        this.lastSpawnTime = System.currentTimeMillis();

        // Add factories for different monsters
        factories = new ArrayList<>();
        factories.add(new ArcherMonsterFactory());
        factories.add(new FighterMonsterFactory());
        factories.add(new WizardMonsterFactory(game));

        spawnMonster(game.getGrid().getColumns(), game.getGrid().getRows());
    }

    public void spawnMonster(int gridWidth, int gridHeight) {

        int factoryIndex = random.nextInt(factories.size()); // Randomly select a factory
        MonsterFactory selectedFactory = factories.get(factoryIndex); // 0: Archer, 1: Fighter, 2: Wizard

        int gridX = random.nextInt(gridWidth);
        int gridY = random.nextInt(gridHeight);

        BaseMonster monster = selectedFactory.createMonster(gridX, gridY, tileSize);
        monsters.add(monster);
    }

    public void updateMonsters(){
        long currentTime = System.currentTimeMillis(); // Get the current time. An error might exist here.
        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) { // If 8 seconds have passed since the last spawn
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