package main;

import domain.UI.MainMenuButton;
import domain.UI.TryAgainButton;
import domain.UI.UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWinningHandler {
    int screenWidth, screenHeight;
    Font font;
    PlayModePanel playModePanel;

    TryAgainButton tryAgainButton;
    MainMenuButton mainMenuButton;

    public GameWinningHandler(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.screenWidth = playModePanel.getScreenWidth();
        this.screenHeight = playModePanel.getScreenHeight();
        this.font = playModePanel.getFont();
    }

    public void handle() {
        playModePanel.getTimeController().pauseTimer();
        drawWinningScreen(playModePanel.getGraphics2());
    }

    private void drawWinningScreen(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the game over message
        g2.setFont(font.deriveFont(40f));
        g2.setColor(Color.GREEN); // Change the color to green

        FontMetrics fm = g2.getFontMetrics();
        String congratsText = "Congrats, You Won!";
        int congratsX = (screenWidth - fm.stringWidth(congratsText)) / 2;
        int congratsY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(congratsText, congratsX, congratsY);

        // Define button dimensions
        int buttonWidth = 200;
        int buttonHeight = 50;
        int buttonSpacing = 20; // Space between buttons

        // Calculate button positions
        int totalButtonWidth = (2 * buttonWidth) + buttonSpacing;
        int startX = (screenWidth - totalButtonWidth) / 2;
        int buttonY = congratsY + fm.getHeight() + 40; // Position below the text

        // Draw "Try Again" button
        tryAgainButton = new TryAgainButton(g2, startX, buttonY, buttonWidth, buttonHeight, font);

        // Draw "Main Menu" button
        mainMenuButton = new MainMenuButton(g2, startX + buttonWidth + buttonSpacing, buttonY, buttonWidth, buttonHeight, font);

        tryAgainButton.drawTryAgainButton(playModePanel);
        mainMenuButton.drawMainMenuButton(playModePanel);

    }

    public void addMainMenuKeyListener() {
        // Add MouseListener to handle clicks
        playModePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Check if the click is within the button bounds
                if (mouseX >= mainMenuButton.getX() && mouseX <= mainMenuButton.getX() + mainMenuButton.getWidth() &&
                        mouseY >= mainMenuButton.getY() && mouseY <= mainMenuButton.getY() + mainMenuButton.getHeight()) {
                    playModePanel.getFrame().dispose();
                    UI ui = new UI();
                    ui.show();
                }
            }
        });
    }
}