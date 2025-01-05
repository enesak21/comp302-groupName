package domain.enchantments;

import java.awt.*;
import java.util.Random;

public interface EnchantmentFactory{
    BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize);
}


