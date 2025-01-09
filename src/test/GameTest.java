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