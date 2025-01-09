package domain.panels.sideBarComponents;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryPanel extends JPanel {

    private static final int MAX_SLOTS = 6;
    private static final int MAX_SLOT_SIZE = 70;
    private static final int MIN_SLOT_SIZE = 40;
    private static final double SCALE_FACTOR = 0.5;

    private final SlotPanel[] slots;
    private Map<String, String> itemImages;
    private Image backgroundImage;

    public InventoryPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        slots = new SlotPanel[MAX_SLOTS];
        itemImages = initializeItemImages();

        loadBackgroundImage();
        initEmptySlots();

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
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 15, 10, 35);

        int[][] positions = {
                {0, 0}, {0, 1},
                {1, 0}, {1, 1},
                {2, 0}, {2, 1}
        };

        for (int i = 0; i < MAX_SLOTS; i++) {
            SlotPanel slot = new SlotPanel(MAX_SLOT_SIZE);
            slots[i] = slot;

            gbc.gridx = positions[i][1];
            gbc.gridy = positions[i][0];

            add(slot, gbc);
        }
    }

    private void adjustSlotSizes() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int calculatedSlotSize = (int) (Math.min(panelWidth / 2, panelHeight / 3) * SCALE_FACTOR);

        int slotSize = Math.max(MIN_SLOT_SIZE, Math.min(MAX_SLOT_SIZE, calculatedSlotSize));

        for (SlotPanel slot : slots) {
            slot.setSlotSize(slotSize);
        }

        revalidate();
        repaint();
    }

    public void setItem(String itemName, int quantity) {
        if (!itemImages.containsKey(itemName)) {
            System.err.println("Invalid item name: " + itemName);
            return;
        }

        for (SlotPanel slot : slots) {
            if (slot.getItemName() != null && slot.getItemName().equals(itemName)) {
                if (quantity > 0) {
                    slot.updateQuantity(quantity);
                } else {
                    slot.clearSlot();
                }
                return;
            }
        }

        if (quantity == 0) {
            System.out.println("Cannot add an item with quantity zero.");
            return;
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
