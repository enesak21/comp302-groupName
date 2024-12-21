package domain.UI;

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

        JTextArea instructions = new JTextArea("""
        Controls:
        - Arrow Keys: Move the hero
        - Mouse Left Click: Interact with objects
        - R Key: Use Reveal Enchantment
        - P Key: Use Cloak of Protection
        - B Key + Direction: Throw Luring Gem
        
        Gameplay:
        - Collect runes to unlock new halls.
        - Avoid monsters and use enchantments to help your journey.
        - Design the halls during build mode.
        """);
        instructions.setFont(new Font("Arial", Font.PLAIN, 16));
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        this.add(new JScrollPane(instructions), BorderLayout.CENTER);

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

        this.setFocusable(true);  // Ensure panel is focusable
        this.requestFocusInWindow(); // Request focus on the panel

        // Ensure focus is properly set when the panel is shown
        SwingUtilities.invokeLater(() -> {
            this.requestFocusInWindow();
        });
    }

}
