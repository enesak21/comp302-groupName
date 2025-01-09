package test;

import static org.junit.Assert.*;

import domain.enchantments.BaseEnchantment;
import domain.enchantments.EnchantmentManager;
import domain.entity.playerObjects.Player;
import domain.game.*;
import domain.panels.PlayModePanel;
import main.PlayerInputHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentManagerTest {

    private EnchantmentManager enchantmentManager;
    private Game game;
    private int tileSize = 32;
    private Player player;
    private PlayModePanel playModePanel;
    private Grid grid;
    private SearchRuneController searchRuneController;
    private PlayerInputHandler playerInputHandler;

    @Before
    public void setup() {
        Hall hall = new HallOfWater();
        List<Hall> halls = new ArrayList<>();
        halls.add(hall);
        playerInputHandler = new PlayerInputHandler();
        grid = new Grid(tileSize);
        playModePanel = new PlayModePanel(halls);
        player= Player.getInstance("Test", 0, 0,tileSize,playModePanel,playerInputHandler);
        game = new Game(player, tileSize, playModePanel, grid, searchRuneController);
        enchantmentManager = new EnchantmentManager(game, tileSize);
    }

    @Test
    public void testGetEnchantmentsWhenEmpty() { // Test when there are no enchantments
        // Act
        List<BaseEnchantment> enchantments = enchantmentManager.getEnchantments();

        // Assert
        assertTrue(enchantments.isEmpty());
    }

    @Test
    public void testGetEnchantmentsWithOneEnchantment() { // Test when there is one enchantment
        // Arrange
        BaseEnchantment enchantment = new BaseEnchantment(0, 0, tileSize) {
            @Override
            public void applyEffect(Game game) {}
        };
        enchantmentManager.getEnchantments().add(enchantment);

        // Act
        List<BaseEnchantment> enchantments = enchantmentManager.getEnchantments();

        // Assert
        assertEquals(1, enchantments.size());
        assertEquals(enchantment, enchantments.get(0));
    }

    @Test
    public void testGetEnchantmentsWithMultipleEnchantments() { // Test when there are multiple enchantments
        // Arrange
        BaseEnchantment enchantment1 = new BaseEnchantment(0, 0, tileSize) {
            @Override
            public void applyEffect(Game game) {}
        };
        BaseEnchantment enchantment2 = new BaseEnchantment(1, 1, tileSize) {
            @Override
            public void applyEffect(Game game) {}
        };
        enchantmentManager.getEnchantments().add(enchantment1);
        enchantmentManager.getEnchantments().add(enchantment2);

        // Act
        List<BaseEnchantment> enchantments = enchantmentManager.getEnchantments();

        // Assert
        assertEquals(2, enchantments.size());
        assertTrue(enchantments.contains(enchantment1));
        assertTrue(enchantments.contains(enchantment2));
    }

    @Test
    public void testSpawnEnchantmentAddsToList() { // Test if a new enchantment is added to the list
        // Act
        enchantmentManager.spawnEnchantment(grid.getColumns(), grid.getRows());

        // Assert
        assertFalse(enchantmentManager.getEnchantments().isEmpty());
    }

    @Test
    public void testSpawnEnchantmentAddsAtValidPosition() { // Test if the enchantment is added at a valid position
        // Act
        enchantmentManager.spawnEnchantment(grid.getColumns(), grid.getRows());

        // Assert
        BaseEnchantment enchantment = enchantmentManager.getEnchantments().get(0);
        Tile tile = game.getGrid().getTileAt(enchantment.getGridX() - PlayModePanel.offsetX, enchantment.getGridY() - PlayModePanel.offsetY);
        assertTrue(tile.isSolid());
    }

    @Test
    public void testSpawnEnchantmentPositionIsCorrect() { // Test if the enchantment's position is correctly set
        // Act
        enchantmentManager.spawnEnchantment(grid.getColumns(), grid.getRows());

        // Assert
        BaseEnchantment enchantment = enchantmentManager.getEnchantments().get(0);
        int gridX = enchantment.getGridX();
        int gridY = enchantment.getGridY();
        assertTrue(gridX >= PlayModePanel.offsetX && gridX < grid.getColumns() - PlayModePanel.offsetX);
        assertTrue(gridY >= PlayModePanel.offsetY && gridY < grid.getRows() - PlayModePanel.offsetY);
    }
}