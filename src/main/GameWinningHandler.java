package main;

import java.awt.*;

public class GameWinningHandler {
    int screenWidth, screenHeight;
    Font font;
    public GameWinningHandler(PlayModePanel playModePanel) {
        this.screenWidth = playModePanel.getScreenWidth();
        this.screenHeight = playModePanel.getScreenHeight();
        this.font = playModePanel.getFont();


        drawWinningScreen(playModePanel.getGraphics2());
    }

    private void drawWinningScreen(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the game over message
        g2.setFont(font.deriveFont(40f));
        g2.setColor(Color.GREEN); // Change the color to red

        FontMetrics fm = g2.getFontMetrics();
        String congratsText = "Congrats, You Won!";
        int congratsX = (screenWidth - fm.stringWidth(congratsText)) / 2;
        int congratsY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(congratsText, congratsX, congratsY);
    }







}
