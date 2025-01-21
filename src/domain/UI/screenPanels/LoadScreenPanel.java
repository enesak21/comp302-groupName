package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;
import domain.config.GameState;
import domain.config.SaveLoad;

import javax.swing.*;
import java.awt.*;

public class LoadScreenPanel extends JPanel {

    public LoadScreenPanel(UI ui) {
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
            slotButton.addActionListener(e -> loadGame(ui, slotNumber));
            add(slotButton);
        }
    }

    private void loadGame(UI ui, int slotNumber) {
        GameState loadedState = SaveLoad.loadGameState(slotNumber);
        if (loadedState != null) {
            ui.createLoadedGame(loadedState);
            ui.showPanel("LoadedGame");
            AudioManager.playPlayModeMusic();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
