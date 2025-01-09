package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.ExtraLife;

public class ExtraLifeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraLife(gridX, gridY, tileSize);
    }
}

