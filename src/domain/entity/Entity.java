package domain.entity;

import main.PlayModePanel;

import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int pixelX, pixelY; // Çizimler için bunlar
    protected int gridX, gridY; // Grid üzerindeki konumları için bunlar
    protected int speed; //pixel türünde hareket için

    protected BufferedImage down1, up1, left1, right1; //diğer yönler için döndürebiliriz direkt komik olur
    protected Direction direction;

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

    public void draw(java.awt.Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case UP -> down1;
            case DOWN -> down1;
            case LEFT -> down1;
            case RIGHT -> down1;
        };
        g2.drawImage(image, pixelX, pixelY, tileSize, tileSize, null);
    }

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
}
