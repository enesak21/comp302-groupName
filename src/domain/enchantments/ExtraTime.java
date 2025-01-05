package domain.enchantments;

import domain.enchantments.Enchantment;
import domain.game.Game;

import java.awt.*;

public class ExtraTime extends Enchantment {
    private int timeToAdd;
    private Image icon;

    public ExtraTime(int timeToAdd, Image icon) {
        super();
        this.timeToAdd = timeToAdd;
        this.icon = icon;
    }


//    public void applyEffect(Game game) {
//        game.getTimeController().addTime(timeToAdd);
//    }


    public String getName() {
        return "Extra Time";
    }


    public Image getIcon() {
        return icon;
    }


    public void applyEffect(Game game) {
        System.out.println("EXTRA TIME WILL BE ADDED");
    }
}
