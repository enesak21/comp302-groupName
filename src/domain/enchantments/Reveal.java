package domain.enchantments;

import domain.game.Game;

import java.awt.*;

public class Reveal extends Enchantment {
    private Image icon;

    public Reveal(Image icon) {
        this.icon = icon;
    }


    public void applyEffect(Game game) {
        //game.revealRuneLocation(); // Implement this in the Game class
        System.out.println("REVEALLLLLLL");
    }

    public String getName() {
        return "Reveal";
    }

    public Image getIcon() {
        return icon;
    }
}