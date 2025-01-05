package domain.enchantments;

import java.awt.*;

public class EnchantmentFactory{
    public static Enchantment createEnchantment(String type, Image icon) {
        switch (type) {
            case "Extra Time":
                return new ExtraTime(5, icon); // Adds 5 seconds
            case "Reveal":
                return new Reveal(icon);
//            case "Cloak of Protection":
//                return new CloakOfProtectionEnchantment(icon);
//            case "Luring Gem":
//                return new LuringGemEnchantment(icon);
//            case "Extra Life":
//                return new ExtraLifeEnchantment(icon);
            default:
                throw new IllegalArgumentException("Unknown enchantment type: " + type);
        }
    }
}
