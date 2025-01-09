package domain.enchantments;

import domain.entity.playerObjects.Player;
import domain.game.Game;


import domain.enchantments.Enchantment;
import domain.game.CollisionChecker;
import domain.game.Game;

import java.awt.*;

public class ExtraLife extends BaseEnchantment {

    private Image icon;
    private long spawnTime; // When the enchantment was spawned
    private int gridX;      // X position on the grid
    private int gridY;      // Y position on the grid
    private String name;



    public ExtraLife(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
        this.name = "ExtraLife";

    }

    public String getName() {
        return "Extra Life";
    }


    public Image getIcon() {
        return icon;
    }


    public void applyEffect(Game game) {
        game.getPlayer().setHealth(game.getPlayer().getHealth()+1);
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {

    }
}
