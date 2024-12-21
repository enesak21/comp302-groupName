package domain.game;

public class HallOfEarth extends Hall {
    private static final int MIN_NUM_STRUCTURE = 6;
    private static final int ANOTHER_REQUIRED_VALUE = 10; // Replace with the actual required value

    public HallOfEarth() {
        super(MIN_NUM_STRUCTURE, ANOTHER_REQUIRED_VALUE);
    }
}