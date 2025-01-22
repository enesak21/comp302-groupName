package domain.enchantments;

import domain.game.CollisionChecker;
import domain.game.Game;

import java.awt.*;

public class ExtraTime extends BaseEnchantment {
    private int timeToAdd;
    private Image icon;
    private long spawnTime; // When the enchantment was spawned
    private int gridX;      // X position on the grid
    private int gridY;      // Y position on the grid
    private String name;

    public ExtraTime(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.name = "ExtraTime";

    }

    public String getName() {
        return "Extra Time";
    }


    public Image getIcon() {
        return icon;
    }


    public void applyEffect(Game game) {
        game.getTimeController().setTimeLeft(game.getTimeController().getTimeLeft() +5);
        //game.setRemainingTime(game.getRemainingTime() + 5);
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {

    }

}
