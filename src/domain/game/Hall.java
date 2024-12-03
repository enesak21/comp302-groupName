package domain.game;

public class Hall {
    private int minNumStructure;

    public Hall(int minNumStructure) {
        this.minNumStructure = minNumStructure;
    }

    public int getMinNumStructure() {
        return minNumStructure;
    }

    public void setMinNumStructure(int minNumStructure) {
        this.minNumStructure = minNumStructure;
    }
}