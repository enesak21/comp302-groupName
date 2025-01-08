package domain.panels;

import domain.panels.sideBarComponents.TimeLeftPanel;
import domain.panels.sideBarComponents.TopButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

public class SidebarPanel extends JPanel {

    // Fields for UI components
    private TopButtonPanel topButtonPanel;
    private JPanel timeLeftPanel;
    private JLabel heartsLeftLabel;
    private JPanel inventoryPanel;

    // Reference to the PlayModePanel
    private PlayModePanel playModePanel;

    public SidebarPanel(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        // Background color
        setBackground(new Color(66, 40, 53));

        // Use a BoxLayout in vertical (Y) axis mode
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add some padding on the right

        // Initialize all components (buttons, labels, sub-panels)
        initComponents();
    }

    private void initComponents() {

        add(Box.createVerticalStrut(10));
        // ============== TOP PANEL (Buttons) ==============
        // 1) Top Button Panel
        topButtonPanel = new TopButtonPanel(playModePanel);
        topButtonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        // Wrap in a small panel or add directly:
        add(topButtonPanel);

        // Add spacing
        add(Box.createVerticalStrut(10));



        // ============== TIME LEFT LABEL ==============
        timeLeftPanel = new TimeLeftPanel();

        // Add the TimeLeftPanel to the sidebar
        add(timeLeftPanel);

        // More vertical spacing
        add(Box.createVerticalStrut(10));



        // ============== HEARTS LEFT LABEL ==============
        heartsLeftLabel = new JLabel("Hearts: ♥♥♥");
        heartsLeftLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(heartsLeftLabel);

        // If we want the inventory to truly sit at the bottom,
        // we can insert a "vertical glue" here. This pushes subsequent components downward.
        add(Box.createVerticalGlue());



        // ============== INVENTORY PANEL ==============
        inventoryPanel = new JPanel();
        inventoryPanel.setOpaque(false); // matches parent background
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
        inventoryPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Add example items in the inventory
        for (int i = 1; i <= 3; i++) {
            JLabel itemLabel = new JLabel("Item " + i);
            inventoryPanel.add(itemLabel);
        }

        add(inventoryPanel);
    }

    // Getters for UI components
    public TopButtonPanel getTopButtonPanel() {
        return topButtonPanel;
    }

    public JPanel getTimeLeftPanel() {
        return timeLeftPanel;
    }

    public JLabel getHeartsLeftLabel() {
        return heartsLeftLabel;
    }

    public JPanel getInventoryPanel() {
        return inventoryPanel;
    }

}
