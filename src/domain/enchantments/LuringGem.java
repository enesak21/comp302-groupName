package domain.enchantments;

import domain.game.Game;

import java.awt.*;

public class LuringGem extends BaseEnchantment {
    private Image icon;
    private boolean isActive;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Luring Gem";

    public LuringGem(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    @Override
    public void applyEffect(Game game) {

    }
}