package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;

public interface EnchantmentFactory{
    BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize);
}


