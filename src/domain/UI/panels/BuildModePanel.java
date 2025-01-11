package domain.UI.panels;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import domain.UI.panels.buildModeComponents.NavigationPanel;
import domain.game.Hall;
import domain.game.HallOfAir;
import domain.game.HallOfEarth;
import domain.game.HallOfFire;
import domain.game.HallOfWater;
import domain.handlers.BuildModeHandler;
import domain.UI.panels.buildModeComponents.StructurePanel;

import java.util.ArrayList;
import java.util.List;


public class BuildModePanel extends JPanel {

    private static final int GRID_SIZE = 16; // Grid size
    private static final int CELL_SIZE = 32; // Original cell size

    private final List<Hall> halls = new ArrayList<>();
    private int currentGridIndex = 0;
    private final GridPanel gridPanel;
    private final HashMap<String, String> structureMap;
    private String selectedStructure = null;
    private final JLabel hallNameLabel;
    private Font pressStart2PFont;
    private BuildModeHandler buildModeHandler;
    private StructurePanel structurePanel;
    private NavigationPanel navigationPanel;


    public BuildModePanel() {
        try {
            pressStart2PFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/PressStart2P-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStart2PFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        initializeHalls();
        structureMap = initializeStructureMap();


        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE + 150, GRID_SIZE * CELL_SIZE + 100));
        this.setBackground(new Color(66, 40, 53));

        // Create the hall name label dynamically
        hallNameLabel = new JLabel(halls.get(currentGridIndex).getName(), SwingConstants.CENTER);
        hallNameLabel.setFont(pressStart2PFont);
        hallNameLabel.setForeground(Color.WHITE);
        add(hallNameLabel, BorderLayout.NORTH);

        // Create the grid panel for drawing
        gridPanel = new GridPanel(halls.get(currentGridIndex), structureMap, this);
        add(gridPanel, BorderLayout.CENTER);

        NavigationPanel navigationPanel = new NavigationPanel(
                () -> switchGrid(-1), // Previous action
                () -> switchGrid(1),  // Next action
                this::checkCurrentHall // Check action
        );
        add(navigationPanel, BorderLayout.SOUTH);

        structurePanel = new StructurePanel(structureMap);
        add(structurePanel, BorderLayout.EAST);

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
        map.put("chest", "src/resources/structures/chest.png");
        map.put("column", "src/resources/structures/column.png");
        map.put("ladder", "src/resources/structures/ladder.png");
        map.put("doubleBox", "src/resources/structures/doubleBox.png");
        map.put("singleBox", "src/resources/structures/singleBox.png");
        map.put("skull", "src/resources/structures/skull.png");
        map.put("tomb", "src/resources/structures/tomb.png");
        map.put("bottle", "src/resources/structures/bottle.png");
        return map;
    }

    private void switchGrid(int direction) {
        currentGridIndex = (currentGridIndex + direction + halls.size()) % halls.size();
        gridPanel.setHall(halls.get(currentGridIndex)); // Update the grid panel's hall
        hallNameLabel.setText(halls.get(currentGridIndex).getName()); // Update the hall name
    }

    private void checkCurrentHall() {
        int remainingStructures = buildModeHandler.checkHall(halls.get(currentGridIndex));
        if (remainingStructures != 0) {
            ImageIcon warningIcon = new ImageIcon(new ImageIcon("src/resources/structures/chest.png")
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(this,
                    "You need to place " + remainingStructures + " more structures to complete this hall.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE, warningIcon);
        } else {
            ImageIcon successIcon = new ImageIcon(new ImageIcon("src/resources/structures/key.png")
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(this,
                    "You have successfully completed this hall.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE, successIcon);
        }
    }

    public String getSelectedStructure() {
        return structurePanel.getSelectedStructure();
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public BuildModeHandler getBuildModeHandler() {
        return buildModeHandler;
    }
}