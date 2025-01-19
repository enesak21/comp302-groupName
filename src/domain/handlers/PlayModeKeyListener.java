package domain.handlers;

import domain.game.Game;
import domain.UI.panels.PlayModePanel;

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
                if (game.getPlayer().getInventory().isInInventory("Reveal")) {
                    game.getPlayer().useRevealEnchantment();
                } else {
                    System.out.println("No Reveal enchantment in inventory.");
                }
                break;
            case KeyEvent.VK_P:
                if (game.getPlayer().getInventory().isInInventory("Cloak of Protection")) {
                    game.getPlayer().useCloakOfProtectionEnchantment();
                } else {
                    System.out.println("No Cloak of Protection enchantment in inventory.");
                }
                break;
            case KeyEvent.VK_B:
                if (game.getPlayer().getInventory().isInInventory("Luring Gem")) {
                    bPressed = true;
                } else {
                    System.out.println("No Luring enchantment in inventory.");
                }
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                handleLuringGemMovement(e);
                break;
            case KeyEvent.VK_Q:
                if (game.getPlayer().getInventory().isInInventory("Speed Up")) {
                    game.getPlayer().useSpeedUpManagement();
                } else {
                    System.out.println("No Speed Up enchantment in inventory.");
                }
                break;
            case KeyEvent.VK_ESCAPE:
                togglePause();
                break;
            default:
                break;
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

    private void togglePause() {
        if (playModePanel.getIsPaused()) {
            playModePanel.resumeGame();
        } else {
            playModePanel.pauseGame();
        }
    }
}
