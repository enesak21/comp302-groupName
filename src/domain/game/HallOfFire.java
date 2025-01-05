package domain.game;

public class HallOfFire extends Hall {
    //private static final int MIN_NUM_STRUCTURE = 17;
    private static final int MIN_NUM_STRUCTURE = 0; //For the test, we set the minimum number of structures to 0

    public HallOfFire() {
        super("Hall of Fire", MIN_NUM_STRUCTURE);
    }
}
