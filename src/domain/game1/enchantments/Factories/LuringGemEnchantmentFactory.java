package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.LuringGem;

public class LuringGemEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new LuringGem(gridX, gridY, tileSize);
    }
}