package domain.UI.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;

public class SlotPanel extends JPanel {

    private JLabel itemIconLabel;
    private JLabel quantityLabel;
    private String itemName;
    private int quantity;
    private int slotSize;

    public SlotPanel(int slotSize) {
        this.slotSize = slotSize;
        setLayout(new OverlayLayout(this));
        setOpaque(false);

        itemIconLabel = new JLabel();
        itemIconLabel.setAlignmentX(0.5f);
        itemIconLabel.setAlignmentY(0.5f);

        quantityLabel = new JLabel();
        quantityLabel.setFont(new Font("Serif", Font.BOLD, slotSize / 6));
        quantityLabel.setForeground(Color.WHITE);
        quantityLabel.setBackground(new Color(0, 0, 0, 150));
        quantityLabel.setAlignmentX(1.0f);
        quantityLabel.setAlignmentY(0.0f);

        add(quantityLabel);
        add(itemIconLabel);
    }

    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
        setPreferredSize(new Dimension(slotSize, slotSize));
        quantityLabel.setFont(new Font("Serif", Font.BOLD, slotSize / 2));

        if (itemIconLabel.getIcon() != null) {
            itemIconLabel.setIcon(scaleIcon((ImageIcon) itemIconLabel.getIcon(), slotSize));
        }
        revalidate();
        repaint();
    }

    public void setItem(String itemName, ImageIcon itemIcon, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        itemIconLabel.setIcon(scaleIcon(itemIcon, slotSize));
        updateQuantity(quantity);
    }

    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;

        if (newQuantity > 0) {
            quantityLabel.setText("x" + newQuantity);
        } else {
            clearSlot();
        }
    }

    public String getItemName() {
        return itemName;
    }

    public void clearSlot() {
        this.itemName = null;
        this.quantity = 0;
        itemIconLabel.setIcon(null);
        quantityLabel.setText("");
        revalidate();
        repaint();
    }

    private ImageIcon scaleIcon(ImageIcon icon, int size) {
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(size - 10, size - 10, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
}
