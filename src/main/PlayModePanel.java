package main;

import domain.entity.playerObjects.Player;
import domain.game.Game;
import domain.game.CollisionChecker;
import domain.game.Grid;
import domain.game.TimeController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;


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
    private Grid grid;
    private Font pressStart2PFont;
    private boolean isPaused = false; // Add a boolean to track game state

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

        // Initialize the grid
        Grid grid = new Grid(tileSize);
        CollisionChecker collisionChecker = new CollisionChecker(grid);
        player.setCollisionChecker(collisionChecker);

        // Initialize the TimeController
        timeController = new TimeController();

        // Add a KeyListener to toggle pause
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Toggle with 'P' or 'Esc'
                    isPaused = !isPaused;
                    if (isPaused) {
                        timeController.pauseTimer();
                    } else {
                        timeController.resumeTimer();
                    }
                    repaint(); // Refresh screen to display/hide menu
                }
            }
        });

        // Load the PressStart2P font
        try {
            pressStart2PFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/PressStart2P-Regular.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStart2PFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
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

            if (!isPaused && delta >= 1) { // Skip updates when paused
                update();
                repaint();
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

        // Draw game grid and player
        game.getGrid().draw(g2, offsetX * tileSize, offsetY * tileSize);
        game.getPlayer().draw(g2);

        // Set the PressStart2P font
        g2.setFont(pressStart2PFont);
        g2.setColor(Color.WHITE);

        // Sidebar Background
        int sidebarWidth = 4 * tileSize + 20; // Make the sidebar slightly wider by 12 pixels
        int sidebarX = screenWidth - sidebarWidth - (tileSize + 10); // Adjust position accordingly
        int sidebarY = offsetY * tileSize; // Set Y position to offsetY * tileSize
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(sidebarX, sidebarY, sidebarWidth, gridHeight); // Set height to gridHeight

        // Add Time section
        try {
            Image clockIcon = ImageIO.read(getClass().getResource("/resources/icons/clock.png"));
            g2.drawImage(clockIcon, sidebarX + 10, sidebarY + 20, 24, 24, null); // Make the icon smaller
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.setFont(pressStart2PFont.deriveFont(13f));
        g2.setColor(Color.WHITE);
        g2.drawString("Time:", sidebarX + 40, sidebarY + 45); // Move the time text 10 pixels to the left
        g2.drawString(timeController.getTimeLeft() + " seconds", sidebarX + 5, sidebarY + 75); // Move the time left text 10 pixels to the left

        // Add health hearts
        try {
            Image heartIcon = ImageIO.read(getClass().getResource("/resources/player/heart.png"));
            for (int i = 0; i < game.getPlayer().getHealth(); i++) {
                g2.drawImage(heartIcon, sidebarX + 5 + i * 32, sidebarY + 100, 32, 32, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Draw the pause menu overlay if paused
        if (isPaused) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, screenWidth, screenHeight);

            g2.setColor(Color.WHITE);
            g2.setFont(pressStart2PFont.deriveFont(20f)); // Use PressStart2P font for Pause text
            String pauseText = "Game Paused";
            FontMetrics fm = g2.getFontMetrics();
            int x = (screenWidth - fm.stringWidth(pauseText)) / 2;
            int y = screenHeight / 2 - fm.getHeight();
            g2.drawString(pauseText, x, y);

            String resumeText = "Press 'ESC' to Resume";
            x = (screenWidth - fm.stringWidth(resumeText)) / 2;
            y += fm.getHeight() + 40;
            g2.drawString(resumeText, x, y);
        }

        // Draw the game over overlay if time is up
        if (timeController.getTimeLeft() <= 0) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, screenWidth, screenHeight);

            g2.setColor(Color.RED);
            g2.setFont(pressStart2PFont.deriveFont(40f)); // Use PressStart2P font for Game Over text
            String gameOverText = "Game Over";
            FontMetrics fm = g2.getFontMetrics();
            int x = (screenWidth - fm.stringWidth(gameOverText)) / 2;
            int y = screenHeight / 2 - fm.getHeight();
            g2.drawString(gameOverText, x, y);
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