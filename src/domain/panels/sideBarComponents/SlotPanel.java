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
    private int slotSize;

    public SlotPanel(int slotSize) {
        this.slotSize = slotSize; // Initial size
        setLayout(new OverlayLayout(this)); // Layer icon and quantity labels
        setOpaque(false);

        // Create and configure the item icon label
        itemIconLabel = new JLabel();
        itemIconLabel.setAlignmentX(0.5f); // Center horizontally
        itemIconLabel.setAlignmentY(0.5f); // Center vertically

        // Create and configure the quantity label
        quantityLabel = new JLabel();
        quantityLabel.setFont(new Font("Serif", Font.BOLD, slotSize / 6)); // Dynamically set font size
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
        quantityLabel.setAlignmentX(1.0f); // Align to the top-right
        quantityLabel.setAlignmentY(0.0f); // Align to the top-right

        // Add the labels to the panel
        add(quantityLabel);
        add(itemIconLabel);
    }

    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
        setPreferredSize(new Dimension(slotSize, slotSize));
        quantityLabel.setFont(new Font("Serif", Font.BOLD, slotSize / 2)); // Adjust font size relative to slot size

        // If an item icon exists, scale it to the new size
        if (itemIconLabel.getIcon() != null) {
            itemIconLabel.setIcon(scaleIcon((ImageIcon) itemIconLabel.getIcon(), slotSize));
        }
    }

    public void setItem(String itemName, ImageIcon itemIcon, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        itemIconLabel.setIcon(scaleIcon(itemIcon, slotSize));
        updateQuantity(quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        quantityLabel.setText("x" + newQuantity); // Display "xN" format
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    private ImageIcon scaleIcon(ImageIcon icon, int size) {
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(size - 10, size - 10, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
}
