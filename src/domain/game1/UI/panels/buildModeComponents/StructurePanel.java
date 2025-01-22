package domain.game1.UI.panels.buildModeComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;

public class StructurePanel extends JPanel {

    private final HashMap<String, String> structureMap;
    private String selectedStructure;

    public StructurePanel(HashMap<String, String> structureMap) {
        this.structureMap = structureMap;
        initializePanel();
    }

    private void initializePanel() {
        // (Optional) This gives an initial size but won't block resizing
        setPreferredSize(new Dimension(200, 700));

        // Use a GridLayout: 5 rows, 2 columns, 10px horizontal/vertical gap
        setLayout(new GridLayout(7, 2, 0, 0));

        // Set a background color in case no image or partial coverage
        setBackground(new Color(66, 40, 53));

        // Create buttons in the order you want them to appear in the grid
        // We'll list out the special items (eraser & dice) plus the domain.game1.main structures

        // 1. Put your domain.game1.main structure keys in a list, in the order you want
        java.util.List<String> keys = new ArrayList<>();
        keys.add("chest");
        keys.add("column");
        keys.add("ladder");
        keys.add("doubleBox");
        keys.add("singleBox");
        keys.add("skull");
        keys.add("tomb");
        keys.add("bottle");
        // We'll also include "eraser" and "dice" in this sequence
        keys.add("eraser");
        keys.add("dice");

        // 2. To skip first row, add empty JLabels
        add(new JLabel());
        add(new JLabel());

        // 3. For each key, determine the icon path
        //    - Some come from structureMap, some are special icons
        for (String key : keys) {
            String iconPath;
            if ("eraser".equals(key)) {
                iconPath = "src/resources/icons/eraser.png";
            } else if ("dice".equals(key)) {
                iconPath = "src/resources/icons/dice.png";
            } else {
                // Otherwise, use the structureMap for the image
                iconPath = structureMap.get(key);
            }
            // 3. Create the button and add it to the panel
            JButton button = createStructureButton(key, iconPath);
            add(button);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw custom background, scaled to panel size
        Image bgImage = new ImageIcon("src/resources/structures/Buildmodechest.png").getImage();
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }

    private JButton createStructureButton(String key, String iconPath) {
        // Safely handle potential null or missing path
        if (iconPath == null) {
            // fallback icon or skip
            iconPath = "src/resources/icons/defaultIcon.png";
        }
        Image img = new ImageIcon(iconPath).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        JButton button = new JButton(new ImageIcon(img));
        button.setToolTipText(key);
        // Style for transparency if desired
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // When clicked, update the selectedStructure
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
