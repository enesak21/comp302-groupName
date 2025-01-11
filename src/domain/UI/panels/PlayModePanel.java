package domain.UI.panels;

import domain.UI.ArrowAnimationView;
import domain.UI.GridView;
import domain.UI.PlayerView;
import domain.UI.MonsterView;
import domain.UI.renderers.GameRenderer;
import domain.enchantments.*;
import domain.handlers.*;
import domain.entity.Entity;
import domain.entity.monsters.*;
import domain.entity.playerObjects.Player;
import domain.game.*;
import domain.handlers.mouseHandlers.PlayModeMouseListener;
import domain.UI.panels.sideBarComponents.HeartsLeftPanel;
import domain.UI.panels.sideBarComponents.InventoryPanel;
import domain.UI.panels.sideBarComponents.TimeLeftPanel;
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

    private SearchRuneController searchRuneController;

    private PlayModeMouseListener playModeMouseListener;
    private String state = "Default";

    // Game renderer
    private GameRenderer gameRenderer;

    // Sidebar panel
    private SidebarPanel sidebarPanel;

    // Game variables
    int FPS = 60;
    Thread gameThread;
    Game game;
    Rune rune;
    Graphics2D g2;

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

        // Initialize game renderer
        gameRenderer = new GameRenderer(grid, player, monsterManager.getMonsters(), enchantmentManager);

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
        gameRenderer.addArrowAnimation(animation);
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

            // Update the game renderer
            gameRenderer.update();

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

        gameRenderer.render(g2, offsetX, offsetY, tileSize);

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