package domain.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeController {
    private int timeLeft = 60; // Timer set for 60 seconds

    public TimeController() {
        Timer timer = new Timer(1000, new ActionListener() { // Fires every 1000ms (1 second)
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    System.out.println("Time left: " + timeLeft + " seconds");
                } else {
                    ((Timer) e.getSource()).stop();
                    System.out.println("Time's up!");
                }
            }
        });
        timer.start();
    }
}
