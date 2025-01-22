package domain.theOutsideGame.world;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import domain.theOutsideGame.entities.Player;
import domain.theOutsideGame.input.InputHandler;
import domain.theOutsideGame.launcher.Game;
import domain.theOutsideGame.ui.HUD;

public class GameWorld extends JPanel {

    private final Image treeImage;
    private final Image caveImage;
    private final Image wallImage;
    private final Game game;
    private final Image ArwenImage;
    private Player player;
    private InputHandler inputHandler;
    private HUD hud;
    private Image grassBackground;
    private final int TILE_SIZE = 200;
    private final List<SolidObject> solids; // List to store solid objects

    public GameWorld(InputHandler inputHandler, Game game) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.player = new Player(465, 140, 50, 50, 5, inputHandler, this);
        this.hud = new HUD();
        this.grassBackground = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/grass.png");
        this.solids = new ArrayList<>();
        this.treeImage = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/tree.png"); // Load the tree image
        this.caveImage = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/cave.png"); // Load the cave image
        this.wallImage = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/wall.png"); // Load the wall image
        this.ArwenImage = Toolkit.getDefaultToolkit().getImage("src/domain/theOutsideGame/resources/world/Arwen.png"); // Load the Arwen image


        // Add some solid objects (like walls, rocks, etc.)
        solids.add(new SolidObject(235, 350, 40, 5)); // Example: solid object at (300, 200) with size 100x100
        solids.add(new SolidObject(0, -20, 800, 150)); // Example: solid object at (300, 200) with size 100x100
        solids.add(new SolidObject(435, 460, 1, 1));

        setFocusable(true);
    }

    public void update() {
        player.update();
        inputHandler.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background
        int width = getWidth();
        int height = getHeight();

        for (int x = 0; x < width; x += TILE_SIZE) {
            for (int y = 0; y < height; y += TILE_SIZE) {
                g.drawImage(grassBackground, x, y, TILE_SIZE, TILE_SIZE, this);
            }
        }

        // Draw the tree and cave at a specific position
        int Tree_Height = 300;
        int Tree_Width = 300;
        int Cave_Height = 200;
        int Cave_Width = 200;
        for (int i = 0; i < 5 ; i++) {
            g.drawImage(wallImage, i * Cave_Width, -20, Cave_Width, Cave_Height, this); // Position (300, 200) and size (100x100)

        }
        g.drawImage(caveImage, 390, -20, Cave_Width, Cave_Height, this); // Position (500, 500) and size (200x200)

        if (player.getY() < 100 + Tree_Height - player.getHeight()) {
            player.render(g); // Render the player
            g.drawImage(treeImage, 100, 100, Tree_Width, Tree_Height, this); // Position (100, 100) and size (200x200)
        } else {
            g.drawImage(treeImage, 100, 100, Tree_Width, Tree_Height, this); // Position (500, 500) and size (200x200)
            player.render(g); // Render the player
        }

        g.drawImage(ArwenImage, 400, 400, 70, 70, this); // Position (500, 500) and size (200x200)



        // Render the HUD
        hud.render(g);
    }

    // Check for collisions between player and solids
    public boolean checkCollision(Rectangle playerBounds) {
        for (SolidObject solid : solids) {
            if (playerBounds.intersects(solid.getBounds())) {
                inputHandler.isInteractNearArwen(player.getX(), player.getY());
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    public void closeWindow() {
        game.getFrame().dispose();
    }
}
