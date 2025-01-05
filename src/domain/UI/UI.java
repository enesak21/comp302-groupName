package domain.UI;

import domain.audio.AudioManager;
import domain.entity.playerObjects.Player;
import domain.game.GameManager;
import domain.game.Hall;
import domain.panels.BuildModePanel;
import domain.handlers.BuildModeHandler;
import domain.panels.PlayModePanel;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * The UI class is responsible for managing the user interface of the game.
 * It creates and displays the main JFrame, which contains the different panels
 * for the home screen, build mode, and play mode.
 */
public class UI {
    private JFrame frame; // Main JFrame for the game
    private CardLayout cardLayout; // CardLayout for switching between panels
    private JPanel mainPanel; // Main container for the different panels
    private List<Hall> halls; // List of halls created in Build Mode
    private AudioManager audioManager = new AudioManager(); // Audio manager for playing music
    private GameManager gameManager; // Game manager for handling game logic

    // Constructor
    public UI() {
        initializeUI();
    }

    // Initialize the main JFrame and CardLayout
    private void initializeUI() {
        frame = new JFrame("2D Game");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add only the home screen initially
        mainPanel.add(createHomeScreen(), "Home");

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Change if needed

        // Set custom colors for message dialogs
        UIManager.put("OptionPane.background", new Color(50, 56, 66)); // Dark gray
        UIManager.put("Panel.background", new Color(50, 56, 66));      // Match background
        UIManager.put("OptionPane.messageForeground", Color.WHITE); // White text
    }

    /*
        * Show the main JFrame and the home screen
     */
    public void show() {
        frame.setVisible(true);
        cardLayout.show(mainPanel, "Home");
    }
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName); // Switch to the specified panel
        mainPanel.revalidate(); // Revalidate the main panel
        mainPanel.repaint(); // Repaint the main panel
    }

    /*
        * Create the home screen panel with the start button
     */
    private JPanel createHomeScreen() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600)); // Set the size of the layered pane

        audioManager.playEnterMusic(); // Play the background music

        // Background Image
        ImageIcon originalIcon = new ImageIcon("src/resources/Rokue-like logo 4.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // JLabel for the background image
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, 800, 600);

        // START BUTTON
        JButton startButton = new JButton("Start Game"); //GO TO BUİLD MODE DIRECTLY
        startButton.setBounds(300, 470, 200, 50);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setFont(new Font("", Font.BOLD, 25));
        startButton.setForeground(Color.black);

        // Add action listener to the start button
        startButton.addActionListener(e -> {
            if (!isPanelAdded("Build")) {
                audioManager.stopEnterMusic();
                mainPanel.add(createBuildScreen(), "Build");
            }
            cardLayout.show(mainPanel, "Build");
        });

        //HELP SCREEN BUTTON
        JButton helpButton = new JButton("Help"); //GO TO BUİLD MODE DIRECTLY
        helpButton.setBounds(300, 510, 200, 50);
        helpButton.setOpaque(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setBorderPainted(false);
        helpButton.setFont(new Font("", Font.BOLD, 25));
        helpButton.setForeground(Color.black);

        helpButton.addActionListener(e -> {
            if (!isPanelAdded("Help")) {
                mainPanel.add(createHelpScreen(), "Help");
            }
            cardLayout.show(mainPanel, "Help");
            mainPanel.revalidate();
            mainPanel.getComponent(1).requestFocusInWindow(); // Ensure HelpPanel gets focus
        });


        // Add Components to LayeredPane
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Background layer
        layeredPane.add(startButton, Integer.valueOf(1)); // Foreground layer
        layeredPane.add(helpButton, Integer.valueOf(1)); // Foreground layer

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(layeredPane, BorderLayout.CENTER);

        return panel;
    }

    /*
        * Create the build mode screen with the BuildModePanel
     */
    private JPanel createBuildScreen() {
        // Create a JPanel as the main container for the Build Mode screen
        JPanel buildScreen = new JPanel();
        buildScreen.setLayout(new BorderLayout());



        // Instantiate BuildModePanel, which includes the dynamic hall name
        BuildModePanel buildModePanel = new BuildModePanel();
        BuildModeHandler buildModeHandler = buildModePanel.getBuildModeHandler();
        buildScreen.add(buildModePanel, BorderLayout.CENTER);

        // Complete Button
        JButton completeButton = new JButton("Complete Build");
        completeButton.addActionListener(e -> {
            if (buildModeHandler.checkBuildMode()) { // Check if the build mode is valid
                List<Hall> halls = buildModePanel.getHalls();
                Player player = Player.getInstance("Osimhen", 0, 0, 32, 32, buildModePanel.getTileSize());
                gameManager = new GameManager(halls, player);

                if (!isPanelAdded("Game")) { //
                    mainPanel.add(createGameScreen(), "Game");
                }
                cardLayout.show(mainPanel, "Game");
                mainPanel.revalidate();
                mainPanel.repaint();
            }
            else { // Display a warning message if the build mode is not complete
                ImageIcon warningIcon = new ImageIcon(new ImageIcon("src/resources/structures/skull.png")
                        .getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
                JOptionPane.showMessageDialog(null, "Please complete all halls before proceeding.", "Warning",
                        JOptionPane.WARNING_MESSAGE, warningIcon);
            }
        });


        // Add the button to the bottom of the screen
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(66, 40, 53));
        buttonPanel.add(completeButton);
        buildScreen.add(buttonPanel, BorderLayout.SOUTH);
        halls = buildModePanel.getHalls(); // Retrieve the halls from BuildModePanel

        return buildScreen;
    }


    /*
        * Create the game screen with the PlayModePanel
     */
    private JPanel createGameScreen() {
        PlayModePanel playModePanel = new PlayModePanel(gameManager);

        playModePanel.startGameThread(); // Start the game loop
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Request focus for key events
        return playModePanel;
    }

    /*
        * Create the help screen panel with instructions
     */
    private JPanel createHelpScreen() {
        return new HelpPanel(this); // Pass the current UI instance
    }

    /*
        * Check if a panel with the given name is already added to the main panel
     */
    private boolean isPanelAdded(String panelName) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(panelName)) {
                return true; // Check the name of the component
            }
        }
        return false;
    }
}