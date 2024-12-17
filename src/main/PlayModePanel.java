package main;

import javax.swing.*;
import java.awt.*;

public class PlayModePanel extends JPanel implements Runnable{

    //Screen settings
    final int originalTileSize = 16; //our assets are 16x16 pixels originally
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    //Here We chose 4:3 ratio for screen. Argue it!!! Then, Choose most common one.
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenColumn;
    final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;

    //Constructor
    public PlayModePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
    }
}
