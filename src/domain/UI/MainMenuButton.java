package domain.UI;

import javax.swing.*;
import java.awt.*;

public class MainMenuButton extends JButton {
    Graphics2D g2;
    int x, y, width, height;
    Font font;
    public MainMenuButton(Graphics2D g2, int x, int y, int width, int height, Font font) {
        this.g2 = g2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
    }

    public void drawMainMenuButton() {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(font.deriveFont(20f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString("Main Menu", x + (width - fm.stringWidth("Main Menu")) / 2,
                y + (height + fm.getAscent()) / 2);
    }
}

