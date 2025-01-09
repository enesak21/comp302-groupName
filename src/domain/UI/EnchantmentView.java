package domain.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentView {

    private int x; // X coordinate for drawing
    private int y; // Y coordinate for drawing
    private String type; // Enchantment type (e.g., "Extra Time", "Reveal")
    private Map<String, Image> enchantmentIcons; // Icon map for enchantments

    public EnchantmentView(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
        loadEnchantmentIcons();
    }

    private void loadEnchantmentIcons() {
        enchantmentIcons = new HashMap<>();
        try {
            enchantmentIcons.put("Extra Time", ImageIO.read(getClass().getResource("/resources/icons/clock.png")));
            enchantmentIcons.put("Reveal", ImageIO.read(getClass().getResource("/resources/items/reveal.png")));
            enchantmentIcons.put("Extra Life", ImageIO.read(getClass().getResource("/resources/player/heart.png")));
            enchantmentIcons.put("Cloak of Protection", ImageIO.read(getClass().getResource("/resources/items/cloakOfProtection.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        Image icon = enchantmentIcons.get(type);
        if (icon != null) {
            g2.drawImage(icon, x, y, 32, 32, null); // Replace 32 with tile size if needed
        }
    }
}
