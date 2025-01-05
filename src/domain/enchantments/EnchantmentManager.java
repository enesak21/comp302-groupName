package domain.enchantments;

import domain.UI.EnchantmentView;
import domain.enchantments.EnchantmentFactory;
import domain.game.CollisionChecker;
import domain.game.Game;
import domain.panels.PlayModePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnchantmentManager {

    private List<BaseEnchantment> enchantments;
    private List<EnchantmentView> enchantmentViews; // Store views separately
    private Random random;
    private int tileSize;
    private Game game;
    private final int SPAWN_INTERVAL = 12 * 1000; // Write it in milliseconds
    private CollisionChecker collisionChecker;
    private long lastSpawnTime; // Track spawn time in milliseconds

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
    }

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
        System.out.println("Enchantment is created" + enchantment.getName());

        // Create a view for the enchantment
        int drawX = gridX * tileSize;
        int drawY = gridY * tileSize;
        EnchantmentView view = new EnchantmentView(drawX, drawY, enchantment.getName());
        enchantmentViews.add(view);
    }

    public void updateEnchantments() {
        long currentTime = System.currentTimeMillis();

        // Spawn a new enchantment if the spawn interval has passed
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL) {
            spawnEnchantment(game.getGrid().getColumns(), game.getGrid().getRows());
            lastSpawnTime = currentTime;
        }

        for (int i = 0; i < enchantments.size(); i++) {
            BaseEnchantment enchantment = enchantments.get(i);

            // Check if the enchantment has expired (disappear after 6 seconds)
            if (currentTime - enchantment.getSpawnTime() > 6000) {
                int gridX = enchantment.getGridX() - PlayModePanel.offsetX;
                int gridY = enchantment.getGridY() - PlayModePanel.offsetY;

                // Free the tile and remove enchantment
                if (isValidTile(gridX, gridY)) {
                    game.getGrid().getTileAt(gridX, gridY).setSolid(false);
                }
                enchantments.remove(i);
                enchantmentViews.remove(i);
                System.out.println("Enchantment REMOVED: " + enchantment.getName());
                i--; // Adjust index to avoid skipping
            }
        }
    }

    private boolean isValidTile(int x, int y) {
        return x >= 0 && y >= 0 && x < game.getGrid().getColumns() && y < game.getGrid().getRows();
    }

    public void drawEnchantments(Graphics2D g2) {
        for (EnchantmentView view : enchantmentViews) {
            view.draw(g2);
        }
    }
}
