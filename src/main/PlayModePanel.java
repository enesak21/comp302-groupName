package main;

import domain.entity.playerObjects.Player;
import domain.game.Game;
import domain.game.Grid;
import domain.game.TimeController;

import javax.swing.*;
import java.awt.*;

public class PlayModePanel extends JPanel implements Runnable {

    // Screen settings
    static final int originalTileSize = 16; // our assets are 16x16 pixels originally
    static final int scale = 2;
    static final int tileSize = originalTileSize * scale;

    static final int gridColumns = 16; // Arena sütun sayısı
    static final int gridRows = 16;    // Arena satır sayısı
    static final int gridWidth = gridColumns * tileSize; // Arena genişliği
    static final int gridHeight = gridRows * tileSize;   // Arena yüksekliği

    static final int screenWidth = 24 * tileSize; // Tüm ekran genişliği
    static final int screenHeight = 20 * tileSize; // Tüm ekran yüksekliği

    public static final int offsetX = ((screenWidth - gridWidth) / (2 * tileSize)) - 2; // offset for gridi ortalama (tile-based)
    public static final int offsetY = (screenHeight - gridHeight) / (2 * tileSize);

    private TimeController timeController;

    // FPS
    int FPS = 60;

    Thread gameThread;
    Game game;

    // Constructor
    public PlayModePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        Player player = new Player("Osimhen", 0, 0, tileSize, this, new PlayerController());
        game = new Game(player, tileSize, this); // Pass the required arguments
        this.addKeyListener(player.getPlayerController());

        // Initialize the TimeController
        timeController = new TimeController();
    }

    public void startGameThread() {
        System.out.println("Starting game thread");

        gameThread = new Thread(this);
        gameThread.start();
    }

    // This will be our main method which is running the Play mode screen
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // 1 Update: update information
                update();
                // 2 Paint: paint or draw screen with the updated information
                repaint();
                System.out.println(game.getPlayer().getGridX() + " " + game.getPlayer().getGridY());
                delta--;
            }
        }
    }

    public void update() {
        game.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        game.getGrid().draw(g2, offsetX * tileSize, offsetY * tileSize);
        game.getPlayer().draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        int textX = (offsetX + gridColumns) * tileSize + 10; // Position to the right of the grid
        int textY = offsetY * tileSize + 20; // Align with the top of the grid

        if (timeController.getTimeLeft() > 0) {
            // Draw the text "Time:" and the remaining time on a new line
            g2.drawString("Time:", textX, textY);

            int timeX = textX; // Same X position as "Time:"
            int timeY = textY + 30; // Position below "Time:"
            g2.drawString(timeController.getTimeLeft() + " seconds", timeX, timeY);
        } else {
            // Draw "Game Over!" message centered on the screen
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            FontMetrics fm = g2.getFontMetrics();
            String gameOverText = "Game Over!";
            int gameOverX = (screenWidth - fm.stringWidth(gameOverText)) / 2;
            int gameOverY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(gameOverText, gameOverX, gameOverY);
        }

        g2.dispose();
    }

    // Getter functions for scale and tileSize
    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }
}