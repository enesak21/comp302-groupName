package domain.game;

public class HallOfWater extends Hall {
    private static final int MIN_NUM_STRUCTURE = 13;
    private static final int ANOTHER_REQUIRED_VALUE = 10; // Replace with the actual required value

    public HallOfWater() {
        super(MIN_NUM_STRUCTURE, ANOTHER_REQUIRED_VALUE);
    }
}