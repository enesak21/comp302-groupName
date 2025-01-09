package domain.panels;

import domain.UI.ArrowAnimationView;
import domain.UI.GridView;
import domain.UI.PlayerView;
import domain.UI.MonsterView;


import domain.enchantments.*;
import domain.handlers.*;
import domain.entity.Entity;

import domain.entity.monsters.*;

import domain.entity.playerObjects.Player;
import domain.game.*;
import domain.handlers.mouseHandlers.PlayModeMouseListener;
import domain.panels.sideBarComponents.HeartsLeftPanel;
import domain.panels.sideBarComponents.InventoryPanel;
import domain.panels.sideBarComponents.TimeLeftPanel;
import domain.game.SearchRuneController;
import main.PlayerInputHandler;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
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

    private static final int gridTopLeftX = offsetX * tileSize;
    private static final int gridTopLeftY = offsetY * tileSize;

    int sidebarWidth = 4 * tileSize + 20; // Sidebar width
    int sidebarX = screenWidth - sidebarWidth - (tileSize + 10) + 20;
    int sidebarY = offsetY * tileSize;
    int arcWidth = 30; // Rounded corner width
    int arcHeight = 30; // Rounded corner height


    private TimeController timeController;
    private Grid grid;
    private Font pressStart2PFont;
    private boolean isPaused = false; // Add a boolean to track game state
    private PlayerView playerView;
    private GridView gridView;

    //ENCHANTMENT MANAGER
    public EnchantmentManager enchantmentManager;

    private MonsterManager monsterManager;
    private int countMonster = 0;
    private CopyOnWriteArrayList<MonsterView> monsterViewList = new CopyOnWriteArrayList<>();


    // Declare the halls variable
    private List<Hall> halls;
    private int hallNum = 0;
    private boolean lastRunefound = false;
    private boolean inTransition = false;

    private GameOverHandler gameOverHandler;
    private GameWinningHandler gameWinningHandler;

    //WALL PART
    private Image leftWall, rightWall, topWall, bottomWall;
    private Image topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner;
    private Image openedWall, closedWall;
    private boolean[][] wallGrid;
    private SearchRuneController searchRuneController;
    private Image cloakSmallIcon;

    //FLAG IMAGES
    private Image hallOfAirFlag, hallOfWaterFlag, hallOfEarthFlag, hallOfFireFlag;
    private PlayModeMouseListener playModeMouseListener;
    private String state = "Default";

    //Inventory image
    private Image inventoryMainImage;
    private Image revealSmallIcon;

    //ARROW ANIMATION
    private List<ArrowAnimationView> arrowAnimations = new ArrayList<>();


    int FPS = 60;
    Thread gameThread;
    Game game;
    Rune rune;
    Graphics2D g2;

    private SidebarPanel sidebarPanel;



    // Constructor
    public PlayModePanel(List<Hall> halls) {
        this.halls = halls; // Initialize the halls variable
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(66, 40, 53));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.rune = new Rune();

        loadFont();
        addPauseKeyListener();

        //KEYLISTENER FOR REVEAL WILL BE REMOVED
        addKeyListenerForUseEnchantments();

        gameWinningHandler = new GameWinningHandler(this);
        gameOverHandler = new GameOverHandler(this);

    }

    public void initializeGameComponents(int hallNum) {
        this.setState("Default");
        isPaused = false;

        Player player = Player.getInstance("Osimhen", 0, 0, tileSize, this, new PlayerInputHandler());
        playerView = new PlayerView(player);

        // Initialize the grid
        grid = halls.get(hallNum).toGrid(tileSize);

        // place The Rune

        searchRuneController = new SearchRuneController(this);
        searchRuneController.placeRune();


        game = new Game(player, tileSize, grid, searchRuneController);
        enchantmentManager = new EnchantmentManager(game, tileSize);
        monsterManager = game.getMonsterManager();

        timeController = game.getTimeController();

        // Set the time based on the number of structures placed
        // timeController.setTimeLeft(halls.get(hallNum).getPlacedStructuresCount() * 5);

        timeController.setTimeLeft(60); // Set the time to 60 seconds for testing purposes


        this.addKeyListener(player.getPlayerInputHandler());
        countMonster = 0;
        monsterViewList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < monsterManager.getMonsters().size(); i++) {
            MonsterView monsterView = new MonsterView((Entity) monsterManager.getMonsters().get(i));
            monsterViewList.add(monsterView);
        }

        this.addKeyListener(player.getPlayerInputHandler());


        gridView = new GridView(grid);
        CollisionChecker collisionChecker = new CollisionChecker(grid);
        player.setCollisionChecker(collisionChecker);
        monsterManager.setCollisionChecker(collisionChecker);

        timeController = game.getTimeController();

        // Create a mouse listener for the Play Mode screen

        if (this.getMouseListeners().length > 0) {
            this.removeMouseListener(this.getMouseListeners()[0]);
        }
        playModeMouseListener = new PlayModeMouseListener(this);
        this.addMouseListener(playModeMouseListener);

        gameWinningHandler = new GameWinningHandler(this);
        gameOverHandler = new GameOverHandler(this);

        // Initialize the heart
        ((HeartsLeftPanel) sidebarPanel.getHeartsLeftPanel()).updateHeartsLeft(game.getPlayer().getHealth());
        ((HeartsLeftPanel) sidebarPanel.getHeartsLeftPanel()).initHearts();
        ((TimeLeftPanel) sidebarPanel.getTimeLeftPanel()).updateTimeLeft(timeController.getTimeLeft());

        for (Map.Entry<String, Integer> entry: game.getPlayer().getInventory().getContent().entrySet()) {
            ((InventoryPanel) sidebarPanel.getInventoryPanel()).setItem(entry.getKey(), entry.getValue());
        }
    }

    //REVEAL KEY HANDLER WILL BE OUT LATER
    private boolean bPressed = false;
    private void addKeyListenerForUseEnchantments() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_R:
                        if (game.getPlayer().getInventory().isInInventory("Reveal")) {
                            game.getPlayer().useRevealEnchantment();

                        } else {
                            System.out.println("No Reveal enchantment in inventory.");
                        }
                        break;
                    case KeyEvent.VK_P:
                        if (game.getPlayer().getInventory().isInInventory("Cloak of Protection")) {
                            game.getPlayer().useCloakOfProtectionEnchantment();
                        } else {
                            System.out.println("No Cloak of Protection enchantment in inventory.");
                        }
                        break;
                    case KeyEvent.VK_B:
                        if (game.getPlayer().getInventory().isInInventory("Luring Gem")) {
                            bPressed = true;
                        } else {
                            System.out.println("No Luring enchantment in inventory.");
                        }
                        break;
                    case KeyEvent.VK_W:
                        if (bPressed) {
                            game.getPlayer().useLuringGemEnchantment(0);
                            bPressed = false;
                        }
                    case KeyEvent.VK_S:
                        if (bPressed) {
                            game.getPlayer().useLuringGemEnchantment(1);
                            bPressed = false;
                        }
                    case KeyEvent.VK_A:
                        if (bPressed) {
                            game.getPlayer().useLuringGemEnchantment(2);
                            bPressed = false;
                        }
                    case KeyEvent.VK_D:
                        if (bPressed) {
                            game.getPlayer().useLuringGemEnchantment(3);
                            bPressed = false;
                        }
                    case KeyEvent.VK_Q:

                }
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (game.getPlayer().getInventory().isInInventory("Speed Up")) {
                        game.getPlayer().useSpeedUpManagement();
                    } else {
                        System.out.println("no Speed Up enchantment in inventory.");
                    }
                }
            }
        });
    }


    public void moveToNextHall() {
        hallNum++; // Move to the next hall
        if (hallNum < halls.size()) {
            transitionToNextHall();
        } else {
            lastRunefound = true;
        }
    }

    private void transitionToNextHall() {
        if (hallNum > 0 ) {
            inTransition = true; // Set the transition flag
            repaint(); // Trigger the transition screen to render
        }

        // Pause briefly to show the transition screen
        if (!this.state.equals("TryAgain")) {
            Timer timer = new Timer(2000, e -> {
                inTransition = false; // Exit transition mode
                initializeGameComponents(hallNum); // Initialize the next hall // Move to the next hall
                repaint(); // Refresh the UI to show the new hall
            });

            timer.setRepeats(false); // Ensure the timer runs only once
            timer.start();
        } else {
            inTransition = false;
            initializeGameComponents(hallNum);
            repaint();
        }
    }

    // Add an arrow animation to the list
    public void addArrowAnimation(ArrowAnimationView animation) {
        arrowAnimations.add(animation);
    }

    private void addPauseKeyListener() {

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        game.pauseGame();

                        timeController.pauseTimer();
                    } else {
                        game.resumeGame();
                        timeController.resumeTimer();

                    }
                    repaint();
                }
            }
        });
    }


    public void pauseGame() {

        isPaused = true; // Set the game state to paused
        timeController.pauseTimer(); // Pause the game timer (if applicable)
        repaint(); // Trigger a repaint to show the pause overlay
    }

    public void resumeGame(){
        isPaused = false;
        timeController.resumeTimer();
        repaint();
    }

    public void exitGame() {
        System.exit(0);
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
        gameThread = new Thread(this);
        gameThread.start();
    }

    // This will be our main method which is running the Play mode screen
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (gameThread != null) {
            double drawInterval = 1000000000 / FPS;
            if (System.nanoTime() - startTime > drawInterval) {
                update();
                repaint();
                startTime = System.nanoTime();
            }
        }
    }

    public void update() {
        if (!isPaused) {
            // Update the player
            game.getPlayer().update();

            //Update monsters
            monsterManager.updateMonsters();
            // Update the arrow animations
            for (ArrowAnimationView animation : arrowAnimations) {
                animation.update();
            }
            arrowAnimations.removeIf(ArrowAnimationView::isFinished);

            enchantmentManager.updateEnchantments();
            //Update monsters view list if there is a new monster
            if (countMonster < monsterManager.getMonsters().size()) {
                for (int i = countMonster; i < monsterManager.getMonsters().size(); i++) {
                    MonsterView monsterView = new MonsterView((Entity) monsterManager.getMonsters().get(i));
                    monsterViewList.add(monsterView);
                }
                countMonster = monsterManager.getMonsters().size();
            }

            // Update the player's inventory
            for (Map.Entry<String, Integer> entry: game.getPlayer().getInventory().getContent().entrySet()) {
                ((InventoryPanel) sidebarPanel.getInventoryPanel()).setItem(entry.getKey(), entry.getValue());
            }

        }
    }

    public void restartGame() {
        // Reset to the first hall
        hallNum = -1;
        lastRunefound = false;
        moveToNextHall();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;

        // Draw game grid and player
        gridView.draw(g2, offsetX * tileSize, offsetY * tileSize);

        playerView.draw(g2);
        drawWallsAndCorners(g2);
        // drawInventory(g2);  //check the location

        //Draw monsters
        //monsterManager'daki her monsterı çek ve onlar için bire View classı oluştur
        for(MonsterView monsterView: monsterViewList){
            monsterView.draw(g2);
        }

        //ENCHANTMENT IS PAINTED HERE
        enchantmentManager.drawEnchantments(g2);


        gridView.drawStructures(g2, offsetX * tileSize, offsetY * tileSize);
        //FOR HIGHLETED REGION
        drawHighlightedRegion(g2);

        if (inTransition) {
            drawTransitionScreen(g2);
        } else {
            if (isPaused) {
                // Draw Pause Overlay only if the game is not over
                drawPauseOverlay(g2);
            }

            // Check if Time is over
            if (timeController.getTimeLeft() <= 0) {
                isPaused = true;
                gameOverHandler.handle();
            } else if (lastRunefound) {
                gameWinningHandler.handle();
            }
        }

        // Draw Time (always display the sidebar)
        ((TimeLeftPanel) sidebarPanel.getTimeLeftPanel()).updateTimeLeft(timeController.getTimeLeft());
        // Draw Hearts Left
        ((HeartsLeftPanel) sidebarPanel.getHeartsLeftPanel()).updateHeartsLeft(game.getPlayer().getHealth());

        //Draw the arrow animations
        if (!arrowAnimations.isEmpty()) {
            for (ArrowAnimationView animation : arrowAnimations) {
                animation.draw(g2);
            }
        }

        g2.dispose();
    }

    private void drawTransitionScreen(Graphics2D g2) {
        // Draw a semi-transparent dark overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw transition message
        g2.setFont(pressStart2PFont.deriveFont(20f));
        g2.setColor(Color.WHITE);

        // Split text into two lines
        String line1 = "You Found the Rune...";
        String line2 = "Moving to the Next Hall...";

        FontMetrics fm = g2.getFontMetrics();

        // Calculate positions for the first line
        int textX1 = (getWidth() - fm.stringWidth(line1)) / 2;
        int textY1 = (getHeight() - fm.getHeight()) / 2;

        // Calculate positions for the second line
        int textX2 = (getWidth() - fm.stringWidth(line2)) / 2;
        int textY2 = textY1 + fm.getHeight() + 20; // Add spacing between lines

        // Draw the two lines
        g2.drawString(line1, textX1, textY1);
        g2.drawString(line2, textX2, textY2);
    }

    private void drawPauseOverlay(Graphics2D g2) {
        if (timeController.getTimeLeft() > 0) {
            g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(Color.WHITE);
            g2.setFont(pressStart2PFont.deriveFont(15f));

            // Draw pause message in the middle
            String pauseText = "Game Paused";
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(pauseText)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2;
            g2.drawString(pauseText, x, y);

            String resumeText = "Press 'ESC' to Resume";
            x = (getWidth() - fm.stringWidth(resumeText)) / 2;
            y += fm.getHeight() + 20;
            g2.drawString(resumeText, x, y);
        }
    }

    private void loadFlagImages(){
        try{
            hallOfAirFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfAir flag.png"));
            hallOfEarthFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfEarth flag.png"));
            hallOfWaterFlag = ImageIO.read(getClass().getResource("/resources/flags/hallOfFire flag.png"));
            hallOfFireFlag = ImageIO.read(getClass().getResource("/resources/flags/hallofWater flag.png"));

        }
        catch (IOException e){
            e.printStackTrace();
            System.err.println("Error loading wall images. Please check the file paths.");
        }
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
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void drawHallFlagNearDoor(Graphics2D g2, int doorX, int doorY) {
        Image flag = null;
        loadFlagImages();
        // Determine the flag based on the current hall
        switch (hallNum) {
            case 0:
                flag = hallOfAirFlag;
                break;
            case 1:
                flag = hallOfWaterFlag;
                break;
            case 2:
                flag = hallOfEarthFlag;
                break;
            case 3:
                flag = hallOfFireFlag;
                break;
            default:
                break;
        }

        // Draw the flag near the door
        if (flag != null) {
            int flagX = doorX-250; // Position the flag to the right of the door
            int flagY = doorY+17 ; // Position the flag above the door
            g2.drawImage(flag, flagX, flagY,20,16, null); // Adjust size as needed
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

        int doorX = rightX - 150;
        int doorY = bottomY - 12;


        // Draw the flag near the door
        drawHallFlagNearDoor(g2, doorX, doorY);
        //g2.setColor(Color.RED);
        //g2.drawRect(doorX, doorY, closedWall.getWidth(null), closedWall.getHeight(null)); //CAN BE RECTANGLE DRAWER FOR REVEAL
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



    private void drawHighlightedRegion(Graphics2D g2) {
        for (int x = 0; x < grid.getColumns(); x++) {
            for (int y = 0; y < grid.getRows(); y++) {
                Tile tile = grid.getTileAt(x, y);
                if (tile.isHighlighted()) {
                    int drawX = (offsetX + x) * tileSize;
                    int drawY = (offsetY + y) * tileSize;

                    // Draw a semi-transparent rectangle for highlighting
                    g2.setColor(new Color(255, 255, 0, 128)); // Yellow with transparency
                    g2.fillRect(drawX, drawY, tileSize, tileSize);

                    // Optional: Draw a border for better visibility
                    g2.setColor(Color.YELLOW);
                    g2.drawRect(drawX, drawY, tileSize, tileSize);
                }
            }
        }
    }


    // Getters
    public int getScale() {
        return scale;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public void setGame(Game game) {
        this.game = game;
        this.playerView= new PlayerView(game.getPlayer());
    }

    public void setPaused(Boolean b){
        this.isPaused = b;
    }


    public int getTopLeftCornerX() {
        return gridTopLeftX;
    }

    public boolean getIsPaused(){return isPaused;}

    public int getTopLeftCornerY() {
        return gridTopLeftY;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getSidebarWidth() {
        return sidebarWidth;
    }

    public int getSidebarX() {
        return sidebarX;
    }

    public int getSidebarY() {
        return sidebarY;
    }

    public int getSidebarHeight() {
        return gridHeight;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public int getGridRows() {
        return gridRows;
    }

    public Grid getGrid() {
        return grid;
    }

    public Game getGame() {
        return game;
    }

    public Rune getRune() {
        return rune;
    }

    public Font getFont() {
        return pressStart2PFont;
    }

    public TimeController getTimeController() {
        return timeController;
    }

    public Graphics2D getGraphics2() {
        return g2;
    }


    public int getScreenHeight() {
        return screenHeight;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public GameOverHandler getGameOverHandler() {
        return gameOverHandler;
    }

    public void setSidebarPanel(SidebarPanel sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    public EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }


}