package domain.config;

import java.io.Serializable;

public class GameConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    // Screen settings
    private int originalTileSize = 16; // Original tile size in pixels
    private int scale = 2; // Scaling factor for the game resolution
    private int gridColumns = 16; // Number of columns in the grid
    private int gridRows = 16; // Number of rows in the grid

    public int getTileSize() {
        return originalTileSize * scale;
    }

    public int getGridWidth() {
        return gridColumns * getTileSize();
    }

    public int getGridHeight() {
        return gridRows * getTileSize();
    }

    public int getScreenWidth() {
        return 24 * getTileSize(); // Total screen width
    }

    public int getScreenHeight() {
        return 20 * getTileSize(); // Total screen height
    }

    public int getOffsetX() {
        return ((getScreenWidth() - getGridWidth()) / (2 * getTileSize())) - 2; // Offset for centering grid horizontally
    }

    public int getOffsetY() {
        return (getScreenHeight() - getGridHeight()) / (2 * getTileSize()); // Offset for centering grid vertically
    }

    public int getGridTopLeftX() {
        return getOffsetX() * getTileSize();
    }

    public int getGridTopLeftY() {
        return getOffsetY() * getTileSize();
    }

    public int getScale() {
        return scale;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public int getGridRows() {
        return gridRows;
    }

    // Rendering settings
    private int framesPerSecond = 60; // Target FPS for the game

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    // Font settings
    private String fontPath = "src/resources/fonts/PressStart2P-Regular.ttf"; // Default font path
    private float fontSize = 20f; // Default font size

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }


}
