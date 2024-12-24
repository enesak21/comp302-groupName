package domain.UI;

import main.PlayModePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TryAgainButton {
    Graphics2D g2;
    int x, y, width, height;
    Font font;
    public TryAgainButton(Graphics2D g2, int x, int y, int width, int height, Font font) {
        this.g2 = g2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
    }

    public void drawTryAgainButton(PlayModePanel playModePanel) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.setFont(font.deriveFont(20f));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString("Try Again", x + (width - fm.stringWidth("Try Again")) / 2,
                y + (height + fm.getAscent()) / 2);

        // Add MouseListener to handle clicks
        playModePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Check if the click is within the button bounds
                if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                    System.out.println("Try Again button clicked");
                    // Create a new game instance and run it
                    playModePanel.restartGame();
                }
            }
        });


    }
}
