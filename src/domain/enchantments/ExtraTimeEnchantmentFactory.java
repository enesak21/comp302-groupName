package domain.enchantments;



import domain.entity.monsters.ArcherMonster;
import domain.entity.monsters.BaseMonster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class ExtraTimeEnchantmentFactory implements EnchantmentFactory {
    public BaseEnchantment createEnchantment(int gridX, int gridY, int tileSize) {
        return new ExtraTime(gridX, gridY, tileSize);
    }
}

