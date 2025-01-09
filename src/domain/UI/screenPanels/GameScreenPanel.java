package domain.UI.screenPanels;

import domain.UI.UI;
import domain.panels.PlayModePanel;
import domain.panels.SidebarPanel;

import javax.swing.*;
import java.awt.*;

public class GameScreenPanel extends JPanel {

    private PlayModePanel playModePanel;
    private SidebarPanel sidebarPanel;

    public GameScreenPanel(UI ui) {
        this.playModePanel = new PlayModePanel(ui.getBuildScreen().getBuildModePanel().getHalls());
        this.sidebarPanel = new SidebarPanel(playModePanel);
        this.playModePanel.setSidebarPanel(sidebarPanel);
        playModePanel.initializeGameComponents(0);

        // Use a BorderLayout
        setLayout(new BorderLayout());

        // Add the PlayModePanel to the center (main game area)
        add(playModePanel, BorderLayout.CENTER);

        // Add the SidebarPanel to the east (right side)
        add(sidebarPanel, BorderLayout.EAST);

        // Set a default preferred size for the sidebar (proportional to window size)
        adjustSidebarSize();

        // Add a resize listener to ensure the sidebar adapts dynamically
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustSidebarSize();
            }
        });

        // Start the game thread
        playModePanel.startGameThread();

        // Request focus on the game area
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow);
    }

    /**
     * Adjusts the size of the sidebar dynamically based on the window dimensions.
     */
    private void adjustSidebarSize() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Sidebar width is 23% of the screen width, height matches the panel height
        int sidebarWidth = Math.max(panelWidth * 18 / 100, 190); // Minimum width of 200px
        int sidebarHeight = panelHeight;

        sidebarPanel.setPreferredSize(new Dimension(sidebarWidth, sidebarHeight));
        sidebarPanel.setMinimumSize(new Dimension(sidebarWidth, sidebarHeight));

        revalidate();
        repaint();
    }
}
