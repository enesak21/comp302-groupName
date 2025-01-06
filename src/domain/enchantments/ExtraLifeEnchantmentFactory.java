package domain.enchantments;

public class ExtraLifeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraLife(gridX, gridY, tileSize);
    }
}

