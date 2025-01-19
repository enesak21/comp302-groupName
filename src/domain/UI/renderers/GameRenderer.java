package domain.UI.renderers;

import domain.UI.ArrowAnimationView;
import domain.UI.GridView;
import domain.UI.MonsterView;
import domain.UI.PlayerView;
import domain.entity.monsters.BaseMonster;
import domain.entity.playerObjects.Player;
import domain.game.Grid;
import domain.enchantments.EnchantmentManager;
import domain.game.Hall;
import domain.game.Tile;

import java.awt.*;
import java.util.List;

public class GameRenderer {

    private final Grid grid;
    private final Player player;
    private final List<BaseMonster> monsters;
    private final EnchantmentManager enchantmentManager;
    private final GridView gridView;
    private final PlayerView playerView;
    private final WallRenderer wallRenderer;
    private final ArrowAnimationRenderer arrowAnimationRenderer;
    private Hall hall;

    // Constructor
    public GameRenderer(Hall hall, Grid grid, Player player, List<BaseMonster> monsters,
                        EnchantmentManager enchantmentManager) {
        this.grid = grid;
        this.player = player;
        this.monsters = monsters;
        this.enchantmentManager = enchantmentManager;
        this.hall = hall;

        this.gridView = new GridView(grid);
        this.playerView = new PlayerView(player);

        this.wallRenderer = new WallRenderer(hall, grid.getTileSize());
        this.arrowAnimationRenderer = new ArrowAnimationRenderer();
    }

    // Add an arrow animation
    public void addArrowAnimation(ArrowAnimationView animation) {
        arrowAnimationRenderer.addArrowAnimation(animation);
    }

    // Update method for the renderer
    public void update() {
        arrowAnimationRenderer.update();
    }

    // Render the entire game
    public void render(Graphics2D g2, int offsetX, int offsetY, int tileSize) {
        drawGrid(g2, offsetX, offsetY, tileSize);
        drawWalls(g2, offsetX, offsetY);
        drawPlayer(g2);
        drawMonsters(g2);
        drawEnchantments(g2);
        drawHighlightedRegions(g2, offsetX, offsetY, tileSize);
        drawWalls(g2, offsetX, offsetY);
        drawStructures(g2, offsetX, offsetY, tileSize);

        // Draw arrow animations
        arrowAnimationRenderer.draw(g2);
    }

    // Render the grid
    private void drawGrid(Graphics2D g2, int offsetX, int offsetY, int tileSize) {
        gridView.draw(g2, offsetX * tileSize, offsetY * tileSize);
    }

    // Render the structures
    private void drawStructures(Graphics2D g2, int offsetX, int offsetY, int tileSize) {
        gridView.drawStructures(g2, offsetX * tileSize, offsetY * tileSize);
    }

    // Render the player
    private void drawPlayer(Graphics2D g2) {
        playerView.draw(g2);
    }

    // Render monsters
    private void drawMonsters(Graphics2D g2) {
        try {
            for (BaseMonster monster : monsters) {
                MonsterView monsterView = new MonsterView(monster);
                monsterView.draw(g2);
            }
        }
        catch (Exception e) {
            // :D
        }
    }

    // Render enchantments
    private void drawEnchantments(Graphics2D g2) {
        enchantmentManager.drawEnchantments(g2);
    }

    // Highlight tiles or regions
    private void drawHighlightedRegions(Graphics2D g2, int offsetX, int offsetY, int tileSize) {
        for (int x = 0; x < grid.getColumns(); x++) {
            for (int y = 0; y < grid.getRows(); y++) {
                Tile tile = grid.getTileAt(x, y);
                if (tile.isHighlighted()) {
                    int drawX = (offsetX + x) * tileSize;
                    int drawY = (offsetY + y) * tileSize;

                    g2.setColor(new Color(255, 255, 0, 128)); // Yellow with transparency
                    g2.fillRect(drawX, drawY, tileSize, tileSize);
                    g2.setColor(Color.YELLOW);
                    g2.drawRect(drawX, drawY, tileSize, tileSize);
                }
            }
        }
    }

    // Render walls
    private void drawWalls(Graphics2D g2, int offsetX, int offsetY) {
        wallRenderer.drawWalls(g2, offsetX, offsetY, grid.getColumns(), grid.getRows());
        wallRenderer.drawCorners(g2, offsetX, offsetY, grid.getColumns(), grid.getRows());
    }
}
