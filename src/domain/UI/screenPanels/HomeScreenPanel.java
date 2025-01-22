package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;
import domain.config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class HomeScreenPanel extends JPanel {
    private UI ui;
    private Font lotrFont;        // LOTR-themed font
    private Image backgroundImage; // Will be painted in paintComponent

    // Buttons declared at class-level so we can access them if needed
    private JButton startButton;
    private JButton helpButton;

    public HomeScreenPanel(UI ui) {
        this.ui = ui;

        // 1. Load the custom font
        this.lotrFont = GameConfig.loadLOTRFont();

        // 2. Initialize all UI components (buttons, layouts, etc.)
        initComponents();
    }


    /**
     * Sets up the layout, background image, and interactive buttons.
     */
    private void initComponents() {
        // 1. Play background music
        AudioManager.playEnterMusic();

        // 2. Load the background image once
        ImageIcon originalIcon = new ImageIcon("src/resources/backgrounds/mainMenuBackground.png");
        backgroundImage = originalIcon.getImage();


        // 3. Use BorderLayout for the domain.main panel
        setLayout(new BorderLayout());

        // 4. Create a sub-panel that will hold the buttons, using a BoxLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // let the background show through
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 5. Create and style the "Start Game" button
        startButton = new JButton("Start Game");
        styleButton(startButton);
        // Center alignment for BoxLayout
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 6. Add an ActionListener to move to LoadStart screen
        startButton.addActionListener(e -> {
            ui.showPanel("LoadStart");       // LoadStart
            AudioManager.stopEnterMusic();
        });

        // 7. Create and style the "Help" button
        helpButton = new JButton("Help");
        styleButton(helpButton);
        // Center alignment for BoxLayout
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 8. Add an ActionListener to show the Help screen
        helpButton.addActionListener(e -> ui.showPanel("Help"));

        // 9. Add the buttons to the button panel with vertical spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(30)); // space between buttons
        buttonPanel.add(helpButton);
        buttonPanel.add(Box.createVerticalGlue());

        // 10. Finally, add the button panel to the center of the domain.main panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper method to style buttons (font, transparency, color, etc.).
     */
    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(lotrFont.deriveFont(Font.BOLD, 30f));     // LOTR font, 30 pt
    }

    /**
     * Paints a scaled background image to fill the entire panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw a bright color so we can see if the panel is visible at all
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Now draw the background image in the panel’s current size
        if (backgroundImage != null) {
            // Option A: Draw at natural size
            // g.drawImage(background, 0, 0, this);

            // Option B: Scale to fill the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
