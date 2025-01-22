package domain.game1.UI.panels.sideBarComponents;

import domain.game1.UI.panels.PlayModePanel;
import domain.game1.audio.AudioManager;

import javax.swing.*;
import java.awt.*;

/**
 * A dedicated panel for the top buttons: "Stop/Resume", "Exit", and "Save".
 */
public class TopButtonPanel extends JPanel {

    private JButton stopResumeButton;
    private JButton exitButton;
    private JButton volumeButton;
    private JButton saveButton;

    // Icons for the buttons
    private ImageIcon stopIcon;
    private ImageIcon resumeIcon;
    private ImageIcon exitIcon;
    private ImageIcon volumeOnIcon;
    private ImageIcon volumeOffIcon;
    private ImageIcon volumeMidIcon;
    private ImageIcon volumeLowIcon;
    private ImageIcon backgroundIcon;
    private ImageIcon saveIcon;

    // State variable: true = game is stopped/paused, false = game is running
    private boolean isStopped = false;
    private enum VolumeState {HIGH, MID, LOW, OFF}
    private VolumeState volumeState = VolumeState.HIGH;

    private PlayModePanel playModePanel;

    public TopButtonPanel(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        // Use a BoxLayout for vertical alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add empty glue components to center the buttons vertically
        add(Box.createVerticalGlue());

        loadIcons();
        initComponents();

        // Add another glue to push everything towards the center
        add(Box.createVerticalGlue());

        // Add a resize listener for dynamic scaling
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustButtonSizes();
            }
        });
    }

    /**
     * Loads the icons for stop/resume, exit, save, and background.
     */
    private void loadIcons() {
        stopIcon = new ImageIcon("src/resources/buttons/pause.png");
        resumeIcon = new ImageIcon("src/resources/buttons/resume.png");
        exitIcon = new ImageIcon("src/resources/buttons/exit.png");
        backgroundIcon = new ImageIcon("src/resources/buttons/Background_cobbleStone.png");
        volumeOnIcon = new ImageIcon("src/resources/buttons/volumeHigh.png");
        volumeMidIcon = new ImageIcon("src/resources/buttons/volumeMid.png");
        volumeLowIcon = new ImageIcon("src/resources/buttons/volumeLow.png");
        volumeOffIcon = new ImageIcon("src/resources/buttons/volumeOff.png");
        saveIcon = new ImageIcon("src/resources/buttons/save.png");
    }

    /**
     * Initializes the buttons and their actions.
     */
    /**
     * Initializes the buttons and their actions.
     */
    private void initComponents() {
        stopResumeButton = createButton(stopIcon);
        exitButton = createButton(exitIcon);
        volumeButton = createButton(volumeOnIcon);
        saveButton = createButton(saveIcon);
        saveButton.setVisible(false); // Initially hidden

        // Create a sub-panel to hold buttons in a row
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 1 row, 4 columns, 10px horizontal gap
        buttonPanel.setOpaque(false); // Ensure transparency for background painting

        // Add all buttons to the panel in a row
        buttonPanel.add(stopResumeButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(volumeButton);
        buttonPanel.add(saveButton); // Add save button to the rightmost position

        // Add the button panel to the domain.game1.main panel
        add(buttonPanel);

        // Stop/Resume action
        stopResumeButton.addActionListener(e -> {
            isStopped = !isStopped;
            stopResumeButton.setIcon(isStopped ? resumeIcon : stopIcon);
            saveButton.setVisible(isStopped); // Show/Hide save button based on game state
            if (isStopped) {
                playModePanel.pauseGame();
                playModePanel.setPaused(true);
            } else {
                playModePanel.resumeGame();
                playModePanel.setPaused(false);
            }
        });

        // Exit action
        exitButton.addActionListener(e -> playModePanel.exitGame());

        // Volume action
        volumeButton.addActionListener(e -> {
            switch (volumeState) {
                case HIGH:
                    volumeState = VolumeState.MID;
                    volumeButton.setIcon(volumeMidIcon);
                    AudioManager.setPlayModeVolume(2);
                    break;
                case MID:
                    volumeState = VolumeState.LOW;
                    volumeButton.setIcon(volumeLowIcon);
                    AudioManager.setPlayModeVolume(1);
                    break;
                case LOW:
                    volumeState = VolumeState.OFF;
                    volumeButton.setIcon(volumeOffIcon);
                    AudioManager.setPlayModeVolume(0);
                    AudioManager.stopPlayModeMusic();
                    break;
                case OFF:
                    volumeState = VolumeState.HIGH;
                    volumeButton.setIcon(volumeOnIcon);
                    AudioManager.setPlayModeVolume(3);
                    AudioManager.playPlayModeMusic();
                    break;
            }
        });

        // Save action
        saveButton.addActionListener(e -> playModePanel.saveGame()); // Implement saveGame method in PlayModePanel
    }


    /**
     * Creates a button with the given icon.
     * @param icon The icon for the button.
     * @return Configured JButton.
     */
    private JButton createButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFocusable(false);
        return button;
    }

    /**
     * Adjusts the button sizes dynamically based on panel size.
     */
    private void adjustButtonSizes() {
        int panelHeight = getHeight();

        // Set button size to a fraction of panel height, with a minimum and maximum constraint
        int buttonSize = Math.min(Math.max(panelHeight, 24), 28); // Between 24px and 40px

        stopResumeButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        exitButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        volumeButton.setPreferredSize(new Dimension(buttonSize, buttonSize));
        saveButton.setPreferredSize(new Dimension(buttonSize, buttonSize));

        scaleIcon(stopIcon, buttonSize);
        scaleIcon(resumeIcon, buttonSize);
        scaleIcon(exitIcon, buttonSize);
        scaleIcon(volumeOnIcon, buttonSize);
        scaleIcon(volumeMidIcon, buttonSize);
        scaleIcon(volumeLowIcon, buttonSize);
        scaleIcon(volumeOffIcon, buttonSize);
        scaleIcon(saveIcon, buttonSize);

        stopResumeButton.setIcon(isStopped ? resumeIcon : stopIcon);
        exitButton.setIcon(exitIcon);
        switch (volumeState) {
            case HIGH:
                volumeButton.setIcon(volumeOnIcon);
                break;
            case MID:
                volumeButton.setIcon(volumeMidIcon);
                break;
            case LOW:
                volumeButton.setIcon(volumeLowIcon);
                break;
            case OFF:
                volumeButton.setIcon(volumeOffIcon);
                break;
        }

        revalidate();
        repaint();
    }

    /**
     * Scales an icon to the specified size.
     * @param icon The icon to scale.
     * @param size The size to scale to.
     */
    private void scaleIcon(ImageIcon icon, int size) {
        if (icon != null && icon.getImage() != null) {
            Image scaledImage = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            icon.setImage(scaledImage);
        }
    }

    /**
     * Paints the background image dynamically scaled to fill the panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundIcon != null && backgroundIcon.getImage() != null) {
            g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(66, 40, 53));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
