package domain.UI;

import main.PlayModePanel;

import javax.swing.*;
import java.awt.*;

public class UI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public UI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("2D Game");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add only the home screen initially
        mainPanel.add(createHomeScreen(), "Home");

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Change if needed
    }

    public void show() {
        frame.setVisible(true);
        cardLayout.show(mainPanel, "Home");
    }
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createHomeScreen() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Background Image
        ImageIcon originalIcon = new ImageIcon("src/resources/Rokue-like logo 4.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 800, 600);

        // START BUTTON
        JButton startButton = new JButton("Start Game"); //GO TO BUİLD MODE DIRECTLY
        startButton.setBounds(300, 470, 200, 50);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFont(new Font("", Font.BOLD, 25));
        startButton.setForeground(Color.black);

        startButton.addActionListener(e -> {
            if (!isPanelAdded("Build")) {
                mainPanel.add(createBuildScreen(), "Build");
            }
            cardLayout.show(mainPanel, "Build");
        });

        //HELP SCREEN BUTTON
        JButton helpButton = new JButton("Help"); //GO TO BUİLD MODE DIRECTLY
        helpButton.setBounds(300, 510, 200, 50);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFont(new Font("", Font.BOLD, 25));
        helpButton.setForeground(Color.black);

        helpButton.addActionListener(e -> {
            if (!isPanelAdded("Help")) {
                mainPanel.add(createHelpScreen(), "Help");
            }
            cardLayout.show(mainPanel, "Help");
            mainPanel.revalidate();
            mainPanel.getComponent(1).requestFocusInWindow(); // Ensure HelpPanel gets focus
        });


        // Add Components to LayeredPane
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(startButton, Integer.valueOf(1)); // Foreground layer
        layeredPane.add(helpButton, Integer.valueOf(1)); // Foreground layer

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(layeredPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBuildScreen() {  // BUİLD SCREEN İÇİN DE PLAYMODEPANEL GİBİ AYRI BİR
                                          //CLASSINI OLUŞTURUP BURADA ÇAĞIRIRIZ
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Build Components Here", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JButton completeButton = new JButton("Complete Build");

        completeButton.addActionListener(e -> {
            if (!isPanelAdded("Game")) {
                mainPanel.add(createGameScreen(), "Game");
            }
            cardLayout.show(mainPanel, "Game");
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        panel.add(title, BorderLayout.CENTER);
        panel.add(completeButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createGameScreen() {
        PlayModePanel playModePanel = new PlayModePanel();

        playModePanel.startGameThread(); // Start the game loop
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Ensure focus is set
        return playModePanel;
    }
    private JPanel createHelpScreen() {
        return new HelpPanel(this); // Pass the current UI instance
    }

    private boolean isPanelAdded(String panelName) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(panelName)) {
                return true;
            }
        }
        return false;
    }
}
