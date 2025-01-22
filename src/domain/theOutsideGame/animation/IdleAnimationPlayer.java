package domain.theOutsideGame.animation;

public class IdleAnimationPlayer extends WalkAnimation {

    public IdleAnimationPlayer() {
        super(new String[] {
                "src/domain/theOutsideGame/resources/player/idlePlayer1.png",
                "src/domain/theOutsideGame/resources/player/idlePlayer2.png",
                "src/domain/theOutsideGame/resources/player/idlePlayer3.png"
        }, 200); // Right idle animation by default
    }

    public IdleAnimationPlayer(boolean isLeft) {
        super(isLeft ?
                new String[] {
                        "src/domain/theOutsideGame/resources/player/idlePlayerL1.png",
                } :
                new String[] {
                        "src/domain/theOutsideGame/resources/player/idlePlayer1.png",

                }, 200);  // Left or right idle animation
    }
}
