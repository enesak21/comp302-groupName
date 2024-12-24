package domain.UI.mouseHandlers;

import main.PlayModePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarMouseListener {

    private final PlayModePanel playModePanel;

    private int sidebarX;
    private int sidebarWidth;
    private int  buttonWidth;
    private int buttonHeight;
    private int buttonPadding;
    private int buttonX1;
    private int buttonX2;
    private int buttonY;
    private int offsetY;
    private int tileSize;
    private int screenWidth;



    public SidebarMouseListener(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        this.sidebarX = playModePanel.getSidebarX(); // Sidebar x position
        this.sidebarWidth = playModePanel.getSidebarWidth(); // Sidebar width
        this.tileSize = playModePanel.getTileSize(); // Tile size
        this.offsetY = playModePanel.getOffsetY(); // Offset Y
        this.buttonWidth = 48; // Button width
        this.buttonHeight = 48; // Button height
        this.buttonPadding = 10; // Spacing between buttons
        this.buttonX1 = sidebarX + 10; // Pause button position
        this.buttonX2 = buttonX1 + buttonWidth + buttonPadding; // Exit button position
        this.buttonY = offsetY * tileSize + 10; // Top margin for both buttons
        this.screenWidth = playModePanel.getScreenWidth(); // Screen width
    }

    public void handleSidebarClick(Point rawClickPoint) {
        // This method is used to handle a click in the sidebar region


        // Possibly do something with the sidebar click point
        System.out.println("Sidebar click at: " + rawClickPoint);

        int mouseX = (int) rawClickPoint.getX();
        int mouseY = (int) rawClickPoint.getY();

        int sidebarWidth = 4 * tileSize + 20; // Sidebar width
        int sidebarX = screenWidth - sidebarWidth - (tileSize + 10) + 20;

//        if (mouseX >= sidebarX && mouseX <= sidebarX + sidebarWidth) {
//            if (mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
//                playModePanel.pauseGame();
//            } else if (mouseY >= buttonY + buttonHeight + buttonPadding && mouseY <= buttonY + 2 * buttonHeight + buttonPadding) {
//                playModePanel.exitGame();
//            }
//        }

    }
}
