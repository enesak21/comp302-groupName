package domain.theOutsideGame.animation;

public class WalkAnimationPlayer extends WalkAnimation {

    public WalkAnimationPlayer(boolean isMovingLeft) {
        String[] walkFrames;

        if (isMovingLeft) {
            walkFrames = new String[] {
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL1.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL2.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL3.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL4.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL5.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL6.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL7.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL8.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayerL9.png"
            };
        } else {
            walkFrames = new String[] {
                    "src/domain/theOutsideGame/resources/player/walkingPlayer1.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer2.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer3.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer4.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer5.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer6.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer7.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer8.png",
                    "src/domain/theOutsideGame/resources/player/walkingPlayer9.png"
            };
        }
        super(walkFrames, 10);  // Initialize with correct frames and frame duration
    }
}
