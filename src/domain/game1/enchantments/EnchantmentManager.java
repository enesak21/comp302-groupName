package domain.game1.enchantments;

import domain.game1.UI.EnchantmentView;
import domain.game1.enchantments.Factories.*;
import domain.game1.game.Game;
import domain.game1.game.Tile;
import domain.game1.UI.panels.PlayModePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnchantmentManager {

    private List<BaseEnchantment> enchantments;
    private List<EnchantmentView> enchantmentViews; // Store views separately
    private Random random;
    private int tileSize;
    private Game game;
    private final int SPAWN_INTERVAL = 6 * 1000; // Write it in milliseconds
    private long lastSpawnTime; // Track spawn time in milliseconds
    private PlayModePanel playModePanel;

    private List<EnchantmentFactory> factories;

    public EnchantmentManager(Game game, int tileSize) {
        this.enchantments = new ArrayList<>();
        this.enchantmentViews = new ArrayList<>(); // Initialize views
        this.random = new Random();
        this.tileSize = tileSize;
        this.game = game;
        this.lastSpawnTime = System.currentTimeMillis(); // Set initial spawn time


        // Add factories for different enchantments
        factories = new ArrayList<>();

        factories.add(new ExtraTimeEnchantmentFactory());
        factories.add(new RevealEnchantmentFactory());
        factories.add(new ExtraLifeEnchantmentFactory());
        factories.add(new CloakOfProtectionEnchantmentFactory());
        factories.add(new SpeedUpEnchantmentFactory());
        factories.add(new LuringGemEnchantmentFactory());

    }

    /**
     * Requires: gridWidth > 0, gridHeight > 0, factories is not empty
     * Modifies: this.enchantments, this.enchantmentViews, game.getGrid().getTileAt(gridX, gridY)
     * Effects: Spawns a new enchantment at a random valid position within the grid and adds it to the enchantments list.
     *          Also creates a view for the enchantment and adds it to the enchantmentViews list.
     */
    public void spawnEnchantment(int gridWidth, int gridHeight) {
        int factoryIndex = random.nextInt(factories.size()); // Randomly select a factory
        EnchantmentFactory selectedFactory = factories.get(factoryIndex); // Select a factory

        int gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
        int gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 * PlayModePanel.offsetY) - 1);

        while (game.getGrid().getTileAt(gridX - PlayModePanel.offsetX, gridY - PlayModePanel.offsetY).isSolid()) {
            gridX = PlayModePanel.offsetX + random.nextInt(gridWidth - (2 * PlayModePanel.offsetX) - 1);
            gridY = PlayModePanel.offsetY + random.nextInt(gridHeight - (2 * PlayModePanel.offsetY) - 1);
        }

        BaseEnchantment enchantment = selectedFactory.createEnchantment(gridX, gridY, tileSize);
        game.getGrid().getTileAt(gridX - PlayModePanel.offsetX, gridY - PlayModePanel.offsetY).setSolid(true);
        enchantments.add(enchantment);

        // Create a view for the enchantment
        int drawX = gridX * tileSize;
        int drawY = gridY * tileSize;
        EnchantmentView view = new EnchantmentView(drawX, drawY, enchantment.getName());
        enchantmentViews.add(view);
    }

    public void updateEnchantments() {
        // Deactivate the enchantments whose time has expired
        updateActiveEnchantments();

        long currentTime = System.currentTimeMillis();
        // Spawn a new enchantment if the spawn interval has passed
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL) {
            spawnEnchantment(game.getGrid().getColumns(), game.getGrid().getRows());
            lastSpawnTime = currentTime;
        }

        // Use iterator to avoid ConcurrentModificationException
        Iterator<BaseEnchantment> iterator = enchantments.iterator();
        Iterator<EnchantmentView> viewIterator = enchantmentViews.iterator();

        while (iterator.hasNext()) {
            BaseEnchantment enchantment = iterator.next();

            // Check if the enchantment has expired (disappear after 6 seconds)
            if (currentTime - enchantment.getSpawnTime() > 6000) {
                int gridX = enchantment.getGridX() - PlayModePanel.offsetX;
                int gridY = enchantment.getGridY() - PlayModePanel.offsetY;

                // Free the tile and remove enchantment
                if (isValidTile(gridX, gridY)) {
                    game.getGrid().getTileAt(gridX, gridY).setSolid(false);
                }
                iterator.remove(); // Remove using iterator
                viewIterator.next(); // Move the view iterator to match removal in enchantmentViews
                viewIterator.remove();
            }
        }
    }

    public void updateActiveEnchantments() {
        List<BaseEnchantment> enchantments = game.getActiveEnchantments();
        Iterator<BaseEnchantment> iterator = enchantments.iterator();

        while (iterator.hasNext()) {
            BaseEnchantment enchantment = iterator.next();

            // Call the update method on each enchantment
            enchantment.update(game);

            // Optionally, check if the enchantment should be removed
            if (!enchantment.isActive()) {
                iterator.remove(); // Safe removal using iterator
            }
        }
    }



    private boolean isValidTile(int x, int y) {
        return x >= 0 && y >= 0 && x < game.getGrid().getColumns() && y < game.getGrid().getRows();
    }

    public void drawEnchantments(Graphics2D g2) {
        try {
            for (EnchantmentView view : enchantmentViews) {
                view.draw(g2);
            }
        } catch (Exception e) {
            // :D
        }

    }

    //COMMON FOR ALL ENCHANTMENTS TO COLLECT THEM
    public void enchantmentCollected(Tile clickedTile) {
        Tile playerTile = game.getGrid().getTileAt(game.getPlayer().getGridX(), game.getPlayer().getGridY());

        Iterator<BaseEnchantment> iterator = enchantments.iterator();
        Iterator<EnchantmentView> viewIterator = enchantmentViews.iterator();

        while (iterator.hasNext() && viewIterator.hasNext()) {
            BaseEnchantment enchantment = iterator.next();

            if ((enchantment.getGridX() - 2) == clickedTile.getGridX()
                    && (enchantment.getGridY() - 2) == clickedTile.getGridY()) {

                String enchantmentType = enchantment.getName();
                if (enchantmentType.equals("Reveal") || enchantmentType.equals("Cloak of Protection") ||
                        enchantmentType.equals("Speed Up") || enchantmentType.equals("Luring Gem")) {
                    game.getPlayer().getInventory().addItem(enchantment);
                } else {
                    enchantment.applyEffect(game);
                }

                int gridX = enchantment.getGridX() - PlayModePanel.offsetX;
                int gridY = enchantment.getGridY() - PlayModePanel.offsetY;

                // Remove the enchantment from the grid
                if (isValidTile(gridX, gridY)) {
                    game.getGrid().getTileAt(gridX, gridY).setSolid(false);
                }
                iterator.remove();
                viewIterator.next(); // Move view iterator to match removal in enchantmentViews
                viewIterator.remove();

                return;
            }

        }
    }


    public List<BaseEnchantment> getEnchantments() {
        return enchantments;
    }
    public BaseEnchantment getSelectedEnchantment(String type) {
        return enchantments.getFirst();
    }
}
