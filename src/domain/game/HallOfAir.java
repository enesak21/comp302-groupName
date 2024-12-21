package domain.game;

public class HallOfAir extends Hall {
    private static final int MIN_NUM_STRUCTURE = 9;
    private static final int ANOTHER_REQUIRED_VALUE = 10; // Replace with the actual required value

    public HallOfAir() {
        super(MIN_NUM_STRUCTURE, ANOTHER_REQUIRED_VALUE);
    }
}