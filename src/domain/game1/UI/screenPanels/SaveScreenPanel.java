package domain.game1.UI.screenPanels;

import domain.game1.config.GameConfig;
import domain.game1.config.GameState;
import domain.game1.config.SaveLoad;

import javax.swing.*;
import java.awt.*;

public class SaveScreenPanel extends JPanel {
    private final Image backgroundImage;

    public SaveScreenPanel(GameState gameState) {
        // Load the background image
        backgroundImage = new ImageIcon("src/resources/backgrounds/saveBackground.png").getImage();

        setLayout(new BorderLayout());
        setOpaque(false); // Ensure transparency for the background image

        // Panel for save slots
        JPanel slotsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        slotsPanel.setOpaque(false);

        for (int i = 1; i <= 3; i++) {
            JButton slotButton = new JButton();

            // Remove default button appearance
            slotButton.setContentAreaFilled(false);
            slotButton.setBorderPainted(false);
            slotButton.setFocusPainted(false);

            // Load an image for the button
            ImageIcon icon = new ImageIcon("src/resources/buttons/slot" + i + ".png"); // Replace with your image paths
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            slotButton.setIcon(new ImageIcon(scaledImage));

            int slotNumber = i;
            slotButton.addActionListener(e -> {
                SaveLoad.saveGameState(gameState, slotNumber); // Only save here

            });
            slotsPanel.add(slotButton);
        }

        add(slotsPanel, BorderLayout.CENTER);

        // "Go Back to Game" button
        JButton goBackButton = new JButton("Go Back to Game");
        goBackButton.setContentAreaFilled(false);
        goBackButton.setBorderPainted(false);
        goBackButton.setFocusPainted(false);
        styleButton(goBackButton);
        goBackButton.addActionListener(e -> closeFrame());

        add(goBackButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(GameConfig.loadLOTRFont().deriveFont(Font.BOLD, 30f));     // LOTR font, 30 pt
    }

    private void closeFrame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose(); // Close the save screen frame
        }
    }
}
