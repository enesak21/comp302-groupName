package domain.enchantments.Factories;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.Reveal;

public class RevealEnchantmentFactory implements EnchantmentFactory {
    @Override
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {

            return new Reveal(gridX, gridY, tileSize);
        }
    }

