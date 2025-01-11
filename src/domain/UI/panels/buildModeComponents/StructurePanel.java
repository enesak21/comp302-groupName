package domain.UI.panels.buildModeComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StructurePanel extends JPanel {

    private final HashMap<String, String> structureMap;
    private String selectedStructure;

    public StructurePanel(HashMap<String, String> structureMap) {
        this.structureMap = structureMap;
        initializePanel();
    }

    private void initializePanel() {
        // Set absolute positioning and preferred size
        setLayout(null);
        setPreferredSize(new Dimension(200, 700));
        setBackground(new Color(66, 40, 53)); // Backup background if no image is drawn

        // Paint background with custom image
        repaint();

        // Define button positions
        HashMap<String, Point> buttonPositions = new HashMap<>();
        buttonPositions.put("chest", new Point(30, 60));   // Column 1
        buttonPositions.put("column", new Point(110, 60)); // Column 2
        buttonPositions.put("ladder", new Point(30, 140)); // Column 1
        buttonPositions.put("doubleBox", new Point(110, 140)); // Column 2
        buttonPositions.put("singleBox", new Point(30, 220)); // Column 1
        buttonPositions.put("skull", new Point(110, 220)); // Column 2
        buttonPositions.put("tomb", new Point(30, 300));   // Column 1
        buttonPositions.put("bottle", new Point(110, 300)); // Column 2

        // Create buttons and add them to the panel
        for (String key : structureMap.keySet()) {
            JButton button = createStructureButton(key, structureMap.get(key));
            Point position = buttonPositions.get(key);
            if (position != null) {
                button.setBounds(position.x, position.y, 40, 40);
            }
            add(button);
        }

        // Add eraser button
        JButton eraserButton = createStructureButton("eraser", "src/resources/icons/eraser.png");
        eraserButton.setBounds(30, 380, 40, 40); // Position below the other buttons
        add(eraserButton);

        // Add dice button
        JButton diceButton = createStructureButton("dice", "src/resources/icons/dice.png");
        diceButton.setBounds(110, 380, 40, 40); // Position below the other buttons
        add(diceButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw custom background
        Image bgImage = new ImageIcon("src/resources/structures/Buildmodechest.png").getImage();
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }

    private JButton createStructureButton(String key, String iconPath) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(iconPath)
                .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        button.setToolTipText(key);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        button.addActionListener(e -> setSelectedStructure(key));
        return button;
    }

    public String getSelectedStructure() {
        return selectedStructure;
    }

    private void setSelectedStructure(String structureKey) {
        this.selectedStructure = structureKey;
    }
}
