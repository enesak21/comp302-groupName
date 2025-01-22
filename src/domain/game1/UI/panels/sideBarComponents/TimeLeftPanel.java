package domain.game1.UI.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A dedicated panel for displaying the "Time Left" information.
 */
public class TimeLeftPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel timeLeftLabel;
    private Font lotrFont;

    /**
     * Constructs a TimeLeftPanel with a custom background and "Time Left" information.
     */
    public TimeLeftPanel() {
        // Set background color
        setBackground(new Color(101, 89, 93));

        // Use vertical BoxLayout for alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize labels
        initLabels();

        // Load LOTR font
        loadLOTRFont();

        // Apply the LOTR font if successfully loaded
        applyFont();

        // Add a resize listener for dynamic font adjustments
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustFontSizes();
            }
        });
    }

    /**
     * Initializes the labels for "Time Left" title and time display.
     */
    private void initLabels() {
        // Title Label
        titleLabel = new JLabel("Time Left", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        // Time Left Label
        timeLeftLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLeftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLeftLabel.setForeground(Color.WHITE);

        // Add components with spacing
        add(Box.createVerticalGlue()); // Push content downward if needed
        add(titleLabel);
        add(Box.createVerticalStrut(5)); // Space between title and time
        add(timeLeftLabel);
        add(Box.createVerticalGlue()); // Push content upward if needed
    }

    /**
     * Updates the displayed time left.
     * @param newTime Time in seconds to be formatted and displayed.
     */
    public void updateTimeLeft(int newTime) {
        // Convert seconds to MM:SS format
        int minutes = newTime / 60;
        int seconds = newTime % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        // Update the label text
        timeLeftLabel.setText(formattedTime);
    }

    /**
     * Adjusts font sizes dynamically based on the panel size.
     */
    private void adjustFontSizes() {
        int panelHeight = getHeight();

        // Dynamically adjust font sizes based on panel height
        float titleFontSize = Math.max(panelHeight / 8f, 16); // Minimum size of 16
        float timeFontSize = Math.max(panelHeight / 4f, 20);  // Minimum size of 20

        if (lotrFont != null) {
            titleLabel.setFont(lotrFont.deriveFont(Font.BOLD, titleFontSize));
            timeLeftLabel.setFont(lotrFont.deriveFont(Font.BOLD, timeFontSize));
        } else {
            titleLabel.setFont(new Font("Serif", Font.BOLD, (int) titleFontSize));
            timeLeftLabel.setFont(new Font("Serif", Font.BOLD, (int) timeFontSize));
        }
    }

    /**
     * Loads the LOTR font from the specified file.
     */
    private void loadLOTRFont() {
        try {
            // Adjust the path if your font file is located elsewhere
            File fontFile = new File("src/resources/fonts/Ringbearer.ttf");
            lotrFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // Register the font so Java can use it
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lotrFont);
        } catch (FontFormatException | IOException e) {
            lotrFont = null; // Fallback to default font
        }
    }

    /**
     * Applies the LOTR font if loaded, otherwise defaults to Serif.
     */
    private void applyFont() {
        if (lotrFont != null) {
            titleLabel.setFont(lotrFont.deriveFont(Font.BOLD, 18f));
            timeLeftLabel.setFont(lotrFont.deriveFont(Font.BOLD, 24f));
        } else {
            titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
            timeLeftLabel.setFont(new Font("Serif", Font.BOLD, 24));
        }
    }
}
