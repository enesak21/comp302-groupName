package domain.entity.playerObjects;

import domain.entity.Direction;
import domain.game.Tile;
import domain.entity.Entity;
import main.PlayModePanel;
import main.PlayerController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class Player extends Entity {
    private String name;
    private int health;
    //private Direction direction;  //Entity classından direction alıyoruz zaten, buna gerek yok şimdilik
    private Inventory inventory;
    private Tile location;
    boolean moving = false;
    int pixelCounter = 0;

    PlayModePanel playModePanel;
    PlayerController playerController;

    public Player(String name, PlayModePanel playModePanel, PlayerController playerController) {
        this.name = name;
        this.playModePanel = playModePanel;
        this.playerController = playerController;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = Direction.DOWN;
    }

    public void getPlayerImage(){

        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/player.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void update(){

        if(!moving){

            if(playerController.upPressed){
                direction = Direction.UP;
                moving = true;
            }
            else if(playerController.downPressed){
                direction = Direction.DOWN;
                moving = true;
            }
            else if(playerController.leftPressed){
                direction = Direction.LEFT;
                moving = true;
            }
            else if(playerController.rightPressed){
                direction = Direction.RIGHT;
                moving = true;
            }
        }else {
            switch (direction){
                case UP:
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
            pixelCounter += speed;
            if(pixelCounter == playModePanel.getScale() * 16){
                moving = false;
                pixelCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case Direction.UP:
                image = down1;
                break;
            case Direction.DOWN:
                image = down1;
                break;
            case Direction.LEFT:
                image = down1;
                break;
            case Direction.RIGHT:
                image = down1;
                break;
        }


        g2.drawImage(image, x, y, playModePanel.getTileSize(), playModePanel.getTileSize(), null);
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