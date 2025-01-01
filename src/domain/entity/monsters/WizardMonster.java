package domain.entity.monsters;

import domain.entity.playerObjects.Player;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.structures.Structure;

import java.util.List;
import java.util.Random;

public class WizardMonster extends BaseMonster{
    private final long TELEPORT_FREQUENCY = 5000; //It teleports the rune in every 5 sec.
    private final long INITIAL_DELAY = 2000;
    private long lastTeleportTime;
    private Random random;
    private Game game;
    private CollisionChecker collisionChecker;
    private long lastAttackTime = System.currentTimeMillis() + INITIAL_DELAY;


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
        attack(game.getPlayer());
    }

    @Override
    public void attack(Player player) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastAttackTime) > TELEPORT_FREQUENCY) {
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
}