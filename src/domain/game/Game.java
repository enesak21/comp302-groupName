package domain.game;

public class Game {
    public float remainingTime;
    public boolean isRuneFound = false;


    public void pauseGame(){
        return;
    }

    public void resumeGame(){
        return;
    }

    public float calculateDistance(Tile tile1,Tile tile2){
        return 0;
    }

    public boolean isInRange(Tile tile1,Tile tile2,float range){
        return calculateDistance(tile1,tile2) <= range;
    }

    public boolean validateHall(Hall hall){
        return false;
    }

}