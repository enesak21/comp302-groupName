package domain.UI.screenPanels;

import domain.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Panel for displaying help and instructions with a LOTR-themed font.
 */
public class HelpScreenPanel extends JPanel {

    private final UI ui;
    private JLabel titleLabel;
    private JTextArea instructionsArea;
    private JButton backButton;

    // Path to a background image (optional, for a fancier look)
    private static final String BACKGROUND_IMG_PATH = "src/resources/backgrounds/help_bg.png";

    // LOTR-like font
    private Font lotrFont;

    public HelpScreenPanel(UI ui) {
        this.ui = ui;
        loadLOTRFont();    // 1. Load the custom TTF/OTF font
        initComponents();  // 2. Build UI
        addListeners();    // 3. Wire up events
    }

    /**
     * Loads the custom "Ringbearer" (or any LOTR-themed) font
     * and registers it with the GraphicsEnvironment.
     */
    private void loadLOTRFont() {
        try {
            // Make sure Ringbearer.ttf exists in your resources/fonts folder
            File fontFile = new File("src/resources/fonts/Ringbearer.ttf");
            lotrFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(28f);

            // Register the font so it can be used by the JVM
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(lotrFont);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // If the custom font fails to load, fallback to a standard font
            lotrFont = new Font("Serif", Font.PLAIN, 28);
        }
    }

    /**
     * Initializes and arranges all UI components for this help screen.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        // -- Title Label --
        titleLabel = new JLabel("Help & Instructions", SwingConstants.CENTER);
        titleLabel.setFont(lotrFont.deriveFont(Font.BOLD, 32f));
        titleLabel.setForeground(new Color(210, 180, 140));  // A gold-ish color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // -- Instructions TextArea --
        instructionsArea = new JTextArea("""
        Controls:
        † Arrow Keys: Move the hero
        † Mouse Left Click: Interact with objects
        † R Key: Use Reveal Enchantment
        † P Key: Use Cloak of Protection
        † B Key + Direction: Throw Luring Gem
        
        Gameplay:
        † Collect runes to unlock new halls.
        † Avoid monsters and use enchantments to help your journey.
        † Design the halls during build mode.
        """);
        instructionsArea.setFont(lotrFont.deriveFont(Font.PLAIN, 18f));
        instructionsArea.setForeground(new Color(245, 222, 179)); // Wheat color
        instructionsArea.setOpaque(false);
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);

        // -- Scroll Pane for instructions --
        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // -- Back Button --
        backButton = new JButton("Back");
        backButton.setFont(lotrFont.deriveFont(Font.BOLD, 20f));
        backButton.setFocusPainted(false);
        backButton.setForeground(new Color(245, 222, 179));   // text color
        backButton.setBackground(new Color(85, 62, 35));       // a brownish background
        backButton.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140), 2));
        backButton.setPreferredSize(new Dimension(120, 50));

        // -- Bottom Panel to hold the button --
        JPanel bottomPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Semi-transparent overlay
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Let the panel handle keyboard focus
        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    /**
     * Adds event listeners for buttons and key presses.
     */
    private void addListeners() {
        // Back button -> Show home screen
        backButton.addActionListener(e -> ui.showPanel("Home"));

        // Esc key -> Show home screen
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ui.showPanel("Home");
                }
            }
        });
    }

    /**
     * Paint a fancy background image or gradient behind the content.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bgImage = new ImageIcon(BACKGROUND_IMG_PATH).getImage();
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fallback to a simple gradient if no image
            Graphics2D g2d = (Graphics2D) g.create();
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 32, 27),
                    0, getHeight(), new Color(85, 62, 35)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}
