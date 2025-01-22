package TheDialogue;

import TheDialogue.DialogueNode;
import TheDialogue.DialogueResponse;
import TheDialogue.DialogueScreen;

import javax.swing.*;

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
        frame.setSize(780, 600);
        frame.add(dialogueScreen);  // Add the DialogueScreen to the JFrame
        frame.setVisible(true);  // Make the frame visible
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
    }

    public void startDialogue() {
        // Show the initial dialogue
        dialogueScreen.addWiseDialogue(currentNode.getNpcDialogue());

        // Add button functionality for responses
        List<DialogueResponse> responses = currentNode.getResponses();
        dialogueScreen.button1.setText(responses.get(0).getPlayerChoice());
        dialogueScreen.button2.setText(responses.get(1).getPlayerChoice());

        // Add action listeners to buttons
        dialogueScreen.button1.addActionListener(e -> {
            currentNode = responses.get(0).getNextNode();
            startDialogue();
        });

        dialogueScreen.button2.addActionListener(e -> {
            currentNode = responses.get(1).getNextNode();
            startDialogue();
        });
    }


}
