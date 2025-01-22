package domain.UI.screenPanels;

import domain.UI.UI;
import domain.config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class loadStartScreenPanel extends JPanel {
    private Image backgroundImage;

    public loadStartScreenPanel(UI ui) {
        // Load the background image
        backgroundImage = new ImageIcon("src/resources/backgrounds/saveLoadBackground.png").getImage();

        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the button panel transparent
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = new JButton("Start New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton backButton = new JButton("Go Back");

        styleButton(newGameButton);
        styleButton(loadGameButton);
        styleButton(backButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(newGameButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(loadGameButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(backButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        newGameButton.addActionListener(e -> ui.showPanel("Build"));
        loadGameButton.addActionListener(e -> ui.showPanel("Load"));
        backButton.addActionListener(e -> ui.showPanel("Home"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Scale background image to fit the panel
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(GameConfig.loadLOTRFont().deriveFont(Font.BOLD, 28f));     // LOTR font, 30 pt
    }
}
