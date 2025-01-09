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
    private final List<JLabel> heartIcons; // Holds heart icons
    private static final int MAX_HEARTS = 10; // Maximum number of hearts displayed

    // Desired dimensions for heart scaling
    private int heartWidth = 32;  // Default heart width
    private int heartHeight = 32; // Default heart height

    /**
     * Constructs the HeartsLeftPanel with a custom background and hearts display.
     * @param initialHearts Initial number of hearts to display.
     */
    public HeartsLeftPanel(int initialHearts) {
        this.heartsLeft = Math.min(initialHearts, MAX_HEARTS); // Limit initial hearts to MAX_HEARTS

        // Set the panel layout and background
        setBackground(new Color(101, 89, 93));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Default gaps; will adjust dynamically

        // Initialize heart icons
        heartIcons = new ArrayList<>();
        initHearts();

        // Add a resize listener to make the hearts adaptable
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustHeartSizes();
            }
        });
    }

    /**
     * Initializes the hearts display with the current number of hearts.
     */
    public void initHearts() {
        removeAll();
        heartIcons.clear();

        for (int i = 0; i < MAX_HEARTS; i++) {
            JLabel heartLabel;
            if (i < heartsLeft) {
                ImageIcon filledIcon = loadScaledIcon("src/resources/player/heart.png", heartWidth, heartHeight);
                heartLabel = new JLabel(filledIcon != null ? filledIcon : new ImageIcon());
            } else {
                heartLabel = new JLabel(); // Empty label for unused hearts
            }
            heartIcons.add(heartLabel);
            add(heartLabel);
        }

        revalidate();
        repaint();
    }

    /**
     * Adjusts the heart sizes dynamically based on panel dimensions.
     */
    private void adjustHeartSizes() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate heart size based on the panel size
        int baseHeartSize = Math.min(panelWidth / MAX_HEARTS - 10, panelHeight / 2);

        // Apply a scaling factor to make the hearts slightly larger
        heartWidth = Math.max(baseHeartSize, 32); // Ensure at least 32x32 size
        heartHeight = heartWidth; // Keep hearts square

        // Update icons for each heart
        for (int i = 0; i < heartIcons.size(); i++) {
            JLabel heartLabel = heartIcons.get(i);
            if (i < heartsLeft) {
                heartLabel.setIcon(loadScaledIcon("src/resources/player/heart.png", heartWidth, heartHeight));
            } else {
                heartLabel.setIcon(null); // Empty slot
            }
        }

        revalidate();
        repaint();
    }

    /**
     * Updates the number of hearts displayed on the panel with GIF animation for disappearing hearts.
     * @param newHearts Number of hearts left.
     */
    public void updateHeartsLeft(int newHearts) {
        if (newHearts < heartsLeft) {
            int disappearingHeartIndex = heartsLeft - 1;
            playDisappearingAnimation(disappearingHeartIndex);
        }
        this.heartsLeft = Math.min(newHearts, MAX_HEARTS); // Ensure it doesn't exceed MAX_HEARTS
    }

    /**
     * Plays a disappearing animation for a specific heart.
     * @param index The index of the heart to animate.
     */
    private void playDisappearingAnimation(int index) {
        if (index < 0 || index >= heartIcons.size()) return;

        JLabel heartLabel = heartIcons.get(index);

        // Load the GIF
        ImageIcon gifIcon = new ImageIcon("src/resources/player/heart_disappear.gif");

        // Scale the GIF to match the heart's size
        Image scaledGif = gifIcon.getImage().getScaledInstance(heartWidth, heartHeight, Image.SCALE_DEFAULT);
        ImageIcon scaledGifIcon = new ImageIcon(scaledGif);

        // Set the scaled GIF as the heart's icon
        heartLabel.setIcon(scaledGifIcon);

        // Approximate or hardcode the GIF duration
        int gifDuration = 1000; // 1 second (1000ms)

        // Use a Timer to remove the heart from the panel after the animation completes
        Timer animationTimer = new Timer(gifDuration, e -> {
            heartIcons.remove(index);
            remove(heartLabel);

            revalidate();
            repaint();
        });

        animationTimer.setRepeats(false); // Ensure the timer runs only once
        animationTimer.start();
    }

    /**
     * Loads and scales an icon from the specified file path.
     * @param path The file path to the image.
     * @param width The desired width of the icon.
     * @param height The desired height of the icon.
     * @return A scaled ImageIcon.
     */
    private ImageIcon loadScaledIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(path);
            if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0) {
                System.err.println("Error loading icon: " + path);
                return null;
            }
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading or scaling icon: " + path + " - " + e.getMessage());
            return null;
        }
    }
}
