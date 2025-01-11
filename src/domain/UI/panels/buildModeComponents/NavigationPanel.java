package domain.UI.panels.buildModeComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NavigationPanel extends JPanel {

    public NavigationPanel(Runnable onPrevious, Runnable onNext, Runnable onCheck) {
        // Set layout for the panel
        super(new FlowLayout());

        // Set custom background color with overridden paintComponent
        setOpaque(false);

        // Add Previous Button
        JButton prevGridButton = createButton("Previous Grid", e -> onPrevious.run());
        add(prevGridButton);

        // Add Next Button
        JButton nextGridButton = createButton("Next Grid", e -> onNext.run());
        add(nextGridButton);

        // Add Check Button
        JButton checkButton = createButton("Check", e -> onCheck.run());
        add(checkButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set custom color for the bottom part
        g.setColor(new Color(66, 40, 53)); // Dark gray
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }
}
