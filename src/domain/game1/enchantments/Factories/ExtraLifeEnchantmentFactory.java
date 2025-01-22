package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.ExtraLife;

public class ExtraLifeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraLife(gridX, gridY, tileSize);
    }
}

