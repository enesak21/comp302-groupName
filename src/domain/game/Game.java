package domain.game;

public class Game {
    public boolean isRuneFound = false;


    /**
     * @return Rune is found
     */
    public boolean isRuneFound() {
        return this.isRuneFound;
    }

    /**
     * Sets the Rune to be found
     * @param runeFound boolean
     */
    public void setRuneFound(boolean runeFound) {
        isRuneFound = runeFound;
    }

    /**
     *
     */
    public void pauseGame(){
        return;
    }

    /**
     * Resumes the game
     */
    public void resumeGame(){
        return;
    }

    /**
     * Calculates the distance between two tiles
     * @param tile1 The first tile
     * @param tile2 The second tile
     * @return The distance between tile1 and tile2
     */
    public float calculateDistance(Tile tile1,Tile tile2){
        int deltaX = tile1.getGridX() - tile2.getGridX();
        int deltaY = tile1.getGridY() - tile2.getGridY();
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY); //eucladian distance
    }

    /**
     * Checks if two tiles are within the given range of each other
     * @param tile1 The first tile
     * @param tile2 The second tile
     * @param range maximum range
     * @return true if tile1 is within range distance of tile2
     */
    public boolean isInRange(Tile tile1,Tile tile2,float range){
        return calculateDistance(tile1,tile2) <= range;
    }

    /**
     * Validates that a hall satisfies the minimum criteria
     * @param hall The hall to be validated
     * @return true if the hall satisfies the minimum criteria
     */
    public boolean validateHall(Hall hall){
        return false;
    }


}