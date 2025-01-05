package domain.UI;

import domain.game.Game;
import domain.game.GameManager;
import java.awt.*;

public class SideBarView {
    private final GameManager gameManager;
    private final Font font;

    public SideBarView(GameManager gameManager, Font font) {
        this.gameManager = gameManager;
        this.font = font;
    }

    public void render(Graphics2D g2, int x, int y, int width, int height) {
        // Draw sidebar background
        g2.setColor(new Color(108, 85, 89));
        g2.fillRoundRect(x, y, width, height, 30, 30);

        // Set font and color for text
        g2.setFont(font.deriveFont(15f));
        g2.setColor(Color.WHITE);

        Game currentGame = gameManager.getCurrentGame();

        // Draw health
        drawHealth(g2, currentGame.getPlayer().getHealth(), x + 10, y + 50);

        // Draw time left
        drawTimeLeft(g2, currentGame.getTimeController().getTimeLeft(), x + 10, y + 100);

        // Draw hall info
        drawHallInfo(g2, gameManager.getCurrentHallName(), x + 10, y + 150);
    }

    private void drawHealth(Graphics2D g2, int health, int x, int y) {
        g2.drawString("Health:", x, y);
        for (int i = 0; i < health; i++) {
            g2.fillRect(x + 60 + (i * 15), y - 12, 10, 10); // Draw a small rectangle for each health unit
        }
    }

    private void drawTimeLeft(Graphics2D g2, int timeLeft, int x, int y) {
        g2.drawString("Time Left: " + timeLeft + "s", x, y);
    }

    private void drawHallInfo(Graphics2D g2, String hallName, int x, int y) {
        g2.drawString("Hall: " + hallName, x, y);
    }
}
