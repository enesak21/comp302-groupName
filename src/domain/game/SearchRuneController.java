package domain.game;

import java.util.List;
import java.util.Random;
import domain.structures.Structure;
import domain.panels.PlayModePanel;
import domain.audio.AudioManager;


/**
 * The SearchRuneController class handles the logic for collecting and placing runes within the game.
 * It interacts with the PlayModePanel to manage game state and audio feedback.
 * The SearchRuneController handles the SearchRune use case
 */
public class SearchRuneController {

    private PlayModePanel playModePanel;
    private Game game;
    private Rune rune;
    private AudioManager audioManager = new AudioManager();

    public SearchRuneController(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

    }

    /**
     * Handles the event when a rune is collected by the player.
     * 
     * @param clickedTile The tile that was clicked by the player.
     * 
     */
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

    /**
     * Places a rune on a randomly selected structure from the list of structures
     * in the play mode panel's grid.
     */
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
