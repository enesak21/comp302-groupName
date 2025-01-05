package domain.game;

public class HallOfAir extends Hall {
    //private static final int MIN_NUM_STRUCTURE = 9;
    private static final int MIN_NUM_STRUCTURE = 0; //For the test, we set the minimum number of structures to 0


    public HallOfAir() {
        super("Hall of Air", MIN_NUM_STRUCTURE);
    }
}
