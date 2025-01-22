package domain.TheDialogue;

import java.util.List;

public class DialogueNode {
    private String npcDialogue;
    private List<DialogueResponse> responses;

    public DialogueNode(String npcDialogue, List<DialogueResponse> responses) {
        this.npcDialogue = npcDialogue;
        this.responses = responses;
    }

    public String getNpcDialogue() {
        return npcDialogue;
    }

    public List<DialogueResponse> getResponses() {
        return responses;
    }
}
