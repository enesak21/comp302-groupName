package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;
import domain.config.GameState;
import domain.config.SaveLoad;

import javax.swing.*;
import java.awt.*;

public class SaveScreenPanel extends JPanel {

    public SaveScreenPanel(GameState gameState) {
        setLayout(new GridLayout(1, 3, 20, 20));
        setBackground(new Color(30, 30, 30)); // Dark gray background

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
            slotButton.addActionListener(e -> SaveLoad.saveGameState(gameState, slotNumber));
            add(slotButton);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
