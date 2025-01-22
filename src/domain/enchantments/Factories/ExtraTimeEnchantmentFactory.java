package domain.enchantments.Factories;



import domain.enchantments.BaseEnchantment;
import domain.enchantments.ExtraTime;

public class ExtraTimeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraTime(gridX, gridY, tileSize);
    }
}

