// src/domain/game/Game.java
package domain.game;

import domain.enchantments.BaseEnchantment;
import java.util.Random;

import domain.entity.monsters.MonsterManager;
import domain.entity.playerObjects.Player;
import domain.panels.PlayModePanel;

import java.util.ArrayList;

public class Game {
    private boolean isRuneFound = false;
    private boolean isPaused = false;
    private Player player;
    private Grid grid;
    private int remainingTime; // Add this field
    private TimeController timeController;
    private SearchRuneController searchRuneController;
    private ArrayList<BaseEnchantment> activeEnchantments = new ArrayList<>();
    private int tileSize;
    private MonsterManager monsterManager;

    public Game(Player player, int tileSize, Grid grid, SearchRuneController searchRuneController) {
        this.player = player;
        this.grid = grid;
        this.remainingTime = 60; // Initialize with a default value
        this.timeController = new TimeController();
        this.searchRuneController = searchRuneController;
        this.tileSize = tileSize;
        this.monsterManager = new MonsterManager(this, tileSize);

    }

    public boolean isRuneFound() {
        return this.isRuneFound;
    }

    public void setRuneFound(boolean runeFound) {
        isRuneFound = runeFound;
    }

    public void pauseGame() {
        timeController.pauseTimer();
    }

    public void resumeGame() {
        timeController.resumeTimer();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public static float calculateDistance(int gridx1, int gridy1, int gridx2, int gridy2) {
        return (float) Math.sqrt(Math.pow(gridx2 - gridx1, 2) + Math.pow(gridy2 - gridy1, 2));
    }

    public static float calculateDistance(Tile tile1, Tile tile2) {
        return calculateDistance(tile1.getGridX(), tile1.getGridY(), tile2.getGridX(), tile2.getGridY());
    }

    public static boolean isInRange(int gridx1, int gridy1, int gridx2, int gridy2, float range) {
        return calculateDistance(gridx1,gridy1,gridx2,gridy2) <= range;
    }

    public static boolean isInRange(Tile tile1, Tile tile2, float range) {
        return isInRange(tile1.getGridX(), tile1.getGridY(), tile2.getGridX(), tile2.getGridY(), range);
    }

//    public boolean validateHall(Hall hall) {
//        return hall != null && hall.getLength() > 0;
//    }

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

    public MonsterManager getMonsterManager() {
        return monsterManager;
    }

    /**
     * Returns the remaining time as a percentage of the total time.
     * 
     * TODO: Total time cannot be accessed, will fix later
     * @return float
     */
    public float getRemainingTimePercentage() {
        return (float) 100 * timeController.getTimeLeft() / 60;
    }
    
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public TimeController getTimeController() {
        return timeController;
    }

    public SearchRuneController getSearchRuneController() {
        return searchRuneController;
    }


    public ArrayList<BaseEnchantment> getActiveEnchantments() {
        return activeEnchantments;
    }
    /**
     * Teleports the player to a random empty location.
     * This method is called by wizard in CloseToLosingBehavior.
     */
    public void teleportPlayer() {
        //TODO: Implement this method
        Random random = new Random();
        int gridWidth = grid.getColumns();
        int gridHeight = grid.getRows();
        int newX, newY;

        do {
            newX = random.nextInt(gridWidth);
            newY = random.nextInt(gridHeight);
        } while (grid.getTileAt(newX - 2, newY - 2).isSolid());

        player.setGridX(newX);
        player.setGridY(newY);

    }
}