package domain.UI.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import domain.game.Hall;

public class GridPanel extends JPanel {

    private static final int GRID_SIZE = 16; // Number of cells in each dimension
    private Hall hall;
    private final HashMap<String, String> structureMap;
    private final BuildModePanel parentPanel;

    public GridPanel(Hall hall, HashMap<String, String> structureMap, BuildModePanel parentPanel) {
        this.hall = hall;
        this.structureMap = structureMap;
        this.parentPanel = parentPanel;

        // You can keep a preferred size as a default, but it won't stop resizing:
        setPreferredSize(new Dimension(16 * 32, 16 * 32));

        initializeMouseListener();
    }

    /**
     * A helper method to compute the current cell size based on panel dimensions.
     * Returns at least 1 to avoid division-by-zero issues when panel is very small.
     */
    private int getCellSize() {
        int cellSize = Math.min(getWidth() / GRID_SIZE, getHeight() / GRID_SIZE);
        return Math.max(cellSize, 1);  // Ensure it's never zero
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Compute the current cellSize for click detection
                int cellSize = getCellSize();

                // Translate the mouse pixel to grid coordinates
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;

                // Check if we're within the grid bounds
                if (x >= 1 && x < (GRID_SIZE - 1) && y >= 1 && y < (GRID_SIZE - 1)) {
                    String selectedStructure = parentPanel.getSelectedStructure();

                    if ("eraser".equals(selectedStructure)) {
                        hall.removeStructure(x, y);
                    } else if (selectedStructure != null) {
                        hall.placeStructure(x, y, selectedStructure);
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Compute dynamic cellSize
        int cellSize = getCellSize();
        // The total pixel width/height of our 16×16 grid
        int gridWidth = cellSize * GRID_SIZE;
        int gridHeight = cellSize * GRID_SIZE;

        // 1. Draw the background scaled to the entire panel
        Image bgImage = new ImageIcon("src/resources/tiles/background.png").getImage();
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        // 2. Load wall/corner images (each drawn at 'cellSize')
        Image topLeftCorner     = new ImageIcon("src/resources/tiles/walls/topLeftCorner.png").getImage();
        Image topRightCorner    = new ImageIcon("src/resources/tiles/walls/topRightCorner.png").getImage();
        Image bottomLeftCorner  = new ImageIcon("src/resources/tiles/walls/bottomLeftCorner.png").getImage();
        Image bottomRightCorner = new ImageIcon("src/resources/tiles/walls/bottomRightCorner.png").getImage();
        Image topWall           = new ImageIcon("src/resources/tiles/walls/frontWall.png").getImage();
        Image leftWall          = new ImageIcon("src/resources/tiles/walls/leftWall.png").getImage();
        Image rightWall         = new ImageIcon("src/resources/tiles/walls/rightWall.png").getImage();

        // -- Draw corners --
        g.drawImage(topLeftCorner, 0, 0, cellSize, cellSize, this);
        g.drawImage(topRightCorner, gridWidth - cellSize, 0, cellSize, cellSize, this);
        g.drawImage(bottomLeftCorner, 0, gridHeight - cellSize, cellSize, cellSize, this);
        g.drawImage(bottomRightCorner, gridWidth - cellSize, gridHeight - cellSize, cellSize, cellSize, this);

        // -- Draw top & bottom walls --
        for (int x = cellSize; x < gridWidth - cellSize; x += cellSize) {
            g.drawImage(topWall, x, 0, cellSize, cellSize, this);                      // top
            g.drawImage(topWall, x, gridHeight - cellSize, cellSize, cellSize, this); // bottom
        }

        // -- Draw left & right walls --
        for (int y = cellSize; y < gridHeight - cellSize; y += cellSize) {
            g.drawImage(leftWall, 0, y, cellSize, cellSize, this);                       // left
            g.drawImage(rightWall, gridWidth - cellSize, y, cellSize, cellSize, this);  // right
        }


        // 3. Draw grid lines (if desired) over the entire grid area
        g.setColor(Color.GRAY);
        for (int i = 1; i < GRID_SIZE; i++) {
            // Vertical lines
            g.drawLine(i * cellSize, cellSize, i * cellSize, gridHeight - cellSize);
            // Horizontal lines
            g.drawLine(cellSize, i * cellSize, gridWidth - cellSize, i * cellSize);
        }

        // 4. Draw structures (scaled to cellSize) from the hall's grid
        String[][] grid = hall.getGrid();
        for (int gx = 0; gx < GRID_SIZE; gx++) {
            for (int gy = 0; gy < GRID_SIZE; gy++) {
                String structureKey = grid[gx][gy];
                if (structureKey != null) {
                    Image structImg = new ImageIcon(structureMap.get(structureKey)).getImage();
                    g.drawImage(structImg,
                            gx * cellSize,       // x position in pixels
                            gy * cellSize,       // y position in pixels
                            cellSize,            // width
                            cellSize,            // height
                            null);
                }
            }
        }
    }

    /** Update the hall reference and redraw. */
    public void setHall(Hall hall) {
        this.hall = hall;
        repaint();
    }
}
