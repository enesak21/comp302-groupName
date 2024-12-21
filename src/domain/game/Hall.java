package domain.game;

public class Hall {
    private int length;
    private int width;

    public Hall(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int calculateArea() {
        return length * width;
    }

    public boolean isValid() {
        return length > 0 && width > 0;
    }
}