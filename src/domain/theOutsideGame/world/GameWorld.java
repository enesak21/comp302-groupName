package domain.theOutsideGame.world;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import domain.theOutsideGame.entities.Player;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.ui.HUD;

public class GameWorld extends JPanel {

    private Player player;
    private InputHandler inputHandler;
    private HUD hud;
    private Image grassBackground; // To hold the grass background image
    private final int TILE_SIZE = 50; // Size of the background tile (change this based on your image)

    public GameWorld(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        this.player = new Player(100, 100, 50, 50, 5, inputHandler); // Initialize player at position (100, 100)
        this.hud = new HUD(); // Initialize HUD
        this.grassBackground = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/grass.png"); // Load the grass image
        setFocusable(true); // Make the panel focusable for key events
    }

    public void update() {
        player.update(); // Update player movement
        inputHandler.update(); // Update input states
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grass background multiple times to cover the entire screen
        int width = getWidth();
        int height = getHeight();

        // Calculate how many tiles fit horizontally and vertically
        for (int x = 0; x < width; x += TILE_SIZE) {
            for (int y = 0; y < height; y += TILE_SIZE) {
                // Draw the grass tile at each (x, y) position
                g.drawImage(grassBackground, x, y, TILE_SIZE, TILE_SIZE, this);
            }
        }

        player.render(g); // Render player to screen
        hud.render(g);    // Render HUD (health, score, etc.)
    }
}
