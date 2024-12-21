package domain.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeController {
    private int timeLeft = 10; // Timer set for 60 seconds
    private Timer timer; // Timer instance

    public TimeController() {
        timer = new Timer(1000, new ActionListener() { // Fires every 1000ms (1 second)
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft >= 0) { // Counts down from 60 to 0
                    System.out.println("Time left: " + timeLeft + " seconds");
                    timeLeft--;
                } else {
                    timer.stop();
                    System.out.println("Time's up!");
                }
            }
        });
        timer.start();
    }

    // Method to pause the timer
    public void pauseTimer() {
        if (timer.isRunning()) {
            timer.stop();
            System.out.println("Timer paused.");
        }
    }

    // Method to resume the timer
    public void resumeTimer() {
        if (!timer.isRunning()) {
            timer.start();
            System.out.println("Timer resumed.");
        }
    }

    // Getter for timeLeft
    public int getTimeLeft() {
        return timeLeft;
    }

    // Setter for timeLeft
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
