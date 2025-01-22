package domain.theOutsideGame.world;

import java.awt.Rectangle;

public class SolidObject {

    private Rectangle bounds; // The area of the solid object

    public SolidObject(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}