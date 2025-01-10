package test;

import static org.junit.Assert.*;

import domain.entity.playerObjects.Player;
import domain.game.*;
import domain.game.SearchRuneController;
import domain.panels.PlayModePanel;
import domain.structures.Structure;
import main.PlayerInputHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchRuneControllerTest {

    private SearchRuneController searchRuneController;
    private PlayModePanel playModePanel;
    private Game game;
    private Player player;
    private Grid grid;
    private PlayerInputHandler playerInputHandler;
    @Before
    public void setup() {
        Hall hall1 = new HallOfAir();
        Hall hall2 = new HallOfEarth();
        List halls = new ArrayList();
        halls.add(hall1);
        halls.add(hall2);
        playModePanel = new PlayModePanel(halls);
        playerInputHandler = new PlayerInputHandler();
        player = Player.getInstance("TestPlayer",0,0,32,playModePanel,playerInputHandler);
        grid = new Grid(32);
        game = new Game(player, 32, playModePanel, grid, searchRuneController);
        playModePanel.setGame(game);
        searchRuneController = new SearchRuneController(playModePanel);
    }

    @Test
    public void testRuneCollectedWithRuneTile() { // The structure contains a rune
        // Arrange
        Tile runeTile = grid.getTileAt(2, 3);
        Structure structure = new Structure("chest",runeTile);
        structure.setHasRune(true);
        runeTile.addStructure(structure);
        player.setGridX(2);
        player.setGridY(2);

        // Act
        searchRuneController.runeCollected(runeTile);

        // Assert
        assertFalse(runeTile.getStructure().hasRune());
        // Add assertion to verify the game moved to the next hall
    }

    @Test
    public void testRuneCollectedWithoutRuneTile() { // The structure does not contain a rune
        // Arrange
        Tile emptyTile = grid.getTileAt(2, 3);
        Structure structure = new Structure("chest",emptyTile);
        structure.setHasRune(false);
        emptyTile.addStructure(structure);
        player.setGridX(2);
        player.setGridY(2);

        // Act
        searchRuneController.runeCollected(emptyTile);

        // Assert
        assertFalse(emptyTile.getStructure().hasRune());
        // Add assertion to verify the game did not move to the next hall
    }

    @Test
    public void testRuneCollectedOutOfRange() { // The player is not in range of the rune
        // Arrange
        Tile distantRuneTile = grid.getTileAt(5, 5);
        Structure structure = new Structure("chest",distantRuneTile);
        structure.setHasRune(true);
        distantRuneTile.addStructure(structure);
        player.setGridX(2);
        player.setGridY(2);

        // Act
        searchRuneController.runeCollected(distantRuneTile);

        // Assert
        assertTrue(distantRuneTile.getStructure().hasRune());
        // Add assertion to verify the game did not move to the next hall
    }
}