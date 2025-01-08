package domain.panels.sideBarComponents;

import domain.game.TimeController;
import domain.panels.PlayModePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * A dedicated panel for displaying the "Time Left" information.
 */
public class TimeLeftPanel extends JPanel {

    private JLabel titleLabel;
    private JLabel timeLeftLabel;
    /**
     * Constructs a TimeLeftPanel with a custom background and "Time Left" information.
     */
    public TimeLeftPanel() {

        // Example background color (RGB 101, 89, 93)
        setBackground(new Color(101, 89, 93));

        // Set vertical layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // --- TITLE LABEL ---
        titleLabel = new JLabel("Time Left", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20)); // Default font, can change to LOTR font

        // --- TIME LEFT LABEL ---
        timeLeftLabel = new JLabel("00:00", SwingConstants.CENTER);
        timeLeftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLeftLabel.setForeground(Color.WHITE);
        timeLeftLabel.setFont(new Font("Serif", Font.BOLD, 16)); // Default font, can change to LOTR font

        // Add spacing between the title and time
        add(Box.createVerticalStrut(10)); // Optional spacing before title
        add(titleLabel);
        add(Box.createVerticalStrut(5)); // Small gap between title and time
        add(timeLeftLabel);

        // Load LOTR font and apply it
        loadLOTRFont();

        // Update fonts to LOTR font if loaded successfully
        if (lotrFont != null) {
            titleLabel.setFont(lotrFont.deriveFont(Font.BOLD, 16f));
            timeLeftLabel.setFont(lotrFont.deriveFont(Font.BOLD, 20f));
        }
    }

    /**
     * Updates the displayed time left.
     * @param newTime A string representing the remaining time
     */
    public void updateTimeLeft(int newTime) {
        // Convert seconds to MM:SS format
        int minutes = newTime / 60;
        int seconds = newTime % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        // Update the label text
        timeLeftLabel.setText(formattedTime);
    }


    // --- LOTR Font Loader ---
    private Font lotrFont;

    private void loadLOTRFont() {
        try {
            // Adjust the path if your font file is located elsewhere
            File fontFile = new File("src/resources/fonts/Ringbearer.ttf");
            lotrFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // Register the font so Java can use it
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lotrFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            lotrFont = null; // Fallback to default font
        }
    }
}
