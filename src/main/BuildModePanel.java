package main;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

import domain.game.Hall;
import domain.game.HallOfAir;
import domain.game.HallOfEarth;
import domain.game.HallOfFire;
import domain.game.HallOfWater;

import java.util.ArrayList;
import java.util.List;

public class BuildModePanel extends JPanel {

    private static final int GRID_SIZE = 16;
    private static final int CELL_SIZE = 32;

    private final List<Hall> halls = new ArrayList<>();
    private int currentGridIndex = 0;
    private final GridPanel gridPanel;
    private final HashMap<String, String> structureMap;
    private String selectedStructure = null;
    private final JLabel hallNameLabel;

    public BuildModePanel() {
        initializeHalls();
        structureMap = initializeStructureMap();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Create a dynamic hall name label (to replace "Build Components Here")
        hallNameLabel = new JLabel(halls.get(currentGridIndex).getName(), SwingConstants.CENTER);
        hallNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(hallNameLabel, BorderLayout.NORTH); // Place the hall name at the very top

        // Create the grid panel for drawing
        gridPanel = new GridPanel(halls.get(currentGridIndex), structureMap, CELL_SIZE, GRID_SIZE, this);
        add(gridPanel, BorderLayout.CENTER);

        // Create the combined action and navigation panel at the bottom
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Create the structure selection panel on the right
        JPanel structurePanel = createStructurePanel();
        add(structurePanel, BorderLayout.EAST);
    }

    private void initializeHalls() {
        halls.add(new HallOfEarth());
        halls.add(new HallOfAir());
        halls.add(new HallOfWater());
        halls.add(new HallOfFire());
    }

    private HashMap<String, String> initializeStructureMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("bottle", "src/resources/structures/bottle.png");
        map.put("chest", "src/resources/structures/chest.png");
        map.put("column", "src/resources/structures/column.png");
        map.put("doubleBox", "src/resources/structures/doubleBox.png");
        map.put("ladder", "src/resources/structures/ladder.png");
        map.put("singleBox", "src/resources/structures/singleBox.png");
        map.put("skull", "src/resources/structures/skull.png");
        map.put("tomb", "src/resources/structures/tomb.png");
        return map;
    }

    private JPanel createStructurePanel() {
        JPanel structurePanel = new JPanel();
        structurePanel.setLayout(new GridLayout(0, 1, 5, 5));
        structurePanel.setPreferredSize(new Dimension(150, 600));

        for (String key : structureMap.keySet()) {
            JButton button = new JButton(new ImageIcon(new ImageIcon(structureMap.get(key))
                    .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
            button.setToolTipText(key);
            button.addActionListener(e -> setSelectedStructure(key));
            structurePanel.add(button);
        }

        JButton eraserButton = new JButton("Eraser");
        eraserButton.setToolTipText("Erase structures from the grid");
        eraserButton.addActionListener(e -> setSelectedStructure("eraser"));
        structurePanel.add(eraserButton);

        return structurePanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Create navigation panel for Previous and Next Grid buttons
        JPanel navigationPanel = new JPanel(new FlowLayout());
        JButton prevGridButton = new JButton("Previous Grid");
        prevGridButton.addActionListener(e -> switchGrid(-1));
        JButton nextGridButton = new JButton("Next Grid");
        nextGridButton.addActionListener(e -> switchGrid(1));
        navigationPanel.add(prevGridButton);
        navigationPanel.add(nextGridButton);

        // Create action panel for the Check button
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> checkCurrentHall());
        actionPanel.add(checkButton);

        // Add the navigation and action panels to the bottom panel
        bottomPanel.add(navigationPanel, BorderLayout.NORTH);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private void checkCurrentHall() {
        Hall currentHall = halls.get(currentGridIndex);

        if (currentHall.isRequirementMet()) {
            JOptionPane.showMessageDialog(this, "The current hall has enough structures! Confirmed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentHall.setGrid(gridPanel.getCurrentGrid());
        } else {
            int remainingStructures = currentHall.getMinStructures() - currentHall.getPlacedStructuresCount();
            JOptionPane.showMessageDialog(this, "You need " + remainingStructures + " more structures.", "Requirement Not Met", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void switchGrid(int direction) {
        currentGridIndex = (currentGridIndex + direction + halls.size()) % halls.size();
        gridPanel.setHall(halls.get(currentGridIndex));
        hallNameLabel.setText(halls.get(currentGridIndex).getName()); // Dynamically update hall name
        gridPanel.repaint();
    }

    public void setSelectedStructure(String structureKey) {
        this.selectedStructure = structureKey;
    }

    public String getSelectedStructure() {
        return selectedStructure;
    }
}
