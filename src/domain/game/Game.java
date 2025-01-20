// src/domain/game/Game.java
package domain.game;

import domain.enchantments.BaseEnchantment;
import java.util.Random;

import domain.entity.monsters.MonsterManager;
import domain.entity.playerObjects.Player;

import java.util.ArrayList;

public class Game {
    private boolean isRuneFound = false;
    private boolean isPaused = false;
    private Player player;
    private Grid grid;
    private TimeController timeController;
    private SearchRuneController searchRuneController;
    private ArrayList<BaseEnchantment> activeEnchantments = new ArrayList<>();
    private int tileSize;
    private MonsterManager monsterManager;
    private boolean isLuringGemActive = false;
    private int initialTime;
    private boolean gameWon = false;

    public Game(Player player, int tileSize, Grid grid, SearchRuneController searchRuneController) {
        this.player = player;
        this.grid = grid;
        this.timeController = new TimeController();
        this.searchRuneController = searchRuneController;
        this.tileSize = tileSize;
        this.monsterManager = new MonsterManager(this, tileSize);
    }
    public void removeFromActiveEnchantments(BaseEnchantment baseEnchantment) {
        activeEnchantments.remove(baseEnchantment);
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

    /**
     * Requires: gridx1, gridy1, gridx2, gridy2 are valid grid coordinates, range >= 0.
     * Modifies: None
     * Effects: Returns true if the distance between two grid positions is within the specified range, otherwise returns false.
     */
    public static boolean isInRange(int gridx1, int gridy1, int gridx2, int gridy2, float range) {
        return calculateDistance(gridx1,gridy1,gridx2,gridy2) <= range;
    }

    /**
     * Requires: tile1 and tile2 are not null and have valid grid coordinates, range >= 0.
     * Modifies: None
     * Effects: Returns true if the distance between two tiles is within the specified range, otherwise returns false.
     */
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
        return timeController.getTimeLeft();
    }

    public MonsterManager getMonsterManager() {
        return monsterManager;
    }

    /**
     * Returns the remaining time as a percentage of the total time.
     * 
     * @return float
     */
    public float getRemainingTimePercentage() {
        return (float) 100 * timeController.getTimeLeft() / initialTime;
    }

    public void setInitialTime(int time) {
        this.initialTime = time;
    }
    
    public void setRemainingTime(int remainingTime) {
        this.timeController.setTimeLeft(remainingTime);
    }

    /**
     * Toggles the pause state of the game.
     *
     * Requires: The game must be in a state where pausing or unpausing is valid.
     * Modifies: this.isPaused
     * Effects: If the game is currently paused, it will be unpaused. If the game is currently unpaused, it will be paused.
     */
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
        } while (grid.getTileAt(newX -2, newY -2) != null && grid.getTileAt(newX - 2, newY - 2).isSolid());

        player.setGridX(newX);
        player.setGridY(newY);
    }

    public boolean isLuringGemActive() {
        return isLuringGemActive;
    }

    public void setLuringGemActive(boolean luringGemActive) {
        isLuringGemActive = luringGemActive;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
}