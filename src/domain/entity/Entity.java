package domain.entity;

import domain.panels.PlayModePanel;

public abstract class Entity {
    protected int pixelX, pixelY; // Çizimler için bunlar
    protected int gridX, gridY; // Grid üzerindeki konumları için bunlar
    protected int speed; //pixel türünde hareket için

    protected Direction direction = Direction.DOWN; //başlangıç yönü

    protected int tileSize; //public yapmak istemedim, burada tekrar tanımladım

    public Entity(int gridX, int gridY, int tileSize){
        this.gridX = gridX + PlayModePanel.offsetX;
        this.gridY = gridY + PlayModePanel.offsetY;
        this.tileSize = tileSize;

        this.pixelX = gridX * tileSize; //piksel konumlarını gride göre hesaplattık
        this.pixelY = gridY * tileSize;
    }

    protected void updatePixelPosition() { //Single responsibility principle onemli hocam her seyi update'e verme
        this.pixelX = gridX * tileSize;
        this.pixelY = gridY * tileSize;
    }

    public abstract void update(); //bunu player ve monsterlar icin ayri yazacagiz

    public int getGridX() {
        return gridX-PlayModePanel.offsetX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY-PlayModePanel.offsetY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPixelX() {
        return pixelX;
    }

    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
