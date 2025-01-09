package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.CloakOfProtection;

public class CloakOfProtectionEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new CloakOfProtection(gridX, gridY, tileSize);
    }
}
