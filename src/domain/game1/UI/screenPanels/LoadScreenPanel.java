package domain.game1.UI.screenPanels;

import domain.game1.UI.UI;
import domain.game1.audio.AudioManager;
import domain.game1.config.GameConfig;
import domain.game1.config.GameState;
import domain.game1.config.SaveLoad;

import javax.swing.*;
import java.awt.*;

public class LoadScreenPanel extends JPanel {
    private final Image backgroundImage;

    public LoadScreenPanel(UI ui) {
        // Load the background image
        backgroundImage = new ImageIcon("src/resources/backgrounds/saveBackground.png").getImage();

        setLayout(new BorderLayout());
        setOpaque(false); // Ensure the background image is visible

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        buttonPanel.setOpaque(false);

        for (int i = 1; i <= 3; i++) {
            JButton slotButton = new JButton();

            // Remove default button appearance
            slotButton.setContentAreaFilled(false);
            slotButton.setBorderPainted(false);
            slotButton.setFocusPainted(false);

            // Load an image for the button
            ImageIcon icon = new ImageIcon("src/resources/buttons/slot" + i + ".png");
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            slotButton.setIcon(new ImageIcon(scaledImage));

            int slotNumber = i;
            slotButton.addActionListener(e -> loadGame(ui, slotNumber));
            buttonPanel.add(slotButton);
        }

        JButton backButton = new JButton("Back to Previous Menu");
        styleButton(backButton);
        backButton.addActionListener(e -> ui.showPanel("LoadStart"));

        add(buttonPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }

    private void loadGame(UI ui, int slotNumber) {
        GameState loadedState = SaveLoad.loadGameState(slotNumber);
        if (loadedState != null) {
            ui.createLoadedGame(loadedState);
            ui.showPanel("LoadedGame");
            AudioManager.playPlayModeMusic();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(GameConfig.loadLOTRFont().deriveFont(Font.BOLD, 30f));     // LOTR font, 30 pt
    }

}
