package domain.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StructureObserver {
    // Static final constant for structure names (immutable list)
    private static final List<String> STRUCTURE_NAMES;

    static {
        // Initialize the static list in a static block
        List<String> names = new ArrayList<>();
        names.add("chest");
        names.add("column");
        names.add("ladder");
        names.add("doubleBox");
        names.add("singleBox");
        names.add("skull");
        names.add("tomb");
        names.add("bottle");
        STRUCTURE_NAMES = Collections.unmodifiableList(names);
    }

    // Returns the static list (no need to recreate)
    public static List<String> getStructureNames() {
        return STRUCTURE_NAMES;
    }

    // Returns a random structure name
    public static String getRandomStructure() {
        return STRUCTURE_NAMES.get((int) (Math.random() * STRUCTURE_NAMES.size()));
    }
}
