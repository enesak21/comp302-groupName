package main;

import domain.playerObjects.Player;

import javax.swing.*;
import java.awt.*;

public class PlayModePanel extends JPanel implements Runnable{

    //Screen settings
    final int originalTileSize = 16; //our assets are 16x16 pixels originally
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    //Here We chose 4:3 ratio for screen. Argue it!!! Then, Choose most common one.
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenColumn;
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    int FPS = 60;

    PlayerController playerController = new PlayerController();
    Thread gameThread;
    Player player = new Player("Cemal Baba", this, playerController);

    //Set player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //Constructor
    public PlayModePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(playerController);
        this.setFocusable(true);
    }


    public void startGameThread(){
        System.out.println("Starting game thread");

        gameThread = new Thread(this);
        gameThread.start();
    }


    // This will be our main method which is running the Play mode screen
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                // 1 Update: update information
                update();
                // 2 Paint: paint or draw screen with the updated information
                repaint();
                delta--;
            }
        }
    }

    public void update(){

        player.update();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();

    }




}
