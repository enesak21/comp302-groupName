package domain.UI.screenPanels;

import domain.UI.UI;
import domain.config.GameConfig;
import domain.config.GameState;
import domain.config.SaveLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class loadStartScreenPanel extends JPanel {
    public loadStartScreenPanel(UI ui) {
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = new JButton("Start New Game");
        JButton loadGameButton = new JButton("Load Game");

        styleButton(newGameButton);
        styleButton(loadGameButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(newGameButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(loadGameButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        newGameButton.addActionListener(e -> ui.showPanel("Build"));

        loadGameButton.addActionListener(e -> loadGame(ui));
    }

    private void loadGame(UI ui) {
        GameState loadedState = SaveLoad.loadGameState();
        if (loadedState != null) {
            ui.createLoadedGame(loadedState.getHallsList(), loadedState.getHallNum());
            ui.showPanel("LoadedGame");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(GameConfig.loadLOTRFont().deriveFont(Font.BOLD, 30f));     // LOTR font, 30 pt
    }
}
