package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;

import javax.swing.*;
import java.awt.*;

public class HomeScreenPanel extends JPanel {
    AudioManager audioManager;
    UI ui;

    public HomeScreenPanel(UI ui) {
        this.audioManager = new AudioManager();
        this.ui = ui;
        initComponents();
    }

    private void initComponents() {
        // Code for initializing components

        audioManager.playEnterMusic(); // Play the background music

        // Create a layered pane to hold the background image and buttons
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600)); // Set the size of the layered pane

        // Background Image
        ImageIcon originalIcon = new ImageIcon("src/resources/Rokue-like logo 4.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // JLabel for the background image
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 800, 600);

        // Start Button
        JButton startButton = new JButton("Start Game"); //GO TO BUİLD MODE DIRECTLY
        startButton.setBounds(300, 470, 200, 50);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFont(new Font("", Font.BOLD, 25));
        startButton.setForeground(Color.black);

        // Add action listener to the start button
        startButton.addActionListener(e -> {
            ui.showPanel("Build");
        });

        // Help Screen Button
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(300, 510, 200, 50);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFont(new Font("", Font.BOLD, 25));
        helpButton.setForeground(Color.black);

        // Add action listener to the help button
        helpButton.addActionListener(e -> {
            ui.showPanel("Help");
        });

        // Add Components to LayeredPane
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(startButton, Integer.valueOf(1)); // Foreground layer
        layeredPane.add(helpButton, Integer.valueOf(1)); // Foreground layer

        this.add(layeredPane, BorderLayout.CENTER);
    }
}
