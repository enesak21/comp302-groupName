package domain.game;

public class Hall {
    private Integer minNumStructure;

    public Hall(Integer minNumStructure) {
        this.minNumStructure = minNumStructure;
    }

    public Integer getMinNumStructure() {
        return minNumStructure;
    }

    public void setMinNumStructure(Integer minNumStructure) {
        this.minNumStructure = minNumStructure;
    }
}