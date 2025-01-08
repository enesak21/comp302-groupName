package domain.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A dedicated panel for displaying the number of hearts left with GIF-based disappearing animation.
 */
public class HeartsLeftPanel extends JPanel {

    private int heartsLeft;
    private List<JLabel> heartIcons; // Holds heart icons or text
    private static final int MAX_HEARTS = 10; // Maximum number of hearts displayed

    // Set desired width and height for the hearts
    private int heartWidth = 32;  // Desired heart width
    private int heartHeight = 32; // Desired heart height


    /**
     * Constructs the HeartsLeftPanel with a custom background and hearts display.
     */
    public HeartsLeftPanel(int initialHearts) {
        this.heartsLeft = Math.min(initialHearts, MAX_HEARTS); // Ensure hearts don't exceed MAX_HEARTS

        // Set the panel layout and background
        setBackground(new Color(101, 89, 93));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Horizontal layout with small gaps

        // Initialize heart icons
        heartIcons = new ArrayList<>();
        initHearts();
    }

    /**
     * Initializes the hearts display with the current number of hearts.
     */
    public void initHearts() {
        // Clear any existing icons
        removeAll();
        heartIcons.clear();

        // Create icons for the number of hearts left
        for (int i = 0; i < MAX_HEARTS; i++) {
            JLabel heartLabel;
            if (i < heartsLeft) {
                // Filled heart
                ImageIcon filledIcon = new ImageIcon("src/resources/player/heart.png");
                Image scaledFilledImage = filledIcon.getImage().getScaledInstance(heartWidth, heartHeight, Image.SCALE_SMOOTH);
                heartLabel = new JLabel(new ImageIcon(scaledFilledImage));
            } else {
                // Empty placeholder (optional, or skip entirely)
                heartLabel = new JLabel();
            }
            heartIcons.add(heartLabel);
            add(heartLabel);
        }

        revalidate(); // Refresh the panel
        repaint();
    }

    /**
     * Updates the number of hearts displayed on the panel with GIF animation for disappearing hearts.
     * @param newHearts Number of hearts left.
     */
    public void updateHeartsLeft(int newHearts) {
        if (newHearts < heartsLeft) {
            // Play animation for the disappearing heart
            int disappearingHeartIndex = heartsLeft - 1;
            playDisappearingAnimation(disappearingHeartIndex);
        }
        this.heartsLeft = Math.min(newHearts, MAX_HEARTS); // Ensure it doesn't exceed MAX_HEARTS
    }

    private void playDisappearingAnimation(int index) {
        if (index < 0 || index >= heartIcons.size()) return;

        JLabel heartLabel = heartIcons.get(index);

        // Load the GIF animation
        ImageIcon gifIcon = new ImageIcon("src/resources/player/heart_disappear.gif");

        // Scale the GIF to match the normal heart size
        int heartWidth = 32;  // Replace with your desired heart width
        int heartHeight = 32; // Replace with your desired heart height
        Image scaledGifImage = gifIcon.getImage().getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
        ImageIcon scaledGifIcon = new ImageIcon(scaledGifImage);

        // Set the scaled GIF as the heart's icon
        heartLabel.setIcon(scaledGifIcon);

        // Hardcoded GIF duration (adjust based on the actual length of your GIF)
        int gifDuration = 1000; // 1 second (1000ms)

        // Use a Timer to remove the heart from the panel after the animation
        Timer animationTimer = new Timer(gifDuration, e -> {
            // Remove the heart label from the panel and list
            heartIcons.remove(index); // Remove by index for clarity
            remove(heartLabel);       // Remove the JLabel from the panel

            // Refresh the layout and repaint
            revalidate();
            repaint();
        });

        animationTimer.setRepeats(false); // Ensure the timer runs only once
        animationTimer.start();           // Start the timer
    }
}
