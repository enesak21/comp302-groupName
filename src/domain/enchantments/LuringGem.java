package domain.enchantments;

import domain.entity.monsters.BaseMonster;
import domain.game.Game;

import java.awt.*;
import java.util.List;

public class LuringGem extends BaseEnchantment {
    private Image icon;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Luring Gem";
    private int direction;

    public LuringGem(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    public LuringGem(int gridX, int gridY, int tileSize, int direction) {
        super(gridX, gridY, tileSize);
        this.direction = direction;
    }

    @Override
    public void applyEffect(Game game) {
        if (!isActive()) {
            game.setLuringGemActive(true);
            setActive(true);
            game.getActiveEnchantments().add(this);
            activationTime = System.currentTimeMillis();
        }
    }


    public void update(Game game) {
        if (isActive() && System.currentTimeMillis() - activationTime >= 5_000) {
            game.setLuringGemActive(false);
            setActive(false);
            System.out.println("Luring Gem deactivated");
        }
    }


    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }

    public int getDirection() {
        return direction;
    }
}