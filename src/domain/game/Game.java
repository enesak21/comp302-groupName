package domain.game;

import domain.entity.playerObjects.Player;
import main.PlayModePanel;

public class Game {
    private boolean isRuneFound = false;
    private boolean isPaused = false;
    private Player player;
    private Grid grid;

    public Game(Player player, int tileSize, PlayModePanel playModePanel) {
        this.player = player;
        this.grid = new Grid(tileSize, playModePanel);
    }

    public boolean isRuneFound() {
        return this.isRuneFound;
    }

    public void setRuneFound(boolean runeFound) {
        isRuneFound = runeFound;
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public float calculateDistance(Tile tile1, Tile tile2) {
        int x1 = tile1.getGridX();
        int y1 = tile1.getGridY();
        int x2 = tile2.getGridX();
        int y2 = tile2.getGridY();
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public boolean isInRange(Tile tile1, Tile tile2, float range) {
        return calculateDistance(tile1, tile2) <= range;
    }

    public boolean validateHall(Hall hall) {
        return hall != null && hall.getLength() > 0;
    }

    public void update() {
        if (!isPaused) {
            player.update();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Grid getGrid() {
        return grid;
    }
}