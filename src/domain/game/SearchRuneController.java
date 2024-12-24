package domain.game;

import java.util.List;
import java.util.Random;

import domain.structures.Structure;
import main.PlayModePanel;


public class SearchRuneController {

    private PlayModePanel playModePanel;
    private Game game;
    private Rune rune;

    public SearchRuneController(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.game = playModePanel.getGame();
    }

    public void runeCollected(Tile clickedTile) {
        Structure clickedStructure = clickedTile.getStructure();
        Tile playerTile = playModePanel.getGrid().getTileAt(game.getPlayer().getGridX(), game.getPlayer().getGridY());
        if (Game.isInRange(clickedTile, playerTile, 1)) {
            if (clickedStructure != null) {
                if (clickedStructure.hasRune()) {
                    System.out.println("Rune collected!");
                    // Transition to the next hall
                    playModePanel.moveToNextHall();
                }
            }
        }
    }

    public Rune placeRune() {
        List<Structure> structureList = playModePanel.getGrid().getStructures();

        if (structureList != null && !structureList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(structureList.size());
            Structure randomStructure = structureList.get(randomIndex);
            rune = new Rune(randomStructure);
        }

        return rune;
    }

}
