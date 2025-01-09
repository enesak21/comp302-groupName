package domain.handlers.mouseHandlers;

import domain.enchantments.EnchantmentManager;
import domain.game.Grid;
import domain.game.SearchRuneController;
import domain.game.Tile;
import domain.UI.panels.PlayModePanel;

import java.awt.*;

public class GridMouseListener {

    private final PlayModePanel playModePanel;

    private final int gridColumns;
    private final int gridRows;
    private final int tileSize;
    private final int offsetX;
    private final int offsetY;
    private final Grid grid;
    private SearchRuneController searchRuneController;
    private EnchantmentManager enchantmentManager;


    public GridMouseListener(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        this.gridColumns = playModePanel.getGridColumns();
        this.gridRows = playModePanel.getGridRows();
        this.tileSize = playModePanel.getTileSize();
        this.offsetX = playModePanel.getOffsetX();
        this.offsetY = playModePanel.getOffsetY();
        this.grid = playModePanel.getGrid();

        this.searchRuneController = new SearchRuneController(playModePanel);
        this.enchantmentManager = playModePanel.getEnchantmentManager();


    }
    public void handleGridClick(Point rawClickPoint) {
        // This method is used to handle a click in the grid region

        Point gridClickPoint = transformToGridCoordinates(rawClickPoint);


        // Possibly do something with the grid click point

        int gridX = gridClickPoint.x;
        int gridY = gridClickPoint.y;

        Tile clickedTile = grid.getTileAt(gridX, gridY);

        searchRuneController.runeCollected(clickedTile);
        if (enchantmentManager != null) {

            enchantmentManager.enchantmentCollected(clickedTile);
        }

    }

    private Point transformToGridCoordinates(Point rawClickPoint) {
        // Transform the raw click point to grid coordinates
        int x = (rawClickPoint.x / tileSize) - offsetX;
        int y = (rawClickPoint.y / tileSize) - offsetY;

        return new Point(x, y);
    }


}
