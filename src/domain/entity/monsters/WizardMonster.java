package domain.entity.monsters;

import domain.entity.Entity;
import domain.entity.playerObjects.Player;
import domain.game.Game;
import domain.game.Tile;
import domain.structures.Structure;

import java.util.List;
import java.util.Random;

public class WizardMonster extends BaseMonster{
    private final long TELEPORT_FREQUENCY = 5000; //It teleports the rune in every 5 sec.
    private long lastTeleportTime;
    private Random random;
    private Game game;

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
    public void update(Player player) {
        attack(player);
        move();
    }

    @Override
    public void attack(Player player) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastTeleportTime) > TELEPORT_FREQUENCY) {
            switchRune();
            lastTeleportTime = currentTime;
        }
    }

    private void switchRune() {
        List<Structure> structures = game.getGrid().getStructures();
        if (!structures.isEmpty()) {
            int selectedIndex = random.nextInt(structures.size());
            Structure selectedStructure = structures.get(selectedIndex);
            game.getRune().setStoredStructure(selectedStructure);
        }
    }

    @Override
    public void move(){

    }
}