package main;

import domain.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HelpPanel extends JPanel {


    public HelpPanel(UI ui) {
        this.setLayout(new BorderLayout());
        JLabel title = new JLabel("Help & Instructions", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(title, BorderLayout.NORTH);



        // Back Button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ui.showPanel("Home")); // Return to Home Screen
        this.add(backButton, BorderLayout.SOUTH);

        // Add Key Listener for Esc key
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ui.showPanel("Home");
                }
            }
        });

        this.setFocusable(true);
        this.requestFocusInWindow(); // Ensure the panel has focus for key events
    }
}
