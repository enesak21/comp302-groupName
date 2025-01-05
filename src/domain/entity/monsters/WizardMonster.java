package domain.entity.monsters;

import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.structures.Structure;

import java.util.List;
import java.util.Random;

public class WizardMonster extends BaseMonster{
    private long teleport_frequecy = 5000; //It teleports the rune in every 5 sec.
    private final long INITIAL_DELAY = 2000;
    private long lastTeleportTime;
    private Random random;
    private Game game;
    private CollisionChecker collisionChecker;
    private long lastAttackTime = System.currentTimeMillis() + INITIAL_DELAY;
    private IWizardBehavior behavior;


    public WizardMonster(int gridX, int gridY, int tileSize, Game game) {
        super(gridX, gridY, tileSize);
        this.lastTeleportTime= System.currentTimeMillis();
        this.random = new Random();
        this.game=game;
    }

    @Override
    public void update() {
    }

    @Override
    public void update(Game game) {
        behavior.execute(this, game.getPlayer());
    }

    @Override
    public void attack(Player player) {
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

    public void getLastTeleportTime(long lastTeleportTime) {
        this.lastTeleportTime = lastTeleportTime;
    }
}