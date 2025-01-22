package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.SpeedUp;

public class SpeedUpEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {

        return new SpeedUp(gridX, gridY, tileSize);
    }
}

