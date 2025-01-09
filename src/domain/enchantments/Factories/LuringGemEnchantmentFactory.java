package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.LuringGem;

public class LuringGemEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new LuringGem(gridX, gridY, tileSize);
    }
}