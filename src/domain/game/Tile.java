package domain.game;

public class Tile {
    private int x;
    private int y;

    //not sure about parameters
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //check whether location is empty
    public boolean isEmpty() {
        return false;
    }
}
