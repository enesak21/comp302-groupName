package domain.UI.mouseHandlers;

import domain.panels.PlayModePanel;

import java.awt.*;

public class GridMouseListener {

    private final PlayModePanel playModePanel;

    private final int gridColumns;
    private final int gridRows;
    private final int tileSize;
    private final int offsetX;
    private final int offsetY;


    public GridMouseListener(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        this.gridColumns = playModePanel.getGridColumns();
        this.gridRows = playModePanel.getGridRows();
        this.tileSize = playModePanel.getTileSize();
        this.offsetX = playModePanel.getOffsetX();
        this.offsetY = playModePanel.getOffsetY();
    }

    public void handleGridClick(Point rawClickPoint) {
        // This method is used to handle a click in the grid region

        Point gridClickPoint = transformToGridCoordinates(rawClickPoint);

        System.out.println("Grid click at: " + gridClickPoint);

        // Possibly do something with the grid click point

        int gridX = gridClickPoint.x;
        int gridY = gridClickPoint.y;

        playModePanel.runeCollected(gridX, gridY);

    }

    private Point transformToGridCoordinates(Point rawClickPoint) {
        // This method is used to transform the raw click point to grid coordinates

        int x = (rawClickPoint.x / tileSize) - offsetX;
        int y = (rawClickPoint.y / tileSize) - offsetY;

        return new Point(x, y);
    }


}
