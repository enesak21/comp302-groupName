package domain.UI.screenPanels;

import domain.UI.UI;
import domain.audio.AudioManager;
import domain.handlers.BuildModeHandler;
import domain.panels.BuildModePanel;

import javax.swing.*;
import java.awt.*;

public class BuildModeScreenPanel extends JPanel {
    AudioManager audioManager;
    UI ui;
    BuildModePanel buildModePanel;

    public BuildModeScreenPanel(UI ui) {
        // Code for initializing components
        this.audioManager = new AudioManager();
        this.ui = ui;
        initComponents();
    }

    public void initComponents() {
        // Code for initializing components

        this.setLayout(new BorderLayout());

        // Instantiate BuildModePanel, which includes the dynamic hall name
        buildModePanel = new BuildModePanel();
        BuildModeHandler buildModeHandler = buildModePanel.getBuildModeHandler();
        this.add(buildModePanel, BorderLayout.CENTER);

        // Complete Button
        JButton completeButton = new JButton("Complete Build");
        completeButton.addActionListener(e -> {
            if (buildModeHandler.checkBuildMode()) { // Check if the build mode is valid
                ui.showPanel("Game");
            }
            else { // Display a warning message if the build mode is not complete
                ImageIcon warningIcon = new ImageIcon(new ImageIcon("src/resources/structures/skull.png")
                        .getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(null, "Please complete all halls before proceeding.", "Warning",
                        JOptionPane.WARNING_MESSAGE, warningIcon);
            }
        });

        // Add the complete button to a panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(66, 40, 53));
        buttonPanel.add(completeButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public BuildModePanel getBuildModePanel() {
        return buildModePanel;
    }
}
