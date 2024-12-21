package domain.game;

public class HallOfFire extends Hall {
    private static final int MIN_NUM_STRUCTURE = 17;
    private static final int ANOTHER_REQUIRED_VALUE = 10; // Replace with the actual required value

    public HallOfFire() {
        super(MIN_NUM_STRUCTURE, ANOTHER_REQUIRED_VALUE);
    }
}