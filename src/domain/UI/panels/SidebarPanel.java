package domain.UI.panels;

import domain.UI.panels.sideBarComponents.HeartsLeftPanel;
import domain.UI.panels.sideBarComponents.InventoryPanel;
import domain.UI.panels.sideBarComponents.TimeLeftPanel;
import domain.UI.panels.sideBarComponents.TopButtonPanel;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {

    private TopButtonPanel topButtonPanel;
    private TimeLeftPanel timeLeftPanel;
    private HeartsLeftPanel heartsPanel;
    private InventoryPanel inventoryPanel;

    private PlayModePanel playModePanel;

    public SidebarPanel(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        // Set the background color
        setBackground(new Color(66, 40, 53));

        // Use a vertical BoxLayout for alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the sidebar

        // Initialize all components
        initComponents();

        // Add resize listener for responsive layout
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustComponentSizes();
            }
        });
    }

    private void initComponents() {
        // Add spacing at the top
        add(Box.createVerticalStrut(10));

        // ============== TOP BUTTON PANEL ==============
        topButtonPanel = new TopButtonPanel(playModePanel);
        add(topButtonPanel);

        // Add vertical spacing
        add(Box.createVerticalStrut(10));

        // ============== TIME LEFT PANEL ==============
        timeLeftPanel = new TimeLeftPanel();
        add(timeLeftPanel);

        // Add spacing
        add(Box.createVerticalStrut(10));

        // ============== HEARTS LEFT PANEL ==============
        heartsPanel = new HeartsLeftPanel(3); // Initial hearts
        add(heartsPanel);

        // Add vertical glue to push inventory downward
        add(Box.createVerticalGlue());

        // ============== INVENTORY PANEL ==============
        inventoryPanel = new InventoryPanel();
        add(inventoryPanel);
    }

    private void adjustComponentSizes() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate dynamic heights for each section
        int topButtonHeight = panelHeight / 12; // 8.33% of the height
        int timeLeftHeight = panelHeight / 10;  // 10% of the height
        int heartsHeight = panelHeight / 10;    // 10% of the height
        int inventoryHeight = (int) (panelHeight * 0.6); // 60% of the height

        // Dynamically set component sizes
        adjustComponentSize(topButtonPanel, panelWidth, topButtonHeight);
        adjustComponentSize(timeLeftPanel, panelWidth, timeLeftHeight);
        adjustComponentSize(heartsPanel, panelWidth, heartsHeight);
        adjustComponentSize(inventoryPanel, panelWidth, inventoryHeight);

        revalidate();
        repaint();
    }

    /**
     * Adjusts the size of a component dynamically.
     * @param component The component to resize.
     * @param width The width to set.
     * @param height The height to set.
     */
    private void adjustComponentSize(JComponent component, int width, int height) {
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
        component.setMinimumSize(new Dimension(width, height));
    }

    // Getters for UI components
    public TopButtonPanel getTopButtonPanel() {
        return topButtonPanel;
    }

    public TimeLeftPanel getTimeLeftPanel() {
        return timeLeftPanel;
    }

    public HeartsLeftPanel getHeartsLeftPanel() {
        return heartsPanel;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }
}
