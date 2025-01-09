package domain.enchantments;

import domain.entity.monsters.BaseMonster;
import domain.game.Game;

import java.awt.*;
import java.util.List;

public class LuringGem extends BaseEnchantment {
    private Image icon;
    private long activationTime; // Timestamp when the effect was activated
    private final String name = "Luring Gem";

    public LuringGem(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }

    @Override
    public void applyEffect(Game game) {
        List<BaseMonster> monsters = game.getMonsterManager().getMonsters();

    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }
}