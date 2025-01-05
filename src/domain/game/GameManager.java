package domain.game;

import domain.entity.playerObjects.Player;
import domain.panels.PlayModePanel;
import main.PlayerInputHandler;

import java.util.List;

public class GameManager {
    private PlayModePanel playModePanel;
    private Game currentGame;
    private List<Hall> halls;
    private int currentHallIndex = 0; // Hangi hall'da olduğumuzu takip eder

    public GameManager(PlayModePanel playModePanel, List<Hall> halls) {
        this.playModePanel = playModePanel;
        this.halls = halls;
        startNewHall();
    }

    public void startNewHall() {
        if (currentHallIndex < halls.size()) {
            Hall currentHall = halls.get(currentHallIndex);
            Grid grid = currentHall.toGrid(playModePanel.getTileSize());
            Player player = Player.getInstance(
                    "Osimhen",
                    0,
                    0,
                    playModePanel.getTileSize(),
                    playModePanel,
                    new PlayerInputHandler()
            );

            // Yeni bir Game başlat
            currentGame = new Game(grid, currentHall.getRune());
            playModePanel.setGame(currentGame); // PlayModePanel'i yeni oyunla güncelle

            currentHallIndex++;
        } else {
            endGame();
        }
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
        startNewGame();
    }
    public void pauseGame() {
        currentGame.pauseGame();
    }

    public void resumeGame() {
        currentGame.resumeGame();
    }

    private void endGame() {
        // Oyunu sonlandır veya ana menüye dön
    }


    public Game getCurrentGame() {
        return currentGame;
    }
}
