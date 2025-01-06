package domain.enchantments;

public class CloakOfProtectionEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new CloakOfProtection(gridX, gridY, tileSize);
    }
}
