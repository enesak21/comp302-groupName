package domain.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends JPanel {

    private static final int MAX_SLOTS = 6; // Maximum number of inventory slots
    private static final int MAX_SLOT_SIZE = 70; // Maximum size for each slot
    private static final int MIN_SLOT_SIZE = 40; // Minimum size for each slot
    private static final double SCALE_FACTOR = 0.5; // Fine-tuning factor for slot size

    private final SlotPanel[] slots; // Array to represent inventory slots
    private Map<String, String> itemImages; // Map item names to image file paths
    private Image backgroundImage; // Background image for the inventory

    public InventoryPanel() {
        setOpaque(false); // Ensure transparency for the background image
        setLayout(new GridBagLayout()); // Use GridBagLayout for precise positioning

        // Add padding/margin to shift the entire grid
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Top, left, bottom, right padding

        slots = new SlotPanel[MAX_SLOTS];
        itemImages = initializeItemImages();

        loadBackgroundImage();
        initEmptySlots();

        // Add a resize listener to adjust slot sizes and icons dynamically
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                adjustSlotSizes();
            }
        });
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = new ImageIcon("src/resources/inventory/inventory_large.png").getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    private Map<String, String> initializeItemImages() {
        Map<String, String> images = new HashMap<>();
        images.put("Cloak of Protection", "src/resources/items/cloakOfProtection.png");
        images.put("Heart Symbol", "src/resources/items/heartSymbol.png");
        images.put("Extra Life (Closed Chest)", "src/resources/items/extraLifeClosedChest.png");
        images.put("Extra Life (Opened Chest)", "src/resources/items/extraLifeOpenedChest.png");
        images.put("Luring Gem", "src/resources/items/luringGem.png");
        images.put("Reveal", "src/resources/items/reveal.png");
        return images;
    }

    private void initEmptySlots() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE; // No stretching
        gbc.insets = new Insets(10, 15, 10, 35); // Fine-tuned spacing for vertical and horizontal alignment

        // Define rows and columns (2 slots per row, 3 rows in total)
        int[][] positions = {
                {0, 0}, {0, 1}, // Row 1
                {1, 0}, {1, 1}, // Row 2
                {2, 0}, {2, 1}  // Row 3
        };

        for (int i = 0; i < MAX_SLOTS; i++) {
            SlotPanel slot = new SlotPanel(MAX_SLOT_SIZE); // Adjust slot size dynamically
            slots[i] = slot;

            // Assign grid positions for each slot
            gbc.gridx = positions[i][1]; // Column index
            gbc.gridy = positions[i][0]; // Row index

            add(slot, gbc); // Add the slot to the grid
        }
    }


    private void adjustSlotSizes() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int calculatedSlotSize = (int) (Math.min(panelWidth / 2, panelHeight / 3) * SCALE_FACTOR); // Apply scaling for 2x2x2 layout

        // Ensure slot size stays within defined limits
        int slotSize = Math.max(MIN_SLOT_SIZE, Math.min(MAX_SLOT_SIZE, calculatedSlotSize));

        for (SlotPanel slot : slots) {
            slot.setSlotSize(slotSize);
        }

        revalidate();
        repaint();
    }

    public void addItem(String itemName, int quantity) {
        if (!itemImages.containsKey(itemName)) {
            System.err.println("Invalid item name: " + itemName);
            return;
        }

        for (SlotPanel slot : slots) {
            if (slot.getItemName() != null && slot.getItemName().equals(itemName)) {
                slot.updateQuantity(slot.getQuantity() + quantity);
                return;
            }
        }

        for (SlotPanel slot : slots) {
            if (slot.getItemName() == null) {
                ImageIcon itemIcon = new ImageIcon(itemImages.get(itemName));
                slot.setItem(itemName, itemIcon, quantity);
                return;
            }
        }

        System.out.println("Inventory is full!");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
