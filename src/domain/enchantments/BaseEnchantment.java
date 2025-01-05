package domain.enchantments;

import domain.game.Game;

import java.awt.*;

public interface BaseEnchantment {
    void applyEffect(Game game); // Apply the effect to the game
    String getName();
    Image getIcon();
}
