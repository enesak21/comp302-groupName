package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.CloakOfProtection;

public class CloakOfProtectionEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new CloakOfProtection(gridX, gridY, tileSize);
    }
}
