package domain.game1.game;

public class HallOfWater extends Hall {
    private static final int MIN_NUM_STRUCTURE = 13;
    // private static final int MIN_NUM_STRUCTURE = 0; //For the test, we set the minimum number of structures to 0

    public HallOfWater() {
        super("Hall of Water", MIN_NUM_STRUCTURE);
    }
}
