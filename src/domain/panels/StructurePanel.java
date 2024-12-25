package domain.panels;

import javax.swing.*;
import java.awt.*;

import java.util.HashMap;

public class StructurePanel extends JPanel {
    public StructurePanel(BuildModePanel buildModePanel) {
        setLayout(new GridLayout(0, 1, 10, 10)); // Single-column layout with vertical spacing
        setPreferredSize(new Dimension(200, 600)); // Set preferred width for the panel

        HashMap<String, String> iconPaths = new HashMap<>();
        iconPaths.put("bottle", "src/resources/structures/bottle.png");
        iconPaths.put("chest", "src/resources/structures/chest.png");
        iconPaths.put("column", "src/resources/structures/column.png");
        iconPaths.put("doubleBox", "src/resources/structures/doubleBox.png");
        iconPaths.put("ladder", "src/resources/structures/ladder.png");
        iconPaths.put("singleBox", "src/resources/structures/singleBox.png");
        iconPaths.put("skull", "src/resources/structures/skull.png");
        iconPaths.put("tomb", "src/resources/structures/tomb.png");

        for (String key : iconPaths.keySet()) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(180, 80)); // Button size adjustment
            button.setToolTipText(key); // Tooltip for better UX

            // Load and resize the icon to a smaller size
            ImageIcon originalIcon = new ImageIcon(iconPaths.get(key));
            Image resizedImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Modest size
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            button.setIcon(resizedIcon);

            // Add action listener to select structure
            button.addActionListener(e -> buildModePanel.setSelectedStructure(key));

            add(button);
        }
    }
}
