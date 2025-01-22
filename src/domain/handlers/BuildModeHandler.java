package domain.handlers;

import domain.game.Hall;

import java.util.ArrayList;
import java.util.List;

public class BuildModeHandler {
    private List<Hall> halls = new ArrayList<>();

    public BuildModeHandler(List<Hall> halls) {
        this.halls = halls;
    }

    public int checkHall(Hall hall) {
        if (hall.isRequirementMet()) {
            return 0;
        }
        else {
            int requiredStructures = hall.getMinStructures();
            int placedStructures = hall.getPlacedStructuresCount();
            int remainingStructures = requiredStructures - placedStructures;
            return remainingStructures;
        }
    }

    public boolean checkBuildMode() {
        for (Hall hall : halls) {
            if (checkHall(hall) != 0) {
                return false;
            }
        }
        return true;
    }
}
