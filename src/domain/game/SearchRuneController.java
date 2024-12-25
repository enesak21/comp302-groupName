package domain.game;

import java.util.List;
import java.util.Random;
import domain.structures.Structure;
import domain.panels.PlayModePanel;
import domain.audio.AudioManager;


public class SearchRuneController {

    private PlayModePanel playModePanel;
    private Game game;
    private Rune rune;
    private AudioManager audioManager = new AudioManager();

    public SearchRuneController(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

    }

    public void runeCollected(Tile clickedTile) {
        game = playModePanel.getGame();
        Structure clickedStructure = clickedTile.getStructure();
        Tile playerTile = playModePanel.getGrid().getTileAt(game.getPlayer().getGridX(), game.getPlayer().getGridY());
        if (Game.isInRange(clickedTile, playerTile, 1)) {
            if (clickedStructure != null) {
                if (clickedStructure.hasRune()) {
                    System.out.println("Rune collected!");
                    // Transition to the next hall
                    playModePanel.moveToNextHall();
                }
                else {
                    audioManager.playNoRuneSound();
                }
            }
        }
    }

    public void placeRune() {
        List<Structure> structureList = playModePanel.getGrid().getStructures();
        rune = playModePanel.getRune();

        if (structureList != null && !structureList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(structureList.size());
            Structure randomStructure = structureList.get(randomIndex);
            rune.setStoredStructure(randomStructure);
        }

    }

}
