package domain.enchantments;

public class LuringGemEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new LuringGem(gridX, gridY, tileSize);
    }
}