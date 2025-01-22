package domain.game1.enchantments.Factories;



import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.ExtraTime;

public class ExtraTimeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraTime(gridX, gridY, tileSize);
    }
}

