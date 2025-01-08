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

        // The "center" area will be your main game (PlayModePanel).
        // The "east" area will be your sidebar.
        add(playModePanel, BorderLayout.CENTER);
        add(sidebarPanel, BorderLayout.EAST);

        // Optional: if you want to fix a certain width for the sidebar,
        // you can set a preferred size on the sidebar panel
        sidebarPanel.setPreferredSize(new Dimension(playModePanel.getScreenWidth() * 23 / 100, playModePanel.getScreenHeight() * 99 / 100));

        // Start the game thread
        playModePanel.startGameThread(); // Start the game loop
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Request focus for
    }
}

