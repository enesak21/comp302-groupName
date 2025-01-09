package test;

import static org.junit.Assert.*;

import domain.game.Game;
import domain.game.Tile;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(null, 32, null, null,null);
    }

    @Test
    public void testIsInRangeWithinRange() {
        // Arrange
        Tile tile1 = new Tile(0, 0, false, null);
        Tile tile2 = new Tile(1, 1, false, null);
        float range = 2.0f;

        // Act
        boolean result = Game.isInRange(tile1, tile2, range);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsInRangeOutOfRange() {
        // Arrange
        Tile tile1 = new Tile(0, 0, false, null);
        Tile tile2 = new Tile(5, 5, false, null);
        float range = 2.0f;

        // Act
        boolean result = Game.isInRange(tile1, tile2, range);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsInRangeAtEdgeOfRange() {
        // Arrange
        Tile tile1 = new Tile(0, 0, false, null);
        Tile tile2 = new Tile(3, 4, false, null); // Distance is 5
        float range = 5.0f;

        // Act
        boolean result = Game.isInRange(tile1, tile2, range);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testTogglePause() {
        game.togglePause();
        assertTrue("Game should be paused", game.isPaused());
    }

    @Test
    public void testTogglePauseResume() {
        game.togglePause();
        game.togglePause();
        assertFalse("Game should not be paused", game.isPaused());
    }

    @Test
    public void testTogglePauseTimesTen() {
        boolean initialPauseState = game.isPaused();

        for (int i = 0; i < 10; i++) {
            game.togglePause();
            initialPauseState = !initialPauseState;
            assertEquals("Pause state should be toggled",initialPauseState, game.isPaused());
        }
    }


    }