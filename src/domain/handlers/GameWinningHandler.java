package domain.handlers;


import domain.UI.UI;
import domain.handlers.buttonHandlers.TryAgainButton;
import domain.panels.PlayModePanel;
import java.awt.*;

public class GameWinningHandler {
    int screenWidth, screenHeight;
    Font font;
    PlayModePanel playModePanel;
    TryAgainButton tryAgainButton;

    public GameWinningHandler(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.screenWidth = playModePanel.getScreenWidth();
        this.screenHeight = playModePanel.getScreenHeight();
        this.font = playModePanel.getFont();
    }

    public void handle() {
        playModePanel.setState("TryAgain");
        playModePanel.setPaused(true);
        playModePanel.getGame().getTimeController().pauseTimer();
        drawWinningScreen(playModePanel.getGraphics2());
    }

    private void drawWinningScreen(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, playModePanel.getWidth(), playModePanel.getHeight());

        // Draw the game winning message
        g2.setFont(font.deriveFont(30f));
        g2.setColor(Color.GREEN); // Change the color to green

        FontMetrics fm = g2.getFontMetrics();
        String congratsText = "Congrats, You Won!";
        int congratsX = (playModePanel.getWidth() - fm.stringWidth(congratsText)) / 2;
        int congratsY = (playModePanel.getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(congratsText, congratsX, congratsY);

        // Define button dimensions
        int buttonWidth = 200;
        int buttonHeight = 50;

        // Calculate button positions
        int startX = (playModePanel.getWidth() - buttonWidth) / 2;
        int buttonY = congratsY + fm.getHeight() + 40; // Position below the text

        // Draw "Try Again" button
        tryAgainButton = new TryAgainButton(g2, startX, buttonY, buttonWidth, buttonHeight, font);
        tryAgainButton.drawTryAgainButton(playModePanel);

    }
}