package domain.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;

/**
 * A custom panel representing a single inventory slot with an item icon and quantity indicator.
 */
public class SlotPanel extends JPanel {
    private JLabel itemIconLabel;
    private JLabel quantityLabel;
    private String itemName;
    private int quantity;

    public SlotPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout to center the layered pane
        setOpaque(false); // Transparent background
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Optional border for debugging

        // Create a layered pane for the icon and quantity
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(48, 48));

        // Create the icon label
        itemIconLabel = new JLabel();
        itemIconLabel.setBounds(0, 0, 48, 48); // Full slot size
        itemIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemIconLabel.setVerticalAlignment(SwingConstants.CENTER);
        layeredPane.add(itemIconLabel, JLayeredPane.DEFAULT_LAYER); // Add to the default layer

        // Create the quantity label
        quantityLabel = new JLabel();
        quantityLabel.setBounds(0, 0, 48, 48); // Full slot size
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        quantityLabel.setFont(new Font("Serif", Font.BOLD, 12));
        quantityLabel.setForeground(Color.WHITE);
        layeredPane.add(quantityLabel, JLayeredPane.PALETTE_LAYER); // Add to a higher layer

        // Add the layered pane to this panel
        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Sets the item icon and quantity in the slot.
     *
     * @param itemName  The name of the item.
     * @param itemIcon  The icon of the item.
     * @param quantity  The quantity of the item.
     */
    public void setItem(String itemName, ImageIcon itemIcon, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        itemIconLabel.setIcon(scaleIcon(itemIcon, 32, 32)); // Scale the icon to fit the slot
        quantityLabel.setText("x" + quantity);
    }

    /**
     * Updates the quantity of the item in the slot.
     *
     * @param newQuantity The new quantity of the item.
     */
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        quantityLabel.setText("x" + newQuantity);
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    private static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
