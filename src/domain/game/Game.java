// src/domain/game/Game.java
package domain.game;

import domain.entity.playerObjects.Player;
import main.PlayModePanel;

public class Game {
    private boolean isRuneFound = false;
    private boolean isPaused = false;
    private Player player;
    private Grid grid;
    private int remainingTime; // Add this field

    public Game(Player player, int tileSize, PlayModePanel playModePanel, Grid grid) {
        this.player = player;
        this.grid = grid;
        this.remainingTime = 60; // Initialize with a default value
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

    public static float calculateDistance(int gridx1, int gridy1, int gridx2, int gridy2) {
        return (float) Math.sqrt(Math.pow(gridx2 - gridx1, 2) + Math.pow(gridy2 - gridy1, 2));
    }

    public static boolean isInRange(int gridx1, int gridy1, int gridx2, int gridy2, float range) {
        return calculateDistance(gridx1,gridy1,gridx2,gridy2) <= range;
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

    // Add the new methods
    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}