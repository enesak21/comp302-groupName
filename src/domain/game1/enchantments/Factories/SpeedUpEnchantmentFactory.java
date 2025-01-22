package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.SpeedUp;

public class SpeedUpEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {

        return new SpeedUp(gridX, gridY, tileSize);
    }
}

