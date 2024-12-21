package main;

import javax.swing.*;

import domain.game.Grid;
import domain.structures.Structure;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BuildModePanel extends JPanel {

    private static final int ORIGINAL_TILE_SIZE = 16; // Original size of each tile in the grid
    private static final int SCALE = 2; // Size of each tile in the grid
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // Size of each tile in the grid

    private static final int GRID_ROWS = 16; // Number of rows in the grid
    private static final int GRID_COLUMNS = 16; // Number of columns in the grid
    private static final int GRID_WIDTH = GRID_COLUMNS * TILE_SIZE; // Width of the grid
    private static final int GRID_HEIGHT = GRID_ROWS * TILE_SIZE; // Height of the grid

    private static final int SCREEN_WIDTH = 24 * TILE_SIZE;
    private static final int SCREEN_HEIGHT = 20 * TILE_SIZE;

    private final int TOOLBAR_WIDTH = 200; // Width of the toolbar

    private Structure selectedStructure; // Track the selected structure
    private JPanel toolbar; // Toolbar for selecting structures
    

    public BuildModePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        
        createToolbar();

    }

    private void createToolbar() {

    }


}
