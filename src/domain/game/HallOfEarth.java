package domain.game;

public class HallOfEarth extends Hall {
    //private static final int MIN_NUM_STRUCTURE = 6;
    private static final int MIN_NUM_STRUCTURE = 0;//For the test, we set the minimum number of structures to 0

    public HallOfEarth() {
        super("Hall of Earth", MIN_NUM_STRUCTURE);
    }
}
