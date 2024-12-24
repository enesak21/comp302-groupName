package domain.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import domain.structures.Structure;

public class Rune {
    private Structure storedStructure;
    private BufferedImage image; // Image of the rune to be displayed after Rune is found

    public Rune(Structure structure) {
        setStoredStructure(structure);
        loadImage();
    }

    public Rune() {}

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/structures/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Structure getStoredStructure() {
        return storedStructure;
    }

    /**
     * Sets the stored structure for this rune. If the rune was previously stored in another structure,
     * it removes the rune from that structure before setting the new structure.
     *
     * @param storedStructure the new structure to store the rune in
     */
    public void setStoredStructure(Structure storedStructure) {
        Structure oldStructure = this.storedStructure;
        if (oldStructure != null) { // If the rune was in a structure, remove the rune from it
            oldStructure.setHasRune(false);
        }
        this.storedStructure = storedStructure;
        storedStructure.setHasRune(true);
    }
}