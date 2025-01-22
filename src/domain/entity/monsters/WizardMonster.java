package domain.entity.monsters;

import domain.game.CollisionChecker;
import domain.game.Game;
import domain.structures.Structure;

import java.util.List;
import java.util.Random;

public class WizardMonster extends BaseMonster {
    private long teleport_frequecy = 5000; // It teleports the rune in every 5 sec.
    private final long INITIAL_DELAY = 2000;
    private long lastTeleportTime;
    private Random random;
    private MonsterManager monsterManager;
    private Game game;
    private CollisionChecker collisionChecker;
    private long lastAttackTime = System.currentTimeMillis() + INITIAL_DELAY;
    private IWizardBehavior behavior;

    public WizardMonster(int gridX, int gridY, int tileSize, MonsterManager monsterManager) {
        super(gridX, gridY, tileSize);
        this.lastTeleportTime = System.currentTimeMillis();
        this.random = new Random();
        this.monsterManager = monsterManager;
        this.game = monsterManager.getGame();
    }

    @Override
    public void update() {
    }

    @Override
    public void update(Game game) {
        float remainingTimePercentage = game.getRemainingTimePercentage();

        if (remainingTimePercentage < 30) {
            setBehavior(new CloseToLosingBehavior());
        } else if (remainingTimePercentage > 70) {
            setBehavior(new ChallengingBehavior());
        } else {
            setBehavior(new IndecisiveBehavior());
        }

        behavior.execute(this, game);
    }

    @Override
    public void attack(Game game) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastAttackTime) > teleport_frequecy) {
            lastAttackTime = currentTime;
            switchRune();
        }
    }

    private void switchRune() {
        List<Structure> structures = game.getGrid().getStructures();
        if (!structures.isEmpty()) {
            game.getSearchRuneController().placeRune();
        }
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public void setTeleportFrequecy(long teleport_frequecy) {
        this.teleport_frequecy = teleport_frequecy;
    }

    public long getTeleportFrequecy() {
        return teleport_frequecy;
    }

    public long getLastTeleportTime() {
        return lastTeleportTime;
    }

    public void setBehavior(IWizardBehavior behavior) {
        this.behavior = behavior;
    }

    public void disappear() {
        monsterManager.removeMonster(this);
    }

    @Override
    public String toString() {
        return "WizardMonster";
    }
}