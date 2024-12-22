package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import domain.game.Hall;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {

    private static final int GRID_SIZE = 16; // Number of cells in the grid
    private static final int CELL_SIZE = 32; // Restore original cell size
    private Hall hall;
    private final HashMap<String, String> structureMap;
    private final BuildModePanel parentPanel;

    public GridPanel(Hall hall, HashMap<String, String> structureMap, BuildModePanel parentPanel) {
        this.hall = hall;
        this.structureMap = structureMap;
        this.parentPanel = parentPanel;

        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        initializeMouseListener();
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;

                String selectedStructure = parentPanel.getSelectedStructure();

                if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
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

        // Load and draw the custom background image
        Image bgImage = new ImageIcon("src/resources/tiles/background.png").getImage();
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        // Load wall images
        Image topLeftCorner = new ImageIcon("src/resources/tiles/walls/topLeftCorner.png").getImage();
        Image topRightCorner = new ImageIcon("src/resources/tiles/walls/topRightCorner.png").getImage();
        Image bottomLeftCorner = new ImageIcon("src/resources/tiles/walls/bottomLeftCorner.png").getImage();
        Image bottomRightCorner = new ImageIcon("src/resources/tiles/walls/bottomRightCorner.png").getImage();
        Image topWall = new ImageIcon("src/resources/tiles/walls/frontWall.png").getImage();
        Image leftWall = new ImageIcon("src/resources/tiles/walls/leftWall.png").getImage();
        Image rightWall = new ImageIcon("src/resources/tiles/walls/rightWall.png").getImage();

        // Grid dimensions
        int gridWidth = GRID_SIZE * CELL_SIZE;
        int gridHeight = GRID_SIZE * CELL_SIZE;

        // Draw corners
        g.drawImage(topLeftCorner, 0, 0, CELL_SIZE, CELL_SIZE, this); // Top-left
        g.drawImage(topRightCorner, gridWidth - CELL_SIZE, 0, CELL_SIZE, CELL_SIZE, this); // Top-right
        g.drawImage(bottomLeftCorner, 0, gridHeight - CELL_SIZE, CELL_SIZE, CELL_SIZE, this); // Bottom-left
        g.drawImage(bottomRightCorner, gridWidth - CELL_SIZE, gridHeight - CELL_SIZE, CELL_SIZE, CELL_SIZE, this); // Bottom-right

        // Draw top and bottom walls
        for (int x = CELL_SIZE; x < gridWidth - CELL_SIZE; x += CELL_SIZE) {
            g.drawImage(topWall, x, 0, CELL_SIZE, CELL_SIZE, this); // Top wall
            g.drawImage(topWall, x, gridHeight - CELL_SIZE, CELL_SIZE, CELL_SIZE, this); // Bottom wall
        }

        // Draw left and right walls
        for (int y = CELL_SIZE; y < gridHeight - CELL_SIZE; y += CELL_SIZE) {
            g.drawImage(leftWall, 0, y, CELL_SIZE, CELL_SIZE, this); // Left wall
            g.drawImage(rightWall, gridWidth - CELL_SIZE, y, CELL_SIZE, CELL_SIZE, this); // Right wall
        }

        // Draw grid lines
        g.setColor(Color.GRAY);
        for (int i = 0; i <= GRID_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, gridHeight);
            g.drawLine(0, i * CELL_SIZE, gridWidth, i * CELL_SIZE);
        }

        // Draw structures on the grid
        String[][] grid = hall.getGrid();
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                String structureKey = grid[x][y];
                if (structureKey != null) {
                    g.drawImage(new ImageIcon(structureMap.get(structureKey)).getImage(),
                            x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }
    }

    public void setHall(Hall hall) {
        this.hall = hall;
        repaint();
    }
}



