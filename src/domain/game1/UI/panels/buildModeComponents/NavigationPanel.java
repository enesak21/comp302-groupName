package domain.game1.UI.panels.buildModeComponents;

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
        JButton prevGridButton = createIconButton("src/resources/icons/leftArrow.png", e -> onPrevious.run());
        add(prevGridButton);

        // Add Next Button
        JButton nextGridButton = createIconButton("src/resources/icons/rightArrow.png", e -> onNext.run());
        add(nextGridButton);

        // Add Check Button
        JButton checkButton =  createIconButton("src/resources/icons/checkButton.png", e -> onCheck.run());
        add(checkButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set custom color for the bottom part
        g.setColor(new Color(66, 40, 53)); // Dark gray
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private JButton createIconButton(String iconPath, ActionListener actionListener) {
        JButton button = new JButton(new ImageIcon(new ImageIcon(iconPath)
                .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        button.addActionListener(actionListener);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        return button;
    }

    private JButton createTextButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }
}
