package domain.game;

import domain.structures.Structure;

public class Rune {
    private Structure storedStructure;
    
    public Rune(Structure structure) {
        this.storedStructure = structure;
    }

    public Structure getStoredStructure() {
        return storedStructure;
    }

    public void setStoredStructure(Structure storedStructure) {
        Structure oldStructure = this.storedStructure;
        oldStructure.setHasRune(false);
        this.storedStructure = storedStructure;
        storedStructure.setHasRune(true);
    }
}