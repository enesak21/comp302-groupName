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
    PlayModePanel playModePanel;

    public GameManager(List<Hall> halls, Player player) {
        this.player=player;
        this.halls = halls;
    }

    public void startNewHall() {
        if (currentHallIndex < halls.size()) {
            Hall currentHall = halls.get(currentHallIndex);
            Grid grid = currentHall.toGrid(playModePanel.getTileSize());

            // Yeni bir Game başlat
            currentGame = new Game(grid,player);
            playModePanel.setGameManager(this); // PlayModePanel'i yeni oyunla güncelle
            currentGame.initializeMonsters(playModePanel.getTileSize());
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
        Player player = Player.getInstance();
        // update PlayModePanel with the new Game
        playModePanel.setGameManager(this);
    }

    public void restartGame() {
        // Oyuncu ve oyun durumlarını sıfırla
        currentHallIndex = 0; // İlk hall'e dön
        player.restart(); // Oyuncunun durumunu sıfırla
        startNewHall(); // İlk hall'i başlat
    }

    public void updateGameState() {
        if (!currentGame.isPaused()) {
            currentGame.getPlayer().update(); // Oyuncunun güncellenmesi
            currentGame.getMonsterManager().updateMonsters(); // Canavarların güncellenmesi
        }
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

    public int getCurrentHallIndex() {
        return currentHallIndex;
    }

    public void setPlayModePanel(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
    }

    public String getCurrentHallName() {
        return halls.get(currentHallIndex).getName();
    }
}
