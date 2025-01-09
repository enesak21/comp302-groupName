package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import org.junit.BeforeClass;

import domain.game.Grid;

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
    public void testTileGenerator() {
        grid.tileGenerator();
        for (int column = 0; column < grid.getColumns(); column++) {
            for (int row = 0; row < grid.getRows(); row++) {
                assertNotNull(grid.getTileAt(column, row));
            }
        }
    }
}
