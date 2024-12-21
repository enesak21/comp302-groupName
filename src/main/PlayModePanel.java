package main;

import domain.UI.GridView;
import domain.UI.PlayerView;
import domain.entity.playerObjects.Player;
import domain.game.Game;
import domain.game.CollisionChecker;
import domain.game.Grid;
import domain.game.TimeController;

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
    private PlayerView playerView;
    private GridView gridView;

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
        playerView = new PlayerView(player);

        game = new Game(player, tileSize, this); // Pass the required arguments
        this.addKeyListener(player.getPlayerController());

        // Initialize the grid
        Grid grid = new Grid(tileSize, this);
        gridView = new GridView(grid);

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
        if (!isPaused) {
            // Oyuncunun durumunu güncelle
            game.getPlayer().update();

            // Zaman bitti mi kontrol et
            if (timeController.getTimeLeft() <= 0) {
                isPaused = true;
                handleGameOver(); // Oyun bitişini işlemek için ayrı bir metot
            }
        }
    }

    private void handleGameOver() {
        System.out.println("Game Over! Time's up.");
        // Burada oyun bitiş ekranına geçebilir veya başka bir işlem yapabilirsiniz
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Grid ve Player View Çizimi
        drawGridAndPlayerView(g2);

        // Zamanı Çiz
        drawTime(g2);

        // Oyun Bitti Mesajını Çiz
        if (timeController.getTimeLeft() <= 0) {
            drawGameOverMessage(g2);
        }

        // Duraklatma Menüsünü Çiz
        if (isPaused) {
            drawPauseOverlay(g2);
        }

        g2.dispose();
    }

    private void drawGridAndPlayerView(Graphics2D g2) {
        gridView.draw(g2, offsetX * tileSize, offsetY * tileSize); // Grid'i View ile çiz
        playerView.draw(g2); // Player'ı View ile çiz
    }

    private void drawTime(Graphics2D g2) {
        g2.setFont(pressStart2PFont.deriveFont(15f));
        g2.setColor(Color.WHITE);

        int textX = (offsetX + gridColumns) * tileSize + 10; // Gridin sağında konum
        int textY = offsetY * tileSize + 20; // Gridin üst kısmıyla hizalı

        if (timeController.getTimeLeft() > 0) {
            g2.drawString("Time:", textX, textY);
            g2.drawString(timeController.getTimeLeft() + " seconds", textX, textY + 30);
        }
    }

    private void drawGameOverMessage(Graphics2D g2) {
        g2.setFont(pressStart2PFont.deriveFont(40f));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        String gameOverText = "Game Over!";
        int gameOverX = (screenWidth - fm.stringWidth(gameOverText)) / 2;
        int gameOverY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(gameOverText, gameOverX, gameOverY);
    }

    private void drawPauseOverlay(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150)); // Yarı şeffaf arka plan
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(pressStart2PFont.deriveFont(15f));

        // Duraklatma mesajını çiz
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


    // Getter functions for scale and tileSize
    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }
}