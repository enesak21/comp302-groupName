package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;
import domain.config.GameConfig;
import domain.handlers.BuildModeHandler;
import domain.UI.panels.BuildModePanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BuildModeScreenPanel extends JPanel {
    private UI ui;
    private BuildModePanel buildModePanel;

    // Optional: background image (like HomeScreenPanel)
    private Image backgroundImage;

    public BuildModeScreenPanel(UI ui) {
        this.ui = ui;

        // 1. (Optional) Load a background image if desired
        loadBackgroundImage("src/resources/buildModeBackground.png");
        // ^ Adjust path/file name as needed

        // 2. Initialize all UI components
        initComponents();
    }

    /**
     * Initialize components: layout, build mode panel, complete button, etc.
     */
    public void initComponents() {
        // Use BorderLayout, which adapts nicely to resizes
        this.setLayout(new BorderLayout());

        // The main build mode area in the CENTER
        buildModePanel = new BuildModePanel();
        BuildModeHandler buildModeHandler = buildModePanel.getBuildModeHandler();
        this.add(buildModePanel, BorderLayout.CENTER);

        // The "Complete Build" button at the bottom (SOUTH)
        JButton completeButton = new JButton("Complete Build");
        styleButton(completeButton);
        completeButton.addActionListener(e -> {
            if (buildModeHandler.checkBuildMode()) {
                ui.showPanel("Game");
                AudioManager.playPlayModeMusic();
            } else {
                ImageIcon warningIcon = new ImageIcon(
                        new ImageIcon("src/resources/structures/skull.png")
                                .getImage()
                                .getScaledInstance(64, 64, Image.SCALE_SMOOTH)
                );
                JOptionPane.showMessageDialog(
                        null,
                        "Please complete all halls before proceeding.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE,
                        warningIcon
                );
            }
        });

        // A panel to hold the Complete button (at the bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(66, 40, 53));
        buttonPanel.add(completeButton);

        // Place this button panel at the bottom (SOUTH)
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * (Optional) Loads a background image from a file.
     */
    private void loadBackgroundImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        backgroundImage = icon.getImage();
    }

    /**
     * Overridden to paint a scaled background image, then the child components.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 1. If you want a colored background behind the image, draw a color fill first
        // g.setColor(Color.RED);
        // g.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw the background image scaled to the panel size (if it’s not null)
        if (backgroundImage != null) {
            g.drawImage(
                    backgroundImage,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }

    /**
     * Helper method to style buttons (font, transparency, color, etc.).
     */
    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(GameConfig.loadLOTRFont().deriveFont(Font.BOLD, 30f));     // LOTR font, 30 pt
    }


    public BuildModePanel getBuildModePanel() {
        return buildModePanel;
    }
}
