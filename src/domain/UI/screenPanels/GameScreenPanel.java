package domain.UI.screenPanels;

import domain.UI.UI;
import domain.UI.panels.PlayModePanel;
import domain.UI.panels.SidebarPanel;
import domain.config.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameScreenPanel extends JPanel {

    private PlayModePanel playModePanel;
    private SidebarPanel sidebarPanel;

    public GameScreenPanel(UI ui) {
        this.playModePanel = new PlayModePanel(ui.getBuildScreen().getBuildModePanel().getHalls());
        this.sidebarPanel = new SidebarPanel(playModePanel);
        this.playModePanel.setSidebarPanel(sidebarPanel);
        playModePanel.initializeGameComponents(0);

        // Use a BorderLayout for flexibility
        setLayout(new BorderLayout());

        // Add components
        add(playModePanel, BorderLayout.CENTER); // Play area in the center
        add(sidebarPanel, BorderLayout.EAST);   // Sidebar on the right

        // Add a resize listener to adjust the sidebar dynamically
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustSidebarSize();
            }
        });

        // Start the game thread
        playModePanel.startGameThread();
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Ensure play area gets focus
    }

    public GameScreenPanel(UI ui, GameState loadedGame) {
        this.playModePanel = new PlayModePanel(loadedGame.getHallsList());
        this.sidebarPanel = new SidebarPanel(playModePanel);
        this.playModePanel.setSidebarPanel(sidebarPanel);
        playModePanel.initializeGameComponents(loadedGame);

        // Use a BorderLayout for flexibility
        setLayout(new BorderLayout());

        // Add components
        add(playModePanel, BorderLayout.CENTER); // Play area in the center
        add(sidebarPanel, BorderLayout.EAST);   // Sidebar on the right

        // Add a resize listener to adjust the sidebar dynamically
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustSidebarSize();
            }
        });

        // Start the game thread
        playModePanel.startGameThread();
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Ensure play area gets focus
    }

    /**
     * Dynamically adjusts the sidebar size to ensure it does not overlap the play area.
     */
    private void adjustSidebarSize() {
        int totalWidth = getWidth();
        int totalHeight = getHeight();

        // Set the sidebar width to a fixed percentage of the total width (25%)
        int sidebarWidth = totalWidth * 23 / 100;

        // Ensure the sidebar width is at least 150 pixels
        sidebarWidth = Math.max(sidebarWidth, 150);

        // Update sidebar dimensions
        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, totalHeight));
        sidebarPanel.setMinimumSize(new Dimension(sidebarWidth, totalHeight));

        // Ensure the play area adjusts accordingly
        playModePanel.setPreferredSize(new Dimension(totalWidth - sidebarWidth, totalHeight));
        playModePanel.setMinimumSize(new Dimension(totalWidth - sidebarWidth, totalHeight));

        revalidate();
        repaint();
    }

}
