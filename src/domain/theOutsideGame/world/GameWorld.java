package domain.theOutsideGame.world;

import java.awt.Graphics;
import javax.swing.JPanel;
import domain.theOutsideGame.entities.Player;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.ui.HUD;

public class GameWorld extends JPanel {

    private Player player;
    private InputHandler inputHandler;
    private HUD hud;

    public GameWorld(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.player = new Player(100, 100, 50, 50, 5, inputHandler); // Initialize player at position (100, 100)
        this.hud = new HUD(); // Initialize HUD
        setFocusable(true); // Make the panel focusable for key events
    }

    public void update() {
        player.update(); // Update player movement
        inputHandler.update(); // Update input states
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.render(g); // Render player to screen
        hud.render(g);    // Render HUD (health, score, etc.)
    }
}
