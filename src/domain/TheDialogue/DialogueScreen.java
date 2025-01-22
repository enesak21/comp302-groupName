package domain.TheDialogue;

import domain.config.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DialogueScreen extends JPanel {
    public JPanel bottomPanel;
    public JPanel buttonPanel;
    public JButton button1;
    public JButton button2;
    public Font customFont;
    public Font customFontSmall;

    public DialogueScreen() {

        try {
            File fontFile = new File("src/resources/fonts/PressStart2P-Regular.ttf"); // Replace with your font file path
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(16f);
            customFontSmall = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(9f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for flexible layout

        // Top panel (make it bigger)
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(getWidth(), 200)); // Make top panel bigger
        topPanel.setLayout(new FlowLayout()); // Make sure the layout can fit the image properly

        // Load and resize the image
        File imageFile = new File("src/domain/TheDialogue/dialogueScene.png");
        ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath()); // Specify the correct path
        Image image = imageIcon.getImage(); // Get the image from the ImageIcon
        Image resizedImage = image.getScaledInstance(780, 300, Image.SCALE_SMOOTH); // Resize the image
        ImageIcon resizedIcon = new ImageIcon(resizedImage); // Create a new ImageIcon with the resized image

        // Add the resized image to the top panel
        JLabel imageLabel = new JLabel(resizedIcon);
        topPanel.add(imageLabel);

        // Bottom panel with scroll (make it smaller)
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(66, 40, 53));

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
        styleButton(button1);
        styleButton(button2);
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

        int maxLineLength = 40;
        String[] lines = splitText(wiseDialogue, maxLineLength);
        for (String line : lines) {
            JLabel wiseLabel = new JLabel(line);
            wiseLabel.setFont(customFont); // Set the font size
            wiseLabel.setForeground(new Color(210, 180, 140)); // Set the color to white
            this.bottomPanel.add(wiseLabel);
        }
        this.bottomPanel.add(new JLabel("\n")); // Add an empty label for spacing
    }

    public void addPlayerDialogue(String dialogue) {
        // Add a new JLabel with the dialogue text
        String playerDialogue = "Rafa Silva: " + dialogue;

        int maxLineLength = 40;
        String[] lines = splitText(playerDialogue, maxLineLength);
        for (String line : lines) {
            JLabel playerLabel = new JLabel(line);
            playerLabel.setFont(customFont); // Set the font size
            playerLabel.setForeground(new Color(210, 180, 140)); // Set the color to blue
            this.bottomPanel.add(playerLabel);
        }
        this.bottomPanel.add(new JLabel("\n")); // Add an empty label for spacing
    }

    private String[] splitText(String text, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");  // Split the text into words

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Check if adding this word to the current line would exceed the maxLineLength
            if (currentLine.length() + word.length() + 1 <= maxLineLength) {
                // Add the word to the current line
                if (currentLine.length() > 0) {
                    currentLine.append(" ");  // Add a space between words
                }
                currentLine.append(word);
            } else {
                // If it exceeds, add the current line to the list and start a new line
                lines.add(currentLine.toString());
                currentLine.setLength(0);  // Clear the StringBuilder
                currentLine.append(word);  // Start a new line with the current word
            }
        }

        // Add the last line if any
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        // Convert List to array and return
        return lines.toArray(new String[0]);  // Use a zero-length array for the conversion
    }

    private void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(new Color(210, 180, 140));         // Gold-ish color
        button.setFont(customFontSmall);     // LOTR font, 30 pt
    }

}
