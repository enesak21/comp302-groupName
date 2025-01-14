package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HomeScreenPanel extends JPanel {
    private AudioManager audioManager;
    private UI ui;
    private Font lotrFont; // LOTR-themed font

    public HomeScreenPanel(UI ui) {
        this.ui = ui;
        loadLOTRFont(); // Load the LOTR-themed font
        initComponents(); // Initialize UI components
    }

    /**
     * Loads the custom "Ringbearer" (or any LOTR-themed) font and registers it.
     */
    private void loadLOTRFont() {
        try {
            // Ensure the font file exists in your resources/fonts folder
            File fontFile = new File("src/resources/fonts/Ringbearer.ttf");
            lotrFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(28f);

            // Register the font with the JVM
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lotrFont);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Fallback to a standard font if loading fails
            lotrFont = new Font("Serif", Font.PLAIN, 28);
        }
    }

    /**
     * Initializes the components and applies the LOTR font to buttons.
     */
    private void initComponents() {
        // Play background music
        AudioManager.playEnterMusic();

        // Set layout and create a layered pane for background and buttons
        this.setLayout(new BorderLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        // Background Image
        ImageIcon originalIcon = new ImageIcon("src/resources/Rokue-like logo 4.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 800, 600);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(247, 470, 250, 50);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setForeground(new Color(210, 180, 140)); // Gold-ish color
        startButton.setFont(lotrFont.deriveFont(Font.BOLD, 30f)); // Apply LOTR font

        // Help Button
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(271, 530, 200, 50);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setForeground(new Color(210, 180, 140)); // Gold-ish color
        helpButton.setFont(lotrFont.deriveFont(Font.BOLD, 30f)); // Apply LOTR font

        // Action Listeners for Buttons
        startButton.addActionListener(e -> ui.showPanel("Build")); // Navigate to Build mode
        startButton.addActionListener(e -> AudioManager.stopEnterMusic()); // Stop the background music
        helpButton.addActionListener(e -> ui.showPanel("Help"));  // Navigate to Help screen

        // Add components to the layered pane
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Background
        layeredPane.add(startButton, Integer.valueOf(1)); // Start Button
        layeredPane.add(helpButton, Integer.valueOf(1)); // Help Button

        // Add the layered pane to the panel
        this.add(layeredPane, BorderLayout.CENTER);
    }
}
