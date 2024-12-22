package main;

import domain.UI.GridView;
import domain.UI.MonsterView;
import domain.UI.PlayerView;
import domain.entity.Entity;
import domain.entity.monsters.ArcherMonster;
import domain.entity.monsters.FighterMonster;
import domain.entity.monsters.WizardMonster;
import domain.entity.playerObjects.Player;
import domain.game.*;
import domain.structures.Structure;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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

    // Declare the halls variable
    private List<Hall> halls;

    //WALL PART
    private Image leftWall, rightWall, topWall, bottomWall;
    private Image topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
    private Image openedWall, closedWall;
    private boolean[][] wallGrid;

    //Cemal test. Bunlar sonradan otomatik oluşturulacak. Şimdilik dokunmayın
    private MonsterView archerView;
    private MonsterView fighterView;
    private MonsterView wizardView;

    int FPS = 60;
    Thread gameThread;
    Game game;
    Rune rune;

    // Constructor
    public PlayModePanel(List<Hall> halls) {
        this.halls = halls; // Initialize the halls variable
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(66, 40, 53));
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        initializeGameComponents();
        addPauseKeyListener();
        loadFont();

        //***TEST***
        //Initialize 3 monsters
        ArcherMonster archerMonster = new ArcherMonster(2, 5, tileSize);
        archerView = new MonsterView((Entity) archerMonster);

        FighterMonster fighterMonster = new FighterMonster(5, 8, tileSize);
        fighterView = new MonsterView((Entity) fighterMonster);

        WizardMonster wizardMonster = new WizardMonster(10, 8, tileSize);
        wizardView = new MonsterView((Entity) wizardMonster);
        //End of the test

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int sidebarWidth = 4 * tileSize + 20; // Sidebar width
                int sidebarX = screenWidth - sidebarWidth - (tileSize + 10) + 20;

                int buttonWidth = 48; // Button width
                int buttonHeight = 48; // Button height
                int buttonPadding = 10; // Spacing between buttons
                int buttonX1 = sidebarX + 10; // Pause button position
                int buttonX2 = buttonX1 + buttonWidth + buttonPadding; // Exit button position
                int buttonY = offsetY * tileSize + 10; // Top margin for both buttons

                // Check if Pause Button is clicked
                if (mouseX >= buttonX1 && mouseX <= buttonX1 + buttonWidth &&
                        mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
                    System.out.println("Pause Button Clicked");
                    isPaused = !isPaused; // Toggle pause state
                    if (isPaused) {
                        timeController.pauseTimer();
                    } else {
                        timeController.resumeTimer();
                    }
                    repaint();
                }

                // Check if Exit Button is clicked
                if (mouseX >= buttonX2 && mouseX <= buttonX2 + buttonWidth &&
                        mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
                    System.out.println("Exit Button Clicked");
                    System.exit(0); // Exit game
                }
            }
        });

    }

    private void initializeGameComponents() {
        Player player = new Player("Osimhen", 0, 0, tileSize, this, new PlayerController());
        playerView = new PlayerView(player);
        // Initialize the grid
        grid = halls.get(0).toGrid(tileSize);
        game = new Game(player, tileSize, this, grid);
        placeRune();

        this.addKeyListener(player.getPlayerController());

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isPaused) {
                    handleMouseClick(e);
                }
            }
        });

        gridView = new GridView(grid);
        CollisionChecker collisionChecker = new CollisionChecker(grid);
        player.setCollisionChecker(collisionChecker);
        timeController = new TimeController();
    }

    /**
     * Handles mouse click events on the game grid.
     *
     * @param e the MouseEvent triggered by the mouse click
     *
     * This method calculates the grid coordinates based on the mouse click position.
     * If the click is within the bounds of the grid, it identifies the clicked tile
     * and checks if it is near the player's current position. If the clicked tile
     * contains a structure, it further checks if the structure has a rune and collects
     * the rune if it does.
     */
    private void handleMouseClick(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int gridX = (mouseX / tileSize) - offsetX;
        int gridY = (mouseY / tileSize) - offsetY;

        if (gridX >= 0 && gridX < gridColumns && gridY >= 0 && gridY < gridRows) {
            System.out.println("Clicked on grid: " + gridX + ", " + gridY);
            Tile clickedTile = grid.getTileAt(gridX, gridY);
            Structure clickedStructure = clickedTile.getStructure();
            Tile playerTile = grid.getTileAt(game.getPlayer().getGridX(), game.getPlayer().getGridY());
            if (Game.isInRange(clickedTile, playerTile, 1)) {
                if (clickedStructure != null) {
                    if (clickedStructure.hasRune()) {
                        System.out.println("Rune collected!");
                    }
                }
            }
        }
    }

    /**
     * Place the rune in a random structure in the grid
     */
    public void placeRune() {
        List <Structure> structures = grid.getStructures();
        System.out.println(structures);
        if (structures != null && !structures.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(structures.size());
            Structure structure = structures.get(randomIndex);
            rune = new Rune(structure);
        }
    }

    private void addPauseKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        timeController.pauseTimer();
                    } else {
                        timeController.resumeTimer();
                    }
                    repaint();
                }
            }
        });
    }

    private void loadFont() {
        try {
            pressStart2PFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/PressStart2P-Regular.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStart2PFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        initializeWalls();
        loadWallImages();
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
            // Update the player
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


        // Draw Time (always display the sidebar)
        drawTime(g2);

        // Draw game grid and player
        gridView.draw(g2, offsetX * tileSize, offsetY * tileSize);

        playerView.draw(g2);
        drawWallsAndCorners(g2);
        gridView.drawStructures(g2, offsetX * tileSize, offsetY * tileSize);

        //Draw monsters
        archerView.draw(g2);
        fighterView.draw(g2);
        wizardView.draw(g2);

        // Draw Game Over Message
        if (timeController.getTimeLeft() <= 0) {
            drawGameOverMessage(g2);
        } else if (isPaused) {
            // Draw Pause Overlay only if the game is not over
            drawPauseOverlay(g2);
        }


        g2.dispose();
    }

    private void drawTime(Graphics2D g2) {
        g2.setFont(pressStart2PFont.deriveFont(15f));
        g2.setColor(Color.WHITE);

        int sidebarWidth = 4 * tileSize + 20; // Sidebar width
        int sidebarX = screenWidth - sidebarWidth - (tileSize + 10) + 20;
        int sidebarY = offsetY * tileSize;
        int arcWidth = 30; // Rounded corner width
        int arcHeight = 30; // Rounded corner height

        // Sidebar Background
        g2.setColor(new Color(108, 85, 89));
        g2.fillRoundRect(sidebarX, sidebarY, sidebarWidth, gridHeight, arcWidth, arcHeight);

        // Load custom button images
        try {
            Image pauseButtonImage = new ImageIcon("src/resources/buttons/pause.png").getImage();
            Image exitButtonImage = new ImageIcon("src/resources/buttons/exit.png").getImage();

            int buttonWidth = 48; // Smaller button width
            int buttonHeight = 48; // Smaller button height
            int buttonPadding = 10; // Spacing between buttons
            int buttonX1 = sidebarX + 10; // First button position
            int buttonX2 = buttonX1 + buttonWidth + buttonPadding; // Second button position
            int buttonY = sidebarY + 10; // Top margin for both buttons

            // Draw Pause Button
            g2.drawImage(pauseButtonImage, buttonX1, buttonY, buttonWidth, buttonHeight, null);

            // Draw Exit Button
            g2.drawImage(exitButtonImage, buttonX2, buttonY, buttonWidth, buttonHeight, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add Time section
        try {
            Image clockIcon = ImageIO.read(getClass().getResource("/resources/icons/clock.png"));
            g2.drawImage(clockIcon, sidebarX + 10, sidebarY + 80, 24, 24, null); // Adjust icon position
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.setFont(pressStart2PFont.deriveFont(13f));
        g2.setColor(Color.WHITE);
        g2.drawString("Time:", sidebarX + 40, sidebarY + 95);
        g2.drawString(timeController.getTimeLeft() + " seconds", sidebarX + 5, sidebarY + 125);

        // Add health hearts
        try {
            Image heartIcon = ImageIO.read(getClass().getResource("/resources/player/heart.png"));
            for (int i = 0; i < game.getPlayer().getHealth(); i++) {
                g2.drawImage(heartIcon, sidebarX + 5 + i * 32, sidebarY + 150, 32, 32, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawGameOverMessage(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, screenWidth, screenHeight);

        // Draw the game over message
        g2.setFont(pressStart2PFont.deriveFont(40f));
        g2.setColor(Color.RED); // Change the color to red

        FontMetrics fm = g2.getFontMetrics();
        String gameOverText = "Game Over!";
        int gameOverX = (screenWidth - fm.stringWidth(gameOverText)) / 2;
        int gameOverY = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(gameOverText, gameOverX, gameOverY);
    }

    private void drawPauseOverlay(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(pressStart2PFont.deriveFont(15f));

        // Draw pause message in the middle
        String pauseText = "Game Paused";
        FontMetrics fm = g2.getFontMetrics();
        int x = (screenWidth - fm.stringWidth(pauseText)) / 2;
        int y = (screenHeight - fm.getHeight()) / 2;
        g2.drawString(pauseText, x, y);

        String resumeText = "Press 'ESC' to Resume";
        x = (screenWidth - fm.stringWidth(resumeText)) / 2;
        y += fm.getHeight() + 20;
        g2.drawString(resumeText, x, y);
    }

    private void loadWallImages() {
        try {
            leftWall = ImageIO.read(getClass().getResource("/resources/Walls/leftWall.png"));
            rightWall = ImageIO.read(getClass().getResource("/resources/Walls/rightWall2.png"));
            topWall = ImageIO.read(getClass().getResource("/resources/Walls/frontWall.png"));
            bottomWall = ImageIO.read(getClass().getResource("/resources/Walls/frontWall.png"));
            topLeftCorner = ImageIO.read(getClass().getResource("/resources/Walls/topLeftCorner.png"));
            topRightCorner = ImageIO.read(getClass().getResource("/resources/Walls/topRightCorner.png"));
            bottomLeftCorner = ImageIO.read(getClass().getResource("/resources/Walls/BottomLeftCorner.png"));
            bottomRightCorner = ImageIO.read(getClass().getResource("/resources/Walls/BottomRightCorner.png"));

            openedWall = ImageIO.read(getClass().getResource("/resources/Walls/doorOpened.png"));
            closedWall = ImageIO.read(getClass().getResource("/resources/Walls/doorClosed.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading wall images. Please check the file paths.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("Image file not found. Please ensure the file exists at the specified path.");
        }
    }

    private void drawWallsAndCorners(Graphics2D g2) {
        int leftX = offsetX * tileSize - leftWall.getWidth(null); // Adjust X for left wall
        int rightX = (offsetX + gridColumns) * tileSize;         // Adjust X for right wall
        int topY = offsetY * tileSize - topWall.getHeight(null); // Adjust Y for top wall
        int bottomY = (offsetY + gridRows) * tileSize;           // Adjust Y for bottom wall

        // Draw top and bottom walls
        for (int col = 0; col < gridColumns; col++) {
            int x = (offsetX + col) * tileSize;
            g2.drawImage(topWall, x, topY, tileSize, topWall.getHeight(null), null); // Draw with fixed width
            g2.drawImage(bottomWall, x, bottomY, tileSize, bottomWall.getHeight(null), null); // Draw with fixed width
        }

        // Draw left and right walls
        for (int row = 0; row < gridRows; row++) {
            int y = (offsetY + row) * tileSize;

            g2.drawImage(leftWall, leftX, y, leftWall.getWidth(null), tileSize, null); // Draw with fixed height
            g2.drawImage(rightWall, rightX, y, rightWall.getWidth(null), tileSize, null); // Draw with fixed height
        }

        // Draw corners
        g2.drawImage(topLeftCorner, leftX, topY, null);                  // Top-left corner

        g2.drawImage(topRightCorner, rightX-11 , topY, null);     // Top-right corner (adjusted)
        g2.drawImage(bottomLeftCorner, leftX, bottomY, null);            // Bottom-left corner
        g2.drawImage(bottomRightCorner, rightX-11, bottomY, null); // Bottom-right corner (adjusted)
        //DOOR OPENED OR CLOSED HERE
        g2.drawImage(closedWall, rightX-150, bottomY-12, null);
    }

    private void initializeWalls() {
        wallGrid = new boolean[gridColumns][gridRows];

        // Create a border of walls around the grid
        for (int col = 0; col < gridColumns; col++) {
            wallGrid[col][0] = true; // Top border
            wallGrid[col][gridRows - 1] = true; // Bottom border
        }
        for (int row = 0; row < gridRows; row++) {
            wallGrid[0][row] = true; // Left border
            wallGrid[gridColumns - 1][row] = true; // Right border
        }
    }

    // Getter functions for scale and tileSize
    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setGame(Game game) {
        this.game = game;
        this.playerView= new PlayerView(game.getPlayer());
    }
}