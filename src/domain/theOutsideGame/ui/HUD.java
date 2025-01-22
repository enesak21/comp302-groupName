package domain.theOutsideGame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {

    private int health;
    private int score;

    public HUD() {
        this.health = 100;  // Initial health
        this.score = 0;     // Initial score
    }

    public void update() {
        // You can update health and score based on game events here
        // For example:
        // health -= 1; // Decrease health over time
        // score += 10; // Increase score for completing actions
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Display Health
        g.drawString("Health: " + health, 10, 20);

        // Display Score
        g.drawString("Score: " + score, 10, 40);
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
