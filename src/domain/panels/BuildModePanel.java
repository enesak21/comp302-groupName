package domain.panels;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import domain.game.Hall;
import domain.game.HallOfAir;
import domain.game.HallOfEarth;
import domain.game.HallOfFire;
import domain.game.HallOfWater;
import domain.handlers.BuildModeHandler;


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

        JPanel navigationPanel = createNavigationPanel();
        add(navigationPanel, BorderLayout.SOUTH);

        JPanel structurePanel = createStructurePanel();
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

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set a custom color for the bottom part
                g.setColor(new Color(66, 40, 53)); // Dark gray
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Previous Grid Button
        JButton prevGridButton = new JButton("Previous Grid");
        prevGridButton.addActionListener(e -> switchGrid(-1));

        // Next Grid Button
        JButton nextGridButton = new JButton("Next Grid");
        nextGridButton.addActionListener(e -> switchGrid(1));

        // Check Button
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> {
            int remainingStructures = buildModeHandler.checkHall(halls.get(currentGridIndex));
            if (remainingStructures != 0) {
                ImageIcon warningIcon = new ImageIcon(new ImageIcon("src/resources/structures/chest.png")
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(this,
                        "You need to place " + remainingStructures + " more structures to complete this hall.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE, warningIcon);
            }
            else {
                ImageIcon successIcon = new ImageIcon(new ImageIcon("src/resources/structures/key.png")
                        .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(this,
                        "You have successfully completed this hall.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE, successIcon);
            }
        });


        navigationPanel.add(prevGridButton);
        navigationPanel.add(nextGridButton);
        navigationPanel.add(checkButton);

        return navigationPanel;
    }

    private JPanel createStructurePanel() {
        JPanel structurePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the BuildMode background image
                Image bgImage = new ImageIcon("src/resources/structures/Buildmodechest.png").getImage();
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        structurePanel.setLayout(null); // Use absolute positioning
        structurePanel.setPreferredSize(new Dimension(200, 700)); // Increased width to accommodate two columns


        // Define button positions in two columns
        HashMap<String, Point> buttonPositions = new HashMap<>();
        buttonPositions.put("chest", new Point(30, 60));   // Column 1
        buttonPositions.put("column", new Point(110, 60)); // Column 2
        buttonPositions.put("ladder", new Point(30, 140)); // Column 1
        buttonPositions.put("doubleBox", new Point(110, 140)); // Column 2
        buttonPositions.put("singleBox", new Point(30, 220)); // Column 1
        buttonPositions.put("skull", new Point(110, 220)); // Column 2
        buttonPositions.put("tomb", new Point(30, 300));   // Column 1
        buttonPositions.put("bottle", new Point(110, 300)); // Column 2

        // Create and position buttons based on the defined layout
        for (String key : structureMap.keySet()) {
            JButton button = new JButton(new ImageIcon(new ImageIcon(structureMap.get(key))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH))); // Adjust button size
            button.setToolTipText(key);
            button.addActionListener(e -> setSelectedStructure(key));

            // Make the button transparent
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            // Set button position
            Point position = buttonPositions.get(key);
            button.setBounds(position.x, position.y, 40, 40); // Adjust button size and spacing
            structurePanel.add(button);
        }

        // Add Eraser Button with an icon
        JButton eraserButton = new JButton(new ImageIcon(new ImageIcon("src/resources/icons/eraser.png")
                .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH))); // Use eraser icon
        eraserButton.setToolTipText("Eraser");
        eraserButton.addActionListener(e -> setSelectedStructure("eraser"));

        // Make the eraser button transparent
        eraserButton.setOpaque(false);
        eraserButton.setContentAreaFilled(false);
        eraserButton.setBorderPainted(false);

        eraserButton.setBounds(70, 380, 40, 40); // Centered below the two columns
        structurePanel.add(eraserButton);

        return structurePanel;
    }

    private void switchGrid(int direction) {
        currentGridIndex = (currentGridIndex + direction + halls.size()) % halls.size();
        gridPanel.setHall(halls.get(currentGridIndex)); // Update the grid panel's hall
        hallNameLabel.setText(halls.get(currentGridIndex).getName()); // Update the hall name

    }

    private void checkCurrentHall() {
        Hall currentHall = halls.get(currentGridIndex);

        if (currentHall.isRequirementMet()) {
            JOptionPane.showMessageDialog(this, "The current hall has enough structures! Confirmed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentHall.setGrid(currentHall.getGrid()); // Confirm and store the grid
        } else {
            int remainingStructures = currentHall.getMinStructures() - currentHall.getPlacedStructuresCount();
            JOptionPane.showMessageDialog(this, "You need " + remainingStructures + " more structures.", "Requirement Not Met", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setSelectedStructure(String structureKey) {
        this.selectedStructure = structureKey;
    }

    public String getSelectedStructure() {
        return selectedStructure;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public BuildModeHandler getBuildModeHandler() {
        return buildModeHandler;
    }

}

