package main;

import domain.UI.UI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    //main class

//            JFrame window = new JFrame();
//            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            window.setResizable(false);
//            window.setTitle("RoKUe-like Game");
//
//            PlayModePanel playModePanel = new PlayModePanel();
//            window.add(playModePanel);
//
//            window.pack();
//
//            playModePanel.startGameThread();
//
//            window.setLocationRelativeTo(null);
//            window.setVisible(true);


            SwingUtilities.invokeLater(() -> {
                    UI ui = new UI();
                    ui.show();
            });
    }
}
