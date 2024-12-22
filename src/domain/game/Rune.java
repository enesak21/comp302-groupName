package domain.game;

import domain.structures.Structure;

public class Rune {
    private Structure storedStructure;
    
    public Rune(Structure structure) {
        setStoredStructure(structure);
    }

    public Structure getStoredStructure() {
        return storedStructure;
    }

    public void setStoredStructure(Structure storedStructure) {
        Structure oldStructure = this.storedStructure;
        if (oldStructure != null) { // If the rune was in a structure, remove the rune from it
            oldStructure.setHasRune(false);
        }
        this.storedStructure = storedStructure;
        storedStructure.setHasRune(true);
    }
}