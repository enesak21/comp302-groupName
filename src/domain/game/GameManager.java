package domain.game;

import domain.entity.playerObjects.Player;
import main.PlayModePanel;
import main.PlayerController;

public class GameManager {
    private PlayModePanel playModePanel;
    private Game currentGame;
    private int totalRunesFound = 0; // Optional: Track total progress

    public GameManager(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        startNewGame();
    }

    public void startNewGame() {
        // Yeni bir Hall ve Grid yarat
        Grid grid = new Grid(playModePanel.getTileSize());
        Player player = new Player("Osimhen", 0, 0, playModePanel.getTileSize(), playModePanel, new PlayerController());

        // Yeni bir Game yarat
        currentGame = new Game(player, playModePanel.getTileSize(), playModePanel, grid);

        // PlayModePanel'i yeni Game ile güncelle
        playModePanel.setGame(currentGame);

        System.out.println("New Game Started!");
    }

    public void onRuneFound() {
        totalRunesFound++;
        System.out.println("Rune Found! Starting new game...");
        startNewGame();
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
