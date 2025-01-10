package test;

import domain.entity.playerObjects.Player;
import static org.junit.Assert.*;

import domain.game.*;
import domain.panels.PlayModePanel;
import main.PlayerInputHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Player player;
    private PlayModePanel playModePanel;
    PlayerInputHandler inputHandler;

    @Before
    public void setUp() {
        List<Hall> halls = new ArrayList<>();
        halls.add(new HallOfFire());
        halls.add(new HallOfWater());
        halls.add(new HallOfEarth());
        halls.add(new HallOfAir());
        playModePanel = new PlayModePanel(halls);
        inputHandler = new PlayerInputHandler();
        player= Player.getInstance("Hakan", 0, 0, 32, playModePanel, inputHandler);
    }

    @Test
    public void testRepOk_ValidState() {
        assertTrue("Player should be in a valid state initially", player.repOk());
    }

    @Test
    public void testPlayerHealthPositive() {
        assertTrue("Player health should be positive", player.repOk());
    }

    @Test
    public void testPlayerPositionValid() {
        assertTrue("Player position should be valid", player.repOk());
    }

    @Test
    public void testPlayerSpeed(){
        assertTrue("Player speed should be positive", player.repOk());
    }


    @Test
    public void testRepOk_InvalidGridPosition() {
        // Oyuncunun grid pozisyonunu geçersiz bir değere ayarla
        player.restart("Hero", -1, -1, 32, playModePanel, inputHandler);
        assertFalse("Player with invalid grid position should not be valid", player.repOk());
    }

    @Test
    public void testSetHealth() {
        player.setHealth(3);
        assertEquals("Player health should be set to 3", 3, player.getHealth());
    }

    @Test
    public void testSetInvalidHealth() {
        player.setHealth(5);
        assertNotEquals("Player health cannot be set to 5", 5, player.getHealth());
    }

    @Test
    public void testReduceHealth() {
        player.reduceHealth();
        assertEquals("Player health should be reduced by 1", 3, player.getHealth());
    }

    @Test
    public void testUseEnchantment() {
        assertFalse("Player should not be able to use an unknown enchantment", player.useEnchantment("unknown"));
    }

}
