package main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import domain.game.Hall;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {
    private Hall hall;
    private final HashMap<String, String> structureMap;
    private final int cellSize;
    private final int gridSize;
    private final BuildModePanel parentPanel;

    public GridPanel(Hall hall, HashMap<String, String> structureMap, int cellSize, int gridSize, BuildModePanel parentPanel) {
        this.hall = hall;
        this.structureMap = structureMap;
        this.cellSize = cellSize;
        this.gridSize = gridSize;
        this.parentPanel = parentPanel;

        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
        initializeMouseListener();
    }

    private void initializeMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;

                String selectedStructure = parentPanel.getSelectedStructure();

                if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
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

    public void setHall(Hall hall) {
        this.hall = hall;
        repaint();
    }

    public String[][] getCurrentGrid() {
        return hall.getGrid();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String[][] grid = hall.getGrid();

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                String structureKey = grid[x][y];
                if (structureKey != null) {
                    g.drawImage(new ImageIcon(structureMap.get(structureKey)).getImage(),
                            x * cellSize, y * cellSize, cellSize, cellSize, null);
                }
            }
        }

        g.setColor(Color.GRAY);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, gridSize * cellSize);
            g.drawLine(0, i * cellSize, gridSize * cellSize, i * cellSize);
        }
    }
}
