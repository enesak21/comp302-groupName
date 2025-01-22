package domain.game;

import java.util.List;
import java.util.Random;
import domain.structures.Structure;
import domain.UI.panels.PlayModePanel;
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

    public SearchRuneController(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

    }

    /**
     * Handles the event when a rune is collected by the player.
     * 
     * @param clickedTile The tile that was clicked by the player.
     *
     * Requires: clickedTile is not null
     * Modifies: clickedTile's structure, playModePanel
     * Effects: If the clicked tile is within range of the player's current tile and has a structure with a rune,
     *          it removes the rune from the structure and moves to the next hall. If the structure does not have a rune,
     *          it plays a "no rune" sound.
     */
    public void runeCollected(Tile clickedTile) {
        game = playModePanel.getGame();
        Structure clickedStructure = clickedTile.getStructure();
        Tile playerTile = playModePanel.getGrid().getTileAt(game.getPlayer().getGridX(), game.getPlayer().getGridY());
        if (Game.isInRange(clickedTile, playerTile, 1)) {
            if (clickedStructure != null) {
                if (clickedStructure.hasRune()) {
                    clickedStructure.setHasRune(false);
                    playModePanel.moveToNextHall();
                }
                else {
                    AudioManager.playNoRuneSound();
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

    public Tile getRuneTile() {
        if (rune != null && rune.getStoredStructure() != null) {
            return rune.getStoredStructure().getTile();
        }
        return null;
    }

}
