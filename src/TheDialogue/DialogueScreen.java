package TheDialogue;

import domain.config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DialogueScreen extends JPanel {
    public JPanel bottomPanel;
    public JPanel buttonPanel;
    public JButton button1;
    public JButton button2;
    public Font customFont;

    public DialogueScreen() {

        try {
            File fontFile = new File("src/resources/fonts/PressStart2P-Regular.ttf"); // Replace with your font file path
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(18f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for flexible layout

        // Top panel (make it bigger)
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(getWidth(), 200)); // Make top panel bigger
        topPanel.setLayout(new FlowLayout()); // Make sure the layout can fit the image properly

        // Load and resize the image
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/TheDialogue/dialogueScene.png")); // Specify the correct path
        Image image = imageIcon.getImage(); // Get the image from the ImageIcon
        Image resizedImage = image.getScaledInstance(780, 300, Image.SCALE_SMOOTH); // Resize the image
        ImageIcon resizedIcon = new ImageIcon(resizedImage); // Create a new ImageIcon with the resized image

        // Add the resized image to the top panel
        JLabel imageLabel = new JLabel(resizedIcon);
        topPanel.add(imageLabel);

        // Bottom panel with scroll (make it smaller)
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        Font customFont = GameConfig.loadLOTRFont();  // Assuming you have a method to load your font

        JScrollPane scrollPane = new JScrollPane(bottomPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set the preferred height of the scrollable bottom panel (make it smaller)
        scrollPane.setPreferredSize(new Dimension(getWidth(), 100)); // Adjust height to make it smaller

        // Button panel at the bottom with two buttons in two rows
        buttonPanel = new JPanel(new GridLayout(2, 1)); // 2 rows
        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Add panels to the main panel
        add(topPanel);
        add(scrollPane); // Add the scrollable panel in the second slot
        add(buttonPanel); // Add the button panel in the third slot
    }

    public void addWiseDialogue(String dialogue) {
        // Add a new JLabel with the dialogue text
        String wiseDialogue = "Arwen: " + dialogue;
        JLabel wiseLabel = new JLabel(wiseDialogue);
        wiseLabel.setFont(customFont); // Set the font size
        this.bottomPanel.add(wiseLabel);
    }
}
