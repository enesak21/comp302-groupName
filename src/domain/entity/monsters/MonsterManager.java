package domain.entity.monsters;

import domain.game.CollisionChecker;
import domain.game.Game;
import domain.panels.PlayModePanel;

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
    private CollisionChecker collisionChecker;

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

        int gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
        int gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 *PlayModePanel.offsetY) - 1);

        while (game.getGrid().getTileAt(gridX - PlayModePanel.offsetX, gridY - PlayModePanel.offsetY).isSolid()) {
            gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
            gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 *PlayModePanel.offsetY) - 1);
        }


        System.out.println("MonsterManager: spawnMonster: gridX: " + gridX + " gridY: " + gridY);
        BaseMonster monster = selectedFactory.createMonster(gridX, gridY, tileSize);
        monster.setCollisionChecker(collisionChecker);


        if (monster.getGridX() - PlayModePanel.offsetX < 0 || monster.getGridY() - PlayModePanel.offsetY < 0) {
            spawnMonster(gridWidth, gridHeight);
        }else {
            game.getGrid().getTileAt(monster.getGridX() - PlayModePanel.offsetX, monster.getGridY() - PlayModePanel.offsetY).setSolid(true);
            monsters.add(monster);
        }


    }

    public void updateMonsters(){
        long currentTime = System.currentTimeMillis(); // Get the current time. An error might exist here.
        if (currentTime - lastSpawnTime > SPAWN_INTERVAL) { // If 8 seconds have passed since the last spawn
            spawnMonster(game.getGrid().getColumns(), game.getGrid().getRows());
            lastSpawnTime = currentTime;
        }
        for (BaseMonster monster : monsters) {
            monster.setCollisionChecker(collisionChecker);
            monster.update(game);
        }
    }

    public List<BaseMonster> getMonsters() {
        return monsters;
    }

    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }
}