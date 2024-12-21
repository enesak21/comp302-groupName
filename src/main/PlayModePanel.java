package main;

import domain.entity.playerObjects.Player;
import domain.game.Game;
import domain.game.Grid;

import javax.swing.*;
import java.awt.*;

public class PlayModePanel extends JPanel implements Runnable{

    //Screen settings
    static final int originalTileSize = 16; //our assets are 16x16 pixels originally
    static final int scale = 2;
    static final int tileSize = originalTileSize * scale;

    static final int gridColumns = 16; // Arena sütun sayısı
    static final int gridRows = 16;    // Arena satır sayısı
    static final int gridWidth = gridColumns * tileSize; // Arena genişliği
    static final int gridHeight = gridRows * tileSize;   // Arena yüksekliği

    static final int screenWidth = 24 * tileSize; // Tüm ekran genişliği
    static final int screenHeight = 20 * tileSize; // Tüm ekran yüksekliği

    public static final int offsetX = ((screenWidth - gridWidth) / (2*tileSize))-2; // offset for gridi ortalama (tile-based)
    public static final int offsetY = (screenHeight - gridHeight) / (2*tileSize);

    //FPS
    int FPS = 60;

    Grid grid;
    PlayerController playerController;
    Thread gameThread;
    Player player;


    //Constructor
    public PlayModePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        playerController = new PlayerController();
        this.addKeyListener(playerController);
        this.setFocusable(true);

        grid = new Grid(tileSize, this);
        player = new Player("Osimhen", 0, 0, tileSize, this, playerController);
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
                System.out.println(player.getGridX()+" "+player.getGridY());
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


        grid.draw(g2, offsetX*tileSize, offsetY*tileSize);
        player.draw(g2);
        g2.dispose();

    }

    //Getter functions for scale and tileSize
    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }


}
