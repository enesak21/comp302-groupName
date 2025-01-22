package TheDialogue;

import java.util.List;
import java.util.ArrayList;

public class DialogueSetup {

    public static DialogueManager createInitialDialogue() {
        // Create the dialogue responses manually with ArrayList
        List<DialogueResponse> responses1 = new ArrayList<>();
        responses1.add(new DialogueResponse("I plan to go for a walk.", new DialogueNode("Enjoy the walk!", new ArrayList<>())));
        responses1.add(new DialogueResponse("I will rest for a while.", new DialogueNode("Good idea, rest well.", new ArrayList<>())));

        List<DialogueResponse> responses2 = new ArrayList<>();
        responses2.add(new DialogueResponse("I'm good", new DialogueNode("Great to hear!", new ArrayList<>())));
        responses2.add(new DialogueResponse("Not so good", new DialogueNode("Sorry to hear that.", new ArrayList<>())));

        // Starting dialogue node
        DialogueNode startingNode = new DialogueNode("Hello, Player! How are you?", responses2);

        // Create and return a DialogueManager with the starting node
        return new DialogueManager(startingNode);
    }
}
