package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import org.junit.BeforeClass;

import domain.game.Grid;
import domain.game.Tile;

public class GridTest {

    static Grid grid;

    @BeforeClass
    static public void setUpBeforeClass() throws Exception {
        grid = new Grid(32);
    }

    @Test
    public void testExample() {
        assertEquals(1, 1);
    }

    @Test
    public void testTileGeneratorTileNotNull() {
        grid.tileGenerator();
        for (int column = 0; column < grid.getColumns(); column++) {
            for (int row = 0; row < grid.getRows(); row++) {
                assertNotNull(grid.getTileAt(column, row));
            }
        }
    }

    @Test
    public void testTileGeneratorGridDimensions() {
        grid.tileGenerator();
        assertEquals("Grid should have correct number of rows", 16, grid.getRows());
        assertEquals("Grid should have correct number of columns", 16, grid.getColumns());
    }

    @Test
    public void testTileGeneratorUniqueness() {
        grid.tileGenerator();
        Tile tile1 = grid.getTileAt(0, 0);
        Tile tile2 = grid.getTileAt(1, 1);
        assertNotEquals("Tiles at different positions should not be identical", tile1, tile2);
    }


}
