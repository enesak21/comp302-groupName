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

    private static final int GRID_SIZE = 16; // Grid size
    private static final int CELL_SIZE = 32; // Original cell size restored

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
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE + 150, GRID_SIZE * CELL_SIZE + 100));

        // Create the hall name label dynamically
        hallNameLabel = new JLabel(halls.get(currentGridIndex).getName(), SwingConstants.CENTER);
        hallNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
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
        halls.add(new HallOfAir());
        halls.add(new HallOfEarth());
        halls.add(new HallOfWater());
        halls.add(new HallOfFire());// Added Hall of Water
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

    private JPanel createNavigationPanel() {
        JPanel navigationPanel = new JPanel(new FlowLayout());

        // Previous Grid Button
        JButton prevGridButton = new JButton("Previous Grid");
        prevGridButton.addActionListener(e -> switchGrid(-1));

        // Next Grid Button
        JButton nextGridButton = new JButton("Next Grid");
        nextGridButton.addActionListener(e -> switchGrid(1));

        // Check Button
        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> checkCurrentHall());

        navigationPanel.add(prevGridButton);
        navigationPanel.add(nextGridButton);
        navigationPanel.add(checkButton);

        return navigationPanel;
    }

    private JPanel createStructurePanel() {
        JPanel structurePanel = new JPanel();
        structurePanel.setLayout(new GridLayout(0, 1, 5, 5));
        structurePanel.setPreferredSize(new Dimension(150, GRID_SIZE * CELL_SIZE));

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
}
