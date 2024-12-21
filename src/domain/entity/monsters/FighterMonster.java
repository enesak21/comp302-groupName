package domain.entity.monsters;

import domain.entity.Entity;

public class FighterMonster extends BaseMonster {
    private int daggerRange = 1;

    public FighterMonster(int gridX, int gridY, int tileSize) {
        super(gridX, gridY, tileSize);
    }


    public void move() {
        //simple random movement code
        int direction = (int) (Math.random() * 4); //0: UP, 1: DOWN, 2: LEFT, 3:RIGHT
        switch (direction){
            case 0 -> gridY--; //Move Up
            case 1 -> gridY++; //Move Down
            case 2 -> gridX--; //Move Left
            case 3 -> gridX++; //Move Right
        }
        updatePixelPosition();
    }


    @Override
    public void update() {
        move();
        attack();

    }
    @Override
    public void attack() {
        // Eğer hero yanındaysa saldır
        // Bu kısım collision checker ile desteklenebilir
        //System.out.println("Hero attacked by FighterMonster!");
    }
}