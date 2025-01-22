package domain.game1.UI.screenPanels;

import domain.game1.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Panel for displaying help and instructions with a LOTR-themed font,
 * now using an HTML-based JTextPane for colorized bullet points and headings.
 */
public class HelpScreenPanel extends JPanel {

    private final UI ui;
    private JLabel titleLabel;
    private JTextPane instructionsPane;
    private JButton backButton;

    // Path to a background image (optional, for a fancier look)
    private static final String BACKGROUND_IMG_PATH = "src/resources/backgrounds/help_bg.png";

    // LOTR-like font reference
    private Font lotrFont;

    public HelpScreenPanel(UI ui) {
        this.ui = ui;
        loadLOTRFont();    // Load the custom TTF/OTF font
        initComponents();  // Build UI
        addListeners();    // Wire up events
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

        // -- Instructions as an HTML-based JTextPane --
        instructionsPane = new JTextPane();
        instructionsPane.setContentType("text/html");
        instructionsPane.setEditable(false);
        instructionsPane.setOpaque(false); // Let background image show through

        /*
         * We use a multi-line HTML string:
         * - <h1> for bigger headings, in gold (#FFD700)
         * - Body text at font-size:16px, in paler yellow (#FFFFCC)
         * - Bullets (†) in #FFD700
         * - Key commands (Arrow Keys, Mouse Left Click, R Key, etc.) in a different color (#FFC30B)
         */
        String htmlText = """
            <html>
              <body style="font-family: 'Ringbearer', Serif; font-size:16px; color:#FFFFCC; margin:10px;">
                <h1 style="text-align:center; color:#FFD700; font-size:24px;">Controls</h1>
                <p>
                  <span style="color:#FFD700;">†</span>
                  <span style="color:#FFC30B; font-weight:bold;">Arrow Keys</span>: Move the hero<br>

                  <span style="color:#FFD700;">†</span>
                  <span style="color:#FFC30B; font-weight:bold;">Mouse Left Click</span>: Interact with objects<br>

                  <span style="color:#FFD700;">†</span> Press
                  <span style="color:#FFC30B; font-weight:bold;">R</span> for Reveal Enchantment<br>

                  <span style="color:#FFD700;">†</span> Press
                  <span style="color:#FFC30B; font-weight:bold;">P</span> for Cloak of Protection<br>

                  <span style="color:#FFD700;">†</span> Press
                  <span style="color:#FFC30B; font-weight:bold;">B Key + Direction</span> to throw a Luring Gem
                </p>

                <h1 style="text-align:center; color:#FFD700; font-size:24px;">Gameplay</h1>
                <p>
                  <span style="color:#FFD700;">†</span> Collect runes to unlock new halls<br>
                  <span style="color:#FFD700;">†</span> Avoid monsters and use enchantments to help your journey<br>
                  <span style="color:#FFD700;">†</span> Design the halls during build mode
                </p>
              </body>
            </html>
            """;

        instructionsPane.setText(htmlText);

        // Scroll Pane for instructions
        JScrollPane scrollPane = new JScrollPane(instructionsPane);
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
