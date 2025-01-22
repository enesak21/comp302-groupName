package domain.game1.enchantments;

import domain.game1.game.Game;

import java.awt.*;

public class LuringGem extends BaseEnchantment {
    private Image icon;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Luring Gem";
    private int direction;

    public LuringGem(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
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

    public void setDirection(int direction) {this.direction = direction;}
}