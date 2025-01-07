package domain.UI;

import domain.UI.screenPanels.BuildModeScreenPanel;
import domain.UI.screenPanels.HelpScreenPanel;
import domain.UI.screenPanels.HomeScreenPanel;
import domain.audio.AudioManager;
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
        if (!isPanelAdded(panelName)) {
            switch (panelName) {
                case "Game":
                    mainPanel.add(createGameScreen(), "Game");
                    break;
                case "Build":
                    mainPanel.add(createBuildScreen(), "Build");
                    break;
                case "Help":
                    mainPanel.add(createHelpScreen(), "Help");
                    break;
            }
        }
        cardLayout.show(mainPanel, panelName); // Show the panel with the given name
        mainPanel.revalidate(); // Revalidate the main panel
        mainPanel.repaint(); // Repaint the main panel
    }

    /*
        * Create the home screen panel with the start button
     */
    private JPanel createHomeScreen() {
       return new HomeScreenPanel(this); // Pass the current
    }

    /*
        * Create the build mode screen with the BuildModePanel
     */
    private JPanel createBuildScreen() {
        // Create a JPanel as the main container for the Build Mode screen

        BuildModeScreenPanel buildScreen = new BuildModeScreenPanel(this); // Pass the current UI instance
        halls = buildScreen.getBuildModePanel().getHalls(); // Retrieve the halls from BuildModePanel

        return buildScreen;
    }


    /*
        * Create the game screen with the PlayModePanel
     */
    private JPanel createGameScreen() {
        PlayModePanel playModePanel = new PlayModePanel(halls);

        playModePanel.startGameThread(); // Start the game loop
        SwingUtilities.invokeLater(playModePanel::requestFocusInWindow); // Request focus for key events
        return playModePanel;
    }

    /*
        * Create the help screen panel with instructions
     */
    private JPanel createHelpScreen() {
        return new HelpScreenPanel(this); // Pass the current UI instance
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