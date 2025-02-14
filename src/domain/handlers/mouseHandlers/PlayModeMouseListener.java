package domain.handlers.mouseHandlers;

import domain.UI.panels.PlayModePanel;
import domain.audio.AudioManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayModeMouseListener extends MouseAdapter {
    // This class is used to handle mouse events in the play mode

    private final PlayModePanel playModePanel;
    private final GridMouseListener gridMouseListener;


    // Constructor with parameters
    public PlayModeMouseListener(PlayModePanel playModePanel) {
        this.playModePanel = playModePanel;

        this.gridMouseListener = new GridMouseListener(playModePanel);
    }

    public void mouseClicked(MouseEvent e) {
        Point clickPoint = e.getPoint();

        if (playModePanel.getState().equals("TryAgain")) {
            // Possibly do something with the click in the game over state

            if (isWithinBounds(clickPoint, 284, 410, 200, 50)) {
                AudioManager.stopGameOverMusic();
                AudioManager.playPlayModeMusic();
                playModePanel.restartGame();
            } else if (isWithinBounds(clickPoint, 284, 470, 200, 50)) {
                AudioManager.stopGameOverMusic();
                domain.theOutsideGame.launcher.Game.launch(null);
            }


        } else {
            // Check if click is in the “grid region” or “sidebar region”
            if (isInGridRegion(clickPoint)) {
                // Possibly transform the click to grid coordinates if needed
                gridMouseListener.handleGridClick(clickPoint);
            }
        }
    }

    private boolean isWithinBounds(Point clickPoint, int x, int y, int width, int height) {
        // Check if the click point is within the bounds of the rectangle
        // with the given x, y, width, and height where the x and y are the top-left corner of the rectangle
        return clickPoint.x >= x && clickPoint.x <= x + width && clickPoint.y >= y && clickPoint.y <= y + height;
    }

    private boolean isInGridRegion(Point clickPoint) {
        // Check if the click point is within the grid
        return isWithinBounds(clickPoint, playModePanel.getTopLeftCornerX(), playModePanel.getTopLeftCornerY(), playModePanel.getGridWidth(), playModePanel.getGridHeight());
    }
}
