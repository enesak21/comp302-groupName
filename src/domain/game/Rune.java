package domain.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import domain.structures.Structure;

public class Rune {
    private Structure storedStructure;
    private BufferedImage image;

    public Rune(Structure structure) {
        setStoredStructure(structure);
        loadImage();
    }

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

    public void setStoredStructure(Structure storedStructure) {
        Structure oldStructure = this.storedStructure;
        if (oldStructure != null) { // If the rune was in a structure, remove the rune from it
            oldStructure.setHasRune(false);
        }
        this.storedStructure = storedStructure;
        storedStructure.setHasRune(true);
    }
}