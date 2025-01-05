package domain.enchantments;

import domain.enchantments.Enchantment;
import domain.game.CollisionChecker;
import domain.game.Game;

import java.awt.*;

public class Reveal extends BaseEnchantment {
    private int timeToAdd;
    private Image icon;
    private long spawnTime; // When the enchantment was spawned
    private int gridX;      // X position on the grid
    private int gridY;      // Y position on the grid
    private String name;



    public Reveal(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.name = "Reveal";

    }
    public String getName() {
        return name;
    }


    public Image getIcon() {
        return icon;
    }


    public void applyEffect(Game game) {
        System.out.println("EXTRA TIME WILL BE ADDED");
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {

    }

}
