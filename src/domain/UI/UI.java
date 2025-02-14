package domain.UI;

import domain.UI.screenPanels.*;
import domain.UI.screenPanels.*;
import domain.config.GameState;
import domain.game.Hall;

import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * The UI class is responsible for managing the user interface of the game.
 * It creates and displays the domain.main JFrame, which contains the different panels
 * for the home screen, build mode, and play mode.
 */
public class UI {
    private JFrame frame; // Main JFrame for the game
    private CardLayout cardLayout; // CardLayout for switching between panels
    private JPanel mainPanel; // Main container for the different panels
    private List<Hall> halls; // List of halls created in Build Mode

    // Screen panels
    private BuildModeScreenPanel buildScreen;
    private GameScreenPanel gameScreen;
    private HelpScreenPanel helpScreen;
    private loadStartScreenPanel loadStartScreen;


    // Constructor
    public UI() {
        initializeUI();
    }

    // Initialize the domain.main JFrame and CardLayout
    private void initializeUI() {
        frame = new JFrame("2D Game");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add only the home screen initially
        mainPanel.add(createHomeScreen(), "Home");

        // Add the domain.main panel to the frame
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 640);
        frame.setLocationRelativeTo(null);
        // frame.setResizable(false); // Change if needed

        // Set custom colors for message dialogs
        UIManager.put("OptionPane.background", new Color(50, 56, 66)); // Dark gray
        UIManager.put("Panel.background", new Color(50, 56, 66));      // Match background
        UIManager.put("OptionPane.messageForeground", Color.WHITE); // White text
    }

    /*
        * Show the domain.main JFrame and the home screen
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
                case "LoadedGame":
                    mainPanel.add(gameScreen, "LoadedGame");
                    break;
                case "Build":
                    mainPanel.add(createBuildScreen(), "Build");
                    break;
                case "Help":
                    mainPanel.add(createHelpScreen(), "Help");
                    break;
                case "LoadStart":
                    mainPanel.add(createLoadStartScreen(), "LoadStart");
                    break;
                case "Load":
                    mainPanel.add(createLoadScreen(), "Load");
                    break;
            }
        }
        cardLayout.show(mainPanel, panelName); // Show the panel with the given name
        mainPanel.revalidate(); // Revalidate the domain.main panel
        mainPanel.repaint(); // Repaint the domain.main panel
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
        // Create a JPanel as the domain.main container for the Build Mode screen

        buildScreen = new BuildModeScreenPanel(this); // Pass the current UI instance
        halls = buildScreen.getBuildModePanel().getHalls(); // Retrieve the halls from BuildModePanel

        return buildScreen;
    }


    /*
        * Create the game screen with the PlayModePanel
     */
    private JPanel createGameScreen() {
        gameScreen = new GameScreenPanel(this); // Pass the current UI instance
        return gameScreen;
    }

    /*
        * Create the loaded game screen with the PlayModePanel
     */
    public void createLoadedGame(GameState loadedGame) {
        gameScreen = new GameScreenPanel(this, loadedGame); // Pass the current UI instance
    }

    /*
        * Create the help screen panel with instructions
     */
    private JPanel createHelpScreen() {
        helpScreen =  new HelpScreenPanel(this); // Pass the current UI instance
        return helpScreen;
    }

    /*
        * Create the save start screen panel with instructions
     */
    private JPanel createLoadStartScreen() {
        loadStartScreen = new loadStartScreenPanel(this); // Pass the current UI instance
        return loadStartScreen;
    }

    /*
        * Create the load screen panel with instructions
     */
    private JPanel createLoadScreen() {
        return new LoadScreenPanel(this); // Pass the current
    }

    /*
        * Check if a panel with the given name is already added to the domain.main panel
     */
    private boolean isPanelAdded(String panelName) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(panelName)) {
                return true; // Check the name of the component
            }
        }
        return false;
    }

    // Getters for screen panels
    public BuildModeScreenPanel getBuildScreen() {
        return buildScreen;
    }

    public GameScreenPanel getGameScreen() {
        return gameScreen;
    }

    public HelpScreenPanel getHelpScreen() {
        return helpScreen;
    }
}