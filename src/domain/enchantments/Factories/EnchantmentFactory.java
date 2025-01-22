package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;

public interface EnchantmentFactory{
    BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize);
}


