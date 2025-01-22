package domain.game1.enchantments.Factories;

import domain.game1.enchantments.BaseEnchantment;
import domain.game1.enchantments.Reveal;

public class RevealEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {

            return new Reveal(gridX, gridY, tileSize);
        }
    }

