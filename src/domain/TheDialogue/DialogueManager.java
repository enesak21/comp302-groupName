package domain.TheDialogue;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.List;

public class DialogueManager {
    private DialogueNode currentNode;
    private DialogueScreen dialogueScreen;
    private JFrame frame;

    public DialogueManager(DialogueNode startingNode) {
        this.currentNode = startingNode;
        this.dialogueScreen = new DialogueScreen();

        // Set up JFrame
        frame = new JFrame("Dialogue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 640);
        frame.add(dialogueScreen);  // Add the DialogueScreen to the JFrame
        frame.setVisible(true);  // Make the frame visible
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);

        startDialogue();
    }

    public void startDialogue() {
        // Show the initial dialogue
        dialogueScreen.addWiseDialogue(currentNode.getNpcDialogue());

        // Add button functionality for responses
        List<DialogueResponse> responses = currentNode.getResponses();
        dialogueScreen.button1.setText(responses.get(0).getPlayerChoice());
        dialogueScreen.button2.setText(responses.get(1).getPlayerChoice());

        // Remove action listeners before adding new ones
        for (ActionListener listener : dialogueScreen.button1.getActionListeners()) {
            dialogueScreen.button1.removeActionListener(listener);
        }
        for (ActionListener listener : dialogueScreen.button2.getActionListeners()) {
            dialogueScreen.button2.removeActionListener(listener);
        }

        // Add action listeners to buttons
        dialogueScreen.button1.addActionListener(e -> {
            handleResponse(responses.get(0));
        });

        dialogueScreen.button2.addActionListener(e -> {
            handleResponse(responses.get(1));
        });
    }

    // Handle responses and close screen if it's a final response
    private void handleResponse(DialogueResponse response) {
        if (response.getNextNode() == null) {
            // If nextNode is null, it's a final response, so close the screen
            frame.dispose();  // Close the JFrame
        } else {
            dialogueScreen.addPlayerDialogue(response.getPlayerChoice());
            // Otherwise, continue with the dialogue
            currentNode = response.getNextNode();
            startDialogue();
        }
    }
}

