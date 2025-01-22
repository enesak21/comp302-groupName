package domain.theOutsideGame.launcher;

import javax.swing.*;
import domain.theOutsideGame.world.GameWorld;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.ui.HUD;

public class Game {

    private GameWorld gameWorld;
    private InputHandler inputHandler;
    private HUD hud;
    private JFrame frame;
    private Timer gameTimer;

    public Game() {
        // Initialize game components
        inputHandler = new InputHandler();
        gameWorld = new GameWorld(inputHandler, this);
        hud = new HUD();

        // Setup the game window
        setupWindow();
    }

    private void setupWindow() {
        frame = new JFrame("The Outside");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Add game panel (game world, input handler, etc.)
        frame.add(gameWorld);
        frame.setFocusable(true);
        frame.addKeyListener(inputHandler);

        frame.setVisible(true);

        // Start the game loop (handled by GameWorld)
        gameLoop();
    }

    private void gameLoop() {
        // The domain.main game loop runs continuously to update game state and render
        gameTimer = new Timer(16, e -> {
            inputHandler.update(); // Update input handler to check for key presses
            gameWorld.update();    // Update game objects (e.g., player)
            gameWorld.repaint();   // Redraw game world
            hud.update();          // Update HUD (e.g., health, score)
        });
        gameTimer.start();
    }

    public static void launch(String[] args) {
        // Start the game
        new Game();
    }

    public JFrame getFrame() {
        gameTimer.stop();
        return frame;
    }
}
