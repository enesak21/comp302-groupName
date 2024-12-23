package domain.game;

import domain.structures.Structure;
import main.PlayModePanel;

public class SearchRuneController {

    private PlayModePanel playModePanel;
    private Game game;

    public SearchRuneController(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.game = playModePanel.getGame();
    }

    public void runeCollected(Tile clickedTile){ {
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
    }
}
