package main;

import domain.UI.MainMenuButton;
import domain.UI.TryAgainButton;

import java.awt.*;

public class GameOverHandler {
    int screenWidth, screenHeight;
    Font font;
    PlayModePanel playModePanel;
    public GameOverHandler(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.screenWidth = playModePanel.getScreenWidth();
        this.screenHeight = playModePanel.getScreenHeight();
        this.font = playModePanel.getFont();
    }

    public void handle() {
        playModePanel.getTimeController().pauseTimer();
        drawGameOverScreen(playModePanel.getGraphics2());
    }

    private void drawGameOverScreen(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the game over message
        g2.setFont(font.deriveFont(40f));
        g2.setColor(Color.RED); // Change the color to red

        FontMetrics fm = g2.getFontMetrics();
        String gameOverText = "Game Over!";
        int gameOverX = (screenWidth - fm.stringWidth(gameOverText)) / 2;
        int gameOverY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(gameOverText, gameOverX, gameOverY);

        // Define button dimensions
        int buttonWidth = 150;
        int buttonHeight = 50;
        int buttonSpacing = 20; // Space between buttons

        // Calculate button positions
        int totalButtonWidth = (2 * buttonWidth) + buttonSpacing;
        int startX = (screenWidth - totalButtonWidth) / 2;
        int buttonY = gameOverY + fm.getHeight() + 40; // Position below the text

        // Draw "Try Again" button
        TryAgainButton tryAgainButton = new TryAgainButton(g2, startX, buttonY, buttonWidth, buttonHeight, font);

        // Draw "Main Menu" button
        MainMenuButton mainMenuButton = new MainMenuButton(g2, startX + buttonWidth + buttonSpacing, buttonY, buttonWidth, buttonHeight, font);

        tryAgainButton.drawTryAgainButton(playModePanel);
        mainMenuButton.drawMainMenuButton();
    }
}
