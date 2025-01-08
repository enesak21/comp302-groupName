package domain.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A dedicated panel for displaying inventory items with images, quantity indicators, and a background image.
 */
public class InventoryPanel extends JPanel {

    private static final int MAX_SLOTS = 6; // Maximum number of inventory slots
    private Map<String, String> itemImages; // Map item names to image file paths
    private SlotPanel[] slots; // Array to represent inventory slots
    private Image backgroundImage; // Background image for the inventory

    public InventoryPanel() {
        setPreferredSize(new Dimension(200, 350)); // Set panel dimensions
        loadBackgroundImage();

        setOpaque(false); // Ensure transparency for the background image
        setLayout(null); // Use null layout for absolute positioning

        itemImages = initializeItemImages(); // Initialize item-image mapping
        slots = new SlotPanel[MAX_SLOTS]; // Initialize slots array
        initEmptySlots(); // Create and position empty slots
    }

    /**
     * Loads the background image for the panel.
     */
    private void loadBackgroundImage() {
        try {
            backgroundImage = new ImageIcon("src/resources/inventory/inventory_large.png").getImage();
            if (backgroundImage == null) {
                throw new Exception("Background image not found.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    /**
     * Initializes a mapping of item names to their image file paths.
     *
     * @return A map of item names to image file paths.
     */
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

    /**
     * Initializes empty inventory slots and positions them based on the background image.
     */
    private void initEmptySlots() {
        // Define positions for a 2x2x2 layout (x, y for each slot)
        int[][] slotPositions = {
                {30, 100}, {91, 100},   // Row 1
                {30, 160}, {91, 160},   // Row 2
                {30, 220}, {91, 220}    // Row 3
        };

        for (int i = 0; i < MAX_SLOTS; i++) {
            SlotPanel slot = new SlotPanel();
            slot.setBounds(slotPositions[i][0], slotPositions[i][1], 48, 48); // Set bounds for each slot
            slots[i] = slot; // Add the slot to the array
            add(slot); // Add the slot to the panel
        }
    }


    /**
     * Adds an item to the inventory or increments its quantity if it already exists.
     *
     * @param itemName The name of the item (e.g., "Cloak of Protection").
     * @param quantity The quantity of the item to add.
     */
    public void addItem(String itemName, int quantity) {
        if (!itemImages.containsKey(itemName)) {
            System.err.println("Invalid item name: " + itemName);
            return;
        }

        // Check for an existing slot with the same item
        for (SlotPanel slot : slots) {
            if (slot.getItemName() != null && slot.getItemName().equals(itemName)) {
                slot.updateQuantity(slot.getQuantity() + quantity);
                return;
            }
        }

        // Add the item to the first empty slot
        for (SlotPanel slot : slots) {
            if (slot.getItemName() == null) {
                ImageIcon itemIcon = new ImageIcon(itemImages.get(itemName));
                slot.setItem(itemName, itemIcon, quantity);
                return;
            }
        }

        System.out.println("Inventory is full!");
    }

    /**
     * Paints the background image.
     *
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
