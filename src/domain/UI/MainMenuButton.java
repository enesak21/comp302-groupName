package domain.UI;

import java.awt.*;

public class MainMenuButton {

    public MainMenuButton(Graphics2D g2, int x, int y, int width, int height, Font pressStart2PFont) {
        drawMainMenuButton(g2, x, y, width, height, pressStart2PFont);
    }

    private void drawMainMenuButton(Graphics2D g2, int x, int y, int width, int height, Font pressStart2PFont) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(pressStart2PFont.deriveFont(20f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString("Main Menu", x + (width - fm.stringWidth("Main Menu")) / 2,
                y + (height + fm.getAscent()) / 2);
    }
}

