package domain.playerObjects;

import domain.game.Tile;
import entity.Entity;
import main.PlayModePanel;
import main.PlayerController;

import java.awt.*;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class Player extends Entity {
    private String name;
    private int health;
    private Direction direction;
    private Inventory inventory;
    private Tile location;

    PlayModePanel playModePanel;
    PlayerController playerController;

    public Player(String name, PlayModePanel playModePanel, PlayerController playerController) {
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerController = playerController;

        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update(){

        if(playerController.upPressed){
            y -= speed;
        }
        if(playerController.downPressed){
            y += speed;
        }
        if(playerController.leftPressed){
            x -= speed;
        }
        if(playerController.rightPressed){
            x += speed;
        }
    }

    public void draw(Graphics2D g2){

        g2.setColor(Color.white);
        g2.fillRect(x,y,playModePanel.tileSize,playModePanel.tileSize);
    }




    /**
     * Uses an enchantment
     * 
     * @param enchantmentType The type of the enchantment to be used
     * @return true if the enchantment was used succesfully
     */
    public boolean useEnchantment(String enchantmentType) {
        return false;
    }
}