package domain.entity.monsters;

import domain.entity.monsters.Factories.ArcherMonsterFactory;
import domain.entity.monsters.Factories.FighterMonsterFactory;
import domain.entity.monsters.Factories.TimerMonsterFactory;
import domain.entity.monsters.Factories.WizardMonsterFactory;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.UI.panels.PlayModePanel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonsterManager {

    private List<BaseMonster> monsters;
    private Random random;
    private int tileSize;
    private Game game;
    private final int SPAWN_INTERVAL = 8; //Write it in seconds
    private CollisionChecker collisionChecker;
    private long lastSpawnLeftTime;

    private List<MonsterFactory> factories;

    public MonsterManager(Game game,int tileSize) {
        this.monsters = new CopyOnWriteArrayList<>();
        this.random = new Random();
        this.tileSize = tileSize;
        this.game = game;
        // Add factories for different monsters
        factories = new ArrayList<>();
        factories.add(new ArcherMonsterFactory());
        factories.add(new FighterMonsterFactory());
        factories.add(new WizardMonsterFactory(this));
        factories.add(new TimerMonsterFactory());

        spawnMonster(game.getGrid().getColumns(), game.getGrid().getRows());
    }

    public void spawnMonster(int gridWidth, int gridHeight) {

        int factoryIndex = random.nextInt(factories.size()); // Randomly select a factory

        MonsterFactory selectedFactory = factories.get(factoryIndex); // 0: Archer, 1: Fighter, 2: Wizard, 3: Timer


        int gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
        int gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 *PlayModePanel.offsetY) - 1);

        while (game.getGrid().getTileAt(gridX - PlayModePanel.offsetX, gridY - PlayModePanel.offsetY).isSolid()) {
            gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
            gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 *PlayModePanel.offsetY) - 1);
        }

        BaseMonster monster = selectedFactory.createMonster(gridX, gridY, tileSize);
        monster.setCollisionChecker(collisionChecker);

        if (monster.getGridX() - PlayModePanel.offsetX < 0 || monster.getGridY() - PlayModePanel.offsetY < 0) {
            spawnMonster(gridWidth, gridHeight);
        }else {

            //After we create the monster, we need to set isSolid to true for the tiles that the monster is on
            game.getGrid().getTileAt(gridX - 2, gridY - 2).setSolid(true);
            monsters.add(monster);

        }
    }

    public void updateMonsters(){
        long timeLeft = game.getTimeController().getTimeLeft(); //52

        if (lastSpawnLeftTime - timeLeft > SPAWN_INTERVAL) { // If 8 seconds have passed since the last spawn

            spawnMonster(game.getGrid().getColumns(), game.getGrid().getRows());
            lastSpawnLeftTime = timeLeft;
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

    public void removeMonster(BaseMonster monster) {
        monsters.remove(monster);
        game.getGrid().getTileAt(monster.getGridX(), monster.getGridY()).setSolid(false); // Update the grid to mark the tile as not solid
    }

    public Game getGame() {
        return game;
    }

    public void setLastSpawnLeftTime(long lastSpawnLeftTime) {
        this.lastSpawnLeftTime = lastSpawnLeftTime;
    }
}