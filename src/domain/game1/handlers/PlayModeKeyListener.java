package domain.game1.handlers;

import domain.game1.game.Game;
import domain.game1.UI.panels.PlayModePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayModeKeyListener extends KeyAdapter {

    private final PlayModePanel playModePanel;
    private final Game game;
    private boolean bPressed = false;

    public PlayModeKeyListener(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;
        this.game = playModePanel.getGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                handleRevealEnchantment();
                break;
            case KeyEvent.VK_P:
                handleCloakOfProtectionEnchantment();
                break;
            case KeyEvent.VK_B:
                handleLuringGemPress();
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                handleLuringGemMovement(e);
                break;
            case KeyEvent.VK_Q:
                handleSpeedUpEnchantment();
                break;
            case KeyEvent.VK_ESCAPE:
                togglePause();
                break;
            default:
                break;
        }
    }

    private void handleRevealEnchantment() {
        if (game.getPlayer().getInventory().isInInventory("Reveal")) {
            game.getPlayer().useRevealEnchantment();
        } else {
            System.out.println("No Reveal enchantment in inventory.");
        }
    }

    private void handleCloakOfProtectionEnchantment() {
        if (game.getPlayer().getInventory().isInInventory("Cloak of Protection")) {
            game.getPlayer().useCloakOfProtectionEnchantment();
        }
    }

    private void handleLuringGemPress() {
        if (game.getPlayer().getInventory().isInInventory("Luring Gem")) {
            bPressed = true;
        }
    }

    private void handleLuringGemMovement(KeyEvent e) {
        if (!bPressed) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> game.getPlayer().useLuringGemEnchantment(0);
            case KeyEvent.VK_S -> game.getPlayer().useLuringGemEnchantment(1);
            case KeyEvent.VK_A -> game.getPlayer().useLuringGemEnchantment(2);
            case KeyEvent.VK_D -> game.getPlayer().useLuringGemEnchantment(3);
        }
        bPressed = false;
    }

    private void handleSpeedUpEnchantment() {
        if (game.getPlayer().getInventory().isInInventory("Speed Up")) {
            game.getPlayer().useSpeedUpManagement();
        }
    }

    private void togglePause() {
        if (playModePanel.getIsPaused()) {
            playModePanel.resumeGame();
        } else {
            playModePanel.pauseGame();
        }
        playModePanel.repaint();
    }
}
