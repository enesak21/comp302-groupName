package domain.UI.screenPanels;

import domain.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class loadStartScreenPanel extends JPanel {
    public loadStartScreenPanel(UI ui) {
        setSize(400, 300);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to My Game", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = new JButton("Start New Game");
        JButton loadGameButton = new JButton("Load Game");

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(newGameButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(loadGameButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        newGameButton.addActionListener(e -> ui.showPanel("Build"));

        loadGameButton.addActionListener(e -> loadGame(ui));

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                title.setFont(new Font("Arial", Font.BOLD, Math.max(24, getWidth() / 20)));
            }
        });
    }

    private void loadGame(UI ui) {
//        String fileName = "gameState.dat";
//        GameState loadedState = SaveLoadManager.loadGameState(fileName);
//        if (loadedState != null) {
//            System.out.println("Loaded game: " + loadedState);
//            ui.showPanel("Build");
//            JOptionPane.showMessageDialog(null, "Game loaded: " + loadedState);
//        } else {
//            JOptionPane.showMessageDialog(null, "No saved game found!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
    }
}
