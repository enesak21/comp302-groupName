package domain.panels.sideBarComponents;

import domain.panels.PlayModePanel;

import javax.swing.*;
import java.awt.*;

/**
 * A dedicated panel for the top buttons: "Stop/Resume" and "Exit".
 */
public class TopButtonPanel extends JPanel {

    private JButton stopResumeButton;
    private JButton exitButton;

    // Icons for the buttons
    private ImageIcon stopIcon;
    private ImageIcon resumeIcon;
    private ImageIcon exitIcon;
    private ImageIcon backgroundIcon;

    // Button dimensions
    private static final int BUTTON_WIDTH = 40;
    private static final int BUTTON_HEIGHT = 40;

    // State variable: true = game is stopped/paused, false = game is running
    private boolean isStopped = false;

    private PlayModePanel playModePanel;

    public TopButtonPanel(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        // Layout for the two buttons
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBorder(BorderFactory.createLineBorder(new Color(101, 89, 93), 2));


        loadIcons();
        initComponents();
    }

    /**
     * Loads and scales the icons for pause/resume, exit, and background.
     */
    private void loadIcons() {
        stopIcon = new ImageIcon("src/resources/buttons/pause.png");
        resumeIcon = new ImageIcon("src/resources/buttons/resume.png");
        exitIcon = new ImageIcon("src/resources/buttons/exit.png");
        backgroundIcon = new ImageIcon("src/resources/buttons/Background_cobbleStone.png");

        // Scale the stop/resume icons
        Image tempStop = stopIcon.getImage()
                .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        stopIcon = new ImageIcon(tempStop);

        Image tempResume = resumeIcon.getImage()
                .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        resumeIcon = new ImageIcon(tempResume);

        // Scale the exit icon
        Image tempExit = exitIcon.getImage()
                .getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
        exitIcon = new ImageIcon(tempExit);
    }

    /**
     * Creates and configures the Stop/Resume and Exit buttons.
     */
    private void initComponents() {
        // If the game starts as "running", we show the 'stopIcon'
        stopResumeButton = new JButton(stopIcon);
        stopResumeButton.setOpaque(false);
        stopResumeButton.setContentAreaFilled(false);
        stopResumeButton.setBorderPainted(false);
        stopResumeButton.setFocusPainted(false);
        stopResumeButton.setFocusable(false);

        exitButton = new JButton(exitIcon);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setFocusable(false);

        // Add them to this panel
        add(stopResumeButton);
        add(exitButton);

        // Toggle pause/resume on click
        stopResumeButton.addActionListener(e -> {
            isStopped = !isStopped;
            if (isStopped) {
                stopResumeButton.setIcon(resumeIcon);
                System.out.println("Game Stopped/Paused...");
                playModePanel.pauseGame();
                playModePanel.setPaused(true);
                // Pause logic here
            } else {
                stopResumeButton.setIcon(stopIcon);
                System.out.println("Game Resumed...");
                playModePanel.resumeGame();
                playModePanel.setPaused(false);
                // Resume logic here
            }
        });

        // Exit button example
        exitButton.addActionListener(e -> {
            System.out.println("Exit clicked!");
            playModePanel.exitGame();
            // e.g., switch to a different screen or close the game
        });
    }

    /**
     * Paints a background image if available, filling the entire panel area.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundIcon != null) {
            Image backgroundImage = backgroundIcon.getImage();
            // Fill the panel with the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fallback to a solid color if no image
            g.setColor(new Color(66, 40, 53));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
