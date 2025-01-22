package domain.TheDialogue;

import java.util.ArrayList;

public class DialogueSetup {

    public static DialogueManager createInitialDialogue() {
        // A Final
        ArrayList<DialogueResponse> responsesFinal = new ArrayList<>();
        DialogueResponse responseFinal1 = new DialogueResponse("I’ll keep that in mind. Thank you for the warning.", null);
        DialogueResponse responseFinal2 = new DialogueResponse("I’ll be careful. Thank you for the advice.", null);
        responsesFinal.add(responseFinal1);
        responsesFinal.add(responseFinal2);

        // Q3
        DialogueNode node3x1 = new DialogueNode("Four? That's... unusual. Such power doesn't appear without purpose. Be cautious, these artifacts might be connected to something much darker.", responsesFinal);
        DialogueNode node3x2 = new DialogueNode("I see. The unknown is never easy to face. Those things might be more than just visions; they could be warnings.", responsesFinal);

        DialogueNode node3x3 = new DialogueNode("I believe you. But in these lands, even the most peaceful travelers must be prepared for what lurks in the shadows.", responsesFinal);
        DialogueNode node3x4 = new DialogueNode("Guardian, perhaps. But more of a watcher, ensuring that those who wander don’t disturb what should remain undisturbed.", responsesFinal);


        // A2
        ArrayList<DialogueResponse> responses2x1 = new ArrayList<>();
        DialogueResponse response2x1 = new DialogueResponse("I came across four strange artifacts. Each one was glowing and pulsating with energy.", node3x1);
        DialogueResponse response2x2 = new DialogueResponse("I saw... things. Things that I can’t explain. I don’t know what they were.", node3x2);
        responses2x1.add(response2x1);
        responses2x1.add(response2x2);

        ArrayList<DialogueResponse> responses2x2 = new ArrayList<>();
        DialogueResponse response2x3 = new DialogueResponse("I’m just a traveler passing through. I don’t mean any harm.", node3x3);
        DialogueResponse response2x4 = new DialogueResponse("Oh? So, are you saying you're some kind of guardian of these places?", node3x4);
        responses2x2.add(response2x3);
        responses2x2.add(response2x4);

        // Q2
        DialogueNode node2x1 = new DialogueNode("It has claimed many before you. What did you find in there? What did you see?", responses2x1);
        DialogueNode node2x2 = new DialogueNode("A fair question. I’m Arwen. But perhaps it’s more important to ask—who are you to be wandering these dangerous lands alone?", responses2x2);

        // A1
        ArrayList<DialogueResponse> responses1 = new ArrayList<>();
        DialogueResponse response1x1 = new DialogueResponse("It was a close call. The cave... it’s not as empty as it seems.", node2x1);
        DialogueResponse response1x2 = new DialogueResponse("...Who the heck are you?", node2x2);
        responses1.add(response1x1);
        responses1.add(response1x2);

        // Q1
        DialogueNode startingNode = new DialogueNode("You... You survived. I was starting to think no one would return from that cursed place.", responses1);

        DialogueManager dialogueManager = new DialogueManager(startingNode);

        return dialogueManager;
    }

}
