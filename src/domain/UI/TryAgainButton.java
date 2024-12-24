package domain.UI;

import java.awt.*;

public class TryAgainButton {

    public TryAgainButton(Graphics2D g2, int x, int y, int width, int height, Font pressStart2PFont) {
        drawTryAgainButton(g2, x, y, width, height, pressStart2PFont);
    }

    private void drawTryAgainButton(Graphics2D g2, int x, int y, int width, int height, Font pressStart2PFont) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(pressStart2PFont.deriveFont(20f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString("Try Again", x + (width - fm.stringWidth("Try Again")) / 2,
                y + (height + fm.getAscent()) / 2);
    }
}
