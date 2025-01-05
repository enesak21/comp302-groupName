package domain.game;

import domain.entity.playerObjects.Player;
import domain.handlers.GameOverHandler;
import domain.panels.PlayModePanel;
import main.PlayerInputHandler;

import java.util.List;

public class GameManager {
    private Game currentGame;
    private final List<Hall> halls;
    private int currentHallIndex = 0; // Hangi hall'da olduğumuzu takip eder
    private final Player player;
    private GameOverHandler gameOverHandler;

    public GameManager(List<Hall> halls, Player player) {
        this.player=player;
        this.halls = halls;
        startNewHall();
    }

    public void startNewHall() {
        if (currentHallIndex < halls.size()) {
            Hall currentHall = halls.get(currentHallIndex);
            Grid grid = currentHall.toGrid(playModePanel.getTileSize());

            // Yeni bir Game başlat
            currentGame = new Game(grid, currentHall.getRune());
            playModePanel.setGame(currentGame); // PlayModePanel'i yeni oyunla güncelle

            currentHallIndex++;
        } else {
            endGame();
        }
    }

    public void moveToNextHall() {
        if (currentHallIndex < halls.size() - 1) { // if not the last hall
            currentHallIndex++;
            startNewHall();
        } else {
            onAllHallsCompleted();
        }
    }

    private void onAllHallsCompleted() {
        System.out.println("All halls completed. Game Over!");
        // Oyunu kazanma ekranı veya başka bir aksiyon tetiklenebilir
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

    public void exitGame() {
        System.exit(0); // Uygulamayı kapat
    }


    private void endGame() {
        // Oyunu sonlandır veya ana menüye dön
        currentGame.getTimeController().pauseTimer(); // Zamanlayıcıyı durdur
        gameOverHandler.handle(); // GameOver ekranını göster
    }

    public void checkPlayerHealth() {
        if (player.isDead()) {
            endGame();
        }
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}
