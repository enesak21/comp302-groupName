package domain.game;

import domain.entity.playerObjects.Player;
import domain.UI.panels.PlayModePanel;
import main.PlayerInputHandler;

public class GameManager {
    private PlayModePanel playModePanel;
    private Game currentGame;
    private int totalRunesFound = 0; // Optional: Track total progress

    public GameManager(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        startNewGame();
    }

    public void startNewGame() {
        //Create a new Hall and Grid
        Grid grid = new Grid(playModePanel.getTileSize());
        Player player = Player.getInstance("Osimhen", 0, 0, playModePanel.getTileSize(), playModePanel, new PlayerInputHandler());

        // currentGame = new Game(player, playModePanel.getTileSize(), playModePanel, grid, searc);

        // update PlayModePanel with the new Game
        playModePanel.setGame(currentGame);
    }

    public void onRuneFound() {
        totalRunesFound++;
        startNewGame();
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
