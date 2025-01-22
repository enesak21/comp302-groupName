package domain.TheDialogue;

public class DialogueResponse {
    private String playerChoice;
    private DialogueNode nextNode;

    public DialogueResponse(String playerChoice, DialogueNode nextNode) {
        this.playerChoice = playerChoice;
        this.nextNode = nextNode;
    }

    public String getPlayerChoice() {
        return playerChoice;
    }

    public DialogueNode getNextNode() {
        return nextNode;
    }
}
