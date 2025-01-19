package domain.UI.panels;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import domain.UI.panels.buildModeComponents.NavigationPanel;
import domain.game.Hall;
import domain.game.HallOfAir;
import domain.game.HallOfEarth;
import domain.game.HallOfFire;
import domain.game.HallOfWater;
import domain.handlers.BuildModeHandler;
import domain.UI.panels.buildModeComponents.StructurePanel;

public class BuildModePanel extends JPanel {

    // Grid constants
    private static final int GRID_SIZE = 16; // 16×16 grid
    private static final int CELL_SIZE = 32; // each cell in the grid

    // Data & state
    private final List<Hall> halls = new ArrayList<>();
    private int currentGridIndex = 0;
    private final HashMap<String, String> structureMap;
    private Font pressStart2PFont;
    private BuildModeHandler buildModeHandler;

    // Child components
    private GridPanel gridPanel;
    private StructurePanel structurePanel;
    private NavigationPanel navigationPanel;
    private JLabel hallNameLabel;

    // Split pane that divides the grid panel (left) and structure panel (right)
    private JSplitPane splitPane;

    // (Optional) background image
    private Image backgroundImage;

    public BuildModePanel() {
        // 1) Load custom font (optional)
        loadCustomFont();

        // 2) Initialize halls
        initializeHalls();

        // 3) Initialize the structure -> image map
        structureMap = initializeStructureMap();

        // 4) (Optional) load a background image
        loadBackgroundImage("src/resources/buildModeBackground.png");

        // 5) Overall layout
        setLayout(new BorderLayout());

        // Provide a default panel size if desired
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE + 150, GRID_SIZE * CELL_SIZE + 100));

        // 6) Build child UI components
        initComponents();
    }

    private void loadCustomFont() {
        try {
            pressStart2PFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    new File("src/resources/fonts/PressStart2P-Regular.ttf")
            ).deriveFont(24f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStart2PFont);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // fallback
            pressStart2PFont = new Font("Serif", Font.PLAIN, 24);
        }
    }

    private void initializeHalls() {
        halls.add(new HallOfEarth());
        halls.add(new HallOfAir());
        halls.add(new HallOfWater());
        halls.add(new HallOfFire());
        buildModeHandler = new BuildModeHandler(halls);
    }

    private HashMap<String, String> initializeStructureMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("chest",      "src/resources/structures/chest.png");
        map.put("column",     "src/resources/structures/column.png");
        map.put("ladder",     "src/resources/structures/ladder.png");
        map.put("doubleBox",  "src/resources/structures/doubleBox.png");
        map.put("singleBox",  "src/resources/structures/singleBox.png");
        map.put("skull",      "src/resources/structures/skull.png");
        map.put("tomb",       "src/resources/structures/tomb.png");
        map.put("bottle",     "src/resources/structures/bottle.png");
        return map;
    }

    private void loadBackgroundImage(String path) {
        File bgFile = new File(path);
        if (!bgFile.exists()) {
            System.err.println("Background image not found: " + path);
        } else {
            ImageIcon icon = new ImageIcon(path);
            backgroundImage = icon.getImage();
        }
    }

    /**
     * Sets up the UI components (label at top, nav at bottom, split pane in center).
     */
    private void initComponents() {
        // Panel background color (backup if image doesn't cover fully)
        setBackground(new Color(66, 40, 53));

        // -- TOP (NORTH): Hall name label
        hallNameLabel = new JLabel(halls.get(currentGridIndex).getName(), SwingConstants.CENTER);
        hallNameLabel.setForeground(Color.WHITE);
        hallNameLabel.setFont(pressStart2PFont);
        add(hallNameLabel, BorderLayout.NORTH);

        // -- CENTER (split pane): gridPanel (left) + structurePanel (right)
        gridPanel = new GridPanel(halls.get(currentGridIndex), structureMap, this);

        // The structure panel that we now want to be resizable in width
        structurePanel = new StructurePanel(structureMap);

        // Set the background color for the split pane and divider
        UIManager.put("SplitPane.background", new Color(66, 40, 53));
        UIManager.put("SplitPaneDivider.background", new Color(66, 40, 53));


        // Create a JSplitPane for horizontal split
        splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                gridPanel,       // left side
                structurePanel   // right side
        );
        splitPane.setOneTouchExpandable(true);
        // 0.8 means 80% of extra space goes to the left panel
        splitPane.setResizeWeight(0.8);

        // Set the divider size
        splitPane.setDividerSize(5);

        // Add the split pane to the center
        add(splitPane, BorderLayout.CENTER);

        // -- BOTTOM (SOUTH): Navigation panel
        navigationPanel = new NavigationPanel(
                () -> switchGrid(-1),  // previous
                () -> switchGrid(1),   // next
                this::checkCurrentHall // check
        );
        add(navigationPanel, BorderLayout.SOUTH);
    }

    /**
     * Paint the background (scaled).
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Switch which hall (grid) is visible
    private void switchGrid(int direction) {
        currentGridIndex = (currentGridIndex + direction + halls.size()) % halls.size();
        gridPanel.setHall(halls.get(currentGridIndex));
        hallNameLabel.setText(halls.get(currentGridIndex).getName());
    }

    private void checkCurrentHall() {
        int remainingStructures = buildModeHandler.checkHall(halls.get(currentGridIndex));
        if (remainingStructures != 0) {
            ImageIcon warningIcon = new ImageIcon(
                    new ImageIcon("src/resources/structures/chest.png")
                            .getImage()
                            .getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            );
            JOptionPane.showMessageDialog(
                    this,
                    "You need to place " + remainingStructures +
                            " more structures to complete this hall.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE,
                    warningIcon
            );
        } else {
            ImageIcon successIcon = new ImageIcon(
                    new ImageIcon("src/resources/structures/key.png")
                            .getImage()
                            .getScaledInstance(32, 32, Image.SCALE_SMOOTH)
            );
            JOptionPane.showMessageDialog(
                    this,
                    "You have successfully completed this hall.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE,
                    successIcon
            );
        }
    }

    // The grid (GridPanel) queries which structure is selected
    // (Dice places a random structure, eraser removes, etc.)
    public String getSelectedStructure() {
        if ("dice".equals(structurePanel.getSelectedStructure())) {
            placeRandomStructure();
            return null;
        } else {
            return structurePanel.getSelectedStructure();
        }
    }

    public void placeRandomStructure() {
        halls.get(currentGridIndex).placeRandomStructures();
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public BuildModeHandler getBuildModeHandler() {
        return buildModeHandler;
    }
}
